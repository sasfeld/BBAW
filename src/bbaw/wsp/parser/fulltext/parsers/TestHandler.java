package bbaw.wsp.parser.fulltext.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.james.mime4j.MimeException;
import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.james.mime4j.stream.BodyDescriptor;
import org.apache.james.mime4j.stream.Field;

public class TestHandler implements ContentHandler{

  @Override
  public void body(BodyDescriptor arg0, InputStream arg1) throws MimeException, IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void endBodyPart() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void endHeader() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void endMessage() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void endMultipart() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void epilogue(InputStream arg0) throws MimeException, IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void field(Field arg0) throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void preamble(InputStream arg0) throws MimeException, IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void raw(InputStream arg0) throws MimeException, IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startBodyPart() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startHeader() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startMessage() throws MimeException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startMultipart(BodyDescriptor arg0) throws MimeException {
    // TODO Auto-generated method stub
    
  }

}
