package bbaw.wsp.metadata;


/**
 * This is a helper class that provides String filters.
 * It fits the JDOM XPath selections.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class ModsHelper {

	/**
	 * Build an array - this methods should be called when using the XPath selectNodes() - method.
	 * @param modsPersonal - the return value of XPath.
	 * @return a String containing the information. 
	 */
	public static String[] getStringToArray(Object modsPersonal) {
		String[] asArray = modsPersonal.toString().split("(\")?],");
		
		return removeCtrlChars(asArray);
	}
	
	/**
	 * Remove the XPath special characters.
	 * @param input - the return value of XPath.
	 * @return a String that doesn't contain any XPath characters.
	 */
	public static String removeCtrlChars(final String input) {
		// Ex. value: []
		String ret = input.replaceFirst("[\\[]+(\\w)+: ((\\w)+=\")?", "");
		int lastPos = input.lastIndexOf("]");
		if(lastPos == input.length()-1) {
			ret = ret.substring(0, ret.length()-1);
			if(ret.lastIndexOf("\"]") == ret.length()-2) {
				ret = ret.substring(0, ret.length()-2);
			}
		}
		return ret;
//		return input;
	}
	
	/**
	 * Remove the Control chars in an Array containing XPath return values.
	 * @param input - the Array of String
	 * @return the "cleaned" array of String
	 */
	public static String[] removeCtrlChars(final String[] input) {
		String[] ret = new String[input.length];
		int i = 0;
		for (String element : input) {
			String newEl = removeCtrlChars(element);
			ret[i] = newEl;
			++i;
		}
		return ret;				
	}

	
}
