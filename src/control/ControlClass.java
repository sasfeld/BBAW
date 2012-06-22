package control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import execution.ParserExcecution;

import bbaw.wsp.crawler.accepter.FileSystemAccepter;
import bbaw.wsp.crawler.harvester.FileSystemHarvester;
import bbaw.wsp.crawler.harvester.IHarvester;

public class ControlClass {
	
	
	public ControlClass() {
	}
	
	public void runParsing() throws IOException, SAXException, TikaException {
		Set<String> acceptedResources = new HashSet<String>();
		acceptedResources.add(".pdf");
		FileSystemAccepter accepter = new FileSystemAccepter(acceptedResources );
		ParserExcecution e = new ParserExcecution(new FileSystemHarvester(), accepter);
		e.parsePdf("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/201");		
	}
	
	public static void main (String[] args)throws IOException, SAXException, TikaException  {
		new ControlClass().runParsing();
	}
}
