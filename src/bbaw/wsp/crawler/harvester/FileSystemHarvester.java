package bbaw.wsp.crawler.harvester;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.crawler.accepter.ResourceAccepter;

/**
 * This static class offers methods to crawl an (UNIX-)file system.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.06.2012
 * @version 0.0.1
 */
public class FileSystemHarvester {
	
	/**
	 * Harvest an (UNIX-)file system beginning at a specified path and return all accepted resources based on that path.
	 * 
	 * @param startDIR
	 *            - the directory to begin the harvest
	 * @return a set of String that contains all accepted resources
	 * @throws IllegalArgumentException if the startDir is not a valid directory
	 */
	public static Set<String> harvest(final String startDir) throws IllegalArgumentException{
		File baseDir = new File(startDir);
		if(!baseDir.exists()) {
			throw new IllegalArgumentException("Can't find base directory in FileSystemHarvester.harvest()!");
		}
		else if(!baseDir.isDirectory()) {
			throw new IllegalArgumentException("Need a valid directory (node), not a file (leaf) in FileSystemHarvester.harvest()!");
		}
		
		// Fetch resources (startDir/XXX)
		Set<String> leafs = new HashSet<String>();	
		for(File child: baseDir.listFiles()) {
			if(ResourceAccepter.acceptLeaf(child.getPath())) {
				leafs.add(child.getAbsolutePath());
			}
			else if(child.isDirectory()) {
				leafs.addAll(harvest(child.getAbsolutePath()));
			}
		}
		
		
		return leafs;
	}

	/*
	 * Test of the FileSystemHarvester
	 */
	public static void main(String[] args) {
		System.out.println("FileSystemHarvest:");
		Set<String> results = FileSystemHarvester.harvest("//192.168.1.203/wsp-web-test");
		System.out.println(results);
		System.out.println("-------------------");
		
		// Compare to WebHarvester-Results
		System.out.println("WebHarvester:");
		Set<String> resultsWeb = WebHarvester.harvest("http://192.168.1.203/wsp/");
		System.out.println(resultsWeb);
		System.out.println("-------------------");
	}
}
