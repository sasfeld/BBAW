package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.html.HtmlParser;

/**
 * This class parses an HTML file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class HTMLParserImpl extends ResourceParser {

	public HTMLParserImpl(String uri) {
		super(uri, new HtmlParser());		
	}

}
