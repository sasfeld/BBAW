/**
 * 
 */
package bbaw.wsp.crawler.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import bbaw.wsp.crawler.control.DebugMode;


/**
 * Write a simple text file (byte stream).
 * This class is a singleton.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class TextFileWriter {
	private static TextFileWriter instance;
	
	private TextFileWriter() {
		
	}
	
	public static TextFileWriter getInstance() {
		if (instance == null) {
			instance = new TextFileWriter();
		}
		return instance;
	}
	
	/**
	 * Write a text file.
	 * @param pDir - the directory where the file will be saved.
	 * @param fileName
	 * @param text - the text file's content.
	 * @param append - true if the file should get appended.
	 * @return true if the file was successfully written.
	 */
	public boolean writeTextFile(final String pDir, final String fileName, final String text, final boolean append)  {
		File dir = new File(pDir);		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File fileToWrite = new File(dir, fileName);
		
		
		FileWriter w;
		BufferedWriter buffer;
		try {			
			w = new FileWriter(fileToWrite, append);
			buffer = new BufferedWriter(w);
			buffer.write(text);
			buffer.flush();
			buffer.close();
			w.close();
			return true;
		} catch (IOException e) {
			if(DebugMode.DEBUG) {
				System.out.println(e);
				}	
			return false;
		} 		
	}


	
}
