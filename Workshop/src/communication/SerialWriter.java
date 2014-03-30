package communication;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Class used, in the communication by serial link between the workshop and the Arduino, in order to send data
 * @author Clement
 *
 */
public class SerialWriter extends Thread {
	
	/**
	 * The stream who output data
	 */
	public final OutputStream out;
	
	
	/**
	 * The byte array set when wall the setDataToBeWrite method
	 * This array correspond to the data who will be send through the serial link
	 */
	private byte[] dataToWrite;

	/**
	 * Boolean who tell if the data is requested to be send
	 */
	public boolean needSending;
	
	/**
	 * The integer who will be sent when the needSending field is True
	 */
	public int integerToSend;
	
	
	public SerialWriter(OutputStream out) {
		this.needSending = false;
		this.out = out;
		dataToWrite = null;
	}

	/**
	 * Set the data who will be sent through the serial link
	 * @param data The data who will be sent
	 */
	public void setDataToBeWrite(byte[] data)
	{
		this.needSending = false;
		this.dataToWrite = data;
	}
	
	/**
	 * Send an integer trough the serial link
	 * @param toSend the integer sent through the serial link
	 */
	public void send(int toSend)
	{
		try {
			this.out.write(toSend);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		integerToSend = (byte) 0xFF;
		while(true)
		{
			if(this.needSending)
			{
				writeData();
				this.needSending = false;
			}
			if(this.integerToSend != (byte)0xFF)
			{
				send(integerToSend);
				integerToSend = (byte) 0xFF;
			}
		}
		
	}

	/**
	 * Close the OutPutStream and stop the current Thread
	 */
	@SuppressWarnings("deprecation")
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
	
	/**
	 * Write the data from the byte array field dataToWrite through the serial link
	 */
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
