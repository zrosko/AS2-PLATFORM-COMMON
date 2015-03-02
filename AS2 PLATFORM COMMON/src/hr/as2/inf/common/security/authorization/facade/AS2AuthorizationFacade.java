package hr.as2.inf.common.security.authorization.facade;

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

public interface AS2AuthorizationFacade {    
    //komponenta (function)
	public abstract void azurirajKomponentu(AS2KomponentaVo value) throws Exception;
	public abstract AS2KomponentaRs procitajSveKomponente(AS2KomponentaVo value) throws Exception;
	public abstract void dodajKomponentu(AS2KomponentaVo value) throws Exception;
	public abstract void brisiKomponentu(AS2KomponentaVo value) throws Exception;
	public abstract AS2KomponentaVo citajSlijedeciIdKomponente(AS2KomponentaVo value) throws Exception;
	//uloga (role)
	public abstract void azurirajUlogu(AS2UlogaVo value) throws Exception;
	public abstract AS2UlogaRs procitajSveUloge(AS2UlogaVo value) throws Exception;
	public abstract void dodajUlogu(AS2UlogaVo value) throws Exception;
	public abstract void brisiUlogu(AS2UlogaVo value) throws Exception;
	public abstract AS2UlogaVo citajSlijedeciIdUloge(AS2UlogaVo value) throws Exception;
	//uloga komponenta (role function)
	public abstract void azurirajUloguKomponentu(AS2UlogaKomponentaVo value) throws Exception;
	public abstract AS2UlogaKomponentaRs procitajSveUlogeKomponente(AS2UlogaKomponentaVo value) throws Exception;
	public abstract void dodajUloguKomponentu(AS2UlogaKomponentaVo value) throws Exception;
	public abstract void brisiUloguKomponentu(AS2UlogaKomponentaVo value) throws Exception;
	//korisnik (user)
	public abstract void azurirajKorisnika(AS2KorisnikVo value) throws Exception;
	public abstract AS2KorisnikRs procitajSveKorisnike(AS2KorisnikVo value) throws Exception;
	public abstract AS2KorisnikRs procitajSveAktivneKorisnike(AS2KorisnikVo value) throws Exception;
	public abstract AS2KorisnikRs procitajSveKorisnikeAplikacije(AS2KorisnikVo value) throws Exception;
	public abstract void dodajKorisnika(AS2KorisnikVo value) throws Exception;
	public abstract void brisiKorisnika(AS2KorisnikVo value) throws Exception;
	//osoba (person)
	public abstract void azurirajOsobu(AS2OsobaVo value) throws Exception;
	public abstract AS2OsobaRs procitajSveOsobe(AS2OsobaVo value) throws Exception;
	public abstract void dodajOsobu(AS2OsobaVo value) throws Exception;
	public abstract void brisiOsobu(AS2OsobaVo value) throws Exception;
}
