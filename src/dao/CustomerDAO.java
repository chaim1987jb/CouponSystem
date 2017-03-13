package dao;

import java.sql.SQLException;
import java.util.Collection;

import basic_classes.Company;
import basic_classes.Coupon;
import basic_classes.Customer;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicateCustomerException;
import exceptions.SystemGoingDownException;

public interface CustomerDAO {
	
	/**
	 * 
	 * @param customer
	 * @throws DuplicateCustomerException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCustomer(Customer customer) throws DuplicateCustomerException, ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * 
	 * @param customer
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * 
	 * @param customer
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CustomerNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCustomer(Customer customer) throws ClassNotFoundException, SQLException, InterruptedException, CustomerNotFoundException, SystemGoingDownException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Customer getCustomer(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Customer> getAllCustomer() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * 
	 * @param custName
	 * @param password
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public boolean login(String custName, String password) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
