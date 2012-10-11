package bbaw.wsp.parser.control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;


import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.execution.FulltextParserExcecution;
import bbaw.wsp.parser.execution.MetadataParserExecution;
import bbaw.wsp.parser.harvester.FileSystemHarvester;
import bbaw.wsp.parser.harvester.Harvester;
import bbaw.wsp.parser.metadata.WSPMetadataRecord;
import bbaw.wsp.parser.saver.EDocToFileTreeStrategy;
import bbaw.wsp.parser.saver.SameStructureStrategy;
import bbaw.wsp.parser.saver.ISaveStrategy;

/**
 * This is the central control class.
 * Start parsing from here.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class ControlClass {
	public static String TEMPFOLDER = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/temp";
	
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
//		acceptedResources.add(".doc");
		acceptedResources.add(".pdf");
//		acceptedResources.add(".odt");
//		acceptedResources.add(".xml");
//		acceptedResources.add(".jpg");
//		acceptedResources.add(".png");
//		acceptedResources.add(".html");
//		FileSystemAccepter accepter = new FileSystemAccepter(acceptedResources );
		FileSystemAccepter accepter = new FileSystemAccepter("C:/ownProjectsWorkspace/wsp/bbaw.wsp.parser/config/harvester.xml");
		String saveDir = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest";
		ISaveStrategy saveStrategy = new SameStructureStrategy(saveDir);
		FulltextParserExcecution e = new FulltextParserExcecution(new FileSystemHarvester(accepter), saveStrategy  );
		e.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/");
//		e.parse("//192.168.1.203/wsp-web-test");
//		e.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/OxygenXMLEditor/samples");
		
//		MetadataParserExecution metaParser = new MetadataParserExecution(new FileSystemHarvester(accepter));
//		Set<WSPMetadataRecord> results = metaParser.parse("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/eXist-wsp/db/wsp/dataMods/");
//		for (WSPMetadataRecord wspMetadataRecord : results) {
//			System.out.println(wspMetadataRecord);
//		}
	}	
	
	public static void main (String[] args)throws IOException, SAXException, TikaException  {
		new ControlClass().runParsing();
	}
}
