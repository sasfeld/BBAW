package bbaw.wsp.crawler.harvester;

import java.io.FileNotFoundException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;

/**
 * This class offers methods to crawl a web (HTTP)-source
 * @author Sascha Feldmann(wsp-shk1) 
 * @date 05.06.2012
 * @version 0.0.1
 */

public class WebHarvester_old extends Thread {
	public static String wkd = "c:/temp/";
	private String configFile;
	
	public WebHarvester_old(final String configFile) {
		this.configFile = configFile;		
	}
	
	public void harvest() throws FileNotFoundException {
		ScraperConfiguration config = new ScraperConfiguration(this.configFile); // Configuration file
		Scraper scraper = new Scraper(config, wkd); // Scraper-Object with workspace folder
		scraper.setDebug(true);
		scraper.execute();		
		
		scraper.addVariableToContext("url", "http://twit88.com/blog/2007/12/31/design-pattern-in-java-101-builder-pattern-creational-pattern/");
		Variable article = (Variable) scraper.getContext().getVar("article");	
		
		System.out.println(article.toString());
	}	
	
}
