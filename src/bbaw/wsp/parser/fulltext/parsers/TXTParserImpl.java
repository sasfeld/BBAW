package bbaw.wsp.parser.fulltext.parsers;

import org.apache.tika.parser.txt.TXTParser;

/**
 * The TXTParser. It uses the Singleton pattern. Only one instance can exist.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.08.2012
 *
 */
public class TXTParserImpl extends ResourceParser {
	private static TXTParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache TIKA OpenDocument
	 * parser.
	 * 
	 * @return
	 */
	public static TXTParserImpl getInstance() {
		if (instance == null) {
			return new TXTParserImpl();
		}
		return instance;
	}
	
	private TXTParserImpl() {
		super(new TXTParser());		
	}

}
