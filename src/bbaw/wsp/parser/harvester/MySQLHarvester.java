package bbaw.wsp.parser.harvester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.MySQLResourceAccepter;
import bbaw.wsp.parser.tools.LogFile;
import bbaw.wsp.parser.tools.MySQLConnectionManager;

/**
 * Harvest an MySQL database beginning at a specified path and return all
 * accepted resources based on that path.
 * This is a Singleton class.
 */
public class MySQLHarvester extends Harvester {
	private static MySQLHarvester instance;
	private static MySQLConnectionManager connectionManager;

	private MySQLHarvester() {
		// Set kind of Dummy-ResourceAccepter
		super(new MySQLResourceAccepter(new HashSet<String>()));
		if (connectionManager == null) {
			throw new IllegalStateException(
					"You must call the setMySQLConnectionManager-method in MySQLHarvester before you fetch the instance.");
		}
	}

	/**
	 * Singleton-Pattern - one instance only.
	 * 
	 * @return the only one {@link MySQLHarvester} instance.
	 */
	public static MySQLHarvester getInstance() {
		if (instance == null) {
			return new MySQLHarvester();
		}
		return instance;
	}

	/**
	 * Set the connection manager.
	 */
	public static void setMySQLConnectionManager(
			final MySQLConnectionManager manager) {
		if (manager == null) {
			throw new IllegalArgumentException(
					"The value for the parameter manager in setMySQLConnectionManager mustn't be empty.");
		}
		connectionManager = manager;
	}

	/**
	 * Build a query to fetch all databases
	 * 
	 * @return a set which contains the database names (String) or null if there
	 *         was a problem.
	 */
	private Set<String> fetchDatabases() {
		Connection conn = connectionManager.connect();

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
				LogFile.writeLog("Problem while fetching databases: "
						+ e.getMessage());
				return null;
			}
		}
		return null;
	}

	/**
	 * Harvest a MySQL - management system.
	 * 
	 * @param uri
	 *            - doesn't have any effect on the functionality.
	 * 
	 * @return a list of resources. Each element has the layout // [ Database ]
	 *         // [ Table ]
	 */
	public Set<String> harvest(final String uri) {
		Connection conn = connectionManager.connect();

		if (conn != null) {
			Set<String> databases = fetchDatabases();
			Set<String> leafs = new HashSet<String>();
			for (String database : databases) {
				try {
					// Build query to crawl each database
					Statement query = conn.createStatement();
					// Command for getting tables names: SHOW TABLES [ IN ] [
					// DATABASE ]
					String command = "SHOW TABLES IN " + database;
					ResultSet result = query.executeQuery(command);

					while (result.next()) {
						leafs.add("//" + database + "/" + result.getString(1));
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					LogFile.writeLog("Problem while fetching table: "
							+ e.getMessage());
				}
			}
			return leafs;
		}
		return null;
	}

	/*
	 * Test the MySQL-Harvester.
	 */
	public static void main(String[] args) {
		System.out.println("MySQLHarvester:");
		MySQLConnectionManager manager = new MySQLConnectionManager();
		manager.setAccess("localhost", "3306", "cdcol", "root", "");
		MySQLHarvester.setMySQLConnectionManager(manager);
		Set<String> results = MySQLHarvester.getInstance().harvest("");
		System.out.println(results);
	}
}
