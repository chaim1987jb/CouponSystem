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

/**
 * This is the class that director all the CustomerFacade class possibility
 * in this system of the coupon project .
 * @author chaim_chagbi
 *
 */
public class CustomerFacade implements CouponClientFacade {
	
	/** Field represents the object CustomerName ....*/
	private String customerName;
	
	/** Field represents the object CustomerDBDAO ....*/
	private CustomerDBDAO customerDBDAO;
	
	/** Field represents the object CuponDBDAO ....*/
	private CouponDBDAO couponDBDAO;

	/** Constructor represents the object CustomerDBDAO and CuponDBDAO that adminFacade manage them ....*/
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
	
	/**
	 * this method gives instruction for the  CustomerDBDAO class
	 * to purchase a coupon all the logic and the work in front of the DB
	 * is in the  CustomerDBDAO class.
	 * not purchased it before (1 purchase max) and there is at least one in stock
	 * not expires
	 * @param coupon
	 */
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
	
	/**
	 * This method is ordered the CustomerDBDAO to get all of the
	 * coupons of the applicant customer own.
	 * all the work with the DB is in the  CustomerDBDAO class.
	 * @return ArrayList<Coupon>allPurchasedCoupon
	 */
	public void getAllPurchasedCoupons() {
		Collection<Coupon> coupons = getPurchasedCoupons();
		int index = 1;
		for (Coupon coupon : coupons) {
			System.out.println("(" + index + ") " + coupon.toString());
			index++;
		}
	}

	//	shows history of all purchases by coupon type
	
	/**
     * This method is ordered the  CustomerDBDAO to get all of the
	 * coupons of the applicant customer own in this case it will return 
	 * only those that fit the same type that received in the method signature.
	 * all the work with the DB is in the CustomerDBDAO class.
	 * @param CouponType.
	 * @return collection of coupons
	 */
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
	
	/**
	 * This method is ordered the  CustomerDBDAO to get all of the
	 * coupons of the applicant customer own in this case it will return 
	 * only those that max price that received in the method signature.
	 * all the work with the DB is in the  CustomerDBDAO class.
	 * @param price
	 * @return ArrayList<Coupon>allCoupon
	 */
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
	
	/**
	 * This method giving the option for the customer to watch all the Purchased
	 *  coupons in the system
	 * using the CouponDBDAO getAllCoupon method.
	 * @return ArrayList<Coupon>allPurchasesCoupon
	 */
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
	
	/**
	 * This method giving the option for the customer to watch all the Purchased
	 *  coupons in the system
	 * using the CouponDBDAO getAllavalibleCoupon method.
	 * @return ArrayList<Coupon>allAvalibleCoupon
	 */
	public Collection<Coupon> getAllAvalibleCoupons() {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			coupons = couponDBDAO.getAllCoupon();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return coupons;
	}
}
