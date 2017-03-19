package exceptions;

/**
 * this class assigned to throw coupon not found Exception
 * when the specific requesting object not found in the DB.
 * @author chaim_chagbi
 */
public class CouponNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * this constructor using Exception super class
	 * method to send out message and cause exception. 
	 * @param message
	 */
	public CouponNotFoundException(String message) {
		super(message);
	}

}
