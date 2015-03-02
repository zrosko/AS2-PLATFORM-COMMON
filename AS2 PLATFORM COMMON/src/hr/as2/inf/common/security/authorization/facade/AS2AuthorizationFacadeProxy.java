package hr.as2.inf.common.security.authorization.facade;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.requesthandlers.AS2FacadeProxy;
import hr.as2.inf.common.security.dto.AS2KomponentaRs;
import hr.as2.inf.common.security.dto.AS2KomponentaVo;
import hr.as2.inf.common.security.dto.AS2KorisnikRs;
import hr.as2.inf.common.security.dto.AS2KorisnikVo;
import hr.as2.inf.common.security.dto.AS2OsobaRs;
import hr.as2.inf.common.security.dto.AS2OsobaVo;
import hr.as2.inf.common.security.dto.AS2UlogaKomponentaRs;
import hr.as2.inf.common.security.dto.AS2UlogaKomponentaVo;
import hr.as2.inf.common.security.dto.AS2UlogaRs;
import hr.as2.inf.common.security.dto.AS2UlogaVo;

public class AS2AuthorizationFacadeProxy extends AS2FacadeProxy implements AS2AuthorizationFacade {
	private static AS2AuthorizationFacadeProxy _instance = null;
	private AS2AuthorizationFacadeProxy() {
		setRemoteObject("hr.as2.inf.server.security.authorization.facade.AS2AuthorizationFacadeServer");
	}

	public static AS2AuthorizationFacadeProxy getInstance()	{
		if (_instance == null)
			_instance = new AS2AuthorizationFacadeProxy();
		return _instance;
	}
	public AS2OsobaRs getAllOsobe(AS2OsobaVo value) throws AS2Exception {
	    AS2OsobaVo vo = new AS2OsobaVo();
	    vo.setRemoteObject("getAllOsobe");
	    AS2OsobaRs rs = (AS2OsobaRs)getQueryFromCache(vo);
	    //if(rs==null){
	        vo.setRemoteObject("procitajSveOsobe");
	        rs =  (AS2OsobaRs) executeQuery(vo); 
	        addToCache("getAllOsobe", rs);
	    //}
	    return rs;
	}
	public AS2KomponentaRs getAllKomponente(AS2KomponentaVo value) throws AS2Exception {
	    AS2KomponentaVo vo = new AS2KomponentaVo();
	    vo.setRemoteObject("getAllKomponente");
	    AS2KomponentaRs rs = (AS2KomponentaRs)getQueryFromCache(vo);
	    //if(rs==null){
	        vo.setRemoteObject("procitajSveKomponente");
	        rs =  (AS2KomponentaRs) executeQuery(vo); 
	        addToCache("getAllKomponente", rs);
	    //}
	    return rs;
	}
	public AS2UlogaRs getAllUloge(AS2UlogaVo value) throws AS2Exception{
	    AS2UlogaVo vo = new AS2UlogaVo();
	    vo.setRemoteObject("getAllUloge");
	    AS2UlogaRs rs = (AS2UlogaRs)getQueryFromCache(vo);
	    //if(rs==null){
	        vo.setRemoteObject("procitajSveUloge");
	        rs =  (AS2UlogaRs) executeQuery(vo); 
	        addToCache("getAllUloge", rs);
	    //}
	    return rs;
	}
	//AUIL support
	   public String getKorisnik(String id){
		    String a= getAllOsobe(new AS2OsobaVo()).rsReadNameForId(id, AS2OsobaVo.RBAC_OSOBA__ID_OSOBE,AS2OsobaVo.RBAC_OSOBA__KORISNIK);
		    return a;
	    }
    public String getImePrezimeFromId(String id){
	    String a= getAllOsobe(new AS2OsobaVo()).rsReadNameForId(id, AS2OsobaVo.RBAC_OSOBA__ID_OSOBE,AS2OsobaVo.RBAC_OSOBA__IME_PREZIME);
	    return a;
    }
    public String getIdForImePrezime(String naziv){
        String a= getAllOsobe(new AS2OsobaVo()).rsReadIdForName(naziv, AS2OsobaVo.RBAC_OSOBA__IME_PREZIME, AS2OsobaVo.RBAC_OSOBA__ID_OSOBE);
        return a;
    }
    public String getNazivUlogeFromId(String id){
	    return getAllUloge(new AS2UlogaVo()).rsReadNameForId(id, AS2UlogaVo.RBAC_ULOGA__ID_ULOGE,AS2UlogaVo.RBAC_ULOGA__ULOGA);
    }
    public String getIdForNazivUloge(String naziv){
	    return getAllUloge(new AS2UlogaVo()).rsReadIdForName(naziv, AS2UlogaVo.RBAC_ULOGA__ULOGA, AS2UlogaVo.RBAC_ULOGA__ID_ULOGE);
    }
    public String getNazivKomponenteFromId(String id){
	    return getAllKomponente(new AS2KomponentaVo()).rsReadNameForId(id, AS2KomponentaVo.RBAC_KOMPONENTA__ID_KOMPONENTE,AS2KomponentaVo.RBAC_KOMPONENTA__KOMPONENTA);
    }
    public String getIdForNazivKomponente(String naziv){
	    return getAllKomponente(new AS2KomponentaVo()).rsReadIdForName(naziv, AS2KomponentaVo.RBAC_KOMPONENTA__KOMPONENTA, AS2KomponentaVo.RBAC_KOMPONENTA__ID_KOMPONENTE);
    }

