package bbaw.wsp.parser.saver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bbaw.wsp.parser.control.DebugMode;
import bbaw.wsp.parser.tools.TextFileWriter;

/**
 * This class realizes a SameStructure Save - Strategy.
 * 
 * This means, all fulltexts will be saved following the structure of the
 * harvested resources.
 * 
 * Output: [saveDir] / name of start uri - directory / [structure of the parsed
 * file system] / resource name / [page] / resource name.txt
 * 
 * It also offers a special method for pdf fulltexts: the fulltexts for each
 * page are saved seperated in a subdirectory.
 * 
 * 
 * @author Sascha Feldmann(wsp-shk1)
 * @version 2.0
 * 
 */
public class SameStructureStrategy implements ISaveStrategy {
	private String saveDir;

	/**
	 * Create a new strategy.
	 * 
	 * @param saveDir
	 *            - the dir where the fulltexts will be saved.
	 */
	public SameStructureStrategy(final String saveDir) {
		this.saveDir = saveDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbaw.wsp.parser.saver.ISaveStrategy#saveFile(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public Object saveFile(String startUri, String uri, String text) {
		if (uri.contains("http://")) { // HTTP resource
			try {
				URL url = new URL(uri);
				uri = url.getFile();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		File startFile = new File(startUri);
		String startDir = startFile.getName();
		startUri = startUri.replace("/", "\\"); // match both file system styles
		String relativePath = uri.replace(startUri, "");

		String dir = this.saveDir + "/" + startDir + "/" + relativePath;
		if (DebugMode.DEBUG) {
			System.out.println("Saving fulltext to: " + dir);
		}
		if (TextFileWriter.getInstance().writeTextFileUtf8(dir,
				new File(uri).getName() + ".txt", text, false)) {
			if (DebugMode.DEBUG) {
				System.out.println("File written");
			}
		} else {
			if (DebugMode.DEBUG) {
				System.out.println("Couldn't write file.");
			}
		}
		
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbaw.wsp.parser.saver.ISaveStrategy#saveFile(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	public Object saveFile(String startUri, String uri, List<String> textPages) {
		int page = 1;
		if (textPages != null) {
			for (String pageText : textPages) {
				if (pageText != null) {
					saveFile(startUri, uri + "/" + page, pageText);
				}
				++page;
			}
		}
		return null;
	}

}
