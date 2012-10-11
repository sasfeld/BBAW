/**
 * 
 */
package bbaw.wsp.parser.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import bbaw.wsp.parser.control.DebugMode;


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
		if (DebugMode.DEBUG) {
			System.out
					.println("TextFileWriter: Saving file "+fileToWrite);
		}
		
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
				System.out.println("problem: " +  e);
				}	
			return false;
		} 		
	}

	public boolean writeTextFileUtf8(final String pDir, final String fileName, final String text, final boolean append) {
	  File dir = new File(pDir);    
    if(!dir.exists()) {
      dir.mkdirs();
    }
    File fileToWrite = new File(dir, fileName);
    if (DebugMode.DEBUG) {
      System.out
          .println("TextFileWriter: Saving file "+fileToWrite);
    }
    
    try {
      Writer w = new OutputStreamWriter(new FileOutputStream(fileToWrite), "UTF8");
      BufferedWriter buffer = new BufferedWriter(w);
      
      buffer.write(text);
      
      buffer.flush();
      buffer.close();
      w.close();
      
      return true;
      
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    return false;
	}
	
}
