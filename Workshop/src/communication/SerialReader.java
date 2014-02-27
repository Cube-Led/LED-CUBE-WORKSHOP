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
		while(true)
		{
			
			try {
				byte[] b = new byte[10];
				in.read(b);
				String str = new String(b);
				System.out.print(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		/*int len;
		byte[] buffer = new byte[100];
		try {
			System.out.println(in.read());
			while ((len = this.in.read(buffer)) > -1) {
				res = res + new String(buffer,0,len);
			}
			System.out.println(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		}
	}
}