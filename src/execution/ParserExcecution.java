
package execution;

import java.io.IOException;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import parser.PdfParserImpl;

import bbaw.wsp.crawler.accepter.FileSystemAccepter;
import bbaw.wsp.crawler.harvester.IHarvester;

/**
 * This class executes the parsing.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class ParserExcecution {

	private IHarvester harvester;	

	/**
	 * Create a new ParserExecution class.
	 * @param harvester - the harvester instance
	 * @param accepter - the accepter instance
	 */
	public ParserExcecution(IHarvester harvester, FileSystemAccepter accepter) {
		if(harvester == null || accepter == null) {
			throw new IllegalArgumentException("Parameters mustn't be null in ParserExecution!");
		}
		this.harvester = harvester;		
		this.harvester.setResourceAccepter(accepter);
	}
	
	/**
	 * Crawl all resources starting at a given URI and parse pdf files.
	 * @param startURI - the URI to start (depends on the harvester).
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void parsePdf(final String startURI) throws IOException, SAXException, TikaException {
		Set<String> uris = harvester.harvest(startURI);
		
		for (String uri : uris) {
			PdfParserImpl parser = new PdfParserImpl(uri);
			parser.parse();
		}
	}
}
