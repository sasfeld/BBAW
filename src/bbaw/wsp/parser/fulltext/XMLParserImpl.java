package bbaw.wsp.parser.fulltext;

import org.apache.tika.parser.xml.XMLParser;

/**
 * This class parses an XML file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class XMLParserImpl extends ResourceParser {

	public XMLParserImpl(String uri) {
		super(uri, new XMLParser());		
	}
	

}
