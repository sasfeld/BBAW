/**
 * 
 */
package bbaw.wsp.parser.fulltext.document;

import java.util.ArrayList;
import java.util.List;

import bbaw.wsp.parser.tools.joseph.MetadataRecord;

/**
 * This class realizes an {@link IDocument} and saves the data for a parsed PDF
 * file.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 16.08.2012
 * 
 */
public class PDFDocument extends GeneralDocument {
  public static String MIME_TYPE = "application/pdf";
  public static String SCHEME_NAME = "pdf";
  
	private List<PDFPage> pages;

	/**
	 * Create a new PDFDocument model.
	 * 
	 * @param url
	 *            - URL of the parsed document.
	 * @param fulltext
	 *            - the parsed fulltext.
	 * @param textPages
	 *            - the pages as list of Strings.
	 * @throws IllegalArgumentException
	 *             if one of the parameters is null.
	 */
	public PDFDocument(final String url, final String fulltext,
			final List<String> textPages) {
		super(url, fulltext);

		if (textPages == null) {
			throw new IllegalArgumentException(
					"The value for the parameter textPages in PDFDocument mustn't be null.");
		}

		this.createPages(textPages);
	}

	private void createPages(List<String> textPages) {
		this.pages = new ArrayList<PDFPage>();

		for (int i = 0; i < textPages.size(); i++) {
			this.pages.add(new PDFPage(i + 1, textPages.get(i)));
		}
	}

	/**
	 * Return a {@link PDFPage}.
	 * @param pageNumber - the number of the page. Counting starts at 1 !
	 * @return the {@link PDFPage}
	 * @throws IllegalArgumentException if the pageNumber is out of range.
	 */
	public PDFPage getPage(final int pageNumber) {
		if (pageNumber < 1 || pageNumber > this.pages.size()) {
			throw new IllegalArgumentException("The page number " + pageNumber
					+ " in PDFDocument.getPage() is out of range. Only "
					+ this.pages.size() + "exist");
		}
		return pages.get(pageNumber-1);
	}
	
	/**
	 * Returns the whole list of PDFPages.
	 * @return the list of {@link PDFPage}.
	 */
	public List<PDFPage> getPages() {
		return this.pages;
	}
	
	/**
	 * Set the metadata.
	 * The pageCount attribute of the metadata will fit the current size of the {@link PDFPage} list.
	 * @param metadata an {@link MetadataRecord}
	 */
	public void setMetadata(final MetadataRecord metadata) {
		// Set the PDF attributes
		metadata.setPageCount(this.pages.size());
		metadata.setSchemaName(SCHEME_NAME);
		metadata.setType(MIME_TYPE);
		super.setMetadata(metadata);
	}
	

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "PDFDocument [pages=" + pages + "]";
	}
}
