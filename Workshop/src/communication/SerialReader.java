package communication;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader extends Thread{
	
	public InputStream in;
	public boolean acknowledgement;
	public SerialReader(InputStream in) {
		this.acknowledgement = false;
		this.in = in;
		try {
			System.out.println(in.available());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		
		while(true)
		{
			try {
				byte r = (byte)in.read();
				if(r != -1)
				{
					//System.out.println("ARRET!!!!!!!!");
					this.acknowledgement = true;
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Recu : " +r);
				}
				//System.out.println(r);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void close()
	{
		try {
			this.in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stop();
	}
	
	
	public void waitForAcknowledgment()
	{
		int b;
		try {
			b = in.read();
			while(b == -1)
			{
				b = in.read();
				Thread.sleep(5);
			}
			this.acknowledgement = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}