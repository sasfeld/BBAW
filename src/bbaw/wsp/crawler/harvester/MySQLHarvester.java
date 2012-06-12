package bbaw.wsp.crawler.harvester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.sql.*;

/**
 * Harvest an MySQL database beginning at a specified path and return all
 * accepted resources based on that path.
 */
public class MySQLHarvester {	
	private static Connection conn;

	/**
	 * Connect to the given database.
	 * @param dbHost - the host name (DNS or IP)
	 * @param dbPort - the port (standard MYSQL: 3306)
	 * @param dbName - the database name
	 * @param dbUser - the user (needs privileges: SELECT and SHOW DATABASES!)
	 * @param dbPassword - the user's password
	 * @return a connection instance that will be used by other methods
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	private static Connection connect(final String dbHost, final String dbPort,
			final String dbName, final String dbUser, final String dbPassword) throws ClassNotFoundException, SQLException{ 		
			// Check if the JDBC Driver can be found
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println();
			conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
					+ dbPort + "/" + dbName + "?" + "user=" + dbUser + "&"
					+ "password=" + dbPassword);
			return conn;			

	}

	/** Build a query to fetch all databases 
	 * @return a set which contains the database names (String) */
	private static Set<String> fetchDatabases() {
		if (conn != null) {
			Statement query;
			Set<String> databases = new HashSet<String>();

			try {
				query = conn.createStatement();
				String command = "SHOW DATABASES";
				ResultSet result = query.executeQuery(command);

				while (result.next()) {
					databases.add(result.getString(1));
				}

				return databases;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Harvest a MySQL - management system.
	 * @param dbHost - the host name (DNS or IP)
	 * @param dbPort - the port (standard MYSQL: 3306)
	 * @param dbName - the database name
	 * @param dbUser - the user (needs privileges: SELECT and SHOW DATABASES!)
	 * @param dbPassword - the user's password
	 * @return a list of resources. Each element has the layout // [ Database ] // [ Table ]
	 */
	public static Set<String> harvest(final String dbHost, final String dbPort,
			final String dbName, final String dbUser, final String dbPassword) throws IllegalArgumentException {
		Connection conn;
		try {
			conn = MySQLHarvester.connect(dbHost, dbPort, dbName,
					dbUser, dbPassword);
			Set<String> databases = fetchDatabases();
			Set<String> leafs = new HashSet<String>();
			for (String database : databases) {
				try {		
					// Build query to crawl each database
					Statement query = conn.createStatement();
					// Command for getting tables names: SHOW TABLES [ IN ] [ DATABASE ]
					String command = "SHOW TABLES IN "+database;
					ResultSet result = query.executeQuery(command);

					while (result.next()) {
						leafs.add("//"+database+"/"+result.getString(1));	// Format: //[ database ] / [ table ] 				
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					throw new IllegalArgumentException(e.getSQLState());				
				}
			}
			return leafs;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Couldn't find the MySQL Connector J (JDBC) - driver!");			
		} catch (SQLException e) {
			throw new IllegalArgumentException("Couldn't connect to the given database: "
					+ e.getMessage());		
		}		
	}

	/* 
	 * Test the MySQL-Harvester.
	 */
	public static void main(String[] args) {
		System.out.println("MySQLHarvester:");

		Set<String> results = MySQLHarvester.harvest("localhost", "3306", "cdcol", "root", "");
		System.out.println(results);
	}
}
