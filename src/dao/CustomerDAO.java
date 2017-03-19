package dao;

import java.sql.SQLException;
import java.util.Collection;

import basic_classes.Coupon;
import basic_classes.Customer;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicateCustomerException;
import exceptions.SystemGoingDownException;

/**
 * this interface class is designed to bound the implements class to apply the following method.
 * @author chaim_chagbi
 *
 */
public interface CustomerDAO {
	
	/**
	 * Adds new customer into database
	 * @param customer - customer to add into database
	 * @throws DuplicateCustomerException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCustomer(Customer customer) throws DuplicateCustomerException, ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * Remove specified customer from database
	 * @param customer -  customer to remove from data base
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * Update specified customer in database
	 * @param customer - - customer to update from database
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * Get specified customer from database 
	 * @param id - id customer from database
	 * @return - return company 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Customer getCustomer(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 *  Get collection of all available customers
	 * @return - collection of all available customers
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Customer> getAllCustomer() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 *  * Get collection of all available coupons
	 * @return -  collection of all available coupons  
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * * This Method is bounding to apply login in front of the DB
	 * @param custName - getting customer by name
	 * @param password - getting customer by password
	 * @return - return customer after login succeed  
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public boolean login(String custName, String password) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
