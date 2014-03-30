package Workshop;

import gui.GUIDisplay;

/**
 * Application launcher (main class)
 * 
 * @author Cl�ment
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
		new Application(display);
	}
}
