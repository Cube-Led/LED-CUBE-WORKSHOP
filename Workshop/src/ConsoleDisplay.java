
/**
 * Console implementation of Display interface
 * Messages will be displayed on the console
 * @author clement
 */
public class ConsoleDisplay implements Display {

	/**
	 * @see Display#displayChoiceOfInstruction(Instruction[])
	 */
	public void displayChoiceOfInstruction(Instruction[] inst) {
		
		for(int i=0; i < inst.length; i++)
			println((int)inst[i].getCodeOp() + "-" + inst[i].getDescription()); 

	}

	/**
	 * @see Display#println(java.lang.String)
	 */
	public void println(String str) {
		System.out.println(str);
		
	}

	
	/**
	 * @see Display#print(java.lang.String)
	 */
	public void print(String str) {
		System.out.print(str);
		
	}

	@Override
	public void setUserPolling(UserPolling poll) {
		// TODO Auto-generated method stub
		
	}

	public void displayBuffer(Instruction[] inst, int countInstructions) {
		println("Instructions : ");
		for (int i = 0; i < countInstructions; i++) {
			println("" + inst[i]);
		}
		println("");
	}
	
	
}
