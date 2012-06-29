package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.Parser;
import org.apache.tika.parser.image.ImageParser;
import org.apache.tika.parser.xml.XMLParser;



public class ImageParserImpl extends ResourceParser {

	public ImageParserImpl(String uri) {
		super(uri, new ImageParser());
	}

}
