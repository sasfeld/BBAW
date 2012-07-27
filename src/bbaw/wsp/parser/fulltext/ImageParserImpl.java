package bbaw.wsp.parser.fulltext;

import org.apache.tika.parser.image.ImageParser;


/**
 * This class parses an image.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class ImageParserImpl extends ResourceParser {

	public ImageParserImpl(String uri) {
		super(uri, new ImageParser());
	}
	

}
