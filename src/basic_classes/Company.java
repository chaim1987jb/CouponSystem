package basic_classes;
import java.io.Serializable;
import java.util.Collection;

/**
 * This class use is to deposit company to the Data Base or to obtain company out from DB. 
 * including collection that shows the specifies coupons that the company markets.
 * Using  Constructor for all the fields except the  collection for easy use to work with DB
 * getter and setter for all the fields to modify or expose fields when needs.
 * toString for show the company you have include it's specifies coupons.
 * @author chaim_chagbi
 *
 */
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	/**
	 * 
	 * Default constructor
	 */
	public Company() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This getter is exposing the id that informed in the id field.
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * this setter is assigned to receiving a double value and update the id field with it.
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * this getter is returning the company name that is informed in the compName field.
	 * @return the compName
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * this setter is receiving a string value and set it as a company name in the compName field.
	 * @param compName the compName to set
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * this setter is receiving a string value and set with it the password field.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**	
	 * this setter is receiving a string value and set with it the password field.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**	
	 * this getter is exposing the email of the company that informed in the email field.
	 * @return string that represent email
	 */
	public String getEmail() {
		return email;
	}

	/**
     * this setter is receiving a string value to set the company email to the email field.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * this ArrayList give the option to receive a coupon  ArrayList that the specific
	 * company markets
	 * @return the coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * /**
	 * this setter is assigned to set the markets coupons instance of the specific company
	 *  into collection
	 * @param coupons the coupons to set
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		if (coupons != null) this.coupons = coupons;
	}

	/* 
	 * 
	 * this override of the toString method is assigned to exposing the company instance with all it's details.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupons=" + coupons + "]";
	}
	
}
