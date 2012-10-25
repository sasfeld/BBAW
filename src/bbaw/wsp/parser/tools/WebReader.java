package bbaw.wsp.parser.tools;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class reads a web resource.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 07.08.2012
 * 
 */
public class WebReader {
	/**
	 * Return the response body.
	 * 
	 * @param uri
	 *            - a valid URL as String to the HTTP resource.
	 * @return the HTTP response body as String.
	 * @throws ClientProtocolException
	 *             or {@link IOException} if the URI doesn't refer to an
	 *             existing resource.
	 */
	public String readWebPage(final String uri) {
		final DefaultHttpClient httpclient = new DefaultHttpClient();
		final HttpGet httpget = new HttpGet(uri);

		final ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			final String responseBody = httpclient.execute(httpget,
					responseHandler);
			return responseBody;

			// System.out.println(responseBody);
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException(
					"Can't locate the uri in WebReader.readWebPage()!");
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Can't locate the uri in WebReader.readWebPage()!");
		} finally {
			// "Free" HTTPconnection
			httpclient.getConnectionManager().shutdown();
		}
	}

}
