package bbaw.wsp.parser.fulltext.parsers;

import org.apache.tika.parser.xml.XMLParser;

/**
 * This class parses an XML file. It uses the Singleton pattern. Only one
 * instance can exist.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.08.2012
 * 
 */
public class XMLParserImpl extends ResourceParser {
	private static XMLParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache XML
	 * parser.
	 * 
	 * @return
	 */
	public static XMLParserImpl getInstance() {
		if (instance == null) {
			return new XMLParserImpl();
		}
		return instance;
	}

	private XMLParserImpl() {
		super(new XMLParser());
	}

}
