package bbaw.wsp.crawler.saver;

import java.io.File;

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.TextFileWriter;

/**
 * This class realizes a SameStructure Save - Strategy.
 * 
 * This means, all fulltexts will be saved following the structure of the harvested resources.
 * 
 * Output:
 * [saveDir] / name of start uri - directory / [structure of the parsed file system] / resource name.txt 
 * @author wsp-shk1
 *
 */
public class SameStructureStrategy extends SaveStrategy {	
	private String saveDir;

	/**
	 * Create a new strategy.
	 * @param saveDir - the dir where the fulltexts will be saved.
	 */
	public SameStructureStrategy(final String saveDir) {
		this.saveDir = saveDir;
	}
	
	/**
	 * Save a fulltext as a .txt file.
	 */
	public void saveFile(String startUri, String uri, String text) {
		File startFile = new File(startUri);
		String startDir = startFile.getName();
		startUri = startUri.replace("/", "\\"); // match both file system styles 
		String relativePath = uri.replace(startUri, "");		
		
		
		String dir = this.saveDir + "/" + startDir + "/" + relativePath;
		if (DebugMode.DEBUG) {
			System.out.println("Saving fulltext to: " + dir);
		}
		if (TextFileWriter.getInstance().writeTextFile(dir, new File(uri).getName()+".txt", text, false)) {
			if (DebugMode.DEBUG) {
				System.out.println("File written");
			}
		} else {
			if (DebugMode.DEBUG) {
				System.out.println("Couldn't write file.");
			}
		}
		
	}

	

}
