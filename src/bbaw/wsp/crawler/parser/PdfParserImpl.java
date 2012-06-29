package bbaw.wsp.crawler.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
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

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.LogFile;
import bbaw.wsp.crawler.tools.TextFileWriter;



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
