package communication;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter extends Thread {
	private final OutputStream out;
	private byte[] dataToWrite;
	public SerialWriter(OutputStream out) {
		this.out = out;
		dataToWrite = null;
	}

	public void setDataToBeWrite(byte[] data)
	{
		this.dataToWrite = data;
	}
	
	public void run() {
		try {
			int i = 0;
			while(i < dataToWrite.length)
			{
				System.out.println("data to write " + dataToWrite[i]);
				this.out.write(dataToWrite[i]);
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("writed");
	}
	
	
}
