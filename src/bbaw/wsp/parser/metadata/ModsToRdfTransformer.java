package bbaw.wsp.parser.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A simple test class to do an XSLT transformation from the old WSP xml
 * documents to the RDF destination.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 04.10.2012
 * 
 */
public class ModsToRdfTransformer implements ErrorListener {

  /**
   * Path to the XSLT file.
   */
  private static final String XSLT_FILE = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/modstordf.xsl";
  /**
   * Path to the original XML file.
   */
  private static final String INPUT_FILE = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/inputs/20090709-1-WS-BBAW.xml";
  /**
   * The resulting output file.
   */
  private static final String OUTPUT_FILE = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/outputs/convertedtordf.rdf";
  private XsltExecutable executable;

  public ModsToRdfTransformer() {
    try {
      XMLReader xmlReader = XMLReaderFactory.createXMLReader();
      
      // XSL file
      InputStream xsltStream = new FileInputStream(new File(XSLT_FILE));
    
      Source xsltSource = new SAXSource(xmlReader, new InputSource(xsltStream));
      
      
      Processor processor = new Processor(false);     
      XsltCompiler compiler = processor.newXsltCompiler();
      compiler.setErrorListener(this);
      
     

      this.executable = compiler.compile(xsltSource);

    } catch (IOException | SAXException | SaxonApiException e) {
      e.printStackTrace();
    }
  }

  public void execute() {
    try {
      // Output file
      OutputStream outputStream = new FileOutputStream(new File(OUTPUT_FILE));
      Serializer serializer = new Serializer();
      serializer.setOutputProperty(Serializer.Property.METHOD, "xml");      
      serializer.setOutputStream(outputStream);
      
      // input file
      InputStream inputStream = new FileInputStream(new File(INPUT_FILE));
      StreamSource inputSource = new StreamSource(inputStream);
      
      // Transformation job
      XsltTransformer transformer = this.executable.load();   
      // source file
      transformer.setSource(inputSource);
      transformer.setDestination(serializer);
      transformer.transform();
    } catch (SaxonApiException | FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void error(TransformerException arg0) throws TransformerException {
    
  }

  @Override
  public void fatalError(TransformerException arg0) throws TransformerException {
    // TODO Auto-generated method stub

  }

  @Override
  public void warning(TransformerException arg0) throws TransformerException {
    // TODO Auto-generated method stub

  }
  
  /**
   * Test of the Saxon Transformation.
   */
  public static void main(String[] args) {
    testModsToRdf();
  }

  private static void testModsToRdf() {
    ModsToRdfTransformer transformer = new ModsToRdfTransformer();
    transformer.execute();    
  }

}
