package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import basic_classes.ClientRetriever;
import basic_classes.Coupon;
import basic_classes.CouponType;
import dao.CouponDBDAO;
import dao.CustomerDBDAO;
import exceptions.CouponNotFoundException;
import exceptions.SystemGoingDownException;

public class CustomerFacade implements CouponClientFacade {
	private String customerName;
	private CustomerDBDAO customerDBDAO;
	private CouponDBDAO couponDBDAO;

	public CustomerFacade() {
		try {
			customerDBDAO = new CustomerDBDAO();
			couponDBDAO = new CouponDBDAO();
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	//	purchases coupon only if:
	//	(1) not purchased it before (1 purchase max)
	//	(2) there is at least one in stock
	//	(3) not expires
	public void purchaseCoupon(Coupon coupon) {
		try {
			ClientRetriever.setID(customerDBDAO.getID(customerName));
			customerDBDAO.purchaseCoupon(coupon);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CouponNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	shows history of all purchases
	public void getAllPurchasedCoupons() {
		Collection<Coupon> coupons = getPurchasedCoupons();
		int index = 1;
		for (Coupon coupon : coupons) {
			System.out.println("(" + index + ") " + coupon.toString());
			index++;
		}
	}

	//	shows history of all purchases by coupon type
	public void getAllPurchasedCouponsByType(CouponType couponType) {
		Collection<Coupon> coupons = getPurchasedCoupons();
		int index = 1;
		for (Coupon coupon : coupons) {
			if (couponType.equals(coupon.getType())) {
				System.out.println("(" + index + ") " + coupon.toString());
				index++;
			}
		}
	}

	//	shows history of all purchases up to max price
	public void getAllPurchasedCouponsByPrice(double price) {
		Collection<Coupon> coupons = getPurchasedCoupons();
		int index = 1;
		for (Coupon coupon : coupons) {
			if (price == coupon.getPrice()) {
				System.out.println("(" + index + ") " + coupon.toString());
				index++;
			}
		}
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		try {
			if (customerDBDAO.login(name, password)) {
				customerName = name;
				return this;
			}
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	//	gets history of all purchases
	private Collection<Coupon> getPurchasedCoupons() {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			ClientRetriever.setID(customerDBDAO.getID(customerName));
			coupons = customerDBDAO.getCoupons();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return coupons;
	}

	//	get all avalible coupons
	public Collection<Coupon> getAllAvalibleCoupons() {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = couponDBDAO.getAllCoupon();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return coupons;
	}
	
	//	get coupon by title
//	public Coupon getCoupon(String title) {
//		Coupon coupon = new Coupon();
//		try {
//			coupon = couponDBDAO.getCoupon(couponDBDAO.getID(title));
//		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
//			System.err.println(e.getMessage());
//		}
//		return coupon;
//	}

}
