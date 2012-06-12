package bbaw.wsp.crawler.harvester;

import java.io.FileNotFoundException;

public class CentralTest {

	public static void main(String[] args) {
		WebHarvester_old webHarvester = new WebHarvester_old("src/test.xml");
		
		try {
			webHarvester.harvest();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Configuration file not found");;
		}
	}
}
