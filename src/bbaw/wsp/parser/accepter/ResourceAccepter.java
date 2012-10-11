package bbaw.wsp.parser.accepter;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import bbaw.wsp.parser.fulltext.readers.IResourceReader;
import bbaw.wsp.parser.fulltext.readers.ResourceReaderImpl;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This abstract class offers methods to read from a config file and to accept crawled files.
 * 
 * It follows the Strategy pattern because it capsules the algorithm which check the resources.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public abstract class ResourceAccepter {

	/**
	 * PDF file extension.
	 */
	public static final String EXT_PDF = ".pdf";
	/**
	 * DOC file extension.
	 */
	public static final String EXT_DOC = ".doc";
	/**
	 * Open document text extension.
	 */
	public static final String EXT_ODT = ".odt";
	/**
	 *  XML extension.
	 */
	public static final String EXT_XML = ".xml";
	/**
	 *  JPG extension.
	 */
	public static final String EXT_JPG = ".jpg";
	/**
	 *  TIFF extension.
	 */
	public static final String EXT_TIFF = ".tiff";
	/**
	 *  PNG extension.
	 */
	public static final String EXT_PNG = ".png";
	/**
	 *  HTML extension.
	 */
	public static final String EXT_HTML = ".html";
	/**
	 *  XHTML extension.
	 */
	public static final String EXT_XHTML = ".xhtml";
	/**
	 *  HTM extension.
	 */
	public static final String EXT_HTM = ".htm";
	/**
	 *  TXT extension.
	 */
	public static final String EXT_TXT = ".txt";
	
	protected Set<String> acceptedResources;

	/**
	 * Create a new {@link ResourceAccepter}.
	 * @param acceptedResources the set that contains the accepted resources as strings (".[extension]")
	 */
	public ResourceAccepter(final Set<String> acceptedResources) {
		this.acceptedResources = acceptedResources;
	}
	
	/**
	 * Creates a new {@link ResourceAccepter} and uses a config-file where the accepted resources are specified.
	 */
	public ResourceAccepter(final String configUri) {
		Processor processor = new Processor(false);
		XPathCompiler xPathCompiler = processor.newXPathCompiler();
		DocumentBuilder builder = processor.newDocumentBuilder();
		try {
			XdmNode contextItem = builder.build(new File(configUri));
			String query = "//acceptedResources/extension/text()";
			XPathExecutable x = xPathCompiler.compile(query);

			XPathSelector selector = x.load();
			selector.setContextItem(contextItem);
			XdmValue value = selector.evaluate();
			this.acceptedResources = new HashSet<String>();
			for (XdmItem xdmItem : value) {
				String extension = xdmItem.getStringValue();
				if (!extension.equals("src")
						|| !extension.equals("org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7")) {
					this.acceptedResources.add(extension);						
				}
			}
		} catch (SaxonApiException e) {
			LogFile.writeLog("Problem while reading from harvester config file " +
					 configUri 
					 + "  -- exception: " + e.getMessage() + "\n");
		}
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
	
	/**
	 * Fetch the URI's extension.
	 */
	public String getExtension(final String uri) {
		final int extPos = uri.lastIndexOf(".");
		final String extension = uri.substring(extPos, uri.length());
		
		return extension;
	}
	
	/**
	 * Check if the resource is an image.
	 * @param uri - the resource's URI.
	 * @return ture if the resource is an image.
	 */
	public boolean isImage(String uri) {		
		if(getExtension(uri).equals(EXT_JPG) || getExtension(uri).equals(EXT_TIFF)|| getExtension(uri).equals(EXT_PNG)) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the resource is an eDoc.
	 * @param uri - the resource's URI.
	 * @return true if the resource is an (KOBV) eDoc.
	 */
	public boolean isEDoc(String uri) {
		File f = new File(uri);		
	
		if(f.getParentFile().getName().equals("pdf") && new File(f.getParentFile().getParentFile(), "index.html").exists()) {
			return true;
		}
		return false;
	}
	
	/**
   * Check if the file is an index.html file to an eDoc.
   * 
   * @param uri
   *          - the URL as string to the index.html file.
   * @return true if the index.html file belongs to an eDoc.
   */
  public boolean isEDocIndex(String uri) {
    IResourceReader reader = new ResourceReaderImpl();
    InputStream in = reader.read(uri);

    if (in != null) {
      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\n");
      StringBuilder builder = new StringBuilder();
      while (scanner.hasNext()) {
        builder.append(scanner.next());
      }
      String content = builder.toString();
      Pattern p = Pattern.compile("(?i)<META NAME=\"(.*?)\" CONTENT=\"(.*?)\">(?i)");
      for (Matcher m = p.matcher(content); m.find();) {
        String tag = m.group(1);
        String value = m.group(2);
        if (tag.equals("DC.Identifier") && value.contains("edoc.bbaw.de/")) {
          return true;
        }
      }
    }
    return false;
  }
	
}
