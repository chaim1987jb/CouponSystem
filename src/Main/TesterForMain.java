package Main;

import java.util.ArrayList;
import java.util.Collection;

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
	
	public static void main(String[] args) {	//	MAIN START

		Pre_Main m = new Pre_Main();
		String exit = "";

		String userName = "";
		String userPass = "";
		ClientType userType = null;
		
		Coupon coupon = null;
		Customer customer = null;
		Company company = null;
		ArrayList<Coupon> list = null;
		
		boolean login = false;
		AdminFacade adminFacade = null;
		CompanyFacade companyFacade = null;
		CustomerFacade customerFacade = null;

		System.out.println("Enter user name to login. ");
		userName = m.scanner.nextLine();
		System.out.println("Enter password to login. ");
		userPass = m.scanner.nextLine();
		System.out.println("Select user type from list to login: ");
		m.printClientTypes();
		userType = m.clientTypes[m.scanner.nextInt()];
		
		System.out.printf("User name = %s, Password = %s, Client Type = %s%n", userName, userPass, userType);
		


		CouponSystem system = CouponSystem.getInstance();
		CouponClientFacade clientFacade = system.login(userName, userPass, userType);
		if (clientFacade != null) {
			switch (userType) {
			case ADMINISTRATOR:
				System.out.println("in admin");
				adminFacade = (AdminFacade) clientFacade;
				
//				m.scanner.nextLine();
//				company = m.inputCompany();
//				adminFacade.createCompany(company);
//				adminFacade.removeCompany(company);
				
				
				Collection<Company> collection = adminFacade.getAllCompanies();
				for (Company company2 : collection) {
					System.out.println(company2.getCompName());
				}
				System.out.println("Enter company name to edit:");
				m.scanner.nextLine();
				String cName = m.scanner.nextLine();
				company = adminFacade.getCompany(cName);
				System.out.println("Enter new passwor:");
//				company.setPassword(m.scanner.nextLine());	//	1
				String input = m.scanner.nextLine();	//	2
				company.setPassword(input);				//	2
				System.out.println("Enter new email:");
				input = m.scanner.nextLine();
				company.setEmail(input);
				adminFacade.updateCompany(company);
//				System.out.println(company);
				
//				long id = 0;
				
				
//				adminFacade.getCompany(id);///
//				adminFacade.getAllCompanies();
				
				
				
//				m.scanner.nextLine();
//				customer = m.inputCustomer();
//				adminFacade.createCustomer(customer);
//				adminFacade.createCustomer(customer);
//				adminFacade.removeCustomer(customer);
//				adminFacade.updateCustomer(customer);
				
//			    adminFacade.getCustomer();///
//				adminFacade.getAllCustomer();
				
				break;
			case COMPANY:
				System.out.println("in company");
				companyFacade = (CompanyFacade) clientFacade;
				
				m.scanner.nextLine();
				coupon = m.inputCoupon();
//				companyFacade.createCoupon(coupon);
//				companyFacade.removeCoupon(coupon);
//				companyFacade.updateCoupon(coupon);
//				companyFacade.getCoupon();//
//				companyFacade.getAllCoupon();
//				companyFacade.getAllCouponByType(couponType);
				
				
				
				coupon = new Coupon();
				list = (ArrayList<Coupon>) companyFacade.getAllCoupon();
				m.companyCoupons = list.toArray();
				
				System.out.println("Select coupon to remove:");
				m.printCompanyCoupons();
				
				coupon = (Coupon) m.companyCoupons[m.scanner.nextInt()];
				companyFacade.removeCoupon(coupon);
				
				break;
			case CUSTOMER:
				System.out.println("in customer");
				customerFacade = (CustomerFacade) clientFacade;
				
				m.scanner.nextLine();
				coupon = new Coupon();
				list = (ArrayList<Coupon>) customerFacade.getAllAvalibleCoupons();
				m.avalibleCoupons = list.toArray();
				
				System.out.println("Select coupon to purchase:");
				m.printCoupons();
				coupon = (Coupon) m.avalibleCoupons[m.scanner.nextInt()];
				
//				customerFacade.purchaseCoupon(coupon);
//				customerFacade.getAllPurchasedCoupons();
//				customerFacade.getAllPurchasedCouponsByType(couponType);
//				customerFacade.getAllPurchasedCouponsByPrice(price);
//				
				break;
			}
		} else {
			System.out.println("SHUTTING DOWN THE SYSTEM!");
			system.shutdown();
		}
	}	//	MAIN END

}
