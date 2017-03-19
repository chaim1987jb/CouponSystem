package facade;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import basic_classes.Company;
import basic_classes.ClientRetriever;
import basic_classes.Coupon;
import basic_classes.CouponType;
import dao.CompanyDBDAO;
import dao.CouponDBDAO;
import exceptions.CouponNotFoundException;
import exceptions.DuplicateCouponException;
import exceptions.SystemGoingDownException;

/**
 * This is the class that director all the CompanyFacade class possibility
 * in this system of the coupon project .
 * @author chaim_chagbi
 *
 */
public class CompanyFacade implements CouponClientFacade {
	
	/** Field represents the object CompanyName ....*/
	private String companyName;
	
	/** Field represents the object CompanyDBDAO ....*/
	private CompanyDBDAO companyDBDAO;
	
	/** Field represents the object CouponDBDAO ....*/
	private CouponDBDAO couponDBDAO;

	//	constructor
	
	/** Constructor represents the object CustomerDBDAO and CompanyDBDAO that adminFacade manage them ....*/
	public CompanyFacade() {
		try {
			companyDBDAO = new CompanyDBDAO();
			couponDBDAO = new CouponDBDAO();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	//	creates coupon only if coupon not exists
	
	/**
	 * this method gives instruction for  CouponDBDAO class
	 * to set a coupon to the DB.it receiving a coupon instance and 
	 * conductor it to the creatCoupon method in  CouponDBDAO  class
	 * @param coupon
	 */
	public void createCoupon(Coupon coupon) {
		try {
			ClientRetriever.setID(companyDBDAO.getID(companyName));
			couponDBDAO.createCoupon(coupon);
		} catch (ClassNotFoundException | DuplicateCouponException | SQLException | InterruptedException
				| CouponNotFoundException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	(1) removes coupon from DB
	//	(2) removes coupon from all customers that purchased it
	
	/**
	 * this method gives instruction for  CouponDBDAO class
     * to remove a coupon from the DB and 
	 * conductor it to the removeCoupon method in  CouponDBDAO class removes 
	 * coupon from all customers that purchased it			
	 * @param coupon
	 */
	public void removeCoupon(Coupon coupon) {
		try {
			couponDBDAO.removeCoupon(coupon);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CouponNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updated price and expire date of coupon
	
	/**
	 * This method gives instruction forCouponDBDAO class
	 * to update a coupon in the DB the price and expire date and  
	 * updateCoupon method in CouponDBDAO class
	 * @param coupon
	 */	
	public void updateCoupon(Coupon coupon) {
		try {
			couponDBDAO.updateCoupon(coupon);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CouponNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//	finds company in DB by id
	
	/**
	 * This method gives instruction for  CouponDBDAO class
	 * to extract a coupon from DB by receiving long id and 
	 * conductor it to the getCouponById method in  CouponDBDAO class
	 * @param id
	 * @return coupon
	 */
	public Company getCompany(long id) {
		Company company = null;
		try {
			company = companyDBDAO.getCompany(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return company;
	}
	
	//	finds in DB id of company by name
	
	/**
	 * This method gives instruction for CouponDBDAO class
	 * to extract a coupon from DB by receiving compName and 
	 * conductor it to the getCouponById method in CouponDBDAO class
	 * @param id
	 * @return coupon
	 */
	public long getCompanyID(String companyName) {
		long id = -1;
		try {
			id = companyDBDAO.getID(companyName);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return id;
	}
	
	//	gets list of all companies
	
	/**
	  * this method gives instruction for  CompanyDBDAO class
	 * to extract all the companies that exist in the DB .
	 * @return allCoupons  ArrayList.
	 */
	public Collection<Company> getAllCompanies() {
		Collection<Company> companies = new ArrayList<>();
		try {
			companies = companyDBDAO.getAllCompanies();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return companies;
	}

	//	gets list of all coupons
	
	/**
	  * this method gives instruction for  CouponDBDAO class
	 * to extract all the coupons that exist in the DB .
	 * @return allCoupons  ArrayList.
	 */
	public Collection<Coupon> getAllCoupon() {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			ClientRetriever.setID(companyDBDAO.getID(companyName));
			coupons = companyDBDAO.getCoupons();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return coupons;
	}

	//	gets list of all coupons by specified coupon type
	
	/**
	  * this method gives instruction for CouponDBDAO class
	 * to extract all the coupons by specified coupon type that exist in the DB .
	 * @return allCoupons ArrayList.
	 */
	public Collection<Coupon> getAllCouponByType(CouponType couponType) {
		Collection<Coupon> coupons = getAllCoupon();
		for (Coupon coupon : coupons) {
			if (!couponType.equals(coupon.getType())) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}
	
	//	gets list of all coupons up to specified price
	
	/**
	  * this method gives instruction for CouponDBDAO class
	 * to extract all the coupons by specified price that exist in the DB .
	 * @return allCouponsByPrice ArrayList.
	 */
	public Collection<Coupon> getCouponByPrice(double maxPrice) {
		Collection<Coupon> coupons = getAllCoupon();
		for (Coupon coupon : coupons) {
			if (maxPrice < coupon.getPrice()) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}
	
	//	gets list of all coupons up to specified expire date
	
	/**
	  * this method gives instruction for CouponDBDAO class
	 * to extract all the coupons by specified endDate that exist in the DB .
	 * @return allCouponsByEndDate ArrayList.
	 */
	public Collection<Coupon> getCouponByEndDate(Date endDate) {
		Collection<Coupon> coupons = getAllCoupon();
		for (Coupon coupon : coupons) {
			if (coupon.getEndDate().after(endDate)) {
				coupons.remove(coupon);
			}
		}
		return coupons;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		try {
			if (companyDBDAO.login(name, password)) {
				companyName = name;
				return this;
			}
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
