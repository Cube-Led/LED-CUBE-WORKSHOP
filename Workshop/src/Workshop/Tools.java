package Workshop;

import java.util.ArrayList;
import java.util.List;

/**
 * Tools used in the application
 * @author Clement
 *
 */
public class Tools {
	
	private static long MASK_LOW_WEIGHT    = 0x000000000000FFFFL;
	private static long MASK_SECOND_WEIGHT = 0x00000000FFFF0000L;
	private static long MASK_THIRD_WEIGHT  = 0x0000FFFF00000000L;
	private static long MASK_HIGH_WEIGHT   = 0xFFFF000000000000L;	

	/**
	 * Transpose a long number into a list of 4 short
	 * @param number The long to transpose in the list
	 * @return A list of short representing a long number given
	 */
	public static List<Short> transformLongToShort(long number){
		List<Short> buf = new ArrayList<Short>();
		buf.add((short)(number & Tools.MASK_LOW_WEIGHT));
		buf.add((short)((number & Tools.MASK_SECOND_WEIGHT)>>16));
		buf.add((short)((number & Tools.MASK_THIRD_WEIGHT)>>32));
		buf.add((short)((number & Tools.MASK_HIGH_WEIGHT)>>48));
		
		int i=buf.size()-1;
		while(buf.get(i) == 0x0000)
		{
			buf.remove(i);
			i--;
		}
		return buf;
	}
	
	public static long transformShortToLong(List<Short> number){
		long arg = 0;
		for(int i = 0; i< number.size(); i++){
			arg += (long)(number.get(i))<<(16*i);
		}
		return arg;
	}

}
