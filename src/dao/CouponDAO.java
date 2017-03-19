package dao;

import java.sql.SQLException;
import java.util.Collection;

import basic_classes.Coupon;
import basic_classes.CouponType;
import exceptions.CouponNotFoundException;
import exceptions.DuplicateCouponException;
import exceptions.SystemGoingDownException;

/**
 * This interface class is designed to bound the implements class to apply the following method.
 * @author chaim_chagbi
 *
 */
public interface CouponDAO {
	
	/**
	 * Adds new coupon into database
	 * @param coupon - coupon to add into database
	 * @throws DuplicateCouponException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCoupon(Coupon coupon) throws DuplicateCouponException, ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * Remove specified coupon from database
	 * @param coupon - coupon to remove from database
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * Update specified coupon into database
	 * @param coupon - coupon to update from database
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * Get specified coupon from database 
	 * @param id - id coupon from database
	 * @return - return company 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Coupon getCoupon(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * Get collection of all available coupons
	 * @return -  collection of all available coupons
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getAllCoupon() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * Get collection of all available coupons by type
	 * @param couponType - collection of all coupon by type
	 * @return - collection of all available coupons by type
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
