package bbaw.wsp.parser.test;

import java.util.HashSet;
import java.util.Set;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;
import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.harvester.FileSystemHarvester;
import bbaw.wsp.parser.harvester.Harvester;
import bbaw.wsp.parser.metadata.transformer.EdocToRdfTransformer;
import bbaw.wsp.parser.metadata.transformer.ModsToRdfTransformer;

/**
 * Several transformation tests.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class TransformationTest {

  
  public static void main(String[] args) throws ApplicationException {
//    doModsToRdf();
    doEdocToRdf();
  }

  private static void doEdocToRdf() throws ApplicationException {
    EdocToRdfTransformer transformer = EdocToRdfTransformer.getInstance();
    String rdfOutput = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/outputs/eDocToRdfTest";
    Set<String> acceptedResources = new HashSet<String>();
    acceptedResources.add(".html");
    Harvester harvester = new FileSystemHarvester(new FileSystemAccepter(acceptedResources ));
    String startURI = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006";
    Set<String> resultUris = harvester.harvest(startURI);
    for (String uri : resultUris) {
      System.out.println("Processing transformation for "+uri);
      transformer.doTransformation(uri, rdfOutput+"/"+uri.substring(uri.lastIndexOf("\\2006\\")+5, uri.lastIndexOf("\\"))+".rdf.xml");
    }    
    
    
  }

  private static void doModsToRdf() throws ApplicationException {
    ModsToRdfTransformer transformer = ModsToRdfTransformer.getInstance();
    String xslInput = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/modstordf.xsl";
    transformer.setXslInput(xslInput);
    
    String rdfOutput = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/outputs/ModsToRdfTest";
    Set<String> acceptedResources = new HashSet<String>();
    acceptedResources.add(".xml");
    Harvester harvester = new FileSystemHarvester(new FileSystemAccepter(acceptedResources ));
    String startURI = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/eXist-wsp/db/wsp/dataMods";
    Set<String> resultUris = harvester.harvest(startURI);
    for (String uri : resultUris) {
      System.out.println("Processing transformation for "+uri);
      transformer.doTransformation(uri, rdfOutput+"/"+uri.substring(uri.lastIndexOf("\\")+1)+".rdf");
    }    
       
  }
}
