package bbaw.wsp.parser.accepter;

import java.io.File;
import java.util.Set;

public class FileSystemAccepter extends ResourceAccepter{

	/**
	 * Create a new FileSystemAccepter.
	 * @param acceptedResources - a set that contains the accepted ressources as string
	 */
	public FileSystemAccepter(Set<String> acceptedResources) {
		super(acceptedResources);		
	}

	private File checkIfFile(String uri) {
		File file = new File(uri);
		if(!file.exists()) {
			throw new IllegalArgumentException("The URI must refer to a valid File");
		}
		return file;
	}
	
	public boolean acceptLeaf(String uri) {		
		this.checkIfFile(uri);	
			
		return super.acceptLeaf(uri);
	}
	
	public boolean acceptNode(String uri) throws IllegalArgumentException{
		File file = this.checkIfFile(uri);		
		
		if(file.isDirectory()) {
			return true;
		}
		return false;	
	}

	
}
