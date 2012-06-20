package bbaw.wsp.crawler.accepter;

import java.util.Set;

public class WebAccepter extends ResourceAccepter{

	public WebAccepter(Set<String> acceptedResources) {
		super(acceptedResources);		
	}

	@Override
	public boolean acceptNode(String uri) {
		if(uri.matches("\\w+/")) {
			return true;
		}
		return false;
	}

}
