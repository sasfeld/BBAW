package bbaw.wsp.parser.fulltext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class is the API for all parsers.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public abstract class ResourceParser {
	/**
	 * The File instance.
	 */
	protected File uri;
	protected Parser parser;

	/**
	 * Create a new PdfParser instance.
	 * 
	 * @param uri
	 *            - the URI to the document.
	 * @throws IllegalArgumentException
	 *             if the uri is null, empty or doesn't refer to an existing
	 *             file.
	 */
	public ResourceParser(String uri, Parser parser) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}
		if (parser == null) {
			throw new IllegalArgumentException(
					"The value for the parameter parser in the constructor of PdfParserImpl mustn't be empty.");
		}
		File f = new File(uri);
		if (!f.exists()) {
			throw new IllegalArgumentException(
					"The document doesn't exist in PdfParserImpl.");
		}
		this.uri = f;
		this.parser = parser;
	}

	/**
	 * Parse a document and return the fulltext.
	 * 
	 * @return a String - the fulltext
	 */
	public String parse() {
		InputStream input;
		try {
			input = new FileInputStream(this.uri);
			if (DebugMode.DEBUG) {
				System.out.println("Parsing document: " + this.uri);
			}
			// Don't limit the amount of characters -> -1 as argument
			ContentHandler textHandler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			ParseContext context = new ParseContext();
			this.parser.parse(input, textHandler, metadata, context);
			input.close();
			textHandler.endDocument();
			return textHandler.toString();
		} catch (Exception e) {
			// Write log
			LogFile.writeLog("Problem while parsing file " + this.uri
					+ "  -- exception: " + e.getMessage() + "\n");
			return null;
		}
	}
}
