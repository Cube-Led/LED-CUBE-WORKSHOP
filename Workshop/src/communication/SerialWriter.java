package communication;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter extends Thread {
	public final OutputStream out;
	private byte[] dataToWrite;

	public boolean willWrite;
	public int willSend;
	public SerialWriter(OutputStream out) {
		this.willWrite = false;
		this.out = out;
		dataToWrite = null;
	}

	public void setDataToBeWrite(byte[] data)
	{
		this.willWrite = false;
		this.dataToWrite = data;
	}
	
	public void send(int willSend2)
	{
		try {
			this.out.write(willSend2);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {

		willSend = (byte) 0xFF;
		while(true)
		{
			if(this.willWrite)
			{
				writeData();
				this.willWrite = false;
			}
			if(this.willSend != (byte)0xFF)
			{
				send(willSend);
				willSend = (byte) 0xFF;
			}
		}
		
	}

	public void close()
	{
		try {
			this.out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stop();
	}
	
	public void writeData() {
		int i=0;
		while(i < dataToWrite.length)
		{
			System.out.println("data to write " + dataToWrite[i]);
			try {
				this.out.write(dataToWrite[i]);
				this.out.flush();
				Thread.sleep(75);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
	
	
}
