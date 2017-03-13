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

public class CompanyFacade implements CouponClientFacade {
	private String companyName;
	private CompanyDBDAO companyDBDAO;
	private CouponDBDAO couponDBDAO;

	//	constructor
	public CompanyFacade() {
		try {
			companyDBDAO = new CompanyDBDAO();
			couponDBDAO = new CouponDBDAO();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	//	creates coupon only if coupon not exists
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
	public void removeCoupon(Coupon coupon) {
		try {
			couponDBDAO.removeCoupon(coupon);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CouponNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updated price and expire date of coupon
	public void updateCoupon(Coupon coupon) {
		try {
			couponDBDAO.updateCoupon(coupon);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CouponNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//	finds company in DB by id
	public Company getCompany(long id) {
		Company company = null;
		try {
			company = companyDBDAO.getCompany(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return company;
	}

	//	gets list of all coupons
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
