package bbaw.wsp.crawler.parser;

import org.apache.tika.parser.pdf.PDFParser;



/**
 * This class parses a PDF file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class PdfParserImpl extends ResourceParser {	
	/**
	 * Create a new PdfParser instance.
	 * 
	 * @param uri
	 *            - the URI to the document.
	 * @throws IllegalArgumentException
	 *             if the uri is null, empty or doesn't refer to an existing
	 *             file.
	 */
	public PdfParserImpl(String uri) {
		super(uri, new PDFParser());
	}


}
