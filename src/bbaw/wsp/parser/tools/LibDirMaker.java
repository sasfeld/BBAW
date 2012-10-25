package bbaw.wsp.parser.tools;

import java.io.File;

import net.sf.saxon.s9api.DocumentBuilder;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathExecutable;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmItem;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;

/**
 * A tool class which creates a /lib - folder which contains all external jars
 * defined in the .classpath - file.
 * 
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * 
 */
public class LibDirMaker {
	private final static String SAVEDIR = "C:/ownProjectsWorkspace/ParserIntegration/lib/";

	/**
	 * This methods reads from the eclipse .classpath file and saves each
	 * referenced external jar to a specified destination.
	 * 
	 * @param classPathFile
	 *            - the .classpath file
	 */
	public static void saveLib(final String classPathFile) {
		Processor processor = new Processor(false);
		XPathCompiler xPathCompiler = processor.newXPathCompiler();
		DocumentBuilder builder = processor.newDocumentBuilder();
		try {
			XdmNode contextItem = builder.build(new File(classPathFile));
			String query = "//classpathentry/@path";
			XPathExecutable x = xPathCompiler.compile(query);

			XPathSelector selector = x.load();
			selector.setContextItem(contextItem);
			XdmValue value = selector.evaluate();
			for (XdmItem xdmItem : value) {
				String path = xdmItem.getStringValue();
				if (!path.equals("src")
						|| !path.equals("org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7")) {
					System.out.println("Saving external jar: "+path);
					File inFile = new File(path);
					File outFile = new File(SAVEDIR + inFile.getName());
					FileTools.copyFile(inFile, outFile);
				}
			}
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		saveLib("C:/ownProjectsWorkspace/wsp/bbaw.wsp.parser/.classpath");
	}
}
