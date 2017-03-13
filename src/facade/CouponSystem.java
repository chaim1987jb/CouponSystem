package facade;

import java.sql.SQLException;

import dao.DailyCouponExpirationTask;

public class CouponSystem implements CouponClientFacade {

	private static CouponSystem instance = null;
	private DailyCouponExpirationTask dailyCouponExpirationTask;

	public static synchronized CouponSystem getInstance() {
		if (instance == null) {
			instance = new CouponSystem();
		}
		return instance;
	}

	private CouponSystem() {
		try {
			dailyCouponExpirationTask = new DailyCouponExpirationTask();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
		new Thread(dailyCouponExpirationTask).start();
	}

	@Override
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
	
	public void shutdown() {
		try {
			dailyCouponExpirationTask.stopTask();
		} catch (ClassNotFoundException | SQLException | InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
