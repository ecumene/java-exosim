package ecumene.exo;

import java.beans.ExceptionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import org.apache.commons.cli.ParseException;

import ecumene.exo.analyze.ExoRuntimeAnalyzer;
import ecumene.exo.analyze.ExoRuntimeAnalyzerTag;
import ecumene.exo.impl.ImplExoRunnableTag;
import ecumene.exo.sim.galaxy.ExoGalaxyMapTag;
import ecumene.exo.sim.map.real.example.ExoExampleRMapRendererTag;

public class ExoRuntime implements Runnable{
	public  ExoArgParse        commands;
	private JFrame             frame;
	private List<ExoRunnable>  running;
	private IExoRunnableTag[]  runnables;
	private ExecutorService    runnableExec;
	private ExceptionListener  exceptionListener;
	
	public static ExoRuntime INSTANCE;
	
	public ExoRuntime(String[] arguments) throws ParseException, IOException {
		commands  = new ExoArgParse(arguments);
		runnableExec = Executors.newCachedThreadPool();
		running = new ArrayList<ExoRunnable>();
		
		exceptionListener = new ExceptionListener() {
			@Override
			public void exceptionThrown(Exception e) {
				e.printStackTrace();
			}
		};

		runnables = new IExoRunnableTag[4];
		runnables[0] = new ExoRuntimeAnalyzerTag();
		runnables[1] = new ExoGalaxyMapTag(System.currentTimeMillis());
		runnables[2] = new ImplExoRunnableTag("Identifier");
		runnables[3] = new ImplExoRunnableTag("Identifier");
	}
	
	@Override
	public void run(){
		System.out.println("Current Working Directory: \"" + commands.getPWD() + "\"");
		System.out.println("For what do? type 'whatdo'");
		Scanner scanner = new Scanner(new UnClosableDecorator(System.in));
		System.out.print("->");
		while(scanner.hasNextLine()){
			String upperLine = scanner.nextLine().toUpperCase();
			try{
				String[] upperLineWords = upperLine.split(" ");
				if(upperLine.equals("WHATDO")){
					System.out.println("---------WHATDO-EXOSIM----------");
					System.out.println("exit             -> Exit");
					System.out.println("run  [id] [args] -> Runs runnable (id)");
					System.out.println("ran              -> Lists running");
					System.out.println("stop [id]        -> Stops runnable (id)");
					System.out.println("--------------------------------");
				} else if(upperLineWords[0].equals("RUN")){                 // Run
					if(runnables.length == 0) System.out.println("Runables Empty...");
					else {
						if(upperLineWords.length == 1){                     // Run a runnable please! (upperLineWords[1] = runnable ID)
							System.out.println("Use: run [runnable] [args]");
							System.out.println("---------RUNNABLES----------");
							for(int i = 0; i < runnables.length; i++){      // Iterate all runnables
								System.out.println(i + ": " + runnables[i].getIdentifier());
							}
							System.out.println("----------------------------");
						} else {
							int chosen = Integer.parseInt(upperLineWords[1]);                                                                                                          // Parse second num as int
							if(chosen >= runnables.length) throw new IllegalArgumentException("Chosen int larger than runnables");                                                     // Is that num illegal?
							ExoRunnable runnable = (ExoRunnable) runnables[chosen].construct(chosen, exceptionListener, Arrays.copyOfRange(upperLineWords, 1, upperLineWords.length)); // Choose a runnable from num
							System.out.println("Running " + chosen);                                                                                                                   // We're running that now!
							runnableExec.submit(runnable);                                                                                                                             // Submit runnable to exec. service
							running.add(runnable);                                                                                                                                     // Add runnable
						}
					}
				} else if(upperLineWords[0].equals("EXIT")){
					System.out.println("Exiting Exo Sim...");                                                                                                       // Exiting!
					break;                                      																									// break while loop
				} else if(upperLineWords[0].equals("RAN")) {
					System.out.println("-----------RUNNING----------");                                                                                             // List running
					if(running.size() == 0) System.out.println("None running...");
					else for(int i = 0; i < running.size(); i++){                                                                                                   // Iterate all running
						System.out.println("Runnable ID: " + i + " Running ID: " + running.get(i).getID());                                                         // Runnable ID: X Running ID: X 
					}
					System.out.println("----------------------------");
			    } else if(upperLineWords[0].equals("STOP")){                                                                                                        // Stop a runnable
					int chosen = Integer.parseInt(upperLineWords[1]);                                                                                               // Parse chosen runnable
					if(chosen >= running.size()) throw new IllegalArgumentException("Chosen int larger than runnables");											// Report if chosen int is too big
					if(running.get(chosen) == null) throw new IllegalArgumentException("Thread " + chosen + " is already kill!");									// Report if chosen isn't running
					running.get(chosen).kill(chosen);                                                                                                               
					running.remove(chosen);
				} else {
					if(upperLine.trim().length() > 0) System.out.println("Unknown command '" + upperLineWords[0] + "'");
				}
				System.out.print("->");
			}catch(Throwable e){
				e.printStackTrace();
				System.out.println("Skipping line " + upperLine);
				continue;
			}
		}
		scanner.close();
		runnableExec.shutdown();
	}
	
	public IExoRunnableTag[] getRunnables(){
		return runnables;
	}

	public static void main(String[] args) throws ParseException, IOException {
		INSTANCE = new ExoRuntime(args);
		INSTANCE.run();
	}
}
