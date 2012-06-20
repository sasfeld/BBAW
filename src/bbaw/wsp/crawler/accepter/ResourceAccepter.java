package bbaw.wsp.crawler.accepter;

import java.util.Set;

/**
 * This abstract class offers methods to read from a config file and to accept crawled files.
 * 
 * It follows the Strategy pattern because it capsules the algorithm which check the resources.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public abstract class ResourceAccepter {

	protected Set<String> acceptedResources;

	public ResourceAccepter(final Set<String> acceptedResources) {
		this.acceptedResources = acceptedResources;
	}
	
	/**
	 * Check an (leaf)-resource. 
	 * @param uri - the URL to the (leaf-)resource
	 * @return true if the resource is accepted
	 */
	public boolean acceptLeaf(String uri) {
		for (String acceptorString : this.acceptedResources) {
			if(uri.endsWith(acceptorString)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check a node-resource (only designed to be used by the WebHarvester)
	 * @param uri - the URL of the node-resource
	 * @return ture if the node-resource is accepted
	 */
	public abstract boolean acceptNode(String uri);
	
}
