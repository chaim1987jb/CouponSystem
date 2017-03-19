package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import basic_classes.ClientRetriever;
import basic_classes.Coupon;
import basic_classes.CouponType;
import exceptions.CouponNotFoundException;
import exceptions.DuplicateCouponException;
import exceptions.SystemGoingDownException;

public class CouponDBDAO implements CouponDAO {

	private ConnectionPool pool;

	public CouponDBDAO() throws ClassNotFoundException, SQLException {
		pool = ConnectionPool.getInstance();
	}

	@SuppressWarnings("resource")
	@Override
	public void createCoupon(Coupon coupon) throws DuplicateCouponException, ClassNotFoundException, 
	SQLException, InterruptedException, CouponNotFoundException, SystemGoingDownException {
		if (coupon == null) {
			throw new CouponNotFoundException("Coupon not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		long idOfLoggedinCompany = ClientRetriever.getID();
		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			Collection<Coupon> existCoupons = getAllCoupon();
			String titleToCheck = coupon.getTitle();
			if (!existCoupons.isEmpty()) {
				for (Coupon couponToCheck : existCoupons) {
					if (titleToCheck.equalsIgnoreCase(couponToCheck.getTitle())) {
						throw new DuplicateCouponException("Coupon (" + titleToCheck + ") already exists!");
					}
				}
			}

			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);
			
			String insertIntoCoupon = "INSERT INTO coupon "
					+ "(TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE)" 
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			stm = tempConn.prepareStatement(insertIntoCoupon, PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, coupon.getTitle());
			stm.setDate	 (2, coupon.getStartDate());
			stm.setDate	 (3, coupon.getEndDate());
			stm.setInt	 (4, coupon.getAmount());
			stm.setString(5, coupon.getType().toString());
			stm.setString(6, coupon.getMessage());
			stm.setDouble(7, coupon.getPrice());
			stm.setString(8, coupon.getImage());
			stm.executeUpdate();

			ResultSet rSet = stm.getGeneratedKeys();
			long id = 0;
			if (rSet.next()) {
				id = rSet.getLong(1);
			}
			
			String insert = "INSERT INTO company_coupon (COMP_ID, COUPON_ID) VALUES (?, ?)";
			stm = tempConn.prepareStatement(insert);
			stm.setLong(1, idOfLoggedinCompany);
			stm.setLong(2, id);
			stm.executeUpdate();
			
			tempConn.commit();
			
			System.out.println("Adding coupon (" + coupon.getTitle() + ") into DB. - Result: SUCCEEDED!");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@SuppressWarnings("resource")
	@Override
	public void removeCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, 
	InterruptedException, CouponNotFoundException, SystemGoingDownException {
		if (coupon == null) {
			throw new CouponNotFoundException("Coupon not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);
			
			String deleteFromCoupon = "DELETE FROM coupon WHERE ID = ?";
			stm = tempConn.prepareStatement(deleteFromCoupon);
			stm.setLong(1, coupon.getId());
			stm.executeUpdate();

			String deleteFromCompany_Coupon = "DELETE FROM company_coupon WHERE COUPON_ID = ?";
			stm = tempConn.prepareStatement(deleteFromCompany_Coupon);
			stm.setLong(1, coupon.getId());
			stm.executeUpdate();

			String deleteFromCustomer_Coupon = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";
			stm = tempConn.prepareStatement(deleteFromCustomer_Coupon);
			stm.setLong(1, coupon.getId());
			stm.executeUpdate();

			tempConn.commit();
			
			System.out.println("Removing coupon (" + coupon.getTitle() + ") from DB. - Result: SUCCEEDED!");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, 
	InterruptedException, CouponNotFoundException, SystemGoingDownException {
		if (coupon == null) {
			throw new CouponNotFoundException("Coupon not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String updateCoupon = "UPDATE coupon SET END_DATE = ?, PRICE = ? WHERE ID = ?";
			stm = tempConn.prepareStatement(updateCoupon);
			stm.setDate	 (1, coupon.getEndDate());
			stm.setDouble(2, coupon.getPrice());
			stm.setLong	 (3, coupon.getId());
			stm.executeUpdate();
			tempConn.commit();

			System.out.println("Updating coupon (" + coupon.getTitle() + ") in DB. - Result: SUCCEEDED!");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@Override
	public Coupon getCoupon(long id) throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Coupon coupon = null;
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectAll = "SELECT * FROM coupon WHERE ID = ?";
			stm = tempConn.prepareStatement(selectAll);
			stm.setLong(1, id);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				coupon = new Coupon();
				coupon.setId(id);
				coupon.setTitle(rSet.getString("TITLE"));
				coupon.setStartDate(rSet.getDate("START_DATE"));
				coupon.setEndDate(rSet.getDate("END_DATE"));
				coupon.setAmount(rSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rSet.getString("TYPE")));
				coupon.setMessage(rSet.getString("MESSAGE"));
				coupon.setPrice(rSet.getDouble("PRICE"));
				coupon.setImage(rSet.getString("IMAGE"));
			}
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
			
			System.out.print("Getting coupon from DB by id (" + id + "). - Result: ");
			if (coupon != null) System.out.println("SUCCEEDED!");
			else System.out.println("FALED!");
		}
		return coupon;
	}
	
	@Override
	public Collection<Coupon> getAllCoupon() throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectAll = "SELECT * FROM coupon";
			stm = tempConn.prepareStatement(selectAll);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rSet.getLong("ID"));
				coupon.setTitle(rSet.getString("TITLE"));
				coupon.setStartDate(rSet.getDate("START_DATE"));
				coupon.setEndDate(rSet.getDate("END_DATE"));
				coupon.setAmount(rSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rSet.getString("TYPE")));
				coupon.setMessage(rSet.getString("MESSAGE"));
				coupon.setPrice(rSet.getDouble("PRICE"));
				coupon.setImage(rSet.getString("IMAGE"));
				allCoupons.add(coupon);
			}
			
			System.out.print("Getting all exist coupons from DB. - Result: SUCCEEDED!");
			if (allCoupons.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCoupons.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCoupons;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws ClassNotFoundException, 
	SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectAll = "SELECT * FROM coupon WHERE TYPE = ?";
			stm = tempConn.prepareStatement(selectAll);
			stm.setString(1, couponType.toString());

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rSet.getLong("ID"));
				coupon.setTitle(rSet.getString("TITLE"));
				coupon.setStartDate(rSet.getDate("START_DATE"));
				coupon.setEndDate(rSet.getDate("END_DATE"));
				coupon.setAmount(rSet.getInt("AMOUNT"));
				coupon.setType(couponType);
				coupon.setMessage(rSet.getString("MESSAGE"));
				coupon.setPrice(rSet.getDouble("PRICE"));
				coupon.setImage(rSet.getString("IMAGE"));
				allCoupons.add(coupon);
			}
			
