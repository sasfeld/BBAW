/**
 * This package contains the saving strategy for fulltexts that are recieved from the parser.
 */
package bbaw.wsp.parser.saver;

import java.util.List;


/**
 * The interface Strategy that all other strategie classes extend.
 * @author wsp-shk1
 *
 */
public interface ISaveStrategy {	
	
	/**
	 * Save a fulltext.
	 */
	Object saveFile(final String startURI, final String uri, final String text);
	
	/**
	 * Save fulltext for a specified PDF page.
	 */
	Object saveFile(final String startURI, String uri, List<String> textPages);
}
