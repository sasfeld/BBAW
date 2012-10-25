package bbaw.wsp.parser.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SimpleFileReader {

	private static final long MAX_FILE_SIZE =  500000000;

	/**
	 * Read from file.
	 * 
	 * @param file
	 * @return the content in one String or an empty string if the file is too big or there were some other errors.
	 */
	public static String readFromFile(File file) {
		FileReader reader;
		String result = "";
		try {
			if (file.length() > MAX_FILE_SIZE) {
				return "";
			}
			reader = new FileReader(file);
			BufferedReader r = new BufferedReader(reader);
			while (r.read() != -1) {
				result += r.readLine();
			}
			r.close();
			reader.close();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";

		}
	}
}
