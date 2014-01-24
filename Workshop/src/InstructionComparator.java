import java.util.Comparator;


public class InstructionComparator implements Comparator<Instruction> {

	@Override
	public int compare(Instruction o1, Instruction o2) {
		if(o1 == null || o2 ==null) return 1;
		if(o1.getCodeOp()< o2.getCodeOp()) return -1;
		if(o1.getCodeOp() >= o2.getCodeOp()) return 1;
		return 0;
	}

}
