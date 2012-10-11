package bbaw.wsp.parser.harvester;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.control.DebugMode;


/**
 * This class offers methods to crawl a web (HTTP)-source
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 07.08.2012
 * @version 1
 */
public class WebHarvester extends Harvester {
	
	/**
	 * Create a new WebHarvester which uses a given {@link ResourceAccepter}
	 * @param accepter - the {@link ResourceAccepter}
	 */
	public WebHarvester(ResourceAccepter accepter) {
		super(accepter);
	}

	/*
	 * (non-Javadoc)
	 * @see bbaw.wsp.parser.harvester.Harvester#harvest(java.lang.String)
	 */
	public Set<String> harvest(final String startURI)
			throws IllegalArgumentException {
		WebReader webReader = new WebReader();
		String responseBody = webReader.readWebPage(startURI);
		if(DebugMode.DEBUG) {
      System.out.println("Harvesting: "+startURI);
    }
		// Fetch resources (startURL/XXX)
		Set<String> leafs = new HashSet<String>();

		Pattern p = Pattern.compile("<a href=\"(.*?)\".*>");
		for (Matcher m = p.matcher(responseBody); m.find();) {
			// Fetch group 1 (from regular expression above)
			String uri = m.group(1);
			if(DebugMode.DEBUG) {
        System.out.println("Fetching URL: "+uri);
      }
			if (this.resourceAccepter.acceptLeaf(uri)) {
			  if(DebugMode.DEBUG) {
			    System.out.println("Adding leaf: "+uri);
			  }
				leafs.add(startURI + uri);
			} else if (this.resourceAccepter.acceptNode(uri)) {
				leafs.addAll(harvest(startURI + uri));
			}
		}

		return leafs;
	}
}
