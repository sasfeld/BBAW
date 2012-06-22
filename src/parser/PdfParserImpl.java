package parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * This class parses a PDF file.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class PdfParserImpl {

	/**
	 * The File instance.
	 */
	private File uri;

	/**
	 * Create a new PdfParser instance.
	 * @param uri - the URI to the document.
	 * @throws IllegalArgumentException if the uri is null, empty or doesn't refer to an existing file.
	 */
	public PdfParserImpl(String uri) {
		if(uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException("The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		File f = new File(uri);
		if(!f.exists()) {
			throw new IllegalArgumentException("The document doesn't exist in PdfParserImpl.");
		}
		this.uri = f;
	}
	
	/**
	 * Parse the pdf file and generate an index.
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void parse() throws IOException, SAXException, TikaException {		
		InputStream input = new FileInputStream(this.uri);
		ContentHandler textHandler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		PDFParser parser = new PDFParser();
		parser.parse(input,textHandler,metadata);
		System.out.println("content:\n\n"+textHandler.toString()+"\n\n");
		input.close();
	}
	
	
	
	
}
