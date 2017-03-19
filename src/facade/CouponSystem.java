package facade;

import java.sql.SQLException;


import dao.DailyCouponExpirationTask;
/**
 * 
 *  This class is a singleton which means that it will provide
 *  only one appearance of this class.
 *  It is the engine system for this project
 *  Hence entrance to the system.
 *  Hence the system's closing.
 *  The  DailyCouponExpirationTask is operated from this class
 *  private constructor.
 * @author chaim_chagbi
 *
 */
public class CouponSystem implements CouponClientFacade {
	
	/** Field represents the static  CouponSystem  ....*/
	private static CouponSystem instance = null;
	
	/** Field represents the  DailyCouponExpirationTask object ....*/
	private DailyCouponExpirationTask dailyCouponExpirationTask;

	/**
	 * This method provide instance of the couponSystem although it will
	 * provide the same instance.
	 * @return
	 */
	public static synchronized CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}
	
	
	/** Constructor represents the  DailyCouponExpirationTask object ..*/
	private CouponSystem() {
		try {
			dailyCouponExpirationTask = new DailyCouponExpirationTask();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
		new Thread(dailyCouponExpirationTask).start();
	}

	@Override
	
	/**
	 * This is the login method which attendant the entering to the system.
	 * It receiving a String values for name and password and an ENUM for
	 * the type of the applicant to adjusted it to the right case.
	 * than it will transmit the values to the facade classes and regain
	 * a boolean value in case the login succeeded it returning an instance
	 * of the required type administrator , company or customer.
	 * @param name
	 * @param password
	 * @param clientType
	 * @return CouponClientFacade instance.
	 */
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		CouponClientFacade clientFacade = null;
		
		switch (clientType) {
		case COMPANY:
			clientFacade = new CompanyFacade();
			break;
		case CUSTOMER:
			clientFacade = new CustomerFacade();
			break;
		case ADMINISTRATOR:
			clientFacade = new AdminFacade();
			break;
		}
		
		CouponClientFacade login = clientFacade.login(name, password, clientType);
		return login;
	}
	
	/**
	 * This method role is to take care that all the the connection in the 
	 *  ConnectionPool will be closed . first it will cause the while loop
	 * in the DailyCouponExpirationTask to stop by set the runner to false and
	 * than run the closeAllConection method in the ConnectionPool.
	 */
	public void shutdown() {
		try {
			dailyCouponExpirationTask.stopTask();
		} catch (ClassNotFoundException | SQLException | InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
