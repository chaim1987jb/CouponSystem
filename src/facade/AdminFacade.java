package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import basic_classes.Admin;
import basic_classes.Company;
import basic_classes.Customer;
import dao.CompanyDBDAO;
import dao.CustomerDBDAO;
import exceptions.CompanyNotFoundException;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicateCompanyException;
import exceptions.DuplicateCustomerException;
import exceptions.SystemGoingDownException;

/**
 * This is the class that director all the manager possibility
 * in this system of the coupon project .
 * @author chaim_chagbi
 *
 */
public class AdminFacade implements CouponClientFacade {
	
	/** Field represents the object CompanyDBDAO ....*/
	private CompanyDBDAO companyDBDAO;
	
	/** Field represents the object CustomerDBDAO ....*/
	private CustomerDBDAO customerDBDAO;

	//	constructor
	/** Constructor represents the object CustomerDBDAO and CompanyDBDAO that adminFacade manage them ....*/
	public AdminFacade() {
		try {
			companyDBDAO = new CompanyDBDAO();
			customerDBDAO = new CustomerDBDAO();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	//	adds company into DB only if it not exists
	
	/**
	 * this method gives instruction for  CompanyDBDAO class
	 * to set a company to the DB.it receiving a company instance and 
	 * conductor it to the createCompany method in CompanyDBDAO class
	 * @param company - specified company to create
	 */
	public void createCompany(Company company) {
		try {
			companyDBDAO.createCompany(company);
		} catch (ClassNotFoundException | DuplicateCompanyException | SQLException | InterruptedException
				| CompanyNotFoundException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	(1) removes company from DB
	//	(2) removes all coupons that was created by this company
	//	(3)	removes all coupons of this company that was purchased by any/all customer/s
	
	/**
	 * this method gives instruction for CompanyDBDAO class
	 * to remove a company from the DB and  deleting all coupons
	 *  that attached to the company
	 *  @param company - specified company to remove			
	 */
	public void removeCompany(Company company) {
		try {
			companyDBDAO.removeCompany(company);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CompanyNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updates password and email fields of company
	/**
	 * this method gives instruction for CompanyDBDAO class
	 * to update a company in the DB it receiving a company instance and 
	 * conductor it to the update method in CompanyDBDAO class
	 * @param company - specified company to update
	 */
	public void updateCompany(Company company) {
		try {
			companyDBDAO.updateCompany(company);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CompanyNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	finds company in DB by id
	
	/**
	 * this method gives instruction for  CompanyDBDAO class
	 * to extract a company from DB by receiving long id and 
	 * conductor it to the getCompanyById method in {@link CompanyDBDAO}class
	 * @param id - specified id to find
	 * @return company - return company if contains
	 */
	public Company getCompany(long id) {
		Company company = null;
		try {
			company = companyDBDAO.getCompany(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return company;
	}
	
	//	finds in DB id of company by name
	
	/**
	 * this method gives instruction for CompanyDBDAO class
	 * to extract a company from DB by name and receiving  from database long id and 
	 * conductor it to the getCompanyById method in CompanyDBDAO class
	 * @param companyName - specified company name to set
	 * @return long - long represents id of company
	 */
	public long getCompanyID(String companyName) {
		long id = -1;
		try {
			id = companyDBDAO.getID(companyName);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

	//	gets list of all companies
	
	/**
	 * this method gives instruction for CompanyDBDAO class
	 * to extract all the companies that exist in the DB .
	 * @return allCompanys ArrayList.
	 */
	public Collection<Company> getAllCompanies() {
		Collection<Company> companies = new ArrayList<>();
		try {
			companies = companyDBDAO.getAllCompanies();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return companies;
	}

	//	adds customer into DB only if it not exists
	
	/**
	 * this method gives instruction for CustomerDBDAO class
	 * to set a customer to the DB.it receiving a customer instance and 
	 * conductor it to the creatCustomer method in CustomerDBDAO class
	 * @param customer - specified customer to create
	 */
	public void createCustomer(Customer customer) {
		try {
			customerDBDAO.createCustomer(customer);
		} catch (ClassNotFoundException | DuplicateCustomerException | SQLException | InterruptedException
				| CustomerNotFoundException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	(1) removes customer from DB
	//	(2) removes all purchased coupons
	
	/**
	 * this method gives instruction for  CustomerDBDAO class
     * to remove a customer from the DB and remove all purchased coupons
	 *  that attached to the customer	
	 * @param customer - specified customer to remove
	 */
	public void removeCustomer(Customer customer) {
		try {
			customerDBDAO.removeCustomer(customer);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CustomerNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updates password of customer
	
	/**
	 * this method gives instruction for  CustomerDBDAO class
	 * to update a customer in the DB it  
	 * conductor it to the update method in CustomerDBDAO class	
	 * @param customer - specified customer to update
	 */
	public void updateCustomer(Customer customer) {
		try {
			customerDBDAO.updateCustomer(customer);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CustomerNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	finds customer in DB by id
	
	/**
	 * this method gives instruction for  CustomerDBDAO class
	 * to extract a customer from DB by receiving long id and 
	 * conductor it to the getCustomer method in  CustomerDBDAO class
	 * @param id - specified customer id to find
	 * @return customer - return customer if contains
	 */
	public Customer getCustomer(long id) {
		Customer customer = null;
		try {
			customer = customerDBDAO.getCustomer(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return customer;
	}
	//	finds in DB id of customer by name
	/**
	 * this method gives instruction for CustomerDBDAO class
	 * to extract a customer from DB by name and 
	 * conductor it to the getCustomer method in  CustomerDBDAO class
	 * @param name
	 * @return customer
	 */
	public long getCustomerID(String name) {
		long id = -1;
		try {
			id = customerDBDAO.getID(name);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return id;
	}

	//	gets list of all customers 
	
	/**
	 * this method gives instruction for  CustomerDBDAO class
	 * to extract all the customers that exist in the DB .
	 * @return allCustomer ArrayList.
	 */
	public Collection<Customer> getAllCustomer(){
		Collection<Customer> customers = new ArrayList<>();
		try {
			customers = customerDBDAO.getAllCustomer();
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return customers;
	}

	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) {
		if (Admin.NAME.equals(name) && Admin.PASSWORD.equals(password)) {
			System.out.println("\n##### - Administrator (" + name + ") trying to login.... - #####");
			System.out.println("##### - Login SUCCEEDED! - #####\n");
			return this;
		}
		System.out.println("\n##### - Administrator (" + name + ") trying to login.... - #####");
		System.out.println("##### - Login FAILED! - #####\n");
		return null;
	}
	
}
