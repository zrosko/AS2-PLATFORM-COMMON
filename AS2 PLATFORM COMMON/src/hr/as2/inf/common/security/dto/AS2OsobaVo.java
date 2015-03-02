package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2OsobaVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String RBAC_OSOBA__ID_OSOBE = "id_osobe";
	public static String RBAC_OSOBA__IME = "ime";
	public static String RBAC_OSOBA__PREZIME = "prezime";
	public static String RBAC_OSOBA__ISPRAVNO = "ispravno";
	public static String RBAC_OSOBA__IME_PREZIME = "ime_prezime";
	public static String RBAC_OSOBA__KORISNIK = "korisnik";

	public AS2OsobaVo() {
		super();
	}

	public AS2OsobaVo(AS2Record value) {
		super(value);
	}

	public String getIdOsobe() {
		return getAsString(RBAC_OSOBA__ID_OSOBE);
	}

	public String getIme() {
		return getAsString(RBAC_OSOBA__IME);
	}

	public String getPrezime() {
		return getAsString(RBAC_OSOBA__PREZIME);
	}

	public String getImePrezime() {
		return getAsString(RBAC_OSOBA__IME_PREZIME);
	}

	public String getKorisnik() {
		return getAsString(RBAC_OSOBA__KORISNIK);
	}

	public String getIspravno() {
		return getAsString(RBAC_OSOBA__ISPRAVNO);
	}

	public void setIdOsobe(String value) {
		set(RBAC_OSOBA__ID_OSOBE, value);
	}

	public void setIme(String value) {
		set(RBAC_OSOBA__IME, value);
	}

	public void setPrezime(String value) {
		set(RBAC_OSOBA__PREZIME, value);
	}

	public void setImePrezime(String value) {
		set(RBAC_OSOBA__IME_PREZIME, value);
	}

	public void setKorisnik(String value) {
		set(RBAC_OSOBA__KORISNIK, value);
	}

	public void setIspravno(String value) {
		set(RBAC_OSOBA__ISPRAVNO, value);
	}
}
