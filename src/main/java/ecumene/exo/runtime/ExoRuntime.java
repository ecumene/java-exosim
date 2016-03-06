package ecumene.exo.runtime;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import ecumene.exo.sim.abstractions.galaxy.ExoGalaxyMap;
import ecumene.exo.sim.abstractions.galaxy.gen.ExoGalaxyMapGen;
import ecumene.exo.sim.abstractions.planet.ExoPlanetMap;
import ecumene.exo.sim.abstractions.planet.TrackingParameters;
import ecumene.exo.sim.abstractions.planet.gen.ExoPlanetMapBuilder;
import ecumene.exo.sim.abstractions.solar.gen.ExoSolarMapBuilder;
import ecumene.exo.sim.abstractions.solar.ExoSolarMap;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureFilter;
import ecumene.exo.sim.abstractions.surface.ExoSFeatureLayer;
import ecumene.exo.sim.abstractions.surface.ExoSurfaceMap;
import ecumene.exo.sim.abstractions.surface.exampleFeature.ExoSFExample;
import ecumene.exo.sim.abstractions.surface.exampleFeature.ExoSFilterExampleMoveRight;
import ecumene.exo.sim.abstractions.surface.exampleFeature.ExoSLExample;
import ecumene.exo.view.rmap.planet.RMVPlanetViewerTag;
import ecumene.exo.view.rmap.surface.SMVSurfaceViewer;
import ecumene.exo.view.rmap.surface.SMVSurfaceViewerTag;
import ecumene.exo.view.rmap.surface.feature.exampleFeature.SMVExampleConfig;
import org.apache.commons.cli.ParseException;

import ecumene.exo.analyze.ExoRuntimeAnalyzerTag;
import ecumene.exo.sim.SimContext;
import ecumene.exo.view.IViewerTag;
import ecumene.exo.view.ViewerRunnable;
import ecumene.exo.view.rmap.galaxy.RMVGalaxyViewerTag;
import ecumene.exo.view.rmap.solar.RMVSolarViewerTag;
import org.joml.Vector2f;

public class ExoRuntime implements Runnable{
	
	public  ExoArgParse          commands;
	private JFrame               frame;
	private List<ViewerRunnable> viewers;
	private IViewerTag[]         viewerDB;
	private ExecutorService      viewerExec;
	private ExceptionListener    exceptionListener;
	private SimContext           context;
	
	public static ExoRuntime INSTANCE;
	
	public ExoRuntime(String[] arguments) throws ParseException, IOException {
		commands  = new ExoArgParse(arguments);
		viewerExec = Executors.newCachedThreadPool();
		viewers = new ArrayList<ViewerRunnable>();
		
		exceptionListener = new ExceptionListener() {
			@Override
			public void exceptionThrown(Exception e) {
				System.out.println(); e.printStackTrace(); //New line + print st
			}
		};

		// In the future, all these builders will be accompanied by XML or .prop files containing
		// exact data on simulation stuffs. This will allow you to "build from file" and essentially
		// enter in a few values.
		// I was planning to make different files showing stuff from movies like interstellar and the martian
		// ~~ IT'S GONNA BE LIT ~~

		// I'll make a builder for this... eventually.
		ExoGalaxyMap galaxy = new ExoGalaxyMapGen(System.currentTimeMillis()).genGalaxy(1, 2, 1, 100, 400).getSource();

		ExoSolarMapBuilder solarBuilder = new ExoSolarMapBuilder(System.currentTimeMillis());
		solarBuilder.genObject(    new Vector2f(4, 4), new Vector2f(0, 0), new Vector2f(0, 0)); // Generate sun
		solarBuilder.genObjectsOrbiting(9,     // Number of orbiters
				new Vector2f(0,0),             // Point to orbit
				new Vector2f(0.001f, 0.0015f), // Orbiter Masses
				new Vector2f(-500, 500),       // Orbiter Position (polar coordinate radius)
				new Vector2f(0, 360),          // Orbiter angle    (polar coordinate angle)
				new Vector2f(0.01f, 0.025f));  // Initial velocity (0 will cause the orbiters to go DIRECTLY towards the sun and die a horrible death)
		// solarBuilder.genObjects(9, new Vector2f(0.001f, 0.0015f), new Vector2f(-500, 500), new Vector2f(-0.25f, 0.25f)); // Generate planets
		ExoSolarMap solar = solarBuilder.build();

		//                                                          Set seed to current time
		ExoPlanetMapBuilder planetBuilder = new ExoPlanetMapBuilder(System.currentTimeMillis());
		planetBuilder.genPlanet(new Vector2f(2, 3)); // Generate planet within mass range of X and Y
		planetBuilder.genMoons(1,                    // Generate # of moons
				new Vector2f(0.5f, 1),               // Generate moon mass
				new Vector2f(50, 200),               // Generate moon diameter from planet
				new Vector2f(0, 360),                // Generate moon angle
				new Vector2f(-0.15f, 0.15f));        // Generate moon beginning velocity
		ExoPlanetMap planet = planetBuilder.build(); // Finish generating and save to exoplanet
		planet.getPlanet().setTracking(0, new TrackingParameters("0xFF00FF", 100, false));// tracking data for moons

		// This should be in a builder!! GIMMIE A MINUET!
		List<ExoSFeatureLayer> featureLayers = new ArrayList<ExoSFeatureLayer>();
		List<ExoSFeatureFilter> featureFilters = new ArrayList<ExoSFeatureFilter>();

		featureLayers.add(new ExoSLExample());
		featureFilters.add(new ExoSFilterExampleMoveRight((ExoSLExample) featureLayers.get(0)));

		ExoSurfaceMap surface = new ExoSurfaceMap(featureLayers,
				                                  featureFilters);

		// Finish generating, save to context for simulation
		context = new SimContext(galaxy, solar, planet, surface);
		
		viewerDB = new IViewerTag[5];
		viewerDB[0] = new ExoRuntimeAnalyzerTag();
		viewerDB[1] = new RMVGalaxyViewerTag();
		viewerDB[2] = new RMVSolarViewerTag();
		viewerDB[3] = new RMVPlanetViewerTag();
		viewerDB[4] = new SMVSurfaceViewerTag(new SMVExampleConfig());
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
					int chosen = Integer.parseInt(upperLineWords[1]);                                                                                                               // Parse second num as int
					if(chosen >= viewerDB.length) throw new IllegalArgumentException("Chosen int larger than runnables");                                                           // Is that num illegal?
					ViewerRunnable runnable = (ViewerRunnable) viewerDB[chosen].construct(chosen, exceptionListener, Arrays.copyOfRange(upperLineWords, 1, upperLineWords.length)); // Choose a runnable from num
					System.out.println("Running " + chosen);                                                                                                                        // We're running that now!
					viewerExec.submit(runnable);                                                                                                                                    // Submit runnable to exec. service
					viewers.add(runnable);                                                                                                                                          // Add runnable
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
		boolean parsingRTCommand = commands.getRTCommands() == null ? false : (commands.getRTCommands().length == 0 ? false : true);
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
	
	public SimContext getContext(){
		return context;
	}
	
	public void setContext(SimContext context){
		for(int i = 0; i < viewers.size(); i++)
			viewers.get(i).onContextChanged(context);
	}
	
	public void step(){
		for(int i = 0; i < viewers.size(); i++)
			viewers.get(i)
			.onStep(context, 
					context.getSteps());
	}
	
	public IViewerTag[] getRunnables(){
		return viewerDB;
	}

	public static void main(String[] args) throws ParseException, IOException {
		INSTANCE = new ExoRuntime(args);
		INSTANCE.run();
		System.exit(0);
	}
}
