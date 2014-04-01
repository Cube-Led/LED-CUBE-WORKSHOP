package Workshop;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import communication.COMManager;

/**
 * Define the application and his main features
 * 
 * @author clement
 * 
 */
public class Application implements ApplicationPolling {

	/**
	 * Size in bytes of an instruction for the cube ((codeOP : 1 byte, arguments
	 * : 5 bytes )*2)
	 */
	public static final int MAX_LENGTH_BUFFER = 6;

	/**
	 * Max size of the list of instruction
	 */
	public static final int MAX_NUMBER_OF_INSTRUCTION_TO_SAVE = 200;

	/**
	 * Number for ending the editing of instructions
	 */
	public static final int END_OF_RECORDING_INSTRUCTION = 11;

	/**
	 * Instruction who will be write in file "instructions.bin" in order to be
	 * send to the cube
	 */
	//TODO change it by a dynamic list
	private List<Instruction> instructionToWrite;

	/**
	 * Number of saved instructions
	 */
	private int countInstructions;

	/**
	 * The general display for this application
	 */
	private final Display display;


	/**
	 * Read only list of instructions supported by the cube they are loaded from
	 * the file "instructionsSupportedByTheCube.inst"
	 */
	private final List<Instruction> cubesInstructions;
	
	private Cube theCube;
	
	/**
	 * Application constructor, set the display and the instructions list
	 * 
	 * @param d
	 *            The display used
	 */
	public Application(Display d) {
		
		this.theCube = new Cube();
		
		this.display = d;

		this.instructionToWrite = new ArrayList<Instruction>();
		this.cubesInstructions = new ArrayList<Instruction>();
		this.countInstructions = 0;

		this.display.setApplicationPolling(this);
		try {
			loadInstructionFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileOfInstructionCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.display.displayChoiceOfInstruction(this.cubesInstructions);
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
		@SuppressWarnings("resource")
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
		r.close();
	}


	public void writeSavedInstructionsInSavefile(File file) {
		try {
			DataOutputStream r = new DataOutputStream(new FileOutputStream(file));
			r.write(0x0);
			r.write(0x3);
			r.write(0x0);
			r.write(0x5);
			r.write(0x0);
			r.write(instructionToWrite.size());
			r.write(0x0);
			r.write(0x4);
			for (int i = 0; i < instructionToWrite.size() && instructionToWrite.get(i) != null; i++) {
				byte s1 = (byte) (instructionToWrite.get(i).getSize() >> 8);
				byte s2 = (byte) (instructionToWrite.get(i).getSize() & 0x00FF);
				r.write(s1);
				r.write(s2);
				byte c1 = (byte) (instructionToWrite.get(i).getCodeOp() >> 8);
				byte c2 = (byte) (instructionToWrite.get(i).getCodeOp() & 0x00FF);
				r.write(c1);
				r.write(c2);
				for (int j = 0; j < instructionToWrite.get(i).getArgs().size(); j++) {
					byte b1 = (byte) (instructionToWrite.get(i).getArgs().get(j) >> 8);
					byte b2 = (byte) (instructionToWrite.get(i).getArgs().get(j) & 0x00FF);
					r.write(b1);
					r.write(b2);					
				}
			}
			r.close();
		} catch (FileNotFoundException e) {
			display.print(e.getMessage());
		} catch (IOException e) {
			display.print(e.getMessage());
		}
	}

	@Override

	public void sendFileToArduino(File file) {

		COMManager l = new COMManager(9600);
		try {
			if (l.connect("COM3"))
			{
				FileInputStream f = new FileInputStream(file);
				byte[] buffer = new byte[(int) file.length()];
				f.read(buffer);
				l.writeData(buffer);
				l.disconnect();
				f.close();
			}
		} catch (Exception e) {
			display.print(e.getMessage());
		}
	}

	public void saveOneInstruction(short codeOp, String desc, int nbArg, String[] descriptionArgs, List<Short> args) {
		Instruction current = new Instruction(codeOp, desc, nbArg);
		current.setArgs(args);
		current.setDescriptionArguments(descriptionArgs);
		instructionToWrite.add(current);
		this.display.displayBuffer(instructionToWrite, countInstructions);
		this.countInstructions++;
	}
	public void saveOneInstruction(Instruction inst) {
		instructionToWrite.add(inst);
		this.display.displayBuffer(instructionToWrite, countInstructions);
		this.countInstructions++;
	}
	

	@Override
	public void requestDisplayOfPrimitiveInstructions() {
		this.display.displayChoiceOfInstruction(this.cubesInstructions);
	}

	@Override
	public Cube getTheCube() {
		return this.theCube;
	}
	@Override
	public void setTheCube(Cube c) {
		this.theCube = c;
	}

	@Override
	public void deleteListOfInstructions() {
		
		while(instructionToWrite.size() != 0)
			this.instructionToWrite.remove(0);
		
		this.display.displayBuffer(instructionToWrite, countInstructions);
		
	}

	@Override
	public void deleteSelectedInstruction(int selectedIndex) {
		
			this.instructionToWrite.remove(selectedIndex);
		
		this.display.displayBuffer(instructionToWrite, countInstructions);
	}	
}
