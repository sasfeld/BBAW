package bbaw.wsp.parser.fulltext;

import org.apache.tika.parser.odf.OpenDocumentParser;

/**
 * The ODFParser.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class ODFParserImpl extends ResourceParser {

	/**
	 * Create a new ODFParser instance.
	 * 
	 * @param uri
	 *            - the URI to the document.
	 */
	public ODFParserImpl(String uri) {
		super(uri, new OpenDocumentParser());
	}
}
