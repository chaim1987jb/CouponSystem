package Main;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;

import basic_classes.Company;
import basic_classes.Coupon;
import basic_classes.CouponType;
import basic_classes.Customer;
import facade.AdminFacade;
import facade.ClientType;
import facade.CompanyFacade;
import facade.CouponSystem;
import facade.CustomerFacade;

public class Pre_Main {

	protected Scanner scanner;
	protected Calendar calendar;
	protected CouponType[] couponTypes;
	protected ClientType[] clientTypes;
	protected Object[] objects;
	protected Collection<Object> collectionOfObjects;
	protected AdminFacade adminFacade;
	protected CustomerFacade customerFacade;
	protected CompanyFacade companyFacade;

	protected Pre_Main() {
		this.scanner = new Scanner(System.in);

		this.clientTypes = new ClientType[3];
		this.clientTypes[0] = ClientType.ADMINISTRATOR;
		this.clientTypes[1] = ClientType.COMPANY;
		this.clientTypes[2] = ClientType.CUSTOMER;

		this.couponTypes = new CouponType[8];
		this.couponTypes[0] = CouponType.CAMPING;
		this.couponTypes[1] = CouponType.ELECTRICITY;
		this.couponTypes[2] = CouponType.FOOD;
		this.couponTypes[3] = CouponType.HEALTH;
		this.couponTypes[4] = CouponType.RESTURANS;
		this.couponTypes[5] = CouponType.SPORTS;
		this.couponTypes[6] = CouponType.TRAVALLING;
		this.couponTypes[7] = CouponType.OTHER;
	}

	protected Coupon inputCoupon() {
		System.out.println();
		System.out.println("\t <<< Creating coupon by user >>>");
		System.out.println();
		Coupon c = new Coupon();
		System.out.println("Enter coupon title: ");
		String s = scanner.nextLine();
		if (s.length() == 0) {
			s = scanner.nextLine();
		}
		c.setTitle(s);
		calendar = Calendar.getInstance();
		c.setStartDate(new Date(calendar.getTimeInMillis()));
		System.out.println("Enter for how many days coupon will be valid: ");
		calendar.add(Calendar.DAY_OF_MONTH, scanner.nextInt());
		c.setEndDate(new Date(calendar.getTimeInMillis()));
		System.out.println("Enter amount of coupons: ");
		c.setAmount(scanner.nextInt());
		c.setType(selectCouponType());
		System.out.println("Enter message of coupon: ");
		s = scanner.nextLine();
		if (s.length() == 0) {
			s = scanner.nextLine();
		}
		c.setMessage(s);
		System.out.println("Enter price of coupon: ");
		c.setPrice(scanner.nextDouble());
		System.out.println("Enter image of coupon: ");
		s = scanner.nextLine();
		if (s.length() == 0) {
			s = scanner.nextLine();
		}
		c.setImage(s);
		return c;
	}

	protected Company inputCompany() {
		System.out.println();
		System.out.println("\t <<< Creating company by user >>>");
		System.out.println();
		Company c = new Company();
		System.out.println("Enter company name: ");
		String s = scanner.nextLine();
		if (s.length() == 0) {
			s = scanner.nextLine();
		}
		c.setCompName(s);
		System.out.println("Enter company password: ");
		c.setPassword(scanner.nextLine());
		System.out.println("Enter company email: ");
		c.setEmail(scanner.nextLine());
		return c;
	}

	protected Customer inputCustomer() {
		System.out.println();
		System.out.println("\t <<< Creating customer by user >>>");
		System.out.println();
		Customer c = new Customer();
		System.out.println("Enter customer name: ");
		String s = scanner.nextLine();
		if (s.length() == 0) {
			s = scanner.nextLine();
		}
		c.setCustName(s);
		System.out.println("Enter customer password: ");
		c.setPassword(scanner.nextLine());
		return c;
	}

	protected CouponType selectCouponType() {
		System.out.println();
		System.out.println(" - Select coupon type from list - ");
		for (int i = 0; i < couponTypes.length; i++) {
			System.out.println("Enter " + (i+1) + " for ==> " + couponTypes[i].toString());
		}
		return couponTypes[scanner.nextInt() - 1];
	}

	protected ClientType selectClientType() {
		System.out.println();
		System.out.println(" - Select client type from list - ");
		for (int i = 0; i < clientTypes.length; i++) {
			System.out.println("Enter " + (i+1) + " for ==> " + clientTypes[i].toString());
		}
		return clientTypes[scanner.nextInt() - 1];
	}

