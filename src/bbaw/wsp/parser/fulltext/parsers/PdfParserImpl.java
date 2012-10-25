package bbaw.wsp.parser.fulltext.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.tika.parser.pdf.PDFParser;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.saver.ISaveStrategy;
import bbaw.wsp.parser.tools.LogFile;

/**
 * This class parses a PDF file. It now uses Apache PDFBox. It uses the
 * Singleton pattern. Only one instance can exist.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.08.2012
 * @version 2.0
 * 
 */
public class PdfParserImpl extends ResourceParser {
	private static PdfParserImpl instance;

	/**
	 * Return the only existing instance. The instance uses an Apache PdfBox
	 * stripper.
	 * 
	 * @return
	 */
	public static PdfParserImpl getInstance() {
		if (instance == null) {
			return new PdfParserImpl();
		}
		return instance;
	}

	// Protected because this parser may get extended
	protected PdfParserImpl() {
		super(new PDFParser());
	}

	/**
	 * Parse a pdf-document and return the object returned by the {@link ISaveStrategy}
	 * .
	 * 
	 * @return Object returned by the {@link ISaveStrategy}
	 * @throws IllegalArgumentException
	 *             if the uri is null or empty.
	 * @throws IllegalStateException
	 *             if the {@link ISaveStrategy} wasn't set before.
	 */
	public Object parse(final String startUri, final String uri) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter parser in the method parse() in PdfParserImpl mustn't be empty.");
		}
		if (this.saveStrategy == null) {
			throw new IllegalStateException(
					"You must define a saveStategy before calling the parse()-method in ResourceParser.");
		}
		try {
			PDDocument document;
			InputStream input = this.resourceReader.read(uri);
			document = PDDocument.load(input);
			List<String> pagesTexts = new ArrayList<String>();
			String text = "";

			for (int i = 1; i <= document.getNumberOfPages(); i++) {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				text += "[page=" + i + "]\n" + stripper.getText(document);
				if (DebugMode.DEBUG) {
					System.out.println("page " + i + ":");
					System.out.println(text);
				}
				pagesTexts.add(text);
			}

			return this.saveStrategy.saveFile(uri, uri, pagesTexts);
		} catch (IOException e) {
			// Write log
			LogFile.writeLog("Problem while parsing file " + uri
					+ "  -- exception: " + e.getMessage() + "\n");
			return null;
		}

	}

	/**
	 * Parse a pdf-document and return a list that contains the fulltext for the
	 * page.
	 * 
	 * @return a list of Strings that map the page number to the fulltext or
	 *         null if the resource can't get opened. * @throws
	 *         IllegalArgumentException if the uri is null or empty
	 * @throws IllegalStateException
	 *             if the {@link ISaveStrategy} wasn't set before.
	 */
	public Object parsePages(final String startUri, final String uri) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter parser in the method parsePages() in PdfParserImpl mustn't be empty.");
		}
		if (this.saveStrategy == null) {
			throw new IllegalStateException(
					"You must define a saveStategy before calling the parse()-method in ResourceParser.");
		}
		try {
			PDDocument document;
			if (DebugMode.DEBUG) {
				System.out.println("PdfParserImpl: Parsing " + uri);
			}
			InputStream input = this.resourceReader.read(uri);
			document = PDDocument.load(input);
			List<String> pagesTexts = new ArrayList<String>();
			String text = "";

			for (int i = 1; i <= document.getNumberOfPages(); i++) {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				text = stripper.getText(document);
				if (DebugMode.DEBUG) {
					System.out.println("page " + i + ":");
					System.out.println(text);

				}
				pagesTexts.add(text);
			}

			return this.saveStrategy.saveFile(uri, uri, pagesTexts);
		} catch (IOException e) {
			// Write log
			LogFile.writeLog("Problem while parsing file " + uri
					+ "  -- exception: " + e.getMessage() + "\n");
			return null;
		}
	}

	public static void main(String[] args) {
		// System.out.println(new
		// PdfParserImpl("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/1/pdf/29kstnGPLz2IM.pdf").parse());
		// new
		// PdfParserImpl("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2006/2/pdf/2565N0u5kOc.pdf").parse();

	}

}
