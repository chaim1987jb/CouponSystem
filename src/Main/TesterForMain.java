package Main;

import java.text.ParseException;

import basic_classes.Company;
import basic_classes.Coupon;
import basic_classes.Customer;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CouponSystem;
import facade.CustomerFacade;

public class TesterForMain {
	
	Company company = null;
	Customer customer = null;
	Coupon coupon = null;
	
	public static void main(String[] args) throws ParseException {	//	MAIN START

		Pre_Main m = new Pre_Main();

		String userName = "";
		String userPass = "";
		ClientType userType = null;
		
		//	user name
		System.out.println("Enter user name to login. ");
		userName = m.scanner.nextLine();
		//	user password
		System.out.println("Enter password to login. ");
		userPass = m.scanner.nextLine();
		//	user type
		userType = m.selectClientType();
		
		System.out.printf("User name = %s, Password = %s, Client Type = %s%n", userName, userPass, userType);
		
		CouponSystem system = CouponSystem.getInstance();
		CouponClientFacade clientFacade = system.login(userName, userPass, userType);
		if (clientFacade != null) {
			switch (userType) {
			case ADMINISTRATOR:
				System.out.println("in admin");
				m.adminFacade = (AdminFacade) clientFacade;
				m.adminOptions();
				break;
			case COMPANY:
				System.out.println("in company");
				m.companyFacade = (CompanyFacade) clientFacade;
				m.companyOptions();
				break;
			case CUSTOMER:
				System.out.println("in customer");
				m.customerFacade = (CustomerFacade) clientFacade;
				m.customerOptions();
				break;
			}
		} else {
			System.out.println();
			System.out.println("SHUTTING DOWN THE SYSTEM!");
			system.shutdown();
		}
	}	//	MAIN END

}
