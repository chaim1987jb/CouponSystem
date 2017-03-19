package basic_classes;
import java.io.Serializable;
import java.sql.Date;

/**
 * this class use is to deposit coupon to the Data Base or to obtain coupon out from DB. 
 * Using Constructor for all the fields .
 * getter and setter for all the fields to modify or expose fields when needs
 * toString for show the coupon instance you have .
 * @author chaim_chagbi
 *
 */
public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	
	/**
	 * 
	 * Default constructor
	 */
	public Coupon() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This getter is expose the id that informed at this moment in the id field.
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * this setter is received an id and update the id field. 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * this getter is expose the coupon title that informed at this moment in the title field.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * this setter is receiving a title and updating the title field.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * this getter is expose the start date of the coupon .
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * this setter receiving a {@link Date}value and set it in the startDate field.
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * this getter is expose the end date value that informed now.
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * this setter is receiving {@link Date}value and update with it the endDate field.
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * this getter is exposing the amount that now informed .
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * this setter is receiving an amount value and update the amount field.
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * this  getters is exposing the type of the coupon that informed, it's a  CouponType type
	 * @return the type
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * this setter is setting the coupon type by receiving {@link CouponType} type
	 * @param type the type to set
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * this getter is for exposing the message of the coupon that informed at this time.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * this setter is receiving a string message and updating the message field with this values.
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * this getter is exposing the price of the coupon.
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * this is for exposing the image values which is string values.
	 * @return string that represent t
	 */
	public String getImage() {
		return image;
	}

	
	/**
	 * this is for exposing the image values which is string values.
	 * this setter is receiving a price to update the price field.
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * this is an override of the toString which is assigned to extract the coupon instance with it's all details.
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
