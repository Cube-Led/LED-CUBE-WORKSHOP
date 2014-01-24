
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
			printlnString((int)inst[i].getCodeOp() + "-" + inst[i].getDescription()); 

	}

	/**
	 * @see Display#printlnString(java.lang.String)
	 */
	public void printlnString(String str) {
		System.out.println(str);
		
	}

	
	/**
	 * @see Display#printString(java.lang.String)
	 */
	public void printString(String str) {
		System.out.print(str);
		
	}

}
