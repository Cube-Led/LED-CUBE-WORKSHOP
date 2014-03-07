package communication;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter extends Thread {
	private final OutputStream out;
	private byte[] dataToWrite;

	boolean willWrite;
	byte willSend;
	public SerialWriter(OutputStream out) {
		this.willWrite = false;
		this.out = out;
		dataToWrite = null;
	}

	public void setDataToBeWrite(byte[] data)
	{
		this.dataToWrite = data;
	}
	
	public void send(byte b)
	{
		try {
			this.out.write(b);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {

		willSend = 0x0;
		while(true)
		{
			if(this.willWrite)
			{
				writeData();
				this.willWrite = false;
			}
			if(this.willSend != 0xFF)
			{
				send(willSend);

				willSend = 0x0;
			}
		}
		
	}

	public void writeData() {
		int i=0;
		while(i < dataToWrite.length)
		{
			System.out.println("data to write " + dataToWrite[i]);
			try {
				this.out.write(dataToWrite[i]);
				this.out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
	}
	
	
}
