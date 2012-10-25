package bbaw.wsp.parser.metadata.factory;

import java.util.HashMap;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

import bbaw.wsp.parser.metadata.parsers.MetadataParser;
import bbaw.wsp.parser.metadata.parsers.ModsMetadataParser;
import bbaw.wsp.parser.metadata.parsers.RdfMetadataParser;

/**
 * This class offers the {@link MetadataParser} instances on an easy way. You
 * don't need to define the namespaces.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 25.10.2012
 * 
 */
public class MetadataParserFactory {

  /**
   * Create a new {@link RdfMetadataParser}
   * 
   * @return the {@link RdfMetadataParser} instance.
   * @throws ApplicationException
   *           if the resource to be parsed is not validated by Saxon.
   */
  public static RdfMetadataParser newRdfMetadataParser(final String uri) throws ApplicationException {
    final HashMap<String, String> namespaces = new HashMap<String, String>();
    namespaces.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
    namespaces.put("dc", "http://purl.org/elements/1.1/");
    return new RdfMetadataParser(uri, namespaces);
  }

  /**
   * Create a new {@link ModsMetadataParser}.
   * 
   * @param uri
   *          the xml resource to be parsed.
   * @return the {@link ModsMetadataParser} instance.
   * @throws ApplicationException
   *           if the resource to be parsed is not validated by Saxon.
   */
  public static ModsMetadataParser newModsMetadataParser(final String uri) throws ApplicationException {
    final HashMap<String, String> namespaces = new HashMap<String, String>();
    namespaces.put("mods", "http://www.loc.gov/mods/v3");
    return new ModsMetadataParser(uri, namespaces);
  }
}
