package bbaw.wsp.parser.test;

import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.accepter.WebAccepter;
import bbaw.wsp.parser.execution.FulltextParserExcecution;
import bbaw.wsp.parser.fulltext.document.IDocument;
import bbaw.wsp.parser.fulltext.document.PDFDocument;
import bbaw.wsp.parser.harvester.FileSystemHarvester;
import bbaw.wsp.parser.harvester.WebHarvester;
import bbaw.wsp.parser.saver.DocumentModelStrategy;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.saver.SameStructureStrategy;

/**
 * Test of the fulltext parser.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class FulltextParsingTest {
	private static  String HARVESTER_CONFIG = "C:/ownProjectsWorkspace/wsp/bbaw.wsp.parser/config/harvester.xml";
//	private static String FILESYSTEMPATH = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2012/";
	private static String FILESYSTEMPATH = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/1/";
	private static String WEBURI = "http://192.168.1.203/wsp/";
	private static String SAVEDIR = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest";
	private static String EULERLINK = "http://euler.bbaw.de/euleriana/index.php";
	
	public static void testEuler() {
	  System.out.println("Testing Euler Archive "+EULERLINK);
	  Set<String> accepted = new HashSet<String>();
	  accepted.add(ResourceAccepter.EXT_DOC);
    ResourceAccepter accepter = new WebAccepter(accepted );
    ISaveStrategy saveStrategy = new DocumentModelStrategy();
    
    FulltextParserExcecution e = new FulltextParserExcecution(new WebHarvester(accepter), saveStrategy);
    Set<Object> results = e.parse(EULERLINK);  
    for (Object r : results) {
      IDocument doc = (IDocument) r;
      System.out.println("Doc: "+doc.getURL());
      System.out.println("Fulltext: \n---------\n"+doc.getTextOrig());
    }
	}
	
	/**
	 * Test of FileSystem-Parsing.
	 */
	public static void testFileSystem() {
		ResourceAccepter accepter = new FileSystemAccepter(HARVESTER_CONFIG);
		ISaveStrategy saveStrategy = new SameStructureStrategy(SAVEDIR+"/filesystemtest" );
		
		FulltextParserExcecution e = new FulltextParserExcecution(new FileSystemHarvester(accepter), saveStrategy);
		e.parse(FILESYSTEMPATH);		
	}
	
	/**
	 * Test of HTTP-Parsing.
	 */
	public static void testHTTP() {
		ResourceAccepter accepter = new WebAccepter(HARVESTER_CONFIG);
		ISaveStrategy saveStrategy = new SameStructureStrategy(SAVEDIR+"/webtest");
		
		FulltextParserExcecution e = new FulltextParserExcecution(new WebHarvester(accepter), saveStrategy);
		e.parse(WEBURI);
	}
	
	public static void testHTTPAndDocumentModel() {
		ResourceAccepter accepter = new WebAccepter(HARVESTER_CONFIG);
		ISaveStrategy saveStrategy = new DocumentModelStrategy();
		
		FulltextParserExcecution e = new FulltextParserExcecution(new WebHarvester(accepter), saveStrategy);
		Object ret = e.parse(WEBURI);
	}
	
	public static void testFileSystemAndDocumentModel() {
		ResourceAccepter accepter = new FileSystemAccepter(HARVESTER_CONFIG);
		ISaveStrategy saveStrategy = new DocumentModelStrategy();
		
		FulltextParserExcecution e = new FulltextParserExcecution(new FileSystemHarvester(accepter), saveStrategy);
		Set<Object> results = e.parse(FILESYSTEMPATH);
		for (Object result : results) {
			PDFDocument doc = (PDFDocument) result;
			System.out.println("ParsingResult:");
			System.out.println("Metadata: "+doc.getMetadata());
			System.out.println("------------");
			
			
//			System.out.println("Fulltext:"+doc.getFulltext());
		}		
	}
	
	public static void main(String[] args) {
//		testHTTP();
//		testFileSystem();
//		testHTTPAndDocumentModel();
//		testFileSystemAndDocumentModel();
	  
	  testEuler();
	}
	
}
