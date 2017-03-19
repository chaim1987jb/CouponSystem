package exceptions;

/**
* This class assigned to throw duplicate coupon Exception
* when the specific requesting object already exist in the DB.
* @author chaim_chagbi
*/
public class DuplicateCouponException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * this constructor using Exception super class
	 * method to send out message and cause exception.
	 * @param message
	 */
	public DuplicateCouponException(String message) {
		super(message);
	}
	
}
