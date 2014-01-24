
public class Instruction {
	
	/**
	 * Code op of the instruction, coded on one byte, allow 255 different instructions
	 */
	private final byte codeOp;
	
	/**
	 * General description of this instruction, used only in the application for users
	 */
	private final String description;
	
	
	/**
	 * Max number of arguments for this instruction
	 */
	private int nbArgs;
	
	
	/**
	 * Tab of parameters for the instruction, coded on bytes
	 */
	private byte args[];
	
	/**
	 * Create an instruction with codeOp,a number of params and an unknown description,
	 * the tab args is Application.MAX_LENGTH_BUFFER-1 bytes
	 * @param codeOp Instruction's codeOp
	 * @param nbArgs Instruction's number of params
	 */
	public Instruction(byte codeOp, int nbArgs)
	{
		this.codeOp = codeOp;
		this.description = "Unknown instruction";
		this.nbArgs = nbArgs;
		this.args = new byte[Application.MAX_LENGTH_BUFFER -1];
	}
	
	/**
	 *  Create an instruction with codeOp,a number of params and a description
	 * the tab args is Application.MAX_LENGTH_BUFFER-1 bytes
	 * @param codeOp
	 * @param description
	 * @param nbArgs
	 */
	public Instruction(byte codeOp, String description, int nbArgs)
	{
		this.codeOp = codeOp;
		this.description = description;
		this.nbArgs = nbArgs;
		this.args = new byte[Application.MAX_LENGTH_BUFFER -1];
	}

	public String toString()
	{
		String str =  "" + (int)codeOp;
		
		for(int i=0; i < this.args.length; i++)
			str = str +" " + args[i];
		
		return str;
		
	}

	/**
	 * Return the arguments of this instruction
	 * @return a byte tab who represent params
	 */
	public byte[] getArgs() {
		return args;
	}

	
	/**
	 * Set the parameter for this instruction
	 * @param args a tab of parameters
	 */
	public void setArgs(byte[] args) {
		this.args = args;
	}

	/**
	 * Return the codeOp of this instruction
	 * @return codeOp
	 */
	public byte getCodeOp() {
		return codeOp;
	}

	/**
	 * Return the description of this instruction
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Return the number of parameters for this instruction
	 * @return nbArgs
	 */
	public int getNbArgs() {
		return nbArgs;
	}

}
