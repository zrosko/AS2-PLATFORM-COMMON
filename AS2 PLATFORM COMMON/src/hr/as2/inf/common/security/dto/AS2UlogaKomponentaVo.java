package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2UlogaKomponentaVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String RBAC_ULOGA_KOMPONENTA__ID_ULOGE = "id_uloge";
	public static String RBAC_ULOGA_KOMPONENTA__ID_KOMPONENTE = "id_komponente";
	public static String RBAC_ULOGA_KOMPONENTA__APLIKACIJA = "aplikacija";
	public static String RBAC_ULOGA_KOMPONENTA__AKCIJE = "akcije";
	public static String RBAC_ULOGA_KOMPONENTA__IZVJESCA = "izvjesca";
	public static String RBAC_ULOGA_KOMPONENTA__RAZINA = "razina";

	public AS2UlogaKomponentaVo() {
		super();
	}

	public AS2UlogaKomponentaVo(AS2Record value) {
		super(value);
	}

	public String getIdUloge() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__ID_ULOGE);
	}

	public String getIdKomponente() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__ID_KOMPONENTE);
	}

	public String getAplikacija() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__APLIKACIJA);
	}

	public String getAkcije() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__AKCIJE);
	}

	public String getIzvjesca() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__IZVJESCA);
	}

	public String getRazina() {
		return getAsString(RBAC_ULOGA_KOMPONENTA__RAZINA);
	}

	public void setIdUloge(String value) {
		set(RBAC_ULOGA_KOMPONENTA__ID_ULOGE, value);
	}

	public void setIdKomponente(String value) {
		set(RBAC_ULOGA_KOMPONENTA__ID_KOMPONENTE, value);
	}

	public void setAplikacija(String value) {
		set(RBAC_ULOGA_KOMPONENTA__APLIKACIJA, value);
	}

	public void setAkcije(String value) {
		set(RBAC_ULOGA_KOMPONENTA__AKCIJE, value);
	}

	public void setIzvjesca(String value) {
		set(RBAC_ULOGA_KOMPONENTA__IZVJESCA, value);
	}

	public void setRazina(String value) {
		set(RBAC_ULOGA_KOMPONENTA__RAZINA, value);
	}
}
