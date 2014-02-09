package Workshop;
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
		//app.sendFile();
		if(display instanceof GUIDisplay)
		{
		}
		else
		{
			app.recordInstructions();
			app.writeSavedInstructionsInSavefile();
		}
	}
}
