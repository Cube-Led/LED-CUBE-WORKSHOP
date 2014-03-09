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
				int r =in.read();
				if(r != -1)
				{
					//System.out.println("ARRET!!!!!!!!");
					this.acknowledgement = true;
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
			}
			this.acknowledgement = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}