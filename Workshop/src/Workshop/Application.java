package Workshop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import communication.COMManager;

/**
 * Define the application and his main features
 * 
 * @author clement
 * 
 */
public class Application implements UserPolling {

	/**
	 * Size in bytes of an instruction for the cube ((codeOP : 1 byte, arguments
	 * : 5 bytes )*2)
	 */
	public static final int MAX_LENGTH_BUFFER = 6;

	/**
	 * Max size of the list of instruction
	 */
	public static final int MAX_NUMBER_OF_INSTRUCTION_TO_SAVE = 150;

	/**
	 * Number for ending the editing of instructions
	 */
	public static final int END_OF_RECORDING_INSTRUCTION = 11;

	/**
	 * Instruction who will be write in file "instructions.bin" in order to be
	 * send to the cube
	 */
	private Instruction instructionToWrite[];

	/**
	 * Number of saved instructions
	 */
	private int countInstructions;

	/**
	 * The general display for this application
	 */
	private final Display display;

	private final ChoiceAsker choice;

	/**
	 * Read only list of instructions supported by the cube they are loaded from
	 * the file "instructionsSupportedByTheCube.inst"
	 */
	private final List<Instruction> cubesInstructions;
	
	
	
	/**
	 * Application constructor, set the display and the instructions list
	 * 
	 * @param d
	 *            The display used
	 */
	public Application(Display d, ChoiceAsker ch) {
		this.display = d;
		this.choice = ch;

		this.instructionToWrite = new Instruction[MAX_NUMBER_OF_INSTRUCTION_TO_SAVE];
		this.cubesInstructions = new ArrayList<Instruction>();
		this.countInstructions = 0;

		this.display.setUserPolling(this);
		try {
			loadInstructionFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileOfInstructionCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.display.displayChoiceOfInstruction(this.cubesInstructions
				.toArray(new Instruction[this.cubesInstructions.size()]));
	}

	/**
	 * Take instruction from file "instructionsSupportedByTheCube.inst" and put
	 * them into a list
	 * 
	 * @throws IOException
	 * @throws FileOfInstructionCorruptedException
	 */
	@SuppressWarnings("deprecation")
	private void loadInstructionFromFile() throws IOException,
			FileOfInstructionCorruptedException {
		File f = new File("instructionsSupportedByTheCube.inst");
		DataInputStream r = new DataInputStream(new FileInputStream(f));
		String buffer;
		int count = 0;
		while ((buffer = r.readLine()) != null) {
			if (buffer.toCharArray()[0] != '/'
					&& buffer.toCharArray()[0] != '*') {
				String str[] = null;
				str = buffer.split(":");
				if (str.length <= 1)
					throw new FileOfInstructionCorruptedException();
				else {
					if (str.length > 2) {
						this.cubesInstructions.add(new Instruction(
								(byte) Integer.parseInt(str[0]), str[2],
								Integer.parseInt(str[1])));
						if (str.length > 3) {
							String[] strArgDesc = str[3].split(";");
							this.cubesInstructions.get(count)
									.setDescriptionArguments(strArgDesc);
						}
					} else {
						this.cubesInstructions.add(new Instruction((byte) Integer.parseInt(str[0]), Integer.parseInt(str[1])));
					}
				}
				count++;
			}
		}
	}

	/**
	 * Record instructions until the user stop it or until the instruction tab
	 * is full
	 */
	public void recordInstructions() {
		Instruction current;
		for (this.countInstructions = 0; (this.countInstructions < MAX_NUMBER_OF_INSTRUCTION_TO_SAVE); this.countInstructions++) {

			Instruction[] x = this.cubesInstructions
					.toArray(new Instruction[this.cubesInstructions.size()]);

			this.display.displayChoiceOfInstruction(x);

			this.display.print(END_OF_RECORDING_INSTRUCTION
					+ "-Envoyer les instructions \n");

			display.displayAskingOfAnArgument("Choix (taper "
					+ END_OF_RECORDING_INSTRUCTION + " pour finir)");

			int codeOpCurrent = choice.askInteger();

			if (codeOpCurrent != END_OF_RECORDING_INSTRUCTION) {
				Iterator<Instruction> iterator = this.cubesInstructions
						.iterator();
				Instruction newInstruct;
				boolean finded = false;
				while (iterator.hasNext()) {
					current = iterator.next();
					if (current.getCodeOp() == codeOpCurrent) {
						finded = true;
						List<Short> args = new ArrayList<Short>();
						for (int j = 0; j < current.getNbArgs(); j++) {
							String desc = current.getDescriptionArguments()[j];
							if (desc == null)
								desc = "Argument " + j + 1;
							display.displayAskingOfAnArgument(desc + " : ");
							int tempArg = choice.askInteger();
							if (tempArg > 0xFF) {
								args.add((short) (tempArg & 0xFF));
								j++;
								args.set(j, (short) (tempArg >> 8));
							} else {
								args.add((short) 0);
								j++;
								args.set(j,(short) tempArg);
							}
						}
						newInstruct = new Instruction((byte) codeOpCurrent,
								current.getDescription(), current.getNbArgs());
						newInstruct.setArgs(args);
						instructionToWrite[this.countInstructions] = newInstruct;
						display.displayBuffer(instructionToWrite,
								countInstructions);
					}
				}
				if (!finded)
					countInstructions--;
			} else
				break;
			if (this.countInstructions == Application.MAX_NUMBER_OF_INSTRUCTION_TO_SAVE)
				display.println("You have reached the end of the instruction buffer");
		}
	}

	private void writeLong(long l, DataOutputStream out) throws IOException
	{
		ByteBuffer b = ByteBuffer.allocate(Long.SIZE/8);
		b.clear();
		b.putLong(l/(long)Math.pow(2,Long.numberOfTrailingZeros(l)));
		int index =0;
		while(b.get(index) == 0)
			index++;
		
		for(;index <8;index++)
				out.writeByte(b.get(index));
	}
	
	/**
	 * Write the current tab of instruction into the file "instructions.bin"
	 */
	public void writeSavedInstructionsInSavefile() {
		File file = new File("instructions.bin");
		try {
			DataOutputStream r = new DataOutputStream(
					new FileOutputStream(file));
			for (int i = 0; i < instructionToWrite.length
					&& instructionToWrite[i] != null; i++) {
				byte c1 = (byte) (instructionToWrite[i].getCodeOp() >> 8);
				byte c2 = (byte) (instructionToWrite[i].getCodeOp() & 0x00FF);
				r.write(c1);
				r.write(c2);
				for (int j = 0; j < instructionToWrite[i].getArgs().size(); j++) {
					byte b1 = (byte) (instructionToWrite[i].getArgs().get(j) >> 8);
					byte b2 = (byte) (instructionToWrite[i].getArgs().get(j) & 0x00FF);
					r.write(b1);
					r.write(b2);
				}
			}
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override

	public void sendFile() {

		COMManager l = new COMManager(9600);
		try {
			if (l.connect("COM6"))
			{
				int len;
				File file = new File("instructions.bin");
				FileInputStream f = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				f.read(buffer);
				l.writeData(buffer);
				l.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveOneInstruction(short codeOp, String desc, int nbArg, String[] descriptionArgs, List<Short> args) {
		Instruction current = new Instruction(codeOp, desc, nbArg);
		current.setArgs(args);
		current.setDescriptionArguments(descriptionArgs);
		instructionToWrite[this.countInstructions] = current;
		System.out.println(current);
		this.display.displayBuffer(instructionToWrite, countInstructions);
		this.countInstructions++;
	}

	@Override
	public void requestDisplayOfPrimitiveInstructions() {
		this.display.displayChoiceOfInstruction(this.cubesInstructions
				.toArray(new Instruction[this.cubesInstructions.size()]));
	}
	
	
	
}
