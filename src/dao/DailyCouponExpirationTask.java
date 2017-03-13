package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import basic_classes.ConnectionPool;
import basic_classes.Coupon;
import exceptions.CouponNotFoundException;
import exceptions.SystemGoingDownException;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDBDAO couponDBDAO;
	private boolean quit;
	private Object key;

	public DailyCouponExpirationTask() throws ClassNotFoundException, SQLException {
		couponDBDAO = new CouponDBDAO();
		quit = false;
		key = new Object();
	}

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
