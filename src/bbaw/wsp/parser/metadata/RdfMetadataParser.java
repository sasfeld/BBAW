package bbaw.wsp.parser.metadata;

import java.io.File;

import net.sf.saxon.om.ValueRepresentation;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.trans.XPathException;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class is able to parse a MODS file that has the specified values of the
 * old knowledge store.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class RdfMetadataParser {
	private XPathCompiler xPathCompiler;
	private XdmNode contextItem;

	/**
	 * Create a new ModsMetadataParser instance.
	 * 
	 * @param uri
	 *            - the URI to the knowledge store metadata record.
	 * @throws IllegalArgumentException
	 *             if the uri is null, empty or doesn't refer to an existing
	 *             file.
	 */
	public RdfMetadataParser(final String uri) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of ModsMetadataParser mustn't be empty.");
		}

		Processor processor = new Processor(false);
		xPathCompiler = processor.newXPathCompiler();
		xPathCompiler.declareNamespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
		xPathCompiler.declareNamespace("dc", "http://purl.org/elements/1.1/");
		DocumentBuilder builder = processor.newDocumentBuilder();
		try {
			contextItem = builder.build(new File(uri));
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}
	}
	
	public void parseTest() {
	  System.out.println("testing...");
	  String[] ergs = (String[]) buildXPath(
        "//rdf:Description/text()", true);
	  for (String erg : ergs) {
	    System.out.println(erg);
	    System.out.println("...");
    }
	 String erg = (String) buildXPath(
       "//rdf:description/dc:source/text()", false);
	 System.out.println(erg);
	 System.out.println("----");
	}

	/**
	 * Parse the record and return an {@link WSPMetadataRecord} object.
	 * 
	 * @return an {@link WSPMetadataRecord} object.
	 */
	public WSPMetadataRecord parse() {
		final WSPMetadataRecord modsObject = new WSPMetadataRecord();

		final String id = parseID();
		modsObject.setID(id);

		final String url = parseURL();
		modsObject.setUrl(url);

		final String title = parseTitle();
		modsObject.setTitle(title);

		final String mods_abstract = parseAbstract();
		modsObject.setMods_abstract(mods_abstract);

		final String publisher = parsePublisher();
		modsObject.setPublisher(publisher);

		final String dateIssued = parseDateIssued();
		modsObject.setDateIssued(dateIssued);

		final String[] persIds = parsePersonalIds();
		modsObject.setPersonals(persIds);

		final String placeTerm = parsePlaceTerm();
		modsObject.setPlaceTerm(placeTerm);

		final String[] topics = parseTopics();
		modsObject.setTopics(topics);

		final String[] geographics = parseGeographics();
		modsObject.setGeographics(geographics);

		final String temporalStart = parseTemporalStart();
		modsObject.setTemporalStart(temporalStart);

		final String temporalEnd = parseTemporalEnd();
		modsObject.setTemporalEnd(temporalEnd);

		return modsObject;
	}

	private String parseTemporalEnd() {
		return (String) buildXPath(
				"//mods:subject/mods:temporal[@point='end']/text()", false);
	}

	private String parseTemporalStart() {
		return (String) buildXPath(
				"//mods:subject/mods:temporal[@point='start']/text()", false);
	}

	private String[] parseGeographics() {
		 return (String[]) buildXPath("//mods:subject/mods:geographic/text()",
		 true);		
	}

	private String[] parseTopics() {
		return (String[]) buildXPath("//mods:subject/mods:topic/text()", true);
	}

	private String parseDateIssued() {
		return (String) buildXPath("//mods:originInfo/mods:dateIssued/text()",
				false);
	}

	private String parsePublisher() {
		return (String) buildXPath("//mods:originInfo/mods:publisher/text()",
				false);
	}

	private String parseAbstract() {
		return (String) buildXPath("//mods:abstract/text()", false);
	}

	private String parseTitle() {
		return (String) buildXPath("//mods:titleInfo/mods:title/text()", false);
	}

	private String parsePlaceTerm() {
		return (String) buildXPath(
				"//mods:originInfo/mods:place/mods:placeTerm/text()", false);
	}

	private String parseID() {
		return (String) buildXPath("//mods:recordIdentifier/text()", false);
	}

	private String[] parsePersonalIds() {
		 return (String[]) buildXPath("//mods:name[@type='personal']/@ID",
		 true);		
	}

	private String parseURL() {
		return (String) buildXPath("//mods:url/text()", false);
	}

	private Object buildXPath(final String query, final boolean moreNodes) {
		try {
			XPathExecutable x = xPathCompiler.compile(query);

			XPathSelector selector = x.load();
			selector.setContextItem(this.contextItem);
			XdmValue value = selector.evaluate();
			if (moreNodes) {
				String[] list = new String[value.size()];
				int i = 0;
				for (XdmItem xdmItem : value) {					
					list[i] = xdmItem.toString();
					++i;
				}
				// Replace attribute chars
				return ModsHelper.removeAttributeChars(list);
			}
			
			System.out.println(value);
			ValueRepresentation rep = value.getUnderlyingValue();
			System.out.println(rep.getStringValue());
			// Replace attribute chars
			return ModsHelper.removeAttributeChars(rep.getStringValue());		
		} catch (SaxonApiException | XPathException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// String url =
		// "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/eXist-wsp/db/wsp/dataMods/20090714-2-WS-BBAW.xml";
		 String url =
		 "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/outputs/eDocToRdfTest/132.rdf.xml";
//		String url = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/eXist-wsp/db/wsp/dataMods/20090716-5-WS-BBAW.xml";
		RdfMetadataParser parser = new RdfMetadataParser(url);
		parser.parseTest();
		

	}

}
