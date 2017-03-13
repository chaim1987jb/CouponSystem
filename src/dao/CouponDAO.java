package dao;

import java.sql.SQLException;
import java.util.Collection;

import basic_classes.Coupon;
import basic_classes.CouponType;
import exceptions.CouponNotFoundException;
import exceptions.DuplicateCouponException;
import exceptions.SystemGoingDownException;

public interface CouponDAO {
	
	/**
	 * @param coupon
	 * @throws DuplicateCouponException 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCoupon(Coupon coupon) throws DuplicateCouponException, ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * @param coupon
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * @param coupon
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CouponNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException;
	
	/**
	 * @param id
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Coupon getCoupon(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getAllCoupon() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * @param couponType
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getCouponByType(CouponType couponType) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
