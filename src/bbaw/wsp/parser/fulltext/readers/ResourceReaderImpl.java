package bbaw.wsp.parser.fulltext.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.tools.LogFile;

/**
 * The Adapter class which builds an InputStream depending on the kind of
 * resource (e.g. HTTP or file system resource)
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 07.08.2012
 * 
 */
public class ResourceReaderImpl implements IResourceReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbaw.wsp.parser.fulltext.IResourceReader#read(java.lang.String)
	 */
	public InputStream read(final String uri) {
		if (DebugMode.DEBUG) {
			System.out
					.println("ResourceReaderImpl: Trying to read from resource: "
							+ uri);
		}
		try {
			if (uri.contains("http://")) {
				URL url;
				url = new URL(uri);
				InputStream in = url.openStream();
				return in;
			} else {
			  System.out.println("Making input stream...");
				InputStream in = new FileInputStream(new File(uri));
				return in;
			}
		} catch (IOException e) {
			LogFile.writeLog("The type of resource for this URI " + uri
					+ " isn't supported: " + e.getMessage());
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * bbaw.wsp.parser.fulltext.readers.IResourceReader#getResourceType(java
	 * .lang.String)
	 */
	public URL getURI(String uri) {
		if (DebugMode.DEBUG) {
			System.out
					.println("ResourceReaderImpl: Trying to read from resource: "
							+ uri);
		}
		try {
			if (uri.contains("http://")) {
				URL url;
				url = new URL(uri);
				return url;
			} else {
				File file = new File(uri);
				URL url;
				url = file.toURL();
				return url;
			}
		} catch (IOException e) {
			LogFile.writeLog("The type of resource for this URI " + uri
					+ " isn't supported: " + e.getMessage());
			return null;
		}
	}
}
