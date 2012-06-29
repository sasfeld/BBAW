package bbaw.wsp.crawler.saver;

import java.io.File;

import bbaw.wsp.crawler.control.DebugMode;
import bbaw.wsp.crawler.tools.TextFileWriter;


/**
 * This class offers a strategy to save parsed fulltexts. The strategy is
 * designed to be used for the BBAW eDocs. Original fulltexts: base dir / year /
 * docID / pdf / [docname].pdf The parsed documents will be saved analog: parsed
 * fulltext: save dir / year / docID / fulltext.txt
 * 
 * You can set the save dir.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class EDocToFileTreeStrategy extends SaveStrategy {
	private static final String FILE_NAME = "fulltext.txt";
	private String saveDir;

	/**
	 * Create a new save strategy. * @param saveDir - the URI to the root
	 * directory of all saved fulltexts.
	 */
	public EDocToFileTreeStrategy(final String saveDir) {
		this.saveDir = saveDir;
	}

	/**
	 * This method saves a fulltext depending on the directory.
	 * 
	 * @param uri
	 *            - the URI to the parsed document.
	 * @param text
	 *            - the text to be saved.
	 */
	public void saveFile(final String uri, final String text) {
		File saveFile = new File(uri);
		String dir = this.saveDir
				+ "/"
				+ saveFile.getParentFile().getParentFile().getParentFile()
						.getName() + "/"
				+ saveFile.getParentFile().getParentFile().getName();
		if (DebugMode.DEBUG) {
			System.out.println("Saving fulltext to: " + dir);
		}
		if (TextFileWriter.getInstance().writeTextFile(dir, FILE_NAME, text, false)) {
			if (DebugMode.DEBUG) {
				System.out.println("File written");
			}
		} else {
			if (DebugMode.DEBUG) {
				System.out.println("Couldn't write file.");
			}
		}
	}

	@Override
	public void saveFile(String startURI, String uri, String text) {
		// TODO Auto-generated method stub
		
	}
}
