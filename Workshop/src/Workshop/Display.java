package Workshop;
/**
 * Interface who define general methods for display the application
 * 
 * @author clement
 * 
 */
public interface Display {

	/**
	 * Display an Instruction tab, used to display only the instructions
	 * supported by the cube
	 * 
	 * @param inst
	 *            Instruction tab to display
	 */
	public void displayChoiceOfInstruction(Instruction[] inst);

	/**
	 * Display a string with '\n' character at the end
	 * 
	 * @param str
	 *            The string to display
	 */
	public void println(String str);

	/**
	 * Display a string
	 * 
	 * @param str
	 *            The string to display
	 */
	public void print(String str);
	
	/**
	 * Set the application polling associated to the display
	 * the display use it to request the display or the modification of objects contained in the class who implements the interface ApplicationPolling
	 * @param poll The ApplicationPolling
	 */
	public void setApplicationPolling(ApplicationPolling poll);
	
	/**
	 * Display a buffer of instructions
	 * @param inst The buffer of instruction
	 * @param countInstructions The number of instructions
	 */
	public void displayBuffer(Instruction[] inst,int countInstructions);
	
}
