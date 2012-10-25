package bbaw.wsp.parser.fulltext.readers;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import bbaw.wsp.parser.harvester.Harvester;

/**
 * This interface offers a method to get an input stream by a different resource type (e.g.: HTTP resource, local file...)
 * 
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public interface IResourceReader {

	/**
	 * Get the input stream for a URI. This uri is given by a {@link Harvester}.
	 * @param uri - the URI given by the {@link Harvester}
	 * @return the {@link InputStream} for the parser
	 */
	InputStream read(final String uri);
	
	/**
	 * Get the resource type for a URI. A resource type can be a {@link File} or an {@link URL}.
	 * @param uri a URI to a {@link File} or a {@link URL}
	 * @return a URI to a {@link File} or a {@link URL}
	 */
	URL getURI(final String uri);
}
