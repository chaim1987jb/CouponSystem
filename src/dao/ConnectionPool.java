package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the class which provide connection to the DB and also close all the connection.
 * it's a singleTone type of class which means that there is only one appearance of this class.
 * @author chaim_chagbi
 *
 */
public class ConnectionPool {

	private static ConnectionPool instance = null;

	private String connectionURL = "jdbc:mysql://127.0.0.1:3306/chaim_chagbi?useSSL=false";	// URL JDBC
	private Object key = new Object();												// Key for wait/notify
	private Set<Connection> connections = new HashSet<>();							// Hash Set of connections
	private final int MAX_CONNECTIONS = 7;											// Max number of connections
	private boolean systemStatus;

	/**
	 * this getInstance method returning the instance of this class if the instance is been taken
	 * once it will provide the same instance again that how it remaining as a singleTone.
	 * it also a static method for the reason that it would be possible to get instance.
	 * it is also synchronized to tackle multiThread condition.
	 * @return instance
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static synchronized ConnectionPool getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	/**
	 * this is a private constructor it bounded to be private for blocking an option to
	 * get an appearance more than once.
	 * it also has the connections {@link Set} in it.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private ConnectionPool() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");										// prepare JDBC driver

		for (int i = 0; i < MAX_CONNECTIONS; i++) {									// create MAX connections and put them into the array list
			Connection oneNewConnection = DriverManager.getConnection(connectionURL, "root", "avihaim");
			connections.add(oneNewConnection);
		}

		systemStatus = false;
	}

	public boolean getSystemStatus() {
		return systemStatus;
	}

	/**
	 * this getConnection method is assigned to supply connection to the DB to all comer
	 * it synchronized on a key object and in case there is no available connections it will 
	 * send the requesting thread to wait and when the connection is returning it also
	 * notify the waiting thread .
	 * eventually it returning the connection and remove it from the {@link Set}connections
	 * @return Connection
	 * @throws InterruptedException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException, InterruptedException {
		synchronized (key) {
			while (connections.isEmpty()) {
				key.wait();		
			}
			Connection connectionToGive = connections.iterator().next();
			connections.remove(connectionToGive);
			return connectionToGive;
		}
	}

	/**
	 * this method is receiving a connection and set it back to the connections {@link Set}
	 * it also notify to the key object for the occasion that there is a thread that wait
	 * for connection.
	 * @param con
	 * @throws SQLException
	 */
	public void returnConnection(Connection conn) {
		synchronized (key) {
			connections.add(conn);
			key.notify();
		}
	}

	/**
	 * this method is closing all the connections to the DB.
	 * first it will update the boolean allowedConnection to false  
	 * and than it will pass on all the connections {@link Set} and close 
	 * them one by one. it also synchronized on the key object for the reason
	 * that no one be able to take connection while it close them.
	 */

	public synchronized void closeConnections() throws SQLException, InterruptedException {
		systemStatus = true;
		int closedConnections = 0;
		for(Connection connection : connections) {
			connection.close();
			closedConnections++;
			if (connections.isEmpty() && closedConnections < MAX_CONNECTIONS) {
				synchronized (key) {
					key.wait();
				}
			}
		}
	}


}
