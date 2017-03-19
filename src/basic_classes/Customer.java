package basic_classes;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class use is to deposit customer to the Data Base or to obtain company out from DB. 
 * including collection that shows the specifies coupons that the company markets.
 * Using  Constructor for all the fields except the  collection for easy use to work with DB
 * getter and setter for all the fields to modify or expose fields when needs.
 * toString for show the company you have include it's specifies coupons.
 * @author chaim_chagbi
 *
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;

	
	/**
	 * 
	 * Default constructor
	 */
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * this getter is returning the id that informed at this moment.
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * This setter is updating the id field for this class.
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * this getter is returning the customer name that informed at this moment.
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * This setter is receiving a customer name and  updating the custName field with it.
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * this getter is returning the password that informed at this moment.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This setter is receiving a password name and  updating the password field with it.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * this getter is returning the collection of coupon that informed at this moment.
	 * @return the coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * This setter is receiving an collection of coupon  and  updating the  field with it.
	 * @param coupons the coupons to set
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		if (coupons != null) this.coupons = coupons;
	}

	/**
	 * this is an override toString which is assigned to extract the customer instance with it's all details.
	 *
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}
	
}
