package bbaw.wsp.crawler.accepter;

/**
 * This static class offers methods to read from a config file and to accept crawled files.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class ResourceAccepter {

	/**
	 * Check an (leaf)-resource. 
	 * @param uri - the URL to the (leaf-)resource
	 * @return true if the resource is accepted
	 */
	public static boolean acceptLeaf(String uri) {
		if(uri.endsWith(".xml") || uri.endsWith(".jpg") || uri.endsWith(".png") || uri.endsWith(".gif")) {
			return true;
		}
		return false;
	}

	/**
	 * Check a node-resource (only designed to be used by the WebHarvester)
	 * @param uri - the URL of the node-resource
	 * @return ture if the node-resource is accepted
	 */
	public static boolean acceptNode(String uri) {
		if(uri.matches("\\w+/")) {
			return true;
		}
		return false;
	}	
}
