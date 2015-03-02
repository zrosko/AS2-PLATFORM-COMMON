/* (c) Adriacom Software d.o.o.
 * 22211 Vodice, Croatia
 * Created by Z.Rosko (zrosko@yahoo.com)
 * Date 2009.12.30 
 * Time: 19:52:21
 */
package hr.as2.inf.common.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class AS2SaxHandler extends DefaultHandler {
/*    CrnaListaVo _crnaLista;
    CrnaListaOsobaVo _osoba;
    CrnaListaDokumentOsobeVo _dokument;
    CrnaListaAdresaOsobeVo _adresa;*/
    String _currentElement ="";
    String _currentEntity ="";
    StringBuffer _textContent = new StringBuffer();
    boolean _ignore = false;
    //This method is invoked when the parser encounters the opening tag
    //of any element. Ignore the arguments but count the element.
    public void startElement(String uri, String localname, String qname, Attributes attributes){        
        _currentElement=localname;
        if(localname.equals("sdnEntry")){
            _currentEntity="sdnEntry";
            //_osoba = new CrnaListaOsobaVo();
            _ignore = false;
        }else if (localname.equals("id")){
            _currentEntity="id";
            //_dokument = new CrnaListaDokumentOsobeVo();
            _ignore = false;
        }else if (localname.equals("address")){
            _currentEntity="address";
            //_adresa = new CrnaListaAdresaOsobeVo();
            _ignore = false;
        }else if (
                localname.equals("sdnList")||
                localname.equals("publshInformation")||
                localname.equals("programList")||
                localname.equals("idList")||
                localname.equals("akaList")||
                localname.equals("addressList")||
                localname.equals("nationalityList")||
                localname.equals("citizenshipList")||
                localname.equals("dateOfBirthList")||
                localname.equals("placeOfBirthList")||
                localname.equals("vesselInfo")){
            _ignore = true;
        }
        
/*        for(int i=0; i<attributes.getLength(); i++){
            String attname = attributes.getLocalName(i);
            String type = attributes.getType(i);
            String value = attributes.getValue(i);
            String qname1 = attributes.getQName(i);
            System.out.println("Attname="+attname+" type="+type+" value="+value+" qname="+qname);
        }*/
        _textContent = new StringBuffer();
    }
    public void endElement(String uri, String localname, String qname){
        _textContent = new StringBuffer();

        if (localname.equals("sdnEntry")) {
/*            if (_osoba != null){
                //stara osoba - spremi
                System.out.println(_osoba);
                _osoba = null;*/
                _ignore = true;
            //}
        } else if (localname.equals("id")) {
/*            if (_osoba != null && _dokument != null) {
                //postoji osoba i dokument - ubaci dokument
                _osoba.setDokument(_dokument);
                System.out.println(_dokument);
                _dokument = null;*/
                _ignore = true;
           // }
        } else if (localname.equals("address")) {
/*            if (_osoba != null && _adresa != null) {
                //postoji osoba i dokument - ubaci dokument
                _osoba.setAdresa(_adresa);
                System.out.println(_adresa);
                _adresa = null;*/
                _ignore = true;
            //}
        }else if (
                localname.equals("sdnList")||
                localname.equals("publshInformation")||
                localname.equals("programList")||
                localname.equals("idList")||
                localname.equals("akaList")||
                localname.equals("addressList")||
                localname.equals("nationalityList")||
                localname.equals("citizenshipList")||
                localname.equals("dateOfBirthList")||
                localname.equals("placeOfBirthList")||
                localname.equals("vesselInfo")){
            _ignore = false;
        }
    }
    //This method is called for any plain text within an element
    //Simply count the number of characters in that text
    public void characters(char[] text, int start, int length){
        if(_ignore)
            return;
        if (_textContent != null) {            
            _textContent.append(text, start, length);
          }
        //if there are more than one line of text it is overwriten in set method
        /*if(_currentEntity.equals("sdnEntry"))
            _osoba.set(_currentElement, _textContent.toString());
        else if(_currentEntity.equals("id"))
            _dokument.set(_currentElement, _textContent.toString());
        else if(_currentEntity.equals("address")) 
            _adresa.set(_currentElement, _textContent.toString());*/
    }
/*    private void addValue(String key, String value, J2EEValueObject vo) {
        if(key!=null && value!=null && vo!=null){
	        if (!value.equals(""))
	          vo.set(key, value);
        }
      }*/
    public void endDocument(){
        System.out.println("endDocument=====");
        //System.out.println(_crnaLista);
    }
    public void startDocument(){
        System.out.println("startDocument=====");
        //_crnaLista = new CrnaListaVo();
    }
    
    //Exception handling
    public void warning (SAXParseException e) throws SAXException {
        System.out.println("J2EE Warning:"+e.getMessage());
     }
     public void error (SAXParseException e) throws SAXException {
        System.out.println("J2EE Error:"+e.getMessage());
        throw new SAXException(e.getMessage());
     }
     public void fatalError (SAXParseException e) throws SAXException {
        System.out.println("J2EE FatalError");
        throw new SAXException(e.getMessage());
     }
}
