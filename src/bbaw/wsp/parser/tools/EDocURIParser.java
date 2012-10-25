package bbaw.wsp.parser.tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A (static) class which offers methods to parse an special eDoc uri.
 * It's used by the {@link EDocURIParser}.
 * @author Sascha Feldmann (wsp-shk1)
 * @date 15.08.2012
 *
 */
public class EDocURIParser {

	/**
	 * Return the {@link URL} to the associated index.html file. This contains the metadata. 
	 * @param docURI the (input) uri (the uri to be parsed) of the eDoc.
	 * @return the {@link URL} to the index.html file
	 */
	public static URL getIndexURI(final URL docURI) {		
		int lastSlash = docURI.toString().lastIndexOf("pdf/");
		String newString = docURI.toString().substring(0, lastSlash) + "index.html";
		
		
		URL indexURI;
		try {
			indexURI = new URL(newString);
			return indexURI;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * Fetch the reference to the underlaying eDoc within an eDoc index.html file.
	 * @param indexContent - the (HTML parsed) index.html content
	 * @return the URL as String to the underlaying eDoc. May return null, if not defined.
	 */
	public static String getDocURI(final String indexContent) {
	  Pattern p = Pattern.compile("(?i)URL: ([/.:\\p{Alnum}]+)"); 
	  for (Matcher m = p.matcher(indexContent); m.find(); ) {
      if(m.group(1).contains("http")) {
        return m.group(1);
      }
    }
	  return null;
	}
}
