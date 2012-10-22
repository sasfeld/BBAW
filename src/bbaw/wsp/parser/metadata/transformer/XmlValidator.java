package bbaw.wsp.parser.metadata.transformer;

import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import bbaw.wsp.parser.fulltext.readers.ResourceReaderImpl;

import thredds.catalog2.builder.ThreddsMetadataBuilder.DocumentationBuilder;
import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XdmNode;

/**
 * This class offers methods to check the validation of an transformed XML. 
 * It uses the saxon library. 
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 22.10.2012
 *
 */
public class XmlValidator {
  
  private static ResourceReaderImpl resourceReader = new ResourceReaderImpl();
  /**
   * Set debug mode. If true, console logs will be generated.
   */
  public static boolean debug = true;

  @SuppressWarnings("unused")
  public static boolean isValid(final String uri) {
    Processor processor = new Processor(false);
     
    DocumentBuilder builder = processor.newDocumentBuilder();
    XMLReader xmlReader;
    try {
      xmlReader = XMLReaderFactory.createXMLReader();
      Source source = new SAXSource(xmlReader, new InputSource(resourceReader.read(uri)));
      XdmNode contextItem = builder.build(source );
      
    } catch (SAXException | SaxonApiException e) {
      // TODO Auto-generated catch block
      if(debug) {
        e.printStackTrace();
      }     
      return false;
    }       
    return true;
  }
  
}
