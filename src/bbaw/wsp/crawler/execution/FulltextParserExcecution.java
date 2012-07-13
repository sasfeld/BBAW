package bbaw.wsp.crawler.execution;

import java.io.IOException;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import bbaw.wsp.crawler.accepter.FileSystemAccepter;
import bbaw.wsp.crawler.accepter.ResourceAccepter;
import bbaw.wsp.crawler.harvester.IHarvester;
import bbaw.wsp.crawler.parser.DocParserImpl;
import bbaw.wsp.crawler.parser.HTMLParserImpl;
import bbaw.wsp.crawler.parser.ImageParserImpl;
import bbaw.wsp.crawler.parser.ODFParserImpl;
import bbaw.wsp.crawler.parser.PdfParserImpl;
import bbaw.wsp.crawler.parser.ResourceParser;
import bbaw.wsp.crawler.parser.XMLParserImpl;
import bbaw.wsp.crawler.saver.SaveStrategy;
import bbaw.wsp.crawler.tools.LogFile;
import bbaw.wsp.crawler.tools.TextFileWriter;

/**
 * This class executes the fulltext parsing.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class FulltextParserExcecution {

	private IHarvester harvester;
	private SaveStrategy saveStrategy;
	private FileSystemAccepter resourceAccepter;

	/**
	 * Create a new ParserExecution class.
	 * 
	 * @param harvester
	 *            - the harvester instance
	 * @param accepter
	 *            - the accepter instance
	 */
	public FulltextParserExcecution(IHarvester harvester, FileSystemAccepter accepter,
			SaveStrategy saveStrategy) {
		if (harvester == null || accepter == null || saveStrategy == null) {
			throw new IllegalArgumentException(
					"Parameters mustn't be null in FulltextParserExecution!");
		}
		this.harvester = harvester;
		this.harvester.setResourceAccepter(accepter);
		this.resourceAccepter = accepter;
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
