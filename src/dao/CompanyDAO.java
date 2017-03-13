package dao;

import java.sql.SQLException;
import java.util.Collection;

import basic_classes.Company;
import basic_classes.Coupon;
import exceptions.CompanyNotFoundException;
import exceptions.DuplicateCompanyException;
import exceptions.SystemGoingDownException;

public interface CompanyDAO {
	
	/**
	 * Adds new company into database
	 * @param company
	 * @throws DuplicateCompanyException
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void createCompany(Company company) throws DuplicateCompanyException, ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 *  remove  company from database
	 * 
	 * @param company
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void removeCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 * 
	 * @param company
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws CompanyNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public void updateCompany(Company company) throws ClassNotFoundException, SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Company getCompany(long id) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
	/**
	 * 
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;
	
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
	 * @param compName
	 * @param password
	 * @return
	 * @throws InterruptedException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws SystemGoingDownException 
	 */
	public boolean login(String compName, String password) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException;

}
