package exceptions;

/**
 * This class assigned to throw duplicate company Exception
 * when the specific requesting object already exist in the DB.
 * @author chaim_chagbi
 */
public class DuplicateCompanyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * this constructor using Exception super class
	 * method to send out message and cause exception. 
	 * @param message
	 */
	public DuplicateCompanyException(String message) {
		super(message);
	}
	
}
