package bbaw.wsp.parser.tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Some helper methods.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class FileTools {

	/**
	 * Copy a file.
	 * @param input - the input {@link File}
	 * @param output - the output {@link File}
	 */
	public static void copyFile(File input, File output) {
		try {
			FileReader reader = new FileReader(input);
			FileWriter writer = new FileWriter(output);
			
			int c;
			// Read and write a single character
			while (( c = reader.read()) != -1) {
				writer.write(c);
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
		
	}
}
