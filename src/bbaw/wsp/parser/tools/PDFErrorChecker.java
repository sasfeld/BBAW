package bbaw.wsp.parser.tools;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.harvester.FileSystemHarvester;

/**
 * This class checks for OCR errors in fulltexts of the eDoc resources
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class PDFErrorChecker {

	private static final String SAVEDIR = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest";

	/**
	 * Check for OCR scan errors (characters like ?) Write a log file containing
	 * an overview (percentage of invalid files) and the path to the invalid
	 * file.
	 */
	public static void checkForErrors(final String fulltextDir) {
		Set<String> extSet = new HashSet<String>();
		extSet.add(".txt");
		FileSystemHarvester harvester = new FileSystemHarvester(
				new FileSystemAccepter(extSet));
		Set<String> results = harvester.harvest(fulltextDir);
		Set<String> fail = new HashSet<String>();
		Set<String> tooBig = new HashSet<String>();

		for (String file : results) {
			System.out.println("Checking file " + file);
			File f = new File(file);
			String text = SimpleFileReader.readFromFile(f);

			System.out.println("Have read");
			if(text.isEmpty()) {
				tooBig.add(f.getAbsolutePath());
			}
			if (text.contains("~")) {
				System.out.println("Checked resources: "+results.size()+" fails: "+fail.size());
				fail.add(f.getAbsolutePath());
			}			
		}
		
		float percent = (1f* fail.size() / ( 1f * results.size()-tooBig.size()))*100;
		String text = "Checked resources: "+results.size()
				+"\nnumber of documents with invalid characters: "+fail.size()
				+"\nnumber of documents which are too large: "+tooBig.size()
				+ "\nPercentage: "+percent 
		 + "\nList of invalid fulltexts: "
		 + "\n";
		for (String string : fail) {
			text += "\n"+string;
		}
		
		TextFileWriter.getInstance().writeTextFile(SAVEDIR, "invalidEdocs.txt", text, false);
		
		
	}

	public static void main(String[] args) {
		checkForErrors("C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/volltexte");
	}
}
