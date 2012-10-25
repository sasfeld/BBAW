package bbaw.wsp.parser.fulltext.parsers;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.fulltext.readers.IResourceReader;
import bbaw.wsp.parser.fulltext.readers.ResourceReaderImpl;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class is the API for all parsers.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public abstract class ResourceParser {
	protected Parser parser;
	protected IResourceReader resourceReader;
	protected ISaveStrategy saveStrategy;

	/**
	 * Create a new PdfParser instance.
	 * 
	 * @param uri
	 *            - the URI to the document.
	 * @throws IllegalArgumentException
	 *             if the uri is null, empty or doesn't refer to an existing
	 *             file.
	 */
	public ResourceParser(final Parser parser) {	
		if (parser == null) {
			throw new IllegalArgumentException(
					"The value for the parameter parser in the constructor of PdfParserImpl mustn't be empty.");
		}		
		
		this.parser = parser;
		this.resourceReader = new ResourceReaderImpl();
	}
	
	/**
	 * Set a {@link ISaveStrategy}
	 * @param saveStrategy
	 */
	public void setSaveStrategy(final ISaveStrategy saveStrategy) {
		if (saveStrategy == null) {
			throw new IllegalArgumentException(
					"The value for the parameter saveStrategy in the constructor of PdfParserImpl mustn't be empty.");
		}
		
		this.saveStrategy = saveStrategy;
	}

	/**
	 * Parse a document and return the fulltext.
	 * 
	 * @param startUri
	 *            - the harvesting URI.
	 * @param uri
	 *            - the URI to the document.
	 * @return a String - the fulltext
	 * @throws IllegalArgumentException
	 *             if the uri is null or empty
	 * @throws IllegalStateException
	 *             if the {@link ISaveStrategy} wasn't set before.
	 */
	public Object parse(final String startUri, final String uri) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter parser in the method parse() in ResourceParser mustn't be empty.");
		}
		if(this.saveStrategy == null) {
			throw new IllegalStateException("You must define a saveStategy before calling the parse()-method in ResourceParser.");
		}
		InputStream input;
		try {
			input = this.resourceReader.read(uri);

			if (DebugMode.DEBUG) {
				System.out.println("Parsing document: " + uri);
			}
			// Don't limit the amount of characters -> -1 as argument
			ContentHandler textHandler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			ParseContext context = new ParseContext();
			this.parser.parse(input, textHandler, metadata, context);
			input.close();
			textHandler.endDocument();
			
			return this.saveStrategy.saveFile(uri, uri, textHandler.toString());
		} catch (Exception e) {
			// Write log
			LogFile.writeLog("Problem while parsing file " + uri
					+ "  -- exception: " + e.getMessage() + "\n");
			return null;
		}
	}
}
