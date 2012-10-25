package org.bbaw.wsp.cms.dochandler.parser.evaluation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bbaw.wsp.cms.dochandler.parser.document.PdfDocument;

import de.mpg.mpiwg.berlin.mpdl.exception.ApplicationException;

import bbaw.wsp.parser.accepter.FileSystemAccepter;
import bbaw.wsp.parser.accepter.ResourceAccepter;
import bbaw.wsp.parser.accepter.WebAccepter;
import bbaw.wsp.parser.harvester.FileSystemHarvester;
import bbaw.wsp.parser.harvester.WebHarvester;

public class EdocEvaluation {

  private static final String EDOC_START_URI = "C:/Dokumente und Einstellungen/wsp-shk1/Eigene Dateien/opus32_bbaw_volltexte_20120607/volltexte/2009/";

  /**
   * Evaluation of eDoc - parsing.
   * @param args
   * @throws ApplicationException 
   */
  public static void main(String[] args) throws ApplicationException {
    Set<String> acceptedResources = new HashSet<String>();
    acceptedResources.add(ResourceAccepter.EXT_HTML);
    FulltextParserExcecution ex = new FulltextParserExcecution(new FileSystemHarvester(new FileSystemAccepter(acceptedResources)));
    
    long startTime = new Date().getTime();
    Set<Object> results = ex.parse(EDOC_START_URI);
    long runningTime = new Date().getTime() - startTime;
    System.out.println("Testlauf:");
    System.out.println("Anzahl der eDocs: "+results.size()+"\n\n\n");
    for (Object result : results) {
      PdfDocument pdf = (PdfDocument) result;
      System.out.println("URI: " + pdf.getURL());
      System.out.println("Metadata: \n"+ pdf.getMetadata());
      System.out.println("---------------\n\n");
    }
    
    
    System.out.println("#######################");
    System.out.println("Benötigte Zeit: "+runningTime+" ms.");    
    System.out.println("Null-Felder: \n\n");
    System.out.println("Subject: \n");
    int counter = 0;
    for (Object result : results) {      
      PdfDocument pdf = (PdfDocument) result;
      if(pdf.getMetadata().getSubject() == null) {
        System.out.println(pdf.getURL());
        counter++;
      }
    }
    System.out.println("("+counter+")++++++");
    counter = 0;
    System.out.println("SWD: \n");
    for (Object result : results) {
      
      PdfDocument pdf = (PdfDocument) result;
      if(pdf.getMetadata().getSwd() == null) {
        System.out.println(pdf.getURL());
        counter++;
      }
    }
    System.out.println("("+counter+")++++++");
    counter = 0;
    System.out.println("Publisher: \n");
    for (Object result : results) {
     
      PdfDocument pdf = (PdfDocument) result;
      if(pdf.getMetadata().getPublisher() == null) {
        System.out.println(pdf.getURL());
        counter++;
      }
    }
    System.out.println("("+counter+")++++++");
    counter = 0;
    System.out.println("collectionNames: \n");
    for (Object result : results) {
     
      PdfDocument pdf = (PdfDocument) result;
      if(pdf.getMetadata().getCollectionNames() == null) {
        System.out.println(pdf.getURL());
        counter++;
      }
    }
    System.out.println("("+counter+")++++++");
    counter = 0;
    System.out.println("documentType: \n");
    for (Object result : results) {
      
      PdfDocument pdf = (PdfDocument) result;
      if(pdf.getMetadata().getDocumentType() == null) {
        System.out.println(pdf.getURL());
        counter++;
      }
    }
    System.out.println("("+counter+")++++++");
    

  }

}
