package bbaw.wsp.crawler.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.LogFile;
import bbaw.wsp.crawler.tools.TextFileWriter;

/**
 * This class is the API for all parsers.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public abstract class ResourceParser {
	/**
	 * The File instance.
	 */
	protected File uri;
	private Parser parser;
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
			return textHandler.toString();
		} catch (Exception e) {
			// Write log
			TextFileWriter.getInstance().writeTextFile(
					LogFile.fileRef.getParentFile().getPath(),
					LogFile.fileRef.getName(),
					"Problem while parsing file " + this.uri
							+ "  -- exception: " + e.getMessage() + "\n", true);
			return null;
		}
	}
}