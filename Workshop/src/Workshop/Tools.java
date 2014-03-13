package Workshop;

import java.util.ArrayList;
import java.util.List;

public class Tools {
	
	private static long MASK_LOW_WEIGHT    = 0x000000000000FFFFL;
	private static long MASK_SECOND_WEIGHT = 0x00000000FFFF0000L;
	private static long MASK_THIRD_WEIGHT  = 0x0000FFFF00000000L;
	private static long MASK_HIGH_WEIGHT   = 0xFFFF000000000000L;	

	public static List<Short> transformLongToShort(long number){
		List<Short> buf = new ArrayList<Short>();
		buf.add((short)(number & Tools.MASK_LOW_WEIGHT));
		buf.add((short)((number & Tools.MASK_SECOND_WEIGHT)>>16));
		buf.add((short)((number & Tools.MASK_THIRD_WEIGHT)>>32));
		buf.add((short)((number & Tools.MASK_HIGH_WEIGHT)>>48));
		return buf;
	}

}
