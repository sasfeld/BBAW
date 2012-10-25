package bbaw.wsp.parser.fulltext.parsers;

import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

/**
 * The ODFParser. It uses the Singleton pattern. Only one instance can exist.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.08.2012
 * 
 */
public class ODFParserImpl extends ResourceParser {
	private static ODFParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache TIKA OpenDocument
	 * parser.
	 * 
	 * @return
	 */
	public static ODFParserImpl getInstance() {
		if (instance == null) {
			return new ODFParserImpl();
		}
		return instance;
	}

	
	private ODFParserImpl() {
		super(new OpenDocumentParser());
	}
	
  public Object parse(final String startUri, final String uri) {
    if (uri == null || uri.isEmpty()) {
      throw new IllegalArgumentException("The value for the parameter parser in the method parse() in ResourceParser mustn't be empty.");
    }
    if (this.saveStrategy == null) {
      throw new IllegalStateException("You must define a saveStategy before calling the parse()-method in ResourceParser.");
    }
    InputStream input;
    try {
      input = this.resourceReader.read(uri);

      // Don't limit the amount of characters -> -1 as argument
      Metadata metadata = new Metadata();
      ContentHandler textHandler = new XHTMLContentHandler(new DefaultHandler(), metadata);

      ParseContext context = new ParseContext();
      this.parser.parse(input, textHandler, metadata, context);
      input.close();
      textHandler.endDocument();
      System.out.println(textHandler.toString());
      
      return null;
    } catch (Exception e) {
      return null;
    }
  }
}
