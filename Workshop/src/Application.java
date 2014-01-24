
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


/**
 * Define the application and his main features
 * @author clement
 *
 */
public class Application {

	/**
	 * Size in bytes of an instruction for the cube (codeOP : 1 byte, arguments : 4 bytes)
	 */
	public static final int MAX_LENGTH_BUFFER = 5;

	/**
	 * Max size of the list of instruction
	 */
	public static final int MAX_NUMBER_OF_INSTRUCTION_TO_SAVE = 3;

	/**
	 * Instruction who will be write in file "instructions.bin" in order to be send to the cube
	 */
	private  Instruction instructionToWrite[];

	/**
	 * TODO : make an interface for this
	 */
	private  Scanner sc = new Scanner(System.in);


	/**
	 * Number of instructions saved
	 */
	private  int countInstructions;


	/**
	 * The general display for this application
	 */
	private Display display;


	/**
	 * Read only list of instructions supported by the cube
	 * they are loaded from the file "instructionsSupportedByTheCube.inst"
	 * TODO : think about a better solution, because it need a comparator
	 */
	private final Set<Instruction> cubesInstructions;

	
	
	/**
	 * Application constructor, set the display and the instructions list
	 * @param d The display used
	 */
	public Application(Display d)
	{
		this.display = d;
		this.instructionToWrite =  new Instruction[MAX_NUMBER_OF_INSTRUCTION_TO_SAVE];
		this.cubesInstructions = new TreeSet<Instruction>(new InstructionComparator());
		try {
			loadInstructionFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileOfInstructionCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Take instruction from file "instructionsSupportedByTheCube.inst" and put them into a list
	 * @throws IOException
	 * @throws FileOfInstructionCorruptedException
	 */
	private void loadInstructionFromFile() throws IOException, FileOfInstructionCorruptedException
	{
		File f = new File("instructionsSupportedByTheCube.inst");
		DataInputStream r = new DataInputStream(new FileInputStream(f));
		String buffer;

		while((buffer = r.readLine()) != null)
		{
			if(buffer.toCharArray()[0] != '/' && buffer.toCharArray()[0] != '*' )
			{
				String str[] = null;
				str = buffer.split(":");
				if(str.length <=1)
					throw new FileOfInstructionCorruptedException();
				else
				{
					if(str.length > 2)
					{
						this.cubesInstructions.add(new Instruction((byte)Integer.parseInt(str[0]), str[2], Integer.parseInt(str[1])));
					}
					else
					{
						this.cubesInstructions.add(new Instruction((byte)Integer.parseInt(str[0]), Integer.parseInt(str[1])));
					}
				}
			}
		}
	}


	/**
	 * Record instructions until the user stop it or until the instruction tab is full
	 */
	public void recordInstructions()
	{
		Instruction current;
		for (countInstructions = 0 ; (countInstructions < MAX_NUMBER_OF_INSTRUCTION_TO_SAVE); countInstructions++)
		{
			this.display.printlnString("Que souhaitez-vous faire ?");

			Instruction[] x = this.cubesInstructions.toArray(new Instruction[this.cubesInstructions.size()]);
			this.display.displayChoiceOfInstruction(x);

			this.display.printlnString("11-Envoyer les instructions");
			
			int codeOpCurrent = sc.nextInt();
			
			if(codeOpCurrent != 11)
			{
				Iterator<Instruction> iterator = this.cubesInstructions.iterator();
				Instruction newInstruct;
				while(iterator.hasNext())
				{
					current = iterator.next();
					if(current.getCodeOp() == codeOpCurrent)
					{
						byte[] args = new byte[MAX_LENGTH_BUFFER - 1];
						for (int j = 0 ; j < current.getNbArgs() ; j++)
						{
							System.out.println("Argument " + (j+1));
							int tempArg = Integer.parseInt(sc.next());
							if(tempArg > 0xFF)
							{
								args[j] = (byte) (tempArg &  0xFF);
								j++;
								args[j] = (byte) (tempArg >> 8);
							}
							else args[j] = (byte) tempArg;
						}
						newInstruct = new Instruction((byte)codeOpCurrent, current.getDescription(), current.getNbArgs());
						newInstruct.setArgs(args);
						instructionToWrite[countInstructions] = newInstruct;
					}
				}
				
			}
			else break;
			if(countInstructions == Application.MAX_NUMBER_OF_INSTRUCTION_TO_SAVE)display.printlnString("You have reached the end of the instruction buffer"); 
		}
	}

	/**
	 * Display the current tab of instruction
	 * TODO : think to move it to Display class
	 */
	public  void displayBuffer()
	{
		this.display.printlnString("Instructions : ");
		for (int i = 0 ; i < instructionToWrite.length ; i++)
		{
			this.display.printlnString("" + instructionToWrite[i]);
		}
		this.display.printlnString("");
	}

	/**
	 * Write the current tab of instruction into the file "instructions.bin"
	 */
	public  void writeSavedInstructionsInSavefile()
	{
		File file = new File("instructions.bin");
		try {

			DataOutputStream r = new DataOutputStream(new FileOutputStream(file));
			for (int i = 0 ; i < instructionToWrite.length && instructionToWrite[i] != null; i++)
			{
				int nbByte = 1 + instructionToWrite[i].getArgs().length;
				r.write(instructionToWrite[i].getCodeOp());
				r.write(instructionToWrite[i].getArgs());

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
