package communication;

/**
 * Exception thrown when a port is found but it is not a serial port
 * @author Clement
 *
 */
public class NoSerialPortException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3433009678102855185L;

	public NoSerialPortException() {
	}

	public NoSerialPortException(String arg0) {
		super(arg0);
	}

}
