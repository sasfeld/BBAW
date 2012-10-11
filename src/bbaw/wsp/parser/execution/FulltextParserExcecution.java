package bbaw.wsp.parser.execution;

import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.fulltext.parsers.DocParserImpl;
import bbaw.wsp.parser.fulltext.parsers.HTMLParserImpl;
import bbaw.wsp.parser.fulltext.parsers.ImageParserImpl;
import bbaw.wsp.parser.fulltext.parsers.ODFParserImpl;
import bbaw.wsp.parser.fulltext.parsers.PdfParserImpl;
import bbaw.wsp.parser.fulltext.parsers.ResourceParser;
import bbaw.wsp.parser.fulltext.parsers.TXTParserImpl;
import bbaw.wsp.parser.fulltext.parsers.XMLParserImpl;
import bbaw.wsp.parser.fulltext.parsers.EDocParserImpl;
import bbaw.wsp.parser.harvester.Harvester;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class executes the fulltext parsing.
 * It includes a complete harvesting, parsing and saving job.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @version 2.0
 * @date 16.08.2012
 * 
 */
public class FulltextParserExcecution {

	private Harvester harvester;
	private ISaveStrategy saveStrategy;

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
	 * Parse resources of all kinds. The resources will be won by the specified {@link Harvester}.
	 * Exceptions are written in a log file defined by the {@link LogFile} class.
	 * 
	 * @return the result of the used {@link ISaveStrategy} in a Set.
	 */
	public Set<Object> parse(final String startURI) {
		Set<String> uris = harvester.harvest(startURI);
		Set<Object> results = new HashSet<Object>();

		for (String uri : uris) {
			if (DebugMode.DEBUG) {
				System.out.println("Building parser for URI: " + uri);
			}
			ResourceParser parser = null;
			if (this.harvester.getResourceAccepter().isEDocIndex(uri)) {
				parser = EDocParserImpl.getInstance();
				System.out.println("is eDoc");
			}
			else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_PDF)) {
				parser = PdfParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_DOC)) {
				parser = DocParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_ODT)) {
				parser = ODFParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_XML)) {
				parser = XMLParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_HTML)
					|| this.harvester.getResourceAccepter().getExtension(uri)
							.equals(ResourceAccepter.EXT_HTM)
					|| this.harvester.getResourceAccepter().getExtension(uri)
							.equals(ResourceAccepter.EXT_XHTML)) {
				parser = HTMLParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().isImage(uri)) {
				parser = ImageParserImpl.getInstance();
			} else if (this.harvester.getResourceAccepter().getExtension(uri)
					.equals(ResourceAccepter.EXT_TXT)) {
				parser = TXTParserImpl.getInstance();
			}

			// Set the SaveStrategy
			if (parser != null) {
				parser.setSaveStrategy(this.saveStrategy);
			}
			
			if(DebugMode.DEBUG) {
				System.out.println("ParserType: "+parser.getClass());
			}

			
			if (parser != null) {
				Object result = parser.parse(startURI, uri);
				results.add(result);
			} else {
				LogFile.writeLog("There's no parser available for this type of resource: "
						+ this.harvester.getResourceAccepter()
								.getExtension(uri));
			}
		}
		return results;
	}
	

}
