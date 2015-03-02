package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.security.encoding.AS2Base64;
import hr.as2.inf.common.security.user.AS2User;

import java.util.Calendar;

public class AS2KorisnikVo extends AS2User {
	private static final long serialVersionUID = 1L;
	public static String RBAC_KORISNIK__ID_KORISNIKA = "id_korisnika";
	public static String RBAC_KORISNIK__ID_OSOBE = "id_osobe";
	public static String RBAC_KORISNIK__APLIKACIJA = "aplikacija";
	public static String RBAC_KORISNIK__KORISNIK = "korisnik";
	public static String RBAC_KORISNIK__ZAPORKA = "zaporka";
	public static String RBAC_KORISNIK__ZADANA_ULOGA = "zadana_uloga";
	public static String RBAC_KORISNIK__VRIJEDI_OD = "vrijedi_od";
	public static String RBAC_KORISNIK__VRIJEDI_DO = "vrijedi_do";
	public static String RBAC_KORISNIK__OPIS = "opis";
	public static String RBAC_KORISNIK__ISPRAVNO = "ispravno";

	public AS2KorisnikVo() {
		super();
	}

	public AS2KorisnikVo(AS2Record value) {
		super(value);
	}

	public String getIdKorisnika() {
		return getAsString(RBAC_KORISNIK__ID_KORISNIKA);
	}

	public String getIdOsobe() {
		return getAsString(RBAC_KORISNIK__ID_OSOBE);
	}

	public String getAplikacija() {
		return getAsString(RBAC_KORISNIK__APLIKACIJA);
	}

	public String getKorisnik() {
		return getAsString(RBAC_KORISNIK__KORISNIK);
	}

	public String getZaporkaDecoded() {
		return AS2Base64.decode(get(RBAC_KORISNIK__ZAPORKA));
	}

	public String getZaporka() {
		// return get(RBAC_KORISNIK__ZAPORKA);
		return AS2Base64.decode(get(RBAC_KORISNIK__ZAPORKA));
	}

	public String getZadanaUloga() {
		return getAsString(RBAC_KORISNIK__ZADANA_ULOGA);
	}

	public Calendar getVrijediOd() {
		return getAsCalendar(RBAC_KORISNIK__VRIJEDI_OD);
	}

	public Calendar getVrijediDo() {
		return getAsCalendar(RBAC_KORISNIK__VRIJEDI_DO);
	}

	public String getOpis() {
		return getAsString(RBAC_KORISNIK__OPIS);
	}

	public String getIspravno() {
		return getAsString(RBAC_KORISNIK__ISPRAVNO);
	}

	public void setIdKorisnika(String value) {
		set(RBAC_KORISNIK__ID_KORISNIKA, value);
	}

	public void setIdOsobe(String value) {
		set(RBAC_KORISNIK__ID_OSOBE, value);
	}

	public void setAplikacija(String value) {
		set(RBAC_KORISNIK__APLIKACIJA, value);
	}

	public void setKorisnik(String value) {
		set(RBAC_KORISNIK__KORISNIK, value);
	}

	public void setZaporka(String value) {
		set(RBAC_KORISNIK__ZAPORKA, AS2Base64.encode(value));
		// set(RBAC_KORISNIK__ZAPORKA, value);
	}

	public void setZadanaUloga(String value) {
		set(RBAC_KORISNIK__ZADANA_ULOGA, value);
	}

	public void setVrijediOd(Calendar value) {
		setCalendarAsDateString(value, RBAC_KORISNIK__VRIJEDI_OD);
	}

	public void setVrijediDo(Calendar value) {
		setCalendarAsDateString(value, RBAC_KORISNIK__VRIJEDI_DO);
	}

	public void setOpis(String value) {
		set(RBAC_KORISNIK__OPIS, value);
	}

	public void setIspravno(String value) {
		set(RBAC_KORISNIK__ISPRAVNO, value);
	}
}
