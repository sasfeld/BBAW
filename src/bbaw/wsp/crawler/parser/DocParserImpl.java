package bbaw.wsp.crawler.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.LogFile;
import bbaw.wsp.crawler.tools.TextFileWriter;

/**
 * This class parses a DOC file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class DocParserImpl extends ResourceParser {

	public DocParserImpl(String uri) {
		super(uri, new OfficeParser());
	}


}