			System.out.print("Getting all exist coupons from DB by type (" + couponType.toString() +
					"). - Result: SUCCEEDED!");
			if (allCoupons.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCoupons.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCoupons;
	}
	/**
	 * This method is extract the coupons  ArrayList by price
     * the type is a  CouponType enumClass type that defines the category of the coupon.
     * quarry and returning an ArrayList.
	 * @param maxPrice - limits price of collection of coupon
	 * @return - collection of coupons by price
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException
	 */
	public Collection<Coupon> getCouponByPrice(double maxPrice) throws ClassNotFoundException, 
	SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectCoupon = "SELECT * FROM coupon WHERE PRICE < ?";
			stm = tempConn.prepareStatement(selectCoupon);
			stm.setDouble(1, maxPrice);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rSet.getLong("ID"));
				coupon.setTitle(rSet.getString("TITLE"));
				coupon.setStartDate(rSet.getDate("START_DATE"));
				coupon.setEndDate(rSet.getDate("END_DATE"));
				coupon.setAmount(rSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rSet.getString("TYPE")));
				coupon.setMessage(rSet.getString("MESSAGE"));
				coupon.setPrice(maxPrice);
				coupon.setImage(rSet.getString("IMAGE"));
				allCoupons.add(coupon);
			}
			
			System.out.print("Getting all exist coupons from DB up to max price (" + maxPrice + 
					"). - Result: SUCCEEDED!");
			if (allCoupons.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCoupons.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCoupons;
	}
	/**
	 * This method is extract the coupons  ArrayList by endDate
	 * @param endDate - return coupon by endDate
	 * @return - collection of coupons by endDate 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException
	 */
	public Collection<Coupon> getCouponByEndDate(Date endDate) throws ClassNotFoundException, 
	SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectCoupon = "SELECT * FROM coupon WHERE DATE(END_DATE) < ?";
			stm = tempConn.prepareStatement(selectCoupon);
			stm.setDate(1, endDate);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rSet.getLong("ID"));
				coupon.setTitle(rSet.getString("TITLE"));
				coupon.setStartDate(rSet.getDate("START_DATE"));
				coupon.setEndDate(endDate);
				coupon.setAmount(rSet.getInt("AMOUNT"));
				coupon.setType(CouponType.valueOf(rSet.getString("TYPE")));
				coupon.setMessage(rSet.getString("MESSAGE"));
				coupon.setPrice(rSet.getDouble("PRICE"));
				coupon.setImage(rSet.getString("IMAGE"));
				allCoupons.add(coupon);
			}
			
			System.out.print("Getting all coupons from DB up to " + endDate.toString() + ". - Result: SUCCEEDED!");
			if (allCoupons.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCoupons.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCoupons;
	}		
}
