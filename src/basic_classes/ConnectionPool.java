package basic_classes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {

	private static ConnectionPool instance = null;

	private String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=chaim;integratedSecurity=true";	// URL JDBC
	private Object key = new Object();												// Key for wait/notify
	private Set<Connection> connections = new HashSet<>();							// Hash Set of connections
	private final int MAX_CONNECTIONS = 7;											// Max number of connections
	private boolean systemStatus;

	public static synchronized ConnectionPool getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}

	private ConnectionPool() throws ClassNotFoundException, SQLException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");										// prepare JDBC driver

		for (int i = 0; i < MAX_CONNECTIONS; i++) {									// create MAX connections and put them into the array list
			Connection oneNewConnection = DriverManager.getConnection(connectionURL, "me", "parol");
			connections.add(oneNewConnection);
		}
		
		systemStatus = false;
	}
	
	public boolean getSystemStatus() {
		return systemStatus;
	}

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

	public void returnConnection(Connection conn) {
		synchronized (key) {
			connections.add(conn);
			key.notify();
		}
	}

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
