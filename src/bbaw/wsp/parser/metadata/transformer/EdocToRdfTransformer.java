package bbaw.wsp.parser.metadata.transformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import bbaw.wsp.parser.fulltext.document.PDFDocument;
import bbaw.wsp.parser.fulltext.parsers.EdocIndexMetadataFetcherTool;
import bbaw.wsp.parser.metadata.transformer.util.TemplateMapper;
import bbaw.wsp.parser.tools.joseph.MetadataRecord;
import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

/**
 * Instances of this (singleton) class transform eDoc metadata to the
 * destination OAI/ORE files.
 * 
 * @author Sascha Feldmann (wsp-shk1)
 * @date 08.10.12
 * 
 */
public class EdocToRdfTransformer extends ToRdfTransformer {
  /**
   * Specify the RDF template here.
   */
  private static final String RDF_TEMPLATE_URL = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/ParserTest/XSLTTest/templates/zielformat.xml";
  /**
   * The aggregation name is it is stored in the quad.
   */
  public static final String AGGREGATION_NAME = "http://wsp.bbaw.de/aggregations/edoc";
  private static EdocToRdfTransformer instance;

  private EdocToRdfTransformer() throws ApplicationException {
    super(ToRdfTransformer.MODE_DIRECT);
  }

  /**
   * 
   * @return the only existing instance.
   * @throws ApplicationException
   *           if the mode wasn't specified correctly.
   */
  public static EdocToRdfTransformer getInstance() throws ApplicationException {
    if (instance == null) {
      return new EdocToRdfTransformer();
    }
    return instance;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * bbaw.wsp.parser.metadata.transformer.ToRdfTransformer#doTransformation(
   * java.lang.String, java.lang.String)
   */
  public void doTransformation(final String inputUrl, final String outputUrl) throws ApplicationException {
    super.doTransformation(inputUrl, outputUrl);
    MetadataRecord mdRecord = new MetadataRecord();
    EdocIndexMetadataFetcherTool.fetchHtmlDirectly(inputUrl, mdRecord);

    // map here
    System.out.println("Processing transformation from eDoc to RDF...");
    TemplateMapper mapper = new TemplateMapper(RDF_TEMPLATE_URL);
    HashMap<String, String> eDocMap = createMap(mdRecord);
    System.out.println("Mapping template...");
    mapper.mapPlaceholder(eDocMap);
   
   
    try {
      FileWriter writer = new FileWriter(new File(outputUrl));
      BufferedWriter buffer = new BufferedWriter(writer);
      buffer.write(mapper.getMappedTemplate());
      buffer.flush();
      buffer.close();
    } catch ( IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
   
    
  }

  private HashMap<String, String> createMap(final MetadataRecord mdRecord) {
    HashMap<String, String> eDocPlaceholderMap = new HashMap<String, String>();

    eDocPlaceholderMap.put("%%aggregation_name%%", AGGREGATION_NAME);
    eDocPlaceholderMap.put("%%creator_name%%", ToRdfTransformer.TRANSFORMER_CREATOR_NAME);
    eDocPlaceholderMap.put("%%creator_url%%", ToRdfTransformer.TRANSFORMER_CREATOR_URL);
    String uri = mdRecord.getRealDocUrl();
    if (uri == null) {
      uri = "";
    }
    eDocPlaceholderMap.put("%%resource_identifier%%", uri);
    String title = mdRecord.getTitle();
    if (title == null) {
      uri = "";
    }
    eDocPlaceholderMap.put("%%dc_title%%", title);

    Date actual = new Date();
    String actualDate = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA_FRENCH).format(actual);
    eDocPlaceholderMap.put("%%actual_date%%", actualDate);

    Date dateCreated = mdRecord.getCreationDate();
    String dateCreatedString = "";
    if (dateCreated == null) {
      dateCreatedString = "";
    } else {
      // KOBF format yyyy -> format to W3CDTF
      dateCreatedString = new SimpleDateFormat("yyyy").format(dateCreated);
    }
    eDocPlaceholderMap.put("%%date_created%%", dateCreatedString);

    Date dateIssued = mdRecord.getPublishingDate();
    System.out.println("date published: " + dateIssued);
    String dateIssuedString = "";
    if (dateIssued == null) {
      dateIssuedString = "";
    } else {
      // KOBV format dd.mm.yyyy -> format to yyyy-dd-mm
      dateIssuedString = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CANADA_FRENCH).format(dateIssued);
    }
    eDocPlaceholderMap.put("%%date_issued%%", dateIssuedString);
    String publisher = mdRecord.getPublisher();
    if (publisher == null) {
      publisher = "";
    }
    eDocPlaceholderMap.put("%%publisher%%", publisher);
    String language = mdRecord.getLanguage();
    if (language.equals("Deutsch")) {
      language = "deu";
    }
    eDocPlaceholderMap.put("%%language%%", language);
    String type = mdRecord.getType();
    if (type == null) {
      type = "";
    }
    eDocPlaceholderMap.put("%%mime_type%%", PDFDocument.MIME_TYPE);
    String description = mdRecord.getDescription();
    if (description == null) {
      description = "";
    }
    eDocPlaceholderMap.put("%%dc_description%%", description);

    // Map SWD subjects and other subjects
    String subject = mdRecord.getSwd();  // swd
    String subString = "";
    if (subject == null) {
      subject = "";
    } else {
      subString = "";
      String[] subjects = subject.split(",");
      for (String sub : subjects) {
        if (!sub.trim().isEmpty()) {
          subString += "<dc:subject>"+sub+"</dc:subject>\n\t\t\t\t";
        }
      }
    }
    String freeWords = mdRecord.getSubject(); // freie schlagwörter
    if (freeWords != null) {
      String[] swds = freeWords.split(",");
      for (String sub : swds) {
        if(!sub.trim().isEmpty()) {
          subString += "\t\t\t<dc:subject>"+sub+"</dc:subject>\n\t\t\t\t";
        }
      }
    }

    eDocPlaceholderMap.put("%%subjects%%", subString);

    return eDocPlaceholderMap;
  }

}