	protected Coupon selectCouponFromList(Collection<Coupon> list) {
		objects = list.toArray();
		System.out.println();
		System.out.println(" - Select coupon from list - ");
		for (int i = 0; i < objects.length; i++) {
			System.out.println("Enter " + (i+1) + " for ==> " + ((Coupon) objects[i]).toString());
		}
		return (Coupon) objects[scanner.nextInt() - 1];
	}

	protected Company selectCompanyFromList(Collection<Company> list) {
		objects = list.toArray();
		System.out.println();
		System.out.println(" - Select company from list - ");
		for (int i = 0; i < objects.length; i++) {
			System.out.println("Enter " + (i+1) + " for ==> " + ((Company) objects[i]).toString());
		}
		return (Company) objects[scanner.nextInt() - 1];
	}

	protected Customer selectCustomerFromList(Collection<Customer> list) {
		objects = list.toArray();
		System.out.println();
		System.out.println(" - Select customer from list - ");
		for (int i = 0; i < objects.length; i++) {
			System.out.println("Enter " + (i+1) + " for ==> " + ((Customer) objects[i]).toString());
		}
		return (Customer) objects[scanner.nextInt() - 1];
	}

	protected void adminOptions() {
		Company company = null;
		Customer customer = null;
		System.out.println();
		System.out.println("\t ##### - Select admin option - #####");
		System.out.println("Enter 1 for ==> create new company");
		System.out.println("Enter 11 for ==> create new customer");
		System.out.println("Enter 2 for ==> remove company");
		System.out.println("Enter 22 for ==> remove customer");
		System.out.println("Enter 3 for ==> update company");
		System.out.println("Enter 33 for ==> update customer");
		System.out.println("Enter 4 for ==> get company");
		System.out.println("Enter 44 for ==> get customer");
		System.out.println("Enter 5 for ==> get all companies");
		System.out.println("Enter 55 for ==> get all customers");
		System.out.println("Enter 000 for ===> logout and shut down the system");
		int option = scanner.nextInt();
		switch (option) {
		case 1:		//	create company
			company = new Company();
			company = inputCompany();
			adminFacade.createCompany(company);
			adminOptions();
			break;
		case 11:	//	create customer
			customer = new Customer();
			customer = inputCustomer();
			adminFacade.createCustomer(customer);
			adminOptions();
			break;
		case 2:		//	remove company
			company = new Company();
			Collection<Company> comps = adminFacade.getAllCompanies();
			if (!comps.isEmpty()) {
				company = selectCompanyFromList(comps);
				adminFacade.removeCompany(company);
			} else System.out.println("There are no companies to view.");
			adminOptions();
			break;
		case 22:	//	remove customer
			customer = new Customer();
			Collection<Customer> custs = adminFacade.getAllCustomer();
			if (!custs.isEmpty()) {
				customer = selectCustomerFromList(custs);
				adminFacade.removeCustomer(customer);
			} else System.out.println("There are no customers to view.");
			adminOptions();
			break;
		case 3:		//	update company
			company = new Company();
			comps = adminFacade.getAllCompanies();
			if (!comps.isEmpty()) {
				company = selectCompanyFromList(comps);
				System.out.println("If you interested to change password -> enter: y. If dont't -> enter: n)");
				String s = scanner.nextLine();
				if (s.length() == 0) {
					s = scanner.nextLine();
				}
				if ("y".equalsIgnoreCase(s)) {
					System.out.println("Enter new password");
					company.setPassword(scanner.nextLine());
				}
				System.out.println("If you interested to change email -> enter: y. If dont't -> enter: n)");
				s = scanner.nextLine();
				if (s.length() == 0) {
					s = scanner.nextLine();
				}
				if ("y".equalsIgnoreCase(s)) {
					System.out.println("Enter new email");
					company.setEmail(scanner.nextLine());
				}
			} else System.out.println("There are no companies to view");
			adminFacade.updateCompany(company);
			adminOptions();
			break;
		case 33:	//	update customer
			customer = new Customer();
			custs = adminFacade.getAllCustomer();
			if (!custs.isEmpty()) {
				customer = selectCustomerFromList(custs);
				System.out.println("If you interested to change password -> enter: y. If dont't -> enter: n)");
				String s = scanner.nextLine();
				if (s.length() == 0) {
					s = scanner.nextLine();
				}
				if ("y".equalsIgnoreCase(s)) {
					System.out.println("Enter new password");
					customer.setPassword(scanner.nextLine());
				}
			} else System.out.println("There are no customers to view.");
			adminFacade.updateCustomer(customer);
			adminOptions();
			break;
		case 4:		//	get company
			company = new Company();
			comps = adminFacade.getAllCompanies();
			if (!comps.isEmpty()) {
				company = selectCompanyFromList(comps);
				System.out.println(company);
			} else System.out.println("There are no companies to view.");
			adminOptions();
			break;
		case 44:	//	get customer
			customer = new Customer();
			custs = adminFacade.getAllCustomer();
			if (!custs.isEmpty()) {
				customer = selectCustomerFromList(custs);
				System.out.println(customer);
			} else System.out.println("There are no customers to view.");
			adminOptions();
			break;
		case 5:		//	get all companies
			Collection<Company> companies = adminFacade.getAllCompanies();
			int i = 1;
			for (Company comp : companies) {
				System.out.println("(" + i + ") " + comp.toString());
				i++;
			}
			adminOptions();
			break;
		case 55:	//	get all customers
			Collection<Customer> customers = adminFacade.getAllCustomer();
			i = 1;
			for (Customer cust : customers) {
				System.out.println("(" + i + ") " + cust.toString());
				i++;
			}
			adminOptions();
			break;
		case 000:	//	logout
			CouponSystem.getInstance().shutdown();
			break;
		default:
			System.out.println();
			System.out.println("Exit the system? (YES: y <===> NO: n)");
			String s = scanner.nextLine();
			if (s.length() == 0) {
				s = scanner.nextLine();
			}
			if ("y".equalsIgnoreCase(s)) {
				CouponSystem.getInstance().shutdown();
			} else adminOptions();
			break;
		}
	}

