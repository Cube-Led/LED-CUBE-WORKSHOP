package Workshop;

public interface UserPolling {
	public void writeSavedInstructionsInSavefile();
	public void recordInstructions();
	public void sendFile();
	void saveOneInstruction(byte codeOp, String description, int nbArg,String[] descriptionArgs, byte[] args);
	void requestDisplayOfPrimitiveInstructions();
	
}
