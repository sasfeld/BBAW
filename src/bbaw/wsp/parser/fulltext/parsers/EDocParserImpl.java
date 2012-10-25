package bbaw.wsp.parser.fulltext.parsers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.fulltext.document.IDocument;
import bbaw.wsp.parser.fulltext.document.PDFDocument;
import bbaw.wsp.parser.fulltext.document.PDFPage;
import bbaw.wsp.parser.saver.DocumentModelStrategy;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.tools.EDocURIParser;
import bbaw.wsp.parser.tools.joseph.DCFetcherTool;
import bbaw.wsp.parser.tools.joseph.MetadataRecord;

/**
 * This class parses an eDoc. An eDoc consists of a basic pdf file and an
 * index.html file which contains the associated metadata. It's represented by
 * the folder structure: [year] / [eDocID] - index.html - /pdf/[eDoc.pdf]
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 15.08.2012
 * 
 */
public class EDocParserImpl extends HTMLParserImpl {
	private static EDocParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache PdfBox
	 * stripper.
	 * 
	 * @return
	 */
	public static EDocParserImpl getInstance() {
		if (instance == null) {
			return new EDocParserImpl();
		}
		return instance;
	}

	private EDocParserImpl() {
		super();
	}

	/**
	 * Parse an eDoc and return the object returned by the {@link ISaveStrategy}
	 * .
	 * 
	 * @return Object returned by the {@link ISaveStrategy}
	 * @throws IllegalArgumentException
	 *             if the uri is null or empty.
	 * @throws IllegalStateException
	 *             if the {@link ISaveStrategy} wasn't set before.
	 */
	public Object parse(final String startUri, final String uri) {
		// Parse eDoc index
		final Object parsedDocIndex = super.parse(startUri, uri);
		
	  if(parsedDocIndex instanceof IDocument) {
	    final IDocument doc = (IDocument) parsedDocIndex;
	    System.out.println("DocContent: "+doc.getTextOrig());
	    String eDocUrl = EDocURIParser.getDocURI(doc.getTextOrig());
	    System.out.println("eDocUrl: "+eDocUrl);
	    MetadataRecord metadata = new MetadataRecord();
	    URL srcUrl;
      try {
        srcUrl = new URL(eDocUrl);
        metadata = DCFetcherTool.fetchHtmlDirectly(srcUrl, metadata);
        
        
        // Parse eDoc
        PdfParserImpl.getInstance().setSaveStrategy(this.saveStrategy);
        final Object parsedEDoc = PdfParserImpl.getInstance().parsePages(startUri, eDocUrl);      
        if(parsedEDoc instanceof PDFDocument) {
          final PDFDocument parsedPDF = (PDFDocument) parsedEDoc;
          parsedPDF.setMetadata(metadata);
          List<String> newPages = new ArrayList<String>();
          for (PDFPage page : parsedPDF.getPages()) {
            newPages.add(page.getTextOrig());
          }
          
          return this.saveStrategy.saveFile(startUri, uri, newPages);
        }
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
	    
	  }
	  
	  return null;
	}
	
	public static void main(String[] args) {
		EDocParserImpl eDocParser = new EDocParserImpl();
		String uri = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/1/pdf/29kstnGPLz2IM.pdf";
		String startUri = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/1/";
		eDocParser.setSaveStrategy(new DocumentModelStrategy());
		eDocParser.parse(startUri, uri);
	}
}
