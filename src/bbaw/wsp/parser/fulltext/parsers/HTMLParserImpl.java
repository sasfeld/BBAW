package bbaw.wsp.parser.fulltext.parsers;

import org.apache.tika.parser.html.HtmlParser;

/**
 * This class parses an HTML file. It uses the Singleton pattern. Only one
 * instance can exist.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.08.2012
 * 
 */
public class HTMLParserImpl extends ResourceParser {
	private static HTMLParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache TIKA HTML
	 * parser.
	 * 
	 * @return
	 */
	public static HTMLParserImpl getInstance() {
		if (instance == null) {
			return new HTMLParserImpl();
		}
		return instance;
	}

	protected HTMLParserImpl() {
		super(new HtmlParser());
	}

}
