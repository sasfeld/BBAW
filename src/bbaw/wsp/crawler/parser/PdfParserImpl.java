package bbaw.wsp.crawler.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.Set;

import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.LogFile;
import bbaw.wsp.crawler.tools.TextFileWriter;



/**
 * This class parses a PDF file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class PdfParserImpl {
	/**
	 * The File instance.
	 */
	private File uri;
	/**
	 * Create a new PdfParser instance.
	 * 
	 * @param uri
	 *            - the URI to the document.
	 * @throws IllegalArgumentException
	 *             if the uri is null, empty or doesn't refer to an existing
	 *             file.
	 */
	public PdfParserImpl(String uri) {
		if (uri == null || uri.isEmpty()) {
			throw new IllegalArgumentException(
					"The value for the parameter uri in the constructor of PdfParserImpl mustn't be empty.");
		}		
		File f = new File(uri);
		if (!f.exists()) {
			throw new IllegalArgumentException(
					"The document doesn't exist in PdfParserImpl.");
		}
		this.uri = f;
	}

	/**
	 * Parse the pdf file and generate a full text file.
	 * The full text file be saved under saveDir/[eDocStructure]+/FILE_NAME
	 * @return the full text as String or null if the parsing was not successfull.
	 * 
	 */
	public String parse()  {
		InputStream input;
		try {
			input = new FileInputStream(this.uri);
			if(DebugMode.DEBUG) {
				System.out.println("Parsing document: " + this.uri);
				}
				// Don't limit the amount of characters -> -1 as argument
				ContentHandler textHandler = new BodyContentHandler(-1);
				Metadata metadata = new Metadata();
				PDFParser parser = new PDFParser();
				parser.parse(input, textHandler, metadata);		
				input.close();
				return textHandler.toString();
		} catch (IOException | SAXException | TikaException e) {
			// Write log					
			TextFileWriter.getInstance().writeTextFile(LogFile.fileRef.getParentFile().getPath(), LogFile.fileRef.getName(), "Problem while parsing file "+this.uri + "  -- exception: "+e.getMessage()+"\n", true);
			return null;
		}		
	}

}
