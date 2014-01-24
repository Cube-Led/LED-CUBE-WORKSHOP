import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * Application launcher (main class)
 * @author clement
 *
 */
public class Workshop {
	

	/**
	 * Main class
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Application app = new Application(new ConsoleDisplay());
		
		app.recordInstructions();
		app.displayBuffer();
		app.writeSavedInstructionsInSavefile();
	}
}
