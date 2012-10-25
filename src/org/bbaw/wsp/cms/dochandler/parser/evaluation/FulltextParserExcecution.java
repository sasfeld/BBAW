package org.bbaw.wsp.cms.dochandler.parser.evaluation;

import java.util.HashSet;
import java.util.Set;

import org.bbaw.wsp.cms.dochandler.parser.text.parser.DocumentModelStrategy;
import org.bbaw.wsp.cms.dochandler.parser.text.parser.EdocParserImpl;
import org.bbaw.wsp.cms.dochandler.parser.text.parser.ResourceParser;
import org.bbaw.wsp.util.EdocIndexMetadataFetcherTool;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

import bbaw.wsp.parser.accepter.ResourceAccepter;

import bbaw.wsp.parser.harvester.Harvester;


/**
 * This class executes the fulltext parsing. It includes a complete harvesting,
 * parsing and saving job.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @version 2.0
 * @date 16.08.2012
 * 
 */
public class FulltextParserExcecution {

  private Harvester harvester;
  private DocumentModelStrategy saveStrategy;

  /**
   * Create a new ParserExecution class.
   * 
   * @param harvester
   *          - the harvester instance
   * @param accepter
   *          - the accepter instance
   */
  public FulltextParserExcecution(Harvester harvester) {
    if (harvester == null) {
      throw new IllegalArgumentException("Parameters mustn't be null in FulltextParserExecution!");
    }
    this.harvester = harvester;
    this.saveStrategy = new DocumentModelStrategy();
  }

  /**
   * Parse resources of all kinds. The resources will be won by the specified
   * {@link Harvester}. Exceptions are written in a log file defined by the
   * {@link LogFile} class.
   * 
   * @return the result of the used {@link ISaveStrategy} in a Set.
   * @throws ApplicationException 
   */
  public Set<Object> parse(final String startURI) throws ApplicationException {
    Set<String> uris = harvester.harvest(startURI);
    Set<Object> results = new HashSet<Object>();

    for (String uri : uris) {
      ResourceParser parser = null;
      if (EdocIndexMetadataFetcherTool.isEDocIndex(uri)) {
        parser = EdocParserImpl.getInstance();
      }    

      if (parser != null) {
        Object result;
        try {
          result = parser.parse(startURI, uri);
          results.add(result);
        } catch (ApplicationException e) {
          System.out.println(e);
        }
       
      } else {
        System.out.println("There's no parser available for this type of resource: " + this.harvester.getResourceAccepter().getExtension(uri));
      }
    }
    return results;
  }
}
