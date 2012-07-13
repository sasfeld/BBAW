package bbaw.wsp.metadata;

import bbaw.wsp.crawler.control.DebugMode;


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
	 * @throws IllegalArgumentException if the input object is null.
	 */
	public static String[] getStringToArray(Object modsPersonal) {
		if(modsPersonal == null ) {
			throw new IllegalArgumentException(
					"The value for the parameter modsPersonal mustn't be empty.");
		}		
		String[] asArray = modsPersonal.toString().split("(\")?],");
		
		return removeCtrlChars(asArray);
	}
	
	/**
	 * Remove the XPath special characters.
	 * @param input - the return value of XPath.
	 * @return a String that doesn't contain any XPath characters.
	 * @throws IllegalArgumentException if the input string is empty or null or equals [] (an empty mods tag).
	 */
	public static String removeCtrlChars(final String input) {
		if(input == null || input.length() == 0 || input.equals("[]")) {
			throw new IllegalArgumentException(
					"The value for the parameter input in removeCtrlChars mustn't be empty.");
		}		
		// Ex. value: []
		String ret = input.replaceFirst("[\\[]+(\\w)+: ((\\w)+=\")?", "");
		int lastPos = input.lastIndexOf("]");
		if(lastPos == input.length()-1) {
			ret = ret.substring(0, ret.length()-1);
			if(ret.length() > 1 && ret.lastIndexOf("\"]") == ret.length()-2) {
				if(DebugMode.DEBUG) {
					System.out.println("removeCtrlChars: String ret: " +ret);
				}
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
	 * @throws IllegalArgumentException if the input array of string is empty or null.
	 */
	public static String[] removeCtrlChars(final String[] input) {
		if(input == null || input.length == 0 ) {
			throw new IllegalArgumentException(
					"The parameter array input mustn't be empty.");
		}		
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
