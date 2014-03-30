package communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPort;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.CommPortIdentifier;
import gnu.io.UnsupportedCommOperationException;

public class COMManager {
	private final int rate;
	private SerialReader comReader;
	private SerialWriter comWriter;
	public static final byte SIG_BEGIN = 0x02;
	public static final byte SIG_END = 0x0;
	private SerialPort serialPort;
	
	public COMManager(int baudRate) {
		this.rate = baudRate;
	}

	public boolean connect(String portName) throws PortInUseException,
			UnsupportedCommOperationException, IOException {
		CommPortIdentifier portIdentifier = null;
		int identifier = 0;
		boolean givenPortOK = false;
		try { // On essaye de trouver le port souhaité
			portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
			if (portIdentifier.isCurrentlyOwned()) // On regarde s'il est libre
			{
				System.err.println("Error: Port " + portName
						+ " is currently in use, try later ...");
				return false;
			}
			else
				givenPortOK =true;
		} catch (NoSuchPortException e1) {
		}

		if (portIdentifier == null) { // On a pas réussit a trouver le port

			System.out.println(portName
					+ " not found, \n \tTrying port COM0 to COM20");
			boolean found = false;
			while (!found && identifier <= 20) { // On essaye tous les ports de COM0 a
										// COM20
				try {
					portIdentifier = CommPortIdentifier.getPortIdentifier("COM"
							+ identifier);
					found = true;

				} catch (NoSuchPortException e) {
					identifier++;
				}
			}
			if (portIdentifier != null)// On a trouvé un port
			{
				if (portIdentifier.isCurrentlyOwned()) // On regarde s'il est
														// libre
				{
					System.err.println("Error: Port COM" + identifier
							+ " is currently in use, try later ...");
					return false;
				}
			} else // Pas de port trouvé;
			{
				System.err.println("No port found in range COM0 and COM20, check if your Arduino is correctly connected ...");
				return false;
			}
		}
		if (portIdentifier != null) { // On a trouvé un port existant et non
										// utilisé
			CommPort commPort = portIdentifier.open(this.getClass().getName(),
					2000);

			if (commPort instanceof SerialPort) { // On regarde si c'est un lien
													// série
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(this.rate,
						SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				InputStream in = serialPort.getInputStream();
				while (in.read() != -1)
					;
				OutputStream out = serialPort.getOutputStream();

				this.comReader = new SerialReader(in);
				this.comWriter = new SerialWriter(out);
				if(givenPortOK)
					System.out.println("Connection successful to " + portName);
				else
					System.out.println("Connection successful to COM" + identifier);
				return true;
			} else {
				System.err.println("Error: Only serial ports can be handle, see Arduino reference if no serial port is created by you arduino");
				return false;
			}
		}
		return false;

	}

	public void disconnect() {
		this.comReader.close();
		this.comWriter.close();
		this.serialPort.close();
	}

	public void writeData(byte[] data) {
		try {
			comWriter.out.write(9600);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int timeCount = 0;
		this.comWriter.start();
		this.comReader.start();
		System.out.println("Debut de transmision");
		try {
			Thread.sleep(2000);
			this.comWriter.integerToSend = SIG_BEGIN;
			while (!this.comReader.acknowledgement && timeCount < 500) {
				Thread.sleep(10);
				timeCount++;
			}
			if (!this.comReader.acknowledgement) {
				System.out.println("ECHEC du transfert");
				return;
			}
			System.out.println("Reponse de l'arduino, envoie en cours de " + data.length + " octets \n  (" +  ((float)data.length*8)/(serialPort.getBaudRate() /10.0F) + " secondes nécéssaires)");
			this.comReader.acknowledgement = false;
			this.comWriter.integerToSend = data.length;
			Thread.sleep(1000);
			this.comWriter.setDataToBeWrite(data);
			this.comWriter.needSending = true;
			while (comWriter.needSending) {
				Thread.sleep(5);
			}
			System.out.println("Fin de transmission");
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRate() {
		return rate;
	}
}
