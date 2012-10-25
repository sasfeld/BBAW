package bbaw.wsp.parser.test;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;
import bbaw.wsp.parser.metadata.factory.MetadataParserFactory;
import bbaw.wsp.parser.metadata.parsers.RdfMetadataParser;

/**
 * Test of the metadata parsers.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class MetadataParsingTest {

  public static void main(String[] args) {
    testRdf();
  }

  private static void testRdf() {
   try {
    RdfMetadataParser rdfParser = MetadataParserFactory.newRdfMetadataParser("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/outputs/eDocToRdfTest/132.rdf.xml");
    System.out.println(rdfParser.getRdfAboutValue());
  } catch (ApplicationException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }    
  }

}