    public void azurirajKomponentu(AS2KomponentaVo value) throws AS2Exception {
        execute(value, "azurirajKomponentu");        
    }

    public AS2KomponentaRs procitajSveKomponente(AS2KomponentaVo value) throws AS2Exception {
        return (AS2KomponentaRs) executeQuery(value, "procitajSveKomponente");
    }

    public void dodajKomponentu(AS2KomponentaVo value) throws AS2Exception {
        execute(value, "dodajKomponentu");    
    }

    public void brisiKomponentu(AS2KomponentaVo value) throws AS2Exception {
        execute(value, "brisiKomponentu");            
    }

    public void azurirajUlogu(AS2UlogaVo value) throws AS2Exception {
        execute(value, "azurirajUlogu");    
    }

    public AS2UlogaRs procitajSveUloge(AS2UlogaVo value) throws AS2Exception {
        return (AS2UlogaRs) executeQuery(value, "procitajSveUloge");
    }

    public void dodajUlogu(AS2UlogaVo value) throws AS2Exception {
        execute(value, "dodajUlogu");    
    }

    public void brisiUlogu(AS2UlogaVo value) throws AS2Exception {
        execute(value, "brisiUlogu");    
    }

    public void azurirajUloguKomponentu(AS2UlogaKomponentaVo value) throws AS2Exception {
        execute(value, "azurirajUloguKomponentu");    
    }

    public AS2UlogaKomponentaRs procitajSveUlogeKomponente(AS2UlogaKomponentaVo value) throws AS2Exception {
        return (AS2UlogaKomponentaRs) executeQuery(value, "procitajSveUlogeKomponente");
    }

    public void dodajUloguKomponentu(AS2UlogaKomponentaVo value) throws AS2Exception {
        execute(value, "dodajUloguKomponentu");    
    }

    public void brisiUloguKomponentu(AS2UlogaKomponentaVo value) throws AS2Exception {
        execute(value, "brisiUloguKomponentu");    
    }

    public void azurirajKorisnika(AS2KorisnikVo value) throws AS2Exception {
        execute(value, "azurirajKorisnika");    
    }

    public AS2KorisnikRs procitajSveKorisnike(AS2KorisnikVo value) throws AS2Exception {
        return (AS2KorisnikRs) executeQuery(value, "procitajSveKorisnike");
    }

    public void dodajKorisnika(AS2KorisnikVo value) throws AS2Exception {
        execute(value,"dodajKorisnika");
    }

    public void brisiKorisnika(AS2KorisnikVo value) throws AS2Exception {
        execute(value, "brisiKorisnika");    
    }

    public void azurirajOsobu(AS2OsobaVo value) throws AS2Exception {
        execute(value, "azurirajOsobu");    
    }

    public AS2OsobaRs procitajSveOsobe(AS2OsobaVo value) throws AS2Exception {
        return (AS2OsobaRs) executeQuery(value, "procitajSveOsobe");
    }

    public void dodajOsobu(AS2OsobaVo value) throws AS2Exception {
        execute(value, "dodajOsobu");    
    }

    public void brisiOsobu(AS2OsobaVo value) throws AS2Exception {
        execute(value, "brisiOsobu");    
    }

    public AS2KomponentaVo citajSlijedeciIdKomponente(AS2KomponentaVo value) throws AS2Exception {
         return (AS2KomponentaVo)execute(value, "citajSlijedeciIdKomponente");
    }

    public AS2UlogaVo citajSlijedeciIdUloge(AS2UlogaVo value) throws AS2Exception {
        return (AS2UlogaVo)execute(value, "citajSlijedeciIdUloge");
    }

	public AS2KorisnikRs procitajSveAktivneKorisnike(AS2KorisnikVo value)throws AS2Exception {
	    value.setRemoteObject("procitajSveAktivneKorisnike");
	    AS2KorisnikRs rs = (AS2KorisnikRs)getQueryFromCache(value);
	    if(rs==null){
	    	value.setRemoteObject("procitajSveAktivneKorisnike");
	        rs =  (AS2KorisnikRs) executeQuery(value); 
	        addToCache("procitajSveAktivneKorisnike", rs);
	    }
	    return rs;
	}
    public String getKorisnikZaNazivDjelatnikaBanke(String naziv){
	    return procitajSveAktivneKorisnike(new AS2KorisnikVo()).rsReadIdForName(naziv);
    }

	public AS2KorisnikRs procitajSveKorisnikeAplikacije(AS2KorisnikVo value) throws AS2Exception {
	    value.setRemoteObject("procitajSveKorisnikeAplikacije");
	    AS2KorisnikRs rs = (AS2KorisnikRs)getQueryFromCache(value);
	    if(rs==null){
	    	value.setRemoteObject("procitajSveKorisnikeAplikacije");
	        rs =  (AS2KorisnikRs) executeQuery(value); 
	        addToCache("procitajSveKorisnikeAplikacije", rs);
	    }
	    return rs;
	}
}
