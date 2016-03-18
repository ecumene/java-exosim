package ecumene.exo.runtime;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ecumene.exo.sim.util.UnClosableDecorator;
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
public class ExoRuntime implements Runnable{

	/**CLI Parser*/
	public  ExoArgParse          commands;         // Object in control of CLI inputs
	private List<ViewerRunnable> viewers;          // List of viewers (things that view the simulation)
	private IViewerTag[]         viewerDB;         // List of running viewers
	private ExecutorService      viewerExec;       // Multi-threaded viewer executor
	private ExceptionListener    exceptionListener;// Univeral thingy for catching any exception
	private SimContext           context;          // Context (container) for all simulations

	/**Public instance of exo-runtime*/
	public static ExoRuntime INSTANCE;

	/**
	 * Creates the exo-runtime and builds simulation objects
	 * @param arguments       Input arguments (CLI)
	 * @param context         Simulation context
	 * @throws ParseException due to apache CLI
	 * @throws IOException    due to loading any io
     */
	public ExoRuntime(String[] arguments, SimContext context) throws ParseException, IOException {
		commands  = new ExoArgParse(arguments);
		viewerExec = Executors.newCachedThreadPool();
		viewers = new ArrayList<ViewerRunnable>();

		//This allows for anything to go wrong in any thread and still report back!
		exceptionListener = (Exception e) -> { System.out.println(); e.printStackTrace(); };

		this.context = context;

		viewerDB = new IViewerTag[5];
		viewerDB[0] = new RuntimeManagerTag();
		viewerDB[1] = new RMVGalaxyViewerTag();
		viewerDB[2] = new RMVSolarViewerTag();
		viewerDB[3] = new RMVPlanetViewerTag();
		viewerDB[4] = new SMVSurfaceViewerTag(new SMVGenericConfig());
	}
	
	private boolean scanLine = false;
	private void parseCommand(String input) throws Throwable{
		String upperCommand = input.toUpperCase();
		String[] upperLineWords = upperCommand.split(" ");
		if(upperCommand.equals("WHATDO")){
			System.out.println("---------WHATDO-EXOSIM----------");
			System.out.println("exit             -> Exit");
			System.out.println("run  [id] [args] -> Runs runnable (id)");
			System.out.println("ran              -> Lists running");
			System.out.println("stop [id]        -> Stops runnable (id)");
			System.out.println("--------------------------------");
		} else if(upperLineWords[0].equals("RUN")){                 // Run
			if(viewerDB.length == 0) System.out.println("Runables Empty...");
			else {
				if(upperLineWords.length == 1){                     // Run a runnable (upperLineWords[1] = runnable ID)
					System.out.println("Use: run [runnable] [args]");
					System.out.println("---------RUNNABLES----------");
					for(int i = 0; i < viewerDB.length; i++){      // Iterate all runnables
						System.out.println(i + ": " + viewerDB[i].getIdentifier());
					}
					System.out.println("----------------------------");
				} else {
					int chosen = Integer.parseInt(upperLineWords[1]);                                                     // Parse second num as int
					if(chosen >= viewerDB.length) throw new IllegalArgumentException("Chosen int larger than runnables"); // Is that num illegal?
					runViewer(chosen, Arrays.copyOfRange(upperLineWords, 1, upperLineWords.length));
				}
			}
		} else if(upperLineWords[0].equals("EXIT")){
			System.out.println("Exiting Exo Sim...");                                                                      // Exiting!
			scanLine = false;                                                                                              // Stop scanning!
			System.exit(0);
		} else if(upperLineWords[0].equals("RAN")) {
			System.out.println("-----------RUNNING----------");                                                            // List running
			if(viewers.size() == 0) System.out.println("None running...");
			else for(int i = 0; i < viewers.size(); i++){                                                                  // Iterate all running
				System.out.println("Runnable ID: " + i + " Running ID: " + viewers.get(i).getID());                        // Runnable ID: X Running ID: X
			}
			System.out.println("----------------------------");
	    } else if(upperLineWords[0].equals("STOP")){                                                                       // Stop a runnable
			int chosen = Integer.parseInt(upperLineWords[1]);                                                              // Parse chosen runnable
			if(chosen >= viewers.size()) throw new IllegalArgumentException("Chosen int larger than runnables");	   	   // Report if chosen int is too big
			if(viewers.get(chosen) == null) throw new IllegalArgumentException("Thread " + chosen + " is already kill!");  // Report if chosen isn't running
			viewers.get(chosen).kill(chosen);                                                                                                               
			viewers.remove(chosen);
		} else {
			if(upperCommand.trim().length() > 0) System.out.println("Unknown command '" + upperLineWords[0] + "'");
		}
		System.out.print("->");
	}
	
	@Override
	public void run(){
		System.out.println("Current Working Directory: \"" + commands.getPWD() + "\"");
		System.out.println("For what do? type 'whatdo'");
		Scanner scanner = new Scanner(new UnClosableDecorator(System.in));
		System.out.print("->");
		boolean parsingRTCommand = commands.getRTCommands()!=null && (commands.getRTCommands().length != 0);
		scanLine                 = true;
		
		if(parsingRTCommand)
			for(int i = 0; i < commands.getRTCommands().length; i++){
				try{
					parseCommand(commands.getRTCommands()[i]);
				}catch(Throwable e){
					e.printStackTrace();
					System.out.println("Skipping line " + commands.getRTCommands()[i]);
					continue;
				}		
			}
		while(scanner.hasNextLine() || !scanLine){
			String nextLine = scanner.nextLine();
			try{
				parseCommand(nextLine);
			}catch(Throwable e){
				e.printStackTrace();
				System.out.println("Skipping line " + nextLine);
				continue;
			}		
		}
		scanner.close();
		viewerExec.shutdown();
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
		ViewerRunnable runnable = (ViewerRunnable) viewerDB[id].construct(id, exceptionListener, upperArguments); // Choose a runnable from num
		System.out.println("Running " + id);                                                                      // We're running that now!
		viewerExec.submit(runnable);                                                                              // Submit runnable to exec. service
		viewers.add(runnable);                                                                                    // Add runnable
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
