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

public class AdminFacade implements CouponClientFacade {
	private CompanyDBDAO companyDBDAO;
	private CustomerDBDAO customerDBDAO;

	//	constructor
	public AdminFacade() {
		try {
			companyDBDAO = new CompanyDBDAO();
			customerDBDAO = new CustomerDBDAO();
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	//	adds company into DB only if it not exists
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
	public void removeCompany(Company company) {
		try {
			companyDBDAO.removeCompany(company);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CompanyNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updates password and email fields of company
	public void updateCompany(Company company) {
		try {
			companyDBDAO.updateCompany(company);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CompanyNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	finds company in DB by id
	public Company getCompany(long id) {
		Company company = null;
		try {
			company = companyDBDAO.getCompany(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return company;
	}
	
	//	finds company in DB by name
	public Company getCompany(String name) {
		Company company = null;
		try {
			company = companyDBDAO.getCompany(companyDBDAO.getID(name));
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return company;
	}

	//	gets list of all companies
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
	public void removeCustomer(Customer customer) {
		try {
			customerDBDAO.removeCustomer(customer);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CustomerNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	updates password of customer
	public void updateCustomer(Customer customer) {
		try {
			customerDBDAO.updateCustomer(customer);
		} catch (ClassNotFoundException | SQLException | InterruptedException | CustomerNotFoundException | 
				SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
	}

	//	finds customer in DB by id
	public Customer getCustomer(long id) {
		Customer customer = null;
		try {
			customer = customerDBDAO.getCustomer(id);
		} catch (ClassNotFoundException | SQLException | InterruptedException | SystemGoingDownException e) {
			System.err.println(e.getMessage());
		}
		return customer;
	}

	//	gets list of all customers
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
