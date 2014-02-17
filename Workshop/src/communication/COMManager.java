package communication;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;


import gnu.io.CommPort;
import gnu.io.SerialPort;
import gnu.io.CommPortIdentifier;


public class COMManager {
	private int rate=9600;
	private SerialReader comReader;
	private SerialWriter comWriter;
	
	
	public COMManager() {
	}
	
	public void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);
			
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				//serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				//serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				serialPort.setSerialPortParams(getRate(), SerialPort.DATABITS_8,SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
 
				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();
				
				this.comReader = new SerialReader(in);
				this.comWriter = new SerialWriter(out);
 
			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
	}
 
	public void writeData(byte[] data)
	{
		this.comWriter.setDataToBeWrite(data);
		this.comWriter.run();
	}
	
	public String readData() 
	{
		this.comReader.run();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.comReader.res;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
}
