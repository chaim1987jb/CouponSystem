package exceptions;

/**
 * this class assigned to throw customer not found Exception
 * when the specific requesting object not found in the DB.
 * @author chaim_chagbi
 */
public class CustomerNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * this constructor using Exception super class
	 * method to send out message and cause exception. 
	 * @param message
	 */
	public CustomerNotFoundException(String message) {
		super(message);
	}

}
