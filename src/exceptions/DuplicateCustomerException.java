package exceptions;

/**
* This class assigned to throw duplicate customer Exception
* when the specific requesting object already exist in the DB.
* @author chaim_chagbi
*/
public class DuplicateCustomerException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * this constructor using Exception super class
	 * method to send out message and cause exception.
	 * @param message
	 */
	public DuplicateCustomerException(String message) {
		super(message);
	}
	
}
