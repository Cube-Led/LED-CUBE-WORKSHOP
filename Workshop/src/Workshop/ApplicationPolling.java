package Workshop;

import java.io.File;
import java.util.List;

/**
 * Interface who provide services in order to manage Cube configuration and requests like save instructions or write it to a file
 * @author Clement
 *
 */
public interface ApplicationPolling {
	
	/**
	 * Write the list of instruction into a file given in parameter.
	 * The instructions are written with the format expected by the Arduino and specified into the documentation
	 * @param file The output file where the instructions are written
	 */
	public void writeSavedInstructionsInSavefile(File file);
	
	/**
	 * Send the file given in parameter to the Arduino through the serial link
	 * @param file The file sent.
	 */
	public void sendFile(File file);
	
	
	/**
	 * Create an instruction with the following arguments.
	 * Then save it in the list of instructions
	 * Also refresh display
	 * @param codeOp
	 * @param description
	 * @param nbArg
	 * @param descriptionArgs
	 * @param args
	 */
	public void saveOneInstruction(short codeOp, String description, int nbArg,String[] descriptionArgs, List<Short> args);
	
	/**
	 * Save an instruction in the list
	 * @param inst The instruction to save
	 */
	public void saveOneInstruction(Instruction inst);
	/**
	 * Service provided by the interface who request the display of list of already saved instructions
	 */
	public void requestDisplayOfPrimitiveInstructions();
	
	
	/**
	 * Service provided by the interface who return the Cube associated to this application
	 * @return A Cube who represent the controlled cube
	 */
	public Cube getTheCube();
	
	
	/**
	 * Service provided by the interface who allow the update of current Cube associated to this application
	 * @param c A new Cube
	 */
	public void setTheCube(Cube c);
	
}
