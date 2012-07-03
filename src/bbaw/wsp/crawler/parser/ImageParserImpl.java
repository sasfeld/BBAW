package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.image.ImageParser;



public class ImageParserImpl extends ResourceParser {

	public ImageParserImpl(String uri) {
		super(uri, new ImageParser());
	}
	

}
