package dao;

import java.sql.SQLException;
import java.util.Collection;
import basic_classes.Company;
import basic_classes.Coupon;
import exceptions.CompanyNotFoundException;
import exceptions.DuplicateCompanyException;
import exceptions.SystemGoingDownException;

/**
 * This interface class is designed to bound the implements class to apply the following method.
 * @author chaim_chagbi
 *
 */
public interface CompanyDAO {
	
	/**
	 * Adds new company into database
	 * @param company - company to add into database
	 * @throws DuplicateCompanyException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCompany(Company company) throws DuplicateCompanyException, ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 * Remove specified company from database
	 * @param company - company to remove from data base
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 * Update specified company in database
	 * @param company - company to update from database
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 * Get specified company from database 
	 * @param id - id company from database
	 * @return - return company 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Company getCompany(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * Get collection of all available companies
	 * @return - collection of all available companies
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * Get collection of all available coupons
	 * @return -  collection of all available coupons 
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * This Method is bounding to apply login in front of the DB
	 * @param compName - getting company by name
	 * @param password - getting company by password
	 * @return - return company after login succeed  
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public boolean login(String compName, String password) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
