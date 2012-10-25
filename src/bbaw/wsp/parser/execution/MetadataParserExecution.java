/**
 * 
 */
package bbaw.wsp.parser.execution;

import java.util.HashSet;
import java.util.Set;

import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.harvester.Harvester;
import bbaw.wsp.parser.metadata.WSPMetadataRecord;
import bbaw.wsp.parser.metadata.parsers.ModsMetadataParser;

/**
 * This class executes the metadata parsing.
 * @author Sascha Feldmann (wsp-shk1)
 *
 */
public class MetadataParserExecution {

	private Harvester harvester;

	/**
	 * Create a new MetadataParserExecution object.
	 * @param harvester - the {@link Harvester} which will be used.
	 */
	public MetadataParserExecution(final Harvester harvester)  {
		if (harvester == null) {
			throw new IllegalArgumentException(
					"Parameters mustn't be null in MetadataParserExecution!");
		}
		
		this.harvester = harvester;
	}
	
	/**
	 * Start parsing using a special startURI:
	 * @param startURI - the root of where the harvesting and parsing begins.
	 * @return a set of {@link WSPMetadataRecord} records matching the MODS-scheme.
	 */
	public Set<WSPMetadataRecord> parse(final String startURI) {
		Set<WSPMetadataRecord> returnRecords = new HashSet<>();
		Set<String> uris = harvester.harvest(startURI);
		
		for (String uri: uris) {
			ModsMetadataParser parser = null;
			
			if (this.harvester.getResourceAccepter().getExtension(uri).equals(
					ResourceAccepter.EXT_XML)) {
				parser = new ModsMetadataParser(uri);
			}
			
			if(parser != null) {
				returnRecords.add(parser.parse());
			}
		}
		
		return returnRecords;
	}
}
