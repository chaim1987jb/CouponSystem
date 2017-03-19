package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import basic_classes.Coupon;
import exceptions.CouponNotFoundException;
import exceptions.SystemGoingDownException;
	/**
	 * This is a {@link Thread} implements Runnable
	 * It's a thread that once a day going through all
	 * the last dates of all the coupons in the DB and delete the expired
	 * one from the DB.
	 * @author chaim_chagbi
	 *
	 */
	public class DailyCouponExpirationTask implements Runnable {
	/** Field represents couponDBDAO  ....*/
	private CouponDBDAO couponDBDAO;
	
	/** Field represents boolean   ....*/
	private boolean quit;
	
	/** Field represents object   ....*/
	private Object key;

	public DailyCouponExpirationTask() throws ClassNotFoundException, SQLException {
		couponDBDAO = new CouponDBDAO();
		quit = false;
		key = new Object();
	}
	
	/**
	 * An Override method that exercises the runnable super class method.
	 * it has an ArrayList of coupons and a Date as a members
	 * for the task it will add all the coupons in the DB to the allCoupons  ArrayList
	 * and go through each one of them and remove the expired by contrasting the 
	 * end date of the coupon to the endDate member that up date with the latest date
	 * it will run once a day until the the boolean runner will receive a negative value.
	 * 
	 */
	@Override
	public void run(){
		while (!quit) {
			synchronized (key) {
				try {
					int count = 0;
					Collection<Coupon> coupons = new ArrayList<>();
					coupons = couponDBDAO.getAllCoupon();
					for (Coupon coupon : coupons) {
						if (new Date().after(coupon.getEndDate())) {
							couponDBDAO.removeCoupon(coupon);
							count++;
						}
					}
					System.out.print("Number of expired coupons was removed by automatic daily check system: ");
					System.out.println(count);
					//	wait up to 24 hours
					key.wait(86400000);
				} catch (ClassNotFoundException | SQLException | InterruptedException | 
						SystemGoingDownException | CouponNotFoundException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	public void stopTask() throws ClassNotFoundException, SQLException, InterruptedException {
		quit = true;
		synchronized (key) {
			key.notify();
		}
		ConnectionPool.getInstance().closeConnections();
	}

}
