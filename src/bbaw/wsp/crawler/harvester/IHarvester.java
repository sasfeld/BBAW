package bbaw.wsp.crawler.harvester;

import java.util.Set;

import bbaw.wsp.crawler.accepter.ResourceAccepter;

/**
 * This interface is the API for all Harvester.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public interface IHarvester {
	/**
	 * Harvest an WebResource located by an URL and return the resources based
	 * on that URL.
	 * 
	 * @param startURI
	 *            - the URL to begin the harvest
	 * @return a set of String that contains all accepted resources
	 * @throws IllegalArgumentException if the startURL does not exist
	 */
	Set<String> harvest(final String startURI) throws IllegalArgumentException;
	
	/**
	 * Set a ResourceAccepter.
	 * @param accepter - the ResourceAccepter (StrategyPattern)
	 */
	void setResourceAccepter(ResourceAccepter accepter);
}
