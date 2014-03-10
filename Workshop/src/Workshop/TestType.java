package Workshop;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TestType {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("test.bin");
		try {
			DataOutputStream r = new DataOutputStream(
					new FileOutputStream(file));
			
			long l = 10;
			ByteBuffer b = ByteBuffer.allocate(8);
			b.clear();
			b.putLong(l/(long)Math.pow(2,Long.numberOfTrailingZeros(l)));
			int index =0;
			while(b.get(index) == 0)
				index++;
			
			for(;index <8;index++)
					r.writeByte(b.get(index));
					
			
			r.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
