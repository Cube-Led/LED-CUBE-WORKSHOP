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
		
		GUIDisplay display = new GUIDisplay();
		GuiChoiceAsker choice = new GuiChoiceAsker();
		
		//ConsoleDisplay display = new ConsoleDisplay();
		//ScannerChoiceAsker choice = new ScannerChoiceAsker();

		Application app = new Application(display, choice);
		
		if(display instanceof GUIDisplay)
		{
			app.start();
		}
		else
		{
			app.recordInstructions();
			app.writeSavedInstructionsInSavefile();
		}
	}
}
