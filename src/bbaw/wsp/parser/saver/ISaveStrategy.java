/**
 * This package contains the saving strategy for fulltexts that are recieved from the parser.
 */
package bbaw.wsp.parser.saver;


/**
 * The interface Strategy that all other strategie classes extend.
 * @author wsp-shk1
 *
 */
public interface ISaveStrategy {	
	
	abstract void saveFile(final String startURI, final String uri, final String text);

	void saveFile(String uri, String text);
}
