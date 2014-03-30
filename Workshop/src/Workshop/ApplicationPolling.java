package Workshop;

import java.io.File;
import java.util.List;

public interface ApplicationPolling {
	//public void writeSavedInstructionsInSavefile();
	public void writeSavedInstructionsInSavefile(File file);
	public void recordInstructions();
	public void sendFile(File file);
	public void saveOneInstruction(short codeOp, String description, int nbArg,String[] descriptionArgs, List<Short> args);
	public void requestDisplayOfPrimitiveInstructions();
	public Cube getTheCube();
	public void setTheCube(Cube c);
	
}
