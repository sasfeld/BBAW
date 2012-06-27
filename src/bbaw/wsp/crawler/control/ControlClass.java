package bbaw.wsp.crawler.control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;


import bbaw.wsp.crawler.accepter.FileSystemAccepter;
import bbaw.wsp.crawler.accepter.ResourceAccepter;
import bbaw.wsp.crawler.execution.ParserExcecution;
import bbaw.wsp.crawler.harvester.FileSystemHarvester;
import bbaw.wsp.crawler.harvester.IHarvester;
import bbaw.wsp.crawler.saver.EDocToFileTreeStrategy;
import bbaw.wsp.crawler.saver.SaveStrategy;

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
	 * Define a {@link ResourceAccepter} here, give it to a {@link ParserExcecution} instance.
	 * The {@link ParserExcecution} needs an {@link IHarvester} which fetches the URIS accepted by the {@link ResourceAccepter}.
	 * Finally, run the parse-command on your {@link ParserExcecution} instance.
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	public void runParsing() throws IOException, SAXException, TikaException {
		Set<String> acceptedResources = new HashSet<String>();
		acceptedResources.add(".pdf");
		FileSystemAccepter accepter = new FileSystemAccepter(acceptedResources );
		String saveDir = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest";
		SaveStrategy saveStrategy = new EDocToFileTreeStrategy(saveDir);
		ParserExcecution e = new ParserExcecution(new FileSystemHarvester(), accepter, saveStrategy  );
		e.parsePdf("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/");		
	}	
	
	public static void main (String[] args)throws IOException, SAXException, TikaException  {
		new ControlClass().runParsing();
	}
}
