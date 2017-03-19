package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import basic_classes.Company;
import basic_classes.ClientRetriever;
import basic_classes.Coupon;
import basic_classes.CouponType;
import exceptions.CompanyNotFoundException;
import exceptions.DuplicateCompanyException;
import exceptions.SystemGoingDownException;
	/**
	 * The class Company is the class that do all the required access
	 * work with DB using the methods below.
	 * implements  CompanyDAO and apply the following methods.
	 * this class using the SqlQueries class for all the SQL queries 
	 * in all methods.
	 * @author chaim_chagbi
	 *
	 */
public class CompanyDBDAO implements CompanyDAO {

	/** Field represents connection pool ....*/
	private ConnectionPool pool;

	/**
	 * Creates new instance of connection pool
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CompanyDBDAO() throws ClassNotFoundException, SQLException {
		pool = ConnectionPool.getInstance();
	}
	
	@Override
	public void createCompany(Company company) throws DuplicateCompanyException, ClassNotFoundException, 
	SQLException, InterruptedException, CompanyNotFoundException, SystemGoingDownException {
		if (company == null) {
			throw new CompanyNotFoundException("Company not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			Collection<Company> existCompanies = getAllCompanies();
			String compNameToCheck = company.getCompName();
			if (!existCompanies.isEmpty()) {
				for (Company companyToCheck : existCompanies) {
					if (compNameToCheck.equalsIgnoreCase(companyToCheck.getCompName())) {
						throw new DuplicateCompanyException("Company (" + compNameToCheck + ") already exists!");
					}
				}
			}

			tempConn = pool.getConnection();

			String insert = "INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)";
			stm = tempConn.prepareStatement(insert);
			stm.setString(1, company.getCompName());
			stm.setString(2, company.getPassword());
			stm.setString(3, company.getEmail());
			stm.executeUpdate();

			System.out.println(" *** Adding company (" + company.getCompName() + 
					") into DB. - Result: SUCCEEDED! ***");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}
	
	@SuppressWarnings("resource")
	@Override
	public void removeCompany(Company company) throws ClassNotFoundException, SQLException, 
	InterruptedException, CompanyNotFoundException, SystemGoingDownException {
		if (company == null) {
			throw new CompanyNotFoundException("Company not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		boolean flag = false;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String deleteFromCompany = "DELETE FROM company WHERE ID = ?";
			stm = tempConn.prepareStatement(deleteFromCompany);
			stm.setLong(1, company.getId());
			stm.executeUpdate();

			Collection<Coupon> collection = company.getCoupons();
			if (collection != null && !collection.isEmpty()) {
				String deleteFromCompany_Coupon = "DELETE FROM company_coupon WHERE COMP_ID = ?";
				stm = tempConn.prepareStatement(deleteFromCompany_Coupon);
				stm.setLong(1, company.getId());
				stm.executeUpdate();

				String deleteFromCoupon = "DELETE FROM coupon WHERE ID = ?";
				stm = tempConn.prepareStatement(deleteFromCoupon);
				for (Coupon coupon : collection) {
					stm.setLong(1, coupon.getId());
					stm.addBatch();
				}
				stm.executeBatch();

				String deleteFromCustomer_Coupon = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";
				stm = tempConn.prepareStatement(deleteFromCustomer_Coupon);
				for (Coupon coupon : collection) {
					stm.setLong(1, coupon.getId());
					stm.addBatch();
				}
				stm.executeBatch();
			}

		
			tempConn.commit();
			flag = true;
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();

			System.out.print(" *** Removing company (" + company.getCompName() + ") from DB. - Result: ");
			if (flag) System.out.println("SUCCEEDED! ***");
			else System.out.println("FALED! ***");
		}
	}

	@Override
	public void updateCompany(Company company) throws ClassNotFoundException, SQLException, 
	InterruptedException, CompanyNotFoundException, SystemGoingDownException {
		if (company == null) {
			throw new CompanyNotFoundException("Company not exists!");
		}
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}

		Connection tempConn = null;
		PreparedStatement stm = null;
		try {
			tempConn = pool.getConnection();
			tempConn.setAutoCommit(false);

			String update = "UPDATE company SET PASSWORD = ?, EMAIL = ? WHERE ID = ?";
			stm = tempConn.prepareStatement(update);
			stm.setString(1, company.getPassword());
			stm.setString(2, company.getEmail());
			stm.setLong	 (3, company.getId());
			stm.executeUpdate();
			tempConn.commit();

			System.out.println(" *** Updating company (" + company.getCompName() + 
					") in DB. - Result: SUCCEEDED! ***");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
		}
	}

	@Override
	public Company getCompany(long id) throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Company company = null;
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String select = "SELECT * FROM company WHERE ID = ?";
			stm = tempConn.prepareStatement(select);
			stm.setLong(1, id);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				company = new Company();
				company.setId(id);
				company.setCompName(rSet.getString("COMP_NAME"));
				company.setPassword(rSet.getString("PASSWORD"));
				company.setEmail(rSet.getString("EMAIL"));
			}
			company.setCoupons(getCouponsByID(id));
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();

			System.out.print(" *** Getting company from DB by id (" + id + "). - Result: ");
			if (company != null) System.out.println("SUCCEEDED! ***");
			else System.out.println("FALED! ***");
		}
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		Collection<Company> allCompnies = new ArrayList<>();
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String select = "SELECT * FROM company";
			stm = tempConn.prepareStatement(select);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Company company = new Company();
				company.setId(rSet.getLong("ID"));
				company.setCompName(rSet.getString("COMP_NAME"));
				company.setPassword(rSet.getString("PASSWORD"));
				company.setEmail(rSet.getString("EMAIL"));
				company.setCoupons(getCouponsByID(company.getId()));
				allCompnies.add(company);
			}

			System.out.print("Getting all exist companies from DB. - Result: SUCCEEDED!");
			if (allCompnies.isEmpty()) System.out.println(" (empty)");
			else System.out.println(" (" + allCompnies.size() + ")");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return allCompnies;
	}

	@Override
	public Collection<Coupon> getCoupons() throws ClassNotFoundException, SQLException, 
	InterruptedException, SystemGoingDownException {
		long idOfLoggedinCompany = ClientRetriever.getID();
		return getCouponsByID(idOfLoggedinCompany);
	}

	@Override
	public boolean login(String compName, String password) throws ClassNotFoundException, 
	SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		boolean flag = false;
		Connection tempConn = null;
		PreparedStatement stm = null;
		ResultSet rSet = null;
		try {
			tempConn = pool.getConnection();

			String select = "SELECT ID, EMAIL FROM company WHERE COMP_NAME = ? AND PASSWORD = ?";
			stm = tempConn.prepareStatement(select);
			stm.setString(1, compName);
			stm.setString(2, password);

			rSet = stm.executeQuery();
			while (rSet.next()) {
				Company company = new Company();
				company.setId(rSet.getLong("ID"));
				company.setCompName(compName);
				company.setPassword(password);
				company.setEmail(rSet.getString("EMAIL"));

				if (company != null) flag = true;
			}

			System.out.print(" ##### Company (" + compName + ") trying to login.... - Result: ");
			if (flag) System.out.println("SUCCEEDED! #####");
			else System.out.println("FAILED! #####");
		} finally {
			if (tempConn != null) pool.returnConnection(tempConn);
			if (stm != null) stm.close();
			if (rSet != null) rSet.close();
		}
		return flag;
	}

	/**
	 * Checks if database contains company with specified name. 
	 * If company exists returns its id, otherwise -1.
	 * @param companyName - String representing specified company name to check
	 * @return id of company if it exists in DB, otherwise -1
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException 
	 */
	public long getID(String companyName) throws ClassNotFoundException, SQLException, InterruptedException, SystemGoingDownException {
		if (pool.getSystemStatus()) {
			throw new SystemGoingDownException("System going down!");
		}
		
		long companyID = -1;
		if (companyName != null && !companyName.isEmpty()) {
			Connection tempConn = null;
			PreparedStatement stm = null;
			ResultSet set = null;
			try {
				tempConn = pool.getConnection();

				String select = "SELECT ID FROM company WHERE COMP_NAME = ?";
				stm = tempConn.prepareStatement(select);
				stm.setString(1, companyName);
				set = stm.executeQuery();
				if (set.next()) {
					companyID = set.getLong("ID");
				}
			} finally {
				if (tempConn != null) pool.returnConnection(tempConn);
				if (stm != null) stm.close();
				if (set != null) set.close();

				System.out.print(" - Getting id of company by name (" + companyName + "). - Result: ");
				if (companyID == -1) System.out.println("FAILED! -");
				else System.out.println("SUCCEEDED! -");
			}
		}
		return companyID;
	}

	/**
	 * Gets all company's collection of coupons by specified company id
	 * @param companyID - specified id of company
	 * @return - collection of coupons  of specified company
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws InterruptedException
	 * @throws SystemGoingDownException
	 */
	private Collection<Coupon> getCouponsByID(long companyID) throws ClassNotFoundException, 
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
	
			String selectC = "SELECT COUPON_ID FROM company_coupon WHERE COMP_ID = ?";
			stm = tempConn.prepareStatement(selectC);
			stm.setLong(1, companyID);
	
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
	
			System.out.print("Getting all company's coupons from DB (by id). - Result: SUCCEEDED!");
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
}
