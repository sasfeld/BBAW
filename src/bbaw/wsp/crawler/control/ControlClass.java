package bbaw.wsp.crawler.control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;


import bbaw.wsp.crawler.accepter.FileSystemAccepter;
import bbaw.wsp.crawler.accepter.ResourceAccepter;
import bbaw.wsp.crawler.execution.FulltextParserExcecution;
import bbaw.wsp.crawler.execution.MetadataParserExecution;
import bbaw.wsp.crawler.harvester.FileSystemHarvester;
import bbaw.wsp.crawler.harvester.IHarvester;
import bbaw.wsp.crawler.saver.EDocToFileTreeStrategy;
import bbaw.wsp.crawler.saver.SameStructureStrategy;
import bbaw.wsp.crawler.saver.SaveStrategy;
import bbaw.wsp.metadata.WSPMetadataRecord;

/**
 * This is the central control class.
 * Start parsing from here.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class ControlClass {
	
	
	public ControlClass() {
	}
	
	/**
	 * Start parsing here.
	 * Define a {@link ResourceAccepter} here, give it to a {@link FulltextParserExcecution} instance.
	 * The {@link FulltextParserExcecution} needs an {@link IHarvester} which fetches the URIS accepted by the {@link ResourceAccepter}.
	 * Finally, run the parse-command on your {@link FulltextParserExcecution} instance.
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void runParsing() throws IOException, SAXException, TikaException {
		Set<String> acceptedResources = new HashSet<String>();
		acceptedResources.add(".doc");
		acceptedResources.add(".pdf");
		acceptedResources.add(".odt");
		acceptedResources.add(".xml");
		acceptedResources.add(".jpg");
		acceptedResources.add(".png");
		acceptedResources.add(".html");
		FileSystemAccepter accepter = new FileSystemAccepter(acceptedResources );
		String saveDir = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest";
		SaveStrategy saveStrategy = new SameStructureStrategy(saveDir);
		FulltextParserExcecution e = new FulltextParserExcecution(new FileSystemHarvester(), accepter, saveStrategy  );
//		e.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/");
//		e.parse("//192.168.1.203/wsp-web-test");
//		e.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/OxygenXMLEditor/samples");
		
		MetadataParserExecution metaParser = new MetadataParserExecution(new FileSystemHarvester(), accepter);
		Set<WSPMetadataRecord> results = metaParser.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/eXist-wsp/db/wsp/dataMods/");
		for (WSPMetadataRecord wspMetadataRecord : results) {
			System.out.println(wspMetadataRecord);
		}
	}	
	
	public static void main (String[] args)throws IOException, SAXException, TikaException  {
		new ControlClass().runParsing();
	}
}
