package basic_classes;

public class ClientRetriever {
	
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
