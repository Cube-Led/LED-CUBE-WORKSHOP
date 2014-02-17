package communication;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader extends Thread{
	InputStream in;
	String res;
	public SerialReader(InputStream in) {
		this.in = in;
	}

	public void run() {
		int len;
		byte[] buffer = new byte[100];
		try {
			while ((len = this.in.read(buffer)) > -1) {
				res = res + new String(buffer,0,len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}