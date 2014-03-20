package Workshop;
import java.io.File;

import gui.GUIDisplay;
import gui.GuiChoiceAsker;

/**
 * Application launcher (main class)
 * 
 * @author clement
 * 
 */
public class Workshop {

	/**
	 * Main class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		

		GuiChoiceAsker choice = new GuiChoiceAsker();
		GUIDisplay display = new GUIDisplay(choice);
		
		//ConsoleDisplay display = new ConsoleDisplay();
		//ScannerChoiceAsker choice = new ScannerChoiceAsker();

		Application app = new Application(display, choice);
		File file = new File("instructions.bin");
		//app.sendFile(file);
		if(!(display instanceof GUIDisplay))
		{
			app.recordInstructions();
			//app.writeSavedInstructionsInSavefile();
		}
	}
}
