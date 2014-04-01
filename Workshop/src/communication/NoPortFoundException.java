package communication;

/**
 * Exception thrown when no port has been found
 * @author Clement
 *
 */
public class NoPortFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6678047280403176629L;

	public NoPortFoundException(String msg)
	{
		super(msg);
	}

}
