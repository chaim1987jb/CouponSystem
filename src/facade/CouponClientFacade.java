package facade;

/**
 * This interface has only one abstract method the login.
 * it designed to bind all facade classes so the  CouponSystem
 * can make instances for all of them in one method . 
 * @author chaim_chagbi
 */
public interface CouponClientFacade {

	/**
	 * login into system
	 * @param name - login user name
	 * @param password - login user password
	 * @param clientType - login client type of login user
	 * @return - coupon client facade by selected client type
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType);

}