	protected void companyOptions() {
		Coupon coupon = null;
		System.out.println();
		System.out.println("\t ##### - Select company option - #####");
		System.out.println("Enter 1 for ==> create new coupon");
		System.out.println("Enter 2 for ==> remove coupon");
		System.out.println("Enter 3 for ==> update coupon");
		System.out.println("Enter 4 for ==> get company");
		System.out.println("Enter 5 for ==> get all coupons of company");
		System.out.println("Enter 6 for ==> view all coupons of company by coupon type");
		System.out.println("Enter 7 for ==> view all coupons of company up to specified coupon price");
		System.out.println("Enter 8 for ==> view all coupons of company up to specified coupon expire date");
		System.out.println("Enter 000 for ===> logout and shut down the system");
		int option = scanner.nextInt();
		switch (option) {
		case 1:		//	create coupon
			coupon = new Coupon();
			coupon = inputCoupon();
			companyFacade.createCoupon(coupon);
			companyOptions();
			break;
		case 2:		//	remove coupon
			coupon = new Coupon();
			Collection<Coupon> coups = companyFacade.getAllCoupon();
			if (!coups.isEmpty()) {
				coupon = selectCouponFromList(coups);
				companyFacade.removeCoupon(coupon);
			} else System.out.println("There are no coupons to view.");
			companyOptions();
			break;
		case 3:		//	update coupon
			coupon = new Coupon();
			coups = companyFacade.getAllCoupon();
			if (!coups.isEmpty()) {
				coupon = selectCouponFromList(coups);
				System.out.println("If you interested to change expire date of coupon -> enter: y. "
						+ "If dont't -> enter: n)");
				String s = scanner.nextLine();
				if (s.length() == 0) {
					s = scanner.nextLine();
				}
				if ("y".equalsIgnoreCase(s)) {
					System.out.println("Enter YEAR for new expire date of coupon:");
					int year = scanner.nextInt();
					System.out.println("Enter MONTH for new expire date of coupon:");
					int month = scanner.nextInt();
					System.out.println("Enter DAY for new expire date of coupon:");
					int day = scanner.nextInt();
					System.out.println(coupon.toString());
					calendar = Calendar.getInstance();
					calendar.setTime(coupon.getEndDate());
					calendar.set(Calendar.YEAR, year);
					calendar.set(Calendar.MONTH, month);
					calendar.set(Calendar.DATE, day);
					coupon.setEndDate(new Date(calendar.getTimeInMillis()));
				}
				System.out.println("If you interested to change price -> enter: y. If dont't -> enter: n)");
				s = scanner.nextLine();
				if (s.length() == 0) {
					s = scanner.nextLine();
				}
				if ("y".equalsIgnoreCase(s)) {
					System.out.println("Enter new price");
					coupon.setPrice(scanner.nextDouble());
				}
				companyFacade.updateCoupon(coupon);
			} else System.out.println("There are no coupons to view.");
			companyOptions();
			break;
		case 4:		//	get company
			Collection<Company> comps = companyFacade.getAllCompanies();
			if (!comps.isEmpty()) {
				Company company = selectCompanyFromList(comps);
				companyFacade.getCompany(companyFacade.getCompanyID(company.getCompName()));
			} else System.out.println("There are no companies to view.");
			companyOptions();
			break;
		case 5:		//	get all coupons of company
			Collection<Coupon> coupons = companyFacade.getAllCoupon();
			int i = 1;
			for (Coupon c : coupons) {
				System.out.println("(" + i + ") " + c.toString());
				i++;
			}
			companyOptions();
			break;
		case 6:		//	get all coupons of company by coupon type
			coupons = companyFacade.getAllCouponByType(selectCouponType());
			i = 1;
			for (Coupon c : coupons) {
				System.out.println("(" + i + ") " + c.toString());
				i++;
			}
			companyOptions();
			break;
		case 7:		//	get all coupons of company up to specified coupon price
			System.out.println();
			System.out.println(" - Enter max price - ");
			coupons = companyFacade.getCouponByPrice(scanner.nextInt());
			i = 1;
			for (Coupon c : coupons) {
				System.out.println("(" + i + ") " + c.toString());
				i++;
			}
			companyOptions();
			break;
		case 8:		//	get all coupons of company up to specified coupon expire date
			System.out.println();
			System.out.println("Enter YEAR for new expire date of coupon:");
			int year = scanner.nextInt();
			System.out.println("Enter MONTH for new expire date of coupon:");
			int month = scanner.nextInt();
			System.out.println("Enter DAY for new expire date of coupon:");
			int day = scanner.nextInt();
			calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month-1);
			calendar.set(Calendar.DATE, day);
			Date date = new Date(calendar.getTimeInMillis());
			System.out.println(date);
			coupons = companyFacade.getCouponByEndDate(date);
			i = 1;
			for (Coupon c : coupons) {
				System.out.println("(" + i + ") " + c.toString());
				i++;
			}
			companyOptions();
			break;
		case 000:	//	logout
			CouponSystem.getInstance().shutdown();
			break;
		default:
			System.out.println();
			System.out.println("Exit the system? (YES: y <===> NO: n)");
			String s = scanner.nextLine();
			if (s.length() == 0) {
				s = scanner.nextLine();
			}
			if ("y".equalsIgnoreCase(s)) {
				CouponSystem.getInstance().shutdown();
			} else companyOptions();
			break;
		}
	}

	protected void customerOptions() {
		Coupon coupon = null;
		System.out.println();
		System.out.println("\t ##### - Select customer option - #####");
		System.out.println("Enter 1 for ==> purchase coupon");
		System.out.println("Enter 2 for ==> view purchase history");
		System.out.println("Enter 3 for ==> view purchase history by specified coupon type");
		System.out.println("Enter 4 for ==> view purchase history up to specified coupon price");
		int option = scanner.nextInt();
		switch (option) {
		case 1:		//	purchase coupon
			Collection<Coupon> coups = customerFacade.getAllAvalibleCoupons();
			if (!coups.isEmpty()) {
				coupon = selectCouponFromList(coups);
				customerFacade.purchaseCoupon(coupon);
			} else System.out.println("There are no coupons to view.");
			customerOptions();
			break;
		case 2:		//	view purchase history
			customerFacade.getAllPurchasedCoupons();
			customerOptions();
			break;
		case 3:		//	view purchase history by coupon type
			customerFacade.getAllPurchasedCouponsByType(selectCouponType());
			customerOptions();
			break;
		case 4:		//	view purchase history up to max price
			System.out.println();
			System.out.println(" - Enter max price - ");
			customerFacade.getAllPurchasedCouponsByPrice(scanner.nextDouble());
			customerOptions();
			break;
		default:
			System.out.println();
			System.out.println("Exit the system? (YES: y <===> NO: n)");
			String s = scanner.nextLine();
			if (s.length() == 0) {
				s = scanner.nextLine();
			}
			if ("y".equalsIgnoreCase(s)) {
				CouponSystem.getInstance().shutdown();
			} else customerOptions();
			break;
		}
	}
}
