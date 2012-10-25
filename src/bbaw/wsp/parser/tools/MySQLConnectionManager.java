package bbaw.wsp.parser.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class offers a connect() - method which returns a connection.
 * @author wsp-shk1 (Sascha Feldmann)
 *
 */
public class MySQLConnectionManager {

	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	
	

	/**
	 * Set connection data.
	 * 
	 * @param dbHost
	 *            - the host name (DNS or IP)
	 * @param dbPort
	 *            - the port (standard MYSQL: 3306)
	 * @param dbName
	 *            - the database name
	 * @param dbUser
	 *            - the user (needs privileges: SELECT and SHOW DATABASES!)
	 * @param dbPassword
	 *            - the user's password
	 * @throws IllegalArgumentException if one of the arguments is null.
	 */
	public void setAccess(final String dbHost, final String dbPort,
			final String dbName, final String dbUser, final String dbPassword) {
		if (dbHost == null || dbHost.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		if (dbPort == null || dbPort.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		if (dbName == null || dbName.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		if (dbUser == null || dbUser.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		if (dbPassword == null) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;	
	}
	
	/**
	 * Connect to the database.
	 * Write the {@link LogFile} if there is a problem with the JDBC driver or the SQL connection.
	 * @return a connection instance that will be used by other methods or null
	 *         if the driver couldn't connect to the database.
	 * @throws IllegalStateException if the connection data wasn't set calling setAccess().
	 */
	public Connection connect() {
		if(this.dbHost == null || this.dbPort == null || this.dbName == null || this.dbUser == null || this.dbPassword == null) {
			throw new IllegalStateException("You must call the setAccess()-method before you try to connect to the database in MySQLConnectionManager.");
		}		
		
		// Check if the JDBC Driver can be found
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"
					+ this.dbHost + ":" + this.dbPort + "/" + this.dbName + "?"
					+ "user=" + this.dbUser + "&" + "password="
					+ this.dbPassword);
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			LogFile.writeLog("Problem while connecting to the database: "+e.getMessage());
			return null;
		}
	}
}
