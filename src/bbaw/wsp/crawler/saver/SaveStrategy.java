/**
 * This package contains the saving strategy for fulltexts that are recieved from the parser.
 */
package bbaw.wsp.crawler.saver;


/**
 * The abstract Strategy that all other strategie classes extend.
 * @author wsp-shk1
 *
 */
public abstract class SaveStrategy {	
	
	public abstract void saveFile(final String uri, final String text);
}
