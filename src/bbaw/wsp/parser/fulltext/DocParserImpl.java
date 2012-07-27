package bbaw.wsp.parser.fulltext;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.tools.LogFile;
import bbaw.wsp.parser.tools.TextFileWriter;

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
