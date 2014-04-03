package Workshop;

import java.math.BigDecimal;
import java.math.BigInteger;

import gui.GUIDisplay;

/**
 * Application launcher (main class)
 * 
 * @author Clément
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
		double d = 9223372036854775808D;
	}
}
