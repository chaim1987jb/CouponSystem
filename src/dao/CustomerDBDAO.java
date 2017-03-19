package dao;

	import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import basic_classes.ClientRetriever;
import basic_classes.Coupon;
import basic_classes.CouponType;
import basic_classes.Customer;
import exceptions.CouponNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicateCustomerException;
import exceptions.SystemGoingDownException;
	/**
	 * The class CustomerDBDAO is the class that do all the required access
	 * work with DB using the methods below.
	 * implements CustomerDAO and apply the following methods.
	 * this class using the SqlQueries class for all the SQL queries 
	 * in all methods.
	 * @author chaim_chagbi
	 */
	public class CustomerDBDAO implements CustomerDAO {
	
	/** Field represents connection pool ....*/
	private ConnectionPool pool;
	
	/**
	 * Creates new instance of connection pool
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CustomerDBDAO() throws ClassNotFoundException, SQLException {
		pool = ConnectionPool.getInstance();
	}

	@Override
	public void createCustomer(Customer customer) throws DuplicateCustomerException, ClassNotFoundException, 
	SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException {
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			Collection<Customer> existCustomers = getAllCustomer();
			String custNameToCheck = customer.getCustName();
			if (!existCustomers.isEmpty()) {
				for (Customer customerToCheck : existCustomers) {
					if (custNameToCheck.equalsIgnoreCase(customerToCheck.getCustName())) {
						throw new DuplicateCustomerException("Customer (" + custNameToCheck + ") already exists!");
					}
				}
			}
			
			tempConn = pool.getConnection();

			String insert = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES (?, ?)";
			stm = tempConn.prepareStatement(insert);
			stm.setString(1, customer.getCustName());
			stm.setString(2, customer.getPassword());
			stm.executeUpdate();

			System.out.println(" *** Adding customer (" + customer.getCustName() + ") into DB. - Result: SUCCEEDED! ***");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@SuppressWarnings("resource")
	@Override
	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, 
	InterruptedException, CustomerNotFoundException, SystemGoingDownException {
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		boolean flag = false;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String deleteFromCustomer = "DELETE FROM customer WHERE ID = ?";
			stm = tempConn.prepareStatement(deleteFromCustomer);
			stm.setLong(1, customer.getId());
			stm.executeUpdate();

			String deleteFromCustome_Coupon = "DELETE FROM customer_coupon WHERE CUST_ID = ?";
			stm = tempConn.prepareStatement(deleteFromCustome_Coupon);
			stm.setLong(1, customer.getId());
			stm.executeUpdate();
			
			tempConn.commit();
			flag = true;
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			
			System.out.print(" *** Removing customer (" + customer.getCustName() + ") from DB. - Result: ");
			if (flag) System.out.println("SUCCEEDED! ***");
			else System.out.println("FALED! ***");
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException, 
	InterruptedException, CustomerNotFoundException, SystemGoingDownException {
		if (customer == null) {
			throw new CustomerNotFoundException("Customer not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String updateCustomer = "UPDATE customer SET PASSWORD = ? WHERE ID = ?";
			stm = tempConn.prepareStatement(updateCustomer);
			stm.setString(1, customer.getPassword());
			stm.setLong	 (2, customer.getId());
			stm.executeUpdate();
			tempConn.commit();

			System.out.println(" *** Updating customer (" + customer.getCustName() + 
					") in DB. - Result: SUCCEEDED! ***");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@Override
	public Customer getCustomer(long id) throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Customer customer = null;
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectAll = "SELECT * FROM customer WHERE ID = ?";
			stm = tempConn.prepareStatement(selectAll);
			stm.setLong(1, id);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				customer = new Customer();
				customer.setId(id);
				customer.setCustName(rSet.getString("CUST_NAME"));
				customer.setPassword(rSet.getString("PASSWORD"));
			}
			customer.setCoupons(getCouponsByID(id));
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
			
			System.out.print("Getting customer from DB by id (" + id + "). - Result: ");
			if (customer != null) System.out.println("SUCCEEDED!");
			else System.out.println("FALED!");
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomer() throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Customer> allCustomers = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectAll = "SELECT * FROM customer";
			stm = tempConn.prepareStatement(selectAll);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Customer customer = new Customer();
				customer.setId(rSet.getLong("ID"));
				customer.setCustName(rSet.getString("CUST_NAME"));
				customer.setPassword(rSet.getString("PASSWORD"));
				customer.setCoupons(getCouponsByID(customer.getId()));
				allCustomers.add(customer);
			}
			
			System.out.print("Getting all exist customers from DB. - Result: SUCCEEDED!");
			if (allCustomers.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCustomers.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCustomers;
	}

	@Override
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException {
		long idOfLoggedinCustomer = ClientRetriever.getID();
		return getCouponsByID(idOfLoggedinCustomer);
	}

	@Override
	public boolean login(String custName, String password) throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		boolean flag = false;
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String selectCustomer = "SELECT ID FROM customer WHERE CUST_NAME = ? AND PASSWORD = ?";
			stm = tempConn.prepareStatement(selectCustomer);
			stm.setString(1, custName);
			stm.setString(2, password);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Customer customer = new Customer();
				customer.setId(rSet.getLong("ID"));
				customer.setCustName(custName);
				customer.setPassword(password);
				
				if (customer != null) flag = true;
			}
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();

			System.out.print(" ##### Customer (" + custName + ") trying to login.... - Result: ");
			if (flag) System.out.println("SUCCEEDED! #####");
			else System.out.println("FAILED! #####");
		}

		return flag;
	}
	
	/**
	 * * Checks if database contains customer with specified name. 
	 * If customer exists returns its id, otherwise -1.
	 * @param customerName - String representing specified customer name to check
	 * @return id of customer if it exists in DB, otherwise -1
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException
	 */
	public long getID(String customerName) throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		long customerID = -1;
		if (customerName != null && !customerName.isEmpty()) {
			Connection tempConn = null;
			PreparedStatement stm = null;
			ResultSet set = null;
			try {
				tempConn = pool.getConnection();

				String select = "SELECT ID FROM customer WHERE CUST_NAME = ?";
				stm = tempConn.prepareStatement(select);
				stm.setString(1, customerName);
				set = stm.executeQuery();
				if (set.next()) {
					customerID = set.getLong("ID");
				}
			} finally {
				if (tempConn != null) pool.returnConnection(tempConn);
				if (stm != null) stm.close();
				if (set != null) set.close();

				System.out.print(" - Getting id of customer by name (" + customerID + "). - Result: ");
				if (customerID == -1) System.out.println("FAILED! -");
				else System.out.println("SUCCEEDED! -");
			}
		}
		return customerID;
	}
	
	/**
	 * /**
	 * Gets all customers collection of coupons by specified customer id
	 * @param customerId - specified id of customer
	 * @return - collection of coupons  of specified customer
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException
	 */
	
	private Collection<Coupon> getCouponsByID(long customerID) throws ClassNotFoundException, 
	SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Coupon> allCoupons = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		ResultSet set = null;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String selectC = "SELECT COUPON_ID FROM customer_coupon WHERE CUST_ID = ?";
			stm = tempConn.prepareStatement(selectC);
			stm.setLong(1, customerID);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				String select = "SELECT * FROM coupon WHERE ID = ?";
				stm = tempConn.prepareStatement(select);
				stm.setLong(1, rSet.getLong("COUPON_ID"));

				set = stm.executeQuery();
				while (set.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(set.getLong("ID"));
					coupon.setTitle(set.getString("TITLE"));
					coupon.setStartDate(set.getDate("START_DATE"));
					coupon.setEndDate(set.getDate("END_DATE"));
					coupon.setAmount(set.getInt("AMOUNT"));
					coupon.setType(CouponType.valueOf(set.getString("TYPE")));
					coupon.setMessage(set.getString("MESSAGE"));
					coupon.setPrice(set.getDouble("PRICE"));
					coupon.setImage(set.getString("IMAGE"));
					allCoupons.add(coupon);
				}
			}
			tempConn.commit();
			
			System.out.print("Getting all customer's coupons from DB (by id). - Result: SUCCEEDED!");
			if (allCoupons.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCoupons.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
			if (set != null) set.close();
		}
		return allCoupons;
	}
	
	/**
	 * 
	 * @param coupon
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws CouponNotFoundException
	 * @throws SystemGoingDownException
	 */

	@SuppressWarnings("resource")
	public void purchaseCoupon(Coupon coupon) throws ClassNotFoundException, SQLException, 
	InterruptedException, CouponNotFoundException, SystemGoingDownException {
		if (coupon == null) {
			throw new CouponNotFoundException("Coupon not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		long idOfLoggedinCustomer = ClientRetriever.getID();
		
		Collection<Coupon> coupons = getCouponsByID(idOfLoggedinCustomer);
		boolean isInside = false;
		for (Coupon c : coupons) {
			if (c.getTitle().equals(coupon.getTitle())) isInside = true; 
		}
		
		Connection tempConn = null;
		PreparedStatement stm = null;
		
		System.out.print(" --= Purchasing coupon (" + coupon.getTitle() + "). ");
		System.out.print("Result: ");
		if (isInside) {
			System.out.println("FALED! - Not allowed to purchase more that one coupon! =--");
		} else {
			if (coupon.getAmount() == 0) {
				System.out.println("FALED! - Coupon out of stock! =--");
			} else {
				if (new Date(Calendar.getInstance().getTime().getTime()).after(coupon.getEndDate())) {
					System.out.println("FALED! - Coupon expired! =--");
				} else {
					try {
						tempConn = pool.getConnection();
						tempConn.setAutoCommit(false);

						String update = "UPDATE coupon SET AMOUNT = ? WHERE ID = ?";
						stm = tempConn.prepareStatement(update);
						stm.setDouble(1, coupon.getAmount() - 1);
						stm.setLong(2, coupon.getId());
						stm.executeUpdate();

						String insert = "INSERT INTO customer_coupon (CUST_ID, COUPON_ID) VALUES (?, ?)";
						stm = tempConn.prepareStatement(insert);
						stm.setLong(1, idOfLoggedinCustomer);
						stm.setLong(2, coupon.getId());
						stm.executeUpdate();

						tempConn.commit();
						
						System.out.println("SUCCEEDED! =--");
					} finally {
						if (tempConn != null) pool.returnConnection(tempConn);
						if (stm != null) stm.close();
					}
				}
			}
		}
	}	
}
