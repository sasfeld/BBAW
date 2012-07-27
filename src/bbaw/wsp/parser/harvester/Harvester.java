package bbaw.wsp.parser.harvester;

import java.util.Set;

import bbaw.wsp.parser.accepter.ResourceAccepter;

/**
 * This interface is the API for all Harvester.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public abstract class Harvester {
	
	protected ResourceAccepter resourceAccepter;

	/**
	 * Create a new harvester.
	 * This constructor must only be called using super().
	 * @param accepter - the {@link ResourceAccepter}.
	 */
	public Harvester(final ResourceAccepter accepter) {
		if(accepter == null) {
			throw new IllegalArgumentException(
					"Parameters mustn't be null in Harvester!");
		}
		this.resourceAccepter = accepter;
	}
	/**
	 * Harvest an WebResource located by an URL and return the resources based
	 * on that URL.
	 * 
	 * @param startURI
	 *            - the URL to begin the harvest
	 * @return a set of String that contains all accepted resources
	 * @throws IllegalArgumentException if the startURL does not exist
	 */
	public abstract Set<String> harvest(final String startURI) throws IllegalArgumentException;

	/**
	 * Get the ResourceAccepter of this harvester.
	 * @return the {@link ResourceAccepter} accepter object.
	 */
	public ResourceAccepter getResourceAccepter() {
		return this.resourceAccepter;
	}
}
