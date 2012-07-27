package bbaw.wsp.parser.harvester;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.accepter.ResourceAccepter;

/**
 * This static class offers methods to crawl an (UNIX-)file system.
 * It implements the {@link IHarvester} interface.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.06.2012
 * @version 0.0.1
 */
public class FileSystemHarvester extends Harvester{
	/**
	 * The reference to the resourceAccepter
	 */
	private ResourceAccepter resourceAccepter;
	
	public FileSystemHarvester(ResourceAccepter accepter) {
		super(accepter);		
	}

	
	public Set<String> harvest(final String startURI) throws IllegalArgumentException{
		File baseDir = new File(startURI);
		if(!baseDir.exists()) {
			throw new IllegalArgumentException("Can't find base directory in FileSystemHarvester.harvest()!");
		}
		else if(!baseDir.isDirectory()) {
			throw new IllegalArgumentException("Need a valid directory (node), not a file (leaf) in FileSystemHarvester.harvest()!");
		}
		
		// Fetch resources (startDir/XXX)
		Set<String> leafs = new HashSet<String>();	
		for(File child: baseDir.listFiles()) {
			if(this.resourceAccepter.acceptLeaf(child.getPath())) {
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
		Set<String> acceptedResources = new HashSet<String>();
		acceptedResources.add(".pdf");
		ResourceAccepter accepter = new FileSystemAccepter(acceptedResources );
		FileSystemHarvester harvester = new FileSystemHarvester(accepter);
		System.out.println("FileSystemHarvest:");
		Set<String> results = harvester.harvest("//192.168.1.203/wsp-web-test");
		System.out.println(results);
		System.out.println("-------------------");
	}

	
}
