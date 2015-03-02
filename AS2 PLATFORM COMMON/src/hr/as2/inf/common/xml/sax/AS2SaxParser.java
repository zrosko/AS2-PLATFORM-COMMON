/* (c) Adriacom Software d.o.o.
 * 22211 Vodice, Croatia
 * Created by Z.Rosko (zrosko@yahoo.com)
 * Date 2009.12.30 
 * Time: 18:48:29
 */
package hr.as2.inf.common.xml.sax;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class AS2SaxParser {

    public static void main(String[] args) {
        String _file = "C:\\IT\\ETL\\pranjenovca\\sdn2.xml";
        String _schema = "C:\\IT\\ETL\\pranjenovca\\sdn.xml";

        validateXMLDocumentAgainstSchema(_file, _schema);
        parseAndSaveXMLDocument(_file, new AS2SaxHandler());
        
    }
    public static boolean parseAndSaveXMLDocument(String file, DefaultHandler handler){ 
        try{
	        SAXParserFactory _parserFactory = SAXParserFactory.newInstance();
	        _parserFactory.setValidating(true);
	        _parserFactory.setNamespaceAware(true);
	        File _documentFile = new File(file);        
            SAXParser _parser = _parserFactory.newSAXParser();
            _parser.parse(_documentFile, handler);
            return true;
        }catch(Exception e){
            System.out.println("*****PROBLEM******"+e);
            return false;
        }
    }
    public static boolean validateXMLDocumentAgainstSchema(String file, String schema){
        try{
            // File _documentFile = new File(file);
            // File _schemaFile = new File(schema);
            //get a parser to parse W3C schemas
	        //SchemaFactory _schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        //now parse the schema file to create a Schema object 	      
	        //Schema _schema = _schemaFactory.newSchema(_schemaFile);
	        //get a Validator object from Schema
	        //Validator _validator = _schema.newValidator();
	        //get a SAXSource object for the docuement 
	        //we could use a DOMSource here as well
	        // SAXSource _source = new SAXSource(new InputSource(new FileReader(_documentFile)));
	        //now validate the document
	        //_validator.validate(_source);
            return true;
        }catch(Exception e){
            if(e instanceof SAXParseException){
                SAXParseException spe = (SAXParseException)e;
                System.out.println("%s:%d: %s%n"+ 
                        spe.getSystemId()+ 
                        spe.getLineNumber()+ 
                        spe.getColumnNumber()+ 
                        spe.getMessage());
            }else {
                System.out.println(e.getMessage());
            }
            return false;
        }       
    }    
}
