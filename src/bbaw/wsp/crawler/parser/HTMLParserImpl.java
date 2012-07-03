package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.html.HtmlParser;

public class HTMLParserImpl extends ResourceParser {

	public HTMLParserImpl(String uri) {
		super(uri, new HtmlParser());		
	}

}
