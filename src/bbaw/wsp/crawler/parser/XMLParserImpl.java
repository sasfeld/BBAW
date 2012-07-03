package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.xml.XMLParser;


public class XMLParserImpl extends ResourceParser {

	public XMLParserImpl(String uri) {
		super(uri, new XMLParser());		
	}
	

}
