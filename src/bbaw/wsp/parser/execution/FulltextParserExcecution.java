package bbaw.wsp.parser.execution;

import java.io.IOException;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.fulltext.DocParserImpl;
import bbaw.wsp.parser.fulltext.HTMLParserImpl;
import bbaw.wsp.parser.fulltext.ImageParserImpl;
import bbaw.wsp.parser.fulltext.ODFParserImpl;
import bbaw.wsp.parser.fulltext.PdfParserImpl;
import bbaw.wsp.parser.fulltext.ResourceParser;
import bbaw.wsp.parser.fulltext.XMLParserImpl;
import bbaw.wsp.parser.harvester.Harvester;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class executes the fulltext parsing.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class FulltextParserExcecution {

	private Harvester harvester;
	private ISaveStrategy saveStrategy;
	private FileSystemAccepter resourceAccepter;

	/**
	 * Create a new ParserExecution class.
	 * 
	 * @param harvester
	 *            - the harvester instance
	 * @param accepter
	 *            - the accepter instance
	 */
	public FulltextParserExcecution(Harvester harvester,
			ISaveStrategy saveStrategy) {
		if (harvester == null || saveStrategy == null) {
			throw new IllegalArgumentException(
					"Parameters mustn't be null in FulltextParserExecution!");
		}
		this.harvester = harvester; 
		this.saveStrategy = saveStrategy;
	}

	/**
	 * Crawl all resources starting at a given URI and parse pdf files.
	 * 
	 * @param startURI
	 *            - the URI to start (depends on the harvester).
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void parsePdf(final String startURI) throws IOException,
			SAXException, TikaException {
		Set<String> uris = harvester.harvest(startURI);

		for (String uri : uris) {
			PdfParserImpl parser = new PdfParserImpl(uri);
			String fulltext = parser.parse();
			if (fulltext != null) {
				this.saveStrategy.saveFile(uri, fulltext);
			}
		}
	}

	/**
	 * Parse resources of all kinds.
	 */
	public void parse(final String startURI) throws IOException, SAXException,
			TikaException {
		Set<String> uris = harvester.harvest(startURI);

		for (String uri : uris) {
			ResourceParser parser = null;
			if (this.resourceAccepter.getExtension(uri).equals(
					ResourceAccepter.EXT_PDF)) {
				parser = new PdfParserImpl(uri);
			} else if (this.resourceAccepter.getExtension(uri).equals(
					ResourceAccepter.EXT_DOC)) {
				parser = new DocParserImpl(uri);
			}
			else if (this.resourceAccepter.getExtension(uri).equals(
					ResourceAccepter.EXT_ODT)) {
				parser = new ODFParserImpl(uri);
			}
			else if (this.resourceAccepter.getExtension(uri).equals(
					ResourceAccepter.EXT_XML)) {
				parser = new XMLParserImpl(uri);
			}
			else if (this.resourceAccepter.getExtension(uri).equals(
							ResourceAccepter.EXT_HTML) || this.resourceAccepter.getExtension(uri).equals(
									ResourceAccepter.EXT_HTM) ||
									this.resourceAccepter.getExtension(uri).equals(
											ResourceAccepter.EXT_XHTML)){
				parser = new HTMLParserImpl(uri);
			}
			else if (this.resourceAccepter.isImage(uri)) {
				parser = new ImageParserImpl(uri);
			}

			if (parser != null) {
				String fulltext = parser.parse();
				if (fulltext != null) {
					this.saveStrategy.saveFile(startURI, uri, fulltext);
				}
			} else {
				LogFile.writeLog("There's no parser available for this type of resource: "
						+ this.resourceAccepter
						.getExtension(uri));
			}
		}
	}
}
