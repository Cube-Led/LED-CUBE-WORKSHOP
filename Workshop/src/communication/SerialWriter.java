package communication;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter extends Thread {
	private final OutputStream out;
	private byte[] dataToWrite;
	public SerialWriter(OutputStream out) {
		this.out = out;
	}

	public void setDataToBeWrite(byte[] data)
	{
		this.dataToWrite = data;
	}
	
	public void run() {
		try {
			this.out.write(dataToWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
