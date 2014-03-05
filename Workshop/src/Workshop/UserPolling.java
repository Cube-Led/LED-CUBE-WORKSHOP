package Workshop;

public interface UserPolling {
	public void writeSavedInstructionsInSavefile();
	public void recordInstructions();
	public void sendFile();
	void saveOneInstruction(short codeOp, String description, int nbArg,String[] descriptionArgs, short[] args);
	void requestDisplayOfPrimitiveInstructions();
	
}
