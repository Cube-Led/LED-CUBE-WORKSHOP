package Workshop;

import java.util.List;

public interface UserPolling {
	public void writeSavedInstructionsInSavefile();
	public void recordInstructions();
	public void sendFile();
	void saveOneInstruction(short codeOp, String description, int nbArg,String[] descriptionArgs, List<Short> args);
	void requestDisplayOfPrimitiveInstructions();
	
}
