package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;


public class AS2KomponentaVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String RBAC_KOMPONENTA__ID_KOMPONENTE = "id_komponente";
    public static String RBAC_KOMPONENTA__APLIKACIJA = "aplikacija";
    public static String RBAC_KOMPONENTA__KOMPONENTA = "komponenta";
    public static String RBAC_KOMPONENTA__OPIS = "opis";
    public static String RBAC_KOMPONENTA__AKCIJE = "akcije";
    public static String RBAC_KOMPONENTA__IZVJESCA = "izvjesca";
    public static String RBAC_KOMPONENTA__ISPRAVNO = "ispravno";
	
    public AS2KomponentaVo()	{
		super();
	}
	public AS2KomponentaVo(AS2Record value){
		super(value);
	}

	public String getIdKomponente() {
		return getAsString(RBAC_KOMPONENTA__ID_KOMPONENTE);
	}
	public String getAplikacija() {
		return getAsString(RBAC_KOMPONENTA__APLIKACIJA);
	}
	public String getKomponenta() {
		return getAsString(RBAC_KOMPONENTA__KOMPONENTA);
	}
	public String getOpis() {
		return getAsString(RBAC_KOMPONENTA__OPIS);
	}
	public String getAkcije() {
		return getAsString(RBAC_KOMPONENTA__AKCIJE);
	}
	public String getIzvjesca() {
		return getAsString(RBAC_KOMPONENTA__IZVJESCA);
	}
	public String getIspravno() {
		return getAsString(RBAC_KOMPONENTA__ISPRAVNO);
	}
	public void setIdKomponente(String value) {
		set(RBAC_KOMPONENTA__ID_KOMPONENTE, value);
	}
	public void setAplikacija(String value) {
		set(RBAC_KOMPONENTA__APLIKACIJA, value);
	}
	public void setKomponenta(String value) {
		set(RBAC_KOMPONENTA__KOMPONENTA, value);
	}
	public void setOpis(String value) {
		set(RBAC_KOMPONENTA__OPIS, value);
	}
	public void setAkcije(String value) {
		set(RBAC_KOMPONENTA__AKCIJE, value);
	}
	public void setIzvjesca(String value) {
		set(RBAC_KOMPONENTA__IZVJESCA, value);
	}
	public void setIspravno(String value) {
		set(RBAC_KOMPONENTA__ISPRAVNO, value);
	}

}
