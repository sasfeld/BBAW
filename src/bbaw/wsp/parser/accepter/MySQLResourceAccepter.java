package bbaw.wsp.parser.accepter;

import java.util.Set;

/**
 * This accepters accepts MySQL resources.
 * @author wsp-shk1 (Sascha Feldmann)
 *
 */
public class MySQLResourceAccepter extends ResourceAccepter {

	/**
	 * Create a new MySQLResourceAccepter.
	 *@param acceptedResources - a set that contains the accepted resources as string
	 */
	public MySQLResourceAccepter(Set<String> acceptedResources) {
		super(acceptedResources);		
	}

	/**
	 * Create a new MySQLResourceAccepter.
	 * @param configUri - the URI to the (harvester) config file.
	 */
	public MySQLResourceAccepter(final String configUri) {
		super(configUri);
	}
	
	@Override
	public boolean acceptNode(String uri) {
		return false;
	}

}
