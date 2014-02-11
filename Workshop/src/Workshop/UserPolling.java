package Workshop;

public interface UserPolling {
	public void writeSavedInstructionsInSavefile();
	public void recordInstructions();
	public void sendFile();
	void saveOneInstruction(byte codeOp, int nbArg, byte[] args);
	void requestDisplayOfPrimitiveInstructions();
}
