package bbaw.wsp.parser.tools;

import java.io.File;
import java.util.Date;


/**
 * This (static) class offers methods to write a simple txt - log file.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class LogFile {

	/**
	 * The reference to the log File.
	 */
	private static File fileRef = new File(
			"C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/log.txt");

	/** Write a log entry.
	 * 
	 * @param message - the String to be added to the log file.
	 */
	public static void writeLog(String message) {
		TextFileWriter.getInstance().writeTextFile(				
				LogFile.fileRef.getParentFile().getPath(),
				LogFile.fileRef.getName(),new Date().getDate()+ " " + message, true);
	}

}
