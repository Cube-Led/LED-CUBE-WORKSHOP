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
	
	public void setUserPolling(UserPolling poll);
	
	public void displayBuffer(Instruction[] inst,int countInstructions);
	
	public void displayAskingOfAnArgument(String str);
}
