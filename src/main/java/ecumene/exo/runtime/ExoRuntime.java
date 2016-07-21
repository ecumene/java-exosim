package ecumene.exo.runtime;

import java.awt.*;
import java.beans.ExceptionListener;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ecumene.exo.runtime.workspace.ExoWorkspace;
import ecumene.exo.utils.UnClosableDecorator;
import ecumene.exo.view.rmap.planet.RMVPlanetViewerTag;
import ecumene.exo.view.rmap.surface.SMVSurfaceViewerTag;
import ecumene.exo.view.rmap.surface.feature.SMVGenericConfig;
import org.apache.commons.cli.ParseException;

import ecumene.exo.view.runtime.RuntimeManagerTag;
import ecumene.exo.sim.SimContext;
import ecumene.exo.runtime.viewer.IViewerTag;
import ecumene.exo.runtime.viewer.ViewerRunnable;
import ecumene.exo.view.rmap.galaxy.RMVGalaxyViewerTag;
import ecumene.exo.view.rmap.solar.RMVSolarViewerTag;

/**
 * ExoRuntime is the class that contains all the bootstrapping and initial building of the application.
 * It includes code for two things: Building simulation objects, and parsing CLI commands.
 */
public class ExoRuntime {

	/**CLI Parser*/
	public  ExoArgParse          commands;         // XMLObject in control of CLI inputs
	private List<ViewerRunnable> viewers;          // List of viewers (things that view the simulation)
	private IViewerTag[]         viewerDB;         // List of running viewers
	private ExecutorService      viewerExec;       // Multi-threaded viewer executor
	private ExceptionListener    exceptionListener;// Univeral thingy for catching any exception

	// Configurations
	private SimContext           context;          // Context (container) for all simulations
	private ExoWorkspace         workspace;        // The loaded workspace file
	private boolean              running = true;

	/**Public instance of ecumene-runtime*/
	public static ExoRuntime INSTANCE;

	/**
	 * Creates the ecumene-runtime and builds simulation objects
	 * @param arguments       Input arguments (CLI)
	 * @param context         Simulation context
	 * @throws ParseException due to apache CLI
	 * @throws IOException    due to loading any io
     */
	public ExoRuntime(ExoArgParse arguments, SimContext context) throws Throwable {
		commands = arguments;
		viewerExec = Executors.newCachedThreadPool();
		viewers = new ArrayList<>();

		//This allows for anything to go wrong in any thread and still report back!
		exceptionListener = (Exception e) -> { System.out.println(); e.printStackTrace(); };

		this.context = context;

		if(commands.getWorkspace() != null) workspace = ExoWorkspace.loadWorkspace(commands.getWorkspace());
		else                                workspace = ExoWorkspace.loadWorkspace(new File("examples/workspace.xml"));
	}

	private boolean scanLine = false;

	public void run() throws Throwable {
		viewerDB = new IViewerTag[5];
		viewerDB[0] = new RuntimeManagerTag();
		viewerDB[1] = new RMVGalaxyViewerTag();
		viewerDB[2] = new RMVSolarViewerTag();
		viewerDB[3] = new RMVPlanetViewerTag();
		viewerDB[4] = new SMVSurfaceViewerTag(new SMVGenericConfig());

		for(IViewerTag tag : viewerDB) tag.parseWorkspace(workspace);

		while(running) { }

		viewerExec.shutdown();
	}

	public ExoWorkspace getWorkspace() {
		return workspace;
	}

	public ExceptionListener getExceptionListener() {
		return exceptionListener;
	}

	/** @return The public runtime instance */
	public SimContext getContext(){
		return context;
	}

	/**@param context The sim object to set */
	public void setContext(SimContext context){
		// Yes, it CAN be replaced by a foreach, if you want it to crash on a viewer creating another viewer
		// Intellij in it's entirety is a 'necessary evil'
		for(int i = 0; i < viewers.size(); i++)
			viewers.get(i).onContextChanged(context);
	}

	public void runViewer(int id, String[] upperArguments) throws Throwable {
		System.out.println("Opening viewer #" + id + " (" + viewerDB[id].getClass().getName() + ") with arguments: " + Arrays.toString(upperArguments));
		ViewerRunnable runnable = (ViewerRunnable) viewerDB[id].construct(id, exceptionListener, upperArguments); // Choose a runnable from num
		viewerExec.submit(runnable);                                                                              // Submit runnable to exec. service
		viewers.add(runnable);                                                                                    // Add runnable
	}

	public void exit(){
		running = false;
		System.out.println("Exiting exosim ... ");
		System.exit(0);
	}

	/** Steps the simulation once */
	public void step(){
		// Yes, it CAN be replaced by a foreach, if you want it to crash on a viewer creating another viewer
		// Intellij in it's entirety is a 'necessary evil'
		for(int i = 0; i < viewers.size(); i++)
			viewers.get(i).onStep(context, context.getSteps());
	}

	/** @return The currently running viewers */
	public IViewerTag[] getRunnables(){
		return viewerDB;
	}
}
