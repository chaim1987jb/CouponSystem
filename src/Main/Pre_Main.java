package Main;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;

import basic_classes.Company;
import basic_classes.Coupon;
import basic_classes.CouponType;
import basic_classes.Customer;
import facade.ClientType;

public class Pre_Main {

	Scanner scanner;
	CouponType[] couponTypes;
	ClientType[] clientTypes;
	Object[] avalibleCoupons;
	Object[] companyCoupons;
	Calendar calendar;

	public Pre_Main() {
		this.scanner = new Scanner(System.in);

		this.clientTypes = new ClientType[3];
		this.clientTypes[0] = ClientType.ADMINISTRATOR;
		this.clientTypes[1] = ClientType.COMPANY;
		this.clientTypes[2] = ClientType.CUSTOMER;

		this.couponTypes = new CouponType[7];
		this.couponTypes[0] = CouponType.CAMPING;
		this.couponTypes[1] = CouponType.ELECTRICITY;
		this.couponTypes[2] = CouponType.FOOD;
		this.couponTypes[3] = CouponType.HEALTH;
		this.couponTypes[4] = CouponType.RESTURANS;
		this.couponTypes[5] = CouponType.SPORTS;
		this.couponTypes[6] = CouponType.TRAVALLING;
	}

	protected Coupon inputCoupon() {
		System.out.println();
		System.out.println("\t <<< Creating coupon by user >>>");
		System.out.println();
		Coupon c = new Coupon();
		System.out.println("Enter coupon title: ");
//		scanner.nextLine();
		c.setTitle(scanner.nextLine());
		calendar = Calendar.getInstance();
		c.setStartDate(new Date(calendar.getTimeInMillis()));
		System.out.println("Enter for how many days coupon will be valid: ");
		calendar.add(Calendar.DAY_OF_MONTH, scanner.nextInt());
		c.setEndDate(new Date(calendar.getTimeInMillis()));
		System.out.println("Enter amount of coupons: ");
		c.setAmount(scanner.nextInt());
		System.out.println("Select coupon type from list:");
		printCouponTypes();
		c.setType(couponTypes[scanner.nextInt()]);
		System.out.println("Enter message of coupon: ");
		scanner.nextLine();
		c.setMessage(scanner.nextLine());
		System.out.println("Enter price of coupon: ");
		c.setPrice(scanner.nextDouble());
		System.out.println("Enter image of coupon: ");
		scanner.nextLine();
		c.setImage(scanner.nextLine());
		return c;
	}

	protected Company inputCompany() {
		System.out.println();
		System.out.println("\t <<< Creating company by user >>>");
		System.out.println();
		Company c = new Company();
		System.out.println("Enter company name: ");
//		scanner.nextLine();
		c.setCompName(scanner.nextLine());
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
//		scanner.nextLine();
		c.setCustName(scanner.nextLine());
		System.out.println("Enter customer password: ");
		c.setPassword(scanner.nextLine());
		return c;
	}

	protected void printCouponTypes() {
		for (int i = 0; i < couponTypes.length; i++) {
			System.out.println("Enter " + i + " for ==> " + couponTypes[i].toString());
		}
	}

	protected void printClientTypes() {
		for (int i = 0; i < clientTypes.length; i++) {
			System.out.println("Enter " + i + " for ==> " + clientTypes[i].toString());
		}
	}
	
	protected void printCoupons() {
		for (int i = 0; i < avalibleCoupons.length; i++) {
			System.out.println("Enter " + i + " for ==> " + ((Coupon) avalibleCoupons[i]).getTitle());
		}
	}
	
	protected void printCompanyCoupons() {
		for (int i = 0; i < companyCoupons.length; i++) {
			System.out.println("Enter " + i + " for ==> " + ((Coupon) companyCoupons[i]).getTitle());
		}
	}

}
