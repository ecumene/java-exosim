package ecumene.exo.runtime;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.cli.*;

public class ExoArgParse {
	private CommandLine cmd;          // Command line parser
	private Options options;          // Program arguments
	private final String[] arguments; // Runtime arguments
	private File pwd = new File("sim");// Primary Working Directory (default "./")
	private String[] runtimeCommands; // Commands to be run, set by the runtime arg parser
	
	public ExoArgParse(String[] arguments) throws org.apache.commons.cli.ParseException, IOException {
		this.arguments = arguments;
		System.out.println("Parsing args " + Arrays.toString(arguments));
		onParse(arguments);
	}
	
	protected void onParse(String[] arguments) throws org.apache.commons.cli.ParseException, IOException {
		CommandLineParser parser = new DefaultParser();
		try{
			cmd = parser.parse(options = constructOptions(), arguments);
		} catch (ParseException exception){
			System.out.println("Exception in command line parse " + exception.getMessage());
		}
		
		if(cmd.hasOption('h')){
			HelpFormatter f = new HelpFormatter();
			f.printHelp("exosim", options);
			System.exit(0);
		}
		if(cmd.hasOption('d')){
			pwd = new File(cmd.getOptionValue("d"));
			if(!pwd.exists()) pwd.mkdirs();
			if(!pwd.isDirectory())  throw new IllegalArgumentException("Error in opening file, must be a dir \"" + pwd.getPath() + "\"");
			else if(!pwd.canRead()) throw new IllegalArgumentException("Error in opening file, cannot read pwd \"" + pwd.getPath() + "\"");
			else {
				// In the case of the file existing, and is a dir.
				if(!getIgnoreOverride()){
					System.out.print("PWD file exists, shall we override it? [Y/n]: ");
					Scanner in = new Scanner(new UnClosableDecorator(System.in));
					while(in.hasNext()){
						String next = in.next(); 
						if(next.contains("Y") || next.contains("y")) break; else {
							System.out.println("Directory in use: " + pwd.getPath() + " exiting...");
							System.exit(0);
						}
					}
					System.out.println("Using directory " + pwd.getPath());
					in.close();
				}
				pwd.delete();
				pwd.mkdirs();
			}
		}
		if(cmd.hasOption('r')){
			System.out.println(cmd.getOptionValue("r"));
			runtimeCommands = cmd.getOptionValue("r").split(";\\s+");
		}
	}
	
	public CommandLine getCommandLine(){ return cmd; }
	public Options getDefaultOptions(){ return options; }
	public String[] getArguments(){ return arguments; }
	public String[] getRTCommands(){ return runtimeCommands; }
	public File getPWD(){ return pwd; }
	public boolean getIgnoreOverride(){ return cmd.hasOption("ignore_override"); }
	
	public static Options constructOptions(){
	    final Options opt = new Options();  
		opt.addOption("h", "help", false, "Print help for this application");
		opt.addOption("d", "dir", true, "The initial directory to use / store data in");
		opt.addOption("r", "run", true, "Run commands in quotes");
		opt.addOption("", "ignore_override", false, "Should the directories be overriden when duped");
		return opt;  
	}
	
}
