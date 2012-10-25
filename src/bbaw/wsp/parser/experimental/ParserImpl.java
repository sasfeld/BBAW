package bbaw.wsp.parser.experimental;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ParserImpl implements Parser {

	@Override
	public Set<MediaType> getSupportedTypes(ParseContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(InputStream arg0, ContentHandler arg1, Metadata arg2,
			ParseContext arg3) throws IOException, SAXException, TikaException {
		// TODO Auto-generated method stub
		
	}

}
