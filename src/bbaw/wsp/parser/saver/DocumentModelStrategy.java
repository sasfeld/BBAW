package bbaw.wsp.parser.saver;

import java.util.List;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.fulltext.document.GeneralDocument;
import bbaw.wsp.parser.fulltext.document.IDocument;
import bbaw.wsp.parser.fulltext.document.PDFDocument;

/**
 * This class realizes a DocumentModel - Strategy.
 * That means the strategy creates a new document model which is accessible by the {@link IDocument} interface for each parsed document.
 * Last change: saveFile() now uses a {@link StringBuilder} to concatenate the fulltext String.
 * @author Sascha Feldmann (wsp-shk1)
 * @date 16.08.2012
 *
 */
public class DocumentModelStrategy implements ISaveStrategy {

	/*
	 * (non-Javadoc)
	 * @see bbaw.wsp.parser.saver.ISaveStrategy#saveFile(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Object saveFile(final String startURI, final String uri, final String text) {
		IDocument document = new GeneralDocument(uri, text);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * @see bbaw.wsp.parser.saver.ISaveStrategy#saveFile(java.lang.String, java.lang.String, java.util.List)
	 */
	public Object saveFile(final String startURI, final String uri, final List<String> textPages) {
		StringBuilder textBuilder = new StringBuilder();
		for (int i = 1; i <= textPages.size(); i++) {
		  textBuilder.append("[page="+i+"]\n"+textPages.get(i-1));
		} 
		if(DebugMode.DEBUG) {
			System.out.println("URI: "+uri);
		}
		IDocument document = new PDFDocument(uri, textBuilder.toString(), textPages);
		return document;
	}

}
