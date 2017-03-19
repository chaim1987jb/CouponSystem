package basic_classes;
/**
 * This class help to find out which client currently logged in
  
 * @author Avi
 *
 */
public class ClientRetriever {
	
	/** Field represents the id of client or retriving client */
	private static long ID;

	/**
	 * @return the iD
	 */
	public static long getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public static void setID(long iD) {
		if (iD > 0) ID = iD;
	}
	
}
