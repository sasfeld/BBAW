package bbaw.wsp.crawler.harvester;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import bbaw.wsp.crawler.accepter.ResourceAccepter;

/**
 * This static class offers methods to crawl a web (HTTP)-source
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.06.2012
 * @version 0.5
 */
public class WebHarvester {

	/**
	 * Harvest an WebResource located by an URL and return the resources based
	 * on that URL.
	 * 
	 * @param startURL
	 *            - the URL to begin the harvest
	 * @return a set of String that contains all accepted resources
	 * @throws IllegalArgumentException if the startURL does not exist
	 */
	public static Set<String> harvest(final String startURL) throws IllegalArgumentException{
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		final HttpGet httpget = new HttpGet(startURL);

		final ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			final String responseBody = httpclient.execute(httpget,
					responseHandler);

			// Fetch resources (startURL/XXX)
			Set<String> leafs = new HashSet<String>();			

			Pattern p = Pattern.compile("<a href=\"(.*?)\">");
			for (Matcher m = p.matcher(responseBody); m.find();) {
				// Fetch group 1 (from regular expression above)
				String uri = m.group(1);

				if (ResourceAccepter.acceptLeaf(uri)) {
					leafs.add(startURL + uri);
				} else if (ResourceAccepter.acceptNode(uri)) {
					leafs.addAll(harvest(startURL + uri));
				}
			}
			
		return leafs;		

			// System.out.println(responseBody);
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Can't locate the startURL in WebHarvester.harvest()!");
		} catch (IOException e) {
			throw new IllegalArgumentException("Can't locate the startURL in WebHarvester.harvest()!");
		} finally {
			// "Free" HTTPconnection
			httpclient.getConnectionManager().shutdown();
		}		
	}

	/*
	 * Test of the WebHarvester
	 */
	public static void main(String[] args) {
		Set<String> results = WebHarvester.harvest("http://192.168.1.203/wsp/");
		System.out.println(results);
	}
}
