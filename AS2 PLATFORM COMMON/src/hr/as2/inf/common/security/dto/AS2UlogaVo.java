package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2UlogaVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String RBAC_ULOGA__ID_ULOGE = "id_uloge";
    public static String RBAC_ULOGA__APLIKACIJA = "aplikacija";
    public static String RBAC_ULOGA__ULOGA = "uloga";
    public static String RBAC_ULOGA__ZADANA_KOMPONENTA = "zadana_komponenta";
    public static String RBAC_ULOGA__OPIS = "opis";
    public static String RBAC_ULOGA__ISPRAVNO = "ispravno";
	
    public AS2UlogaVo(){
		super();
	}
	public AS2UlogaVo(AS2Record value){
		super(value);
	}
	public String getIdUloge() {
		return getAsString(RBAC_ULOGA__ID_ULOGE);
	}
	public String getAplikacija() {
		return getAsString(RBAC_ULOGA__APLIKACIJA);
	}
	public String getUloga() {
		return getAsString(RBAC_ULOGA__ULOGA);
	}
	public String getZadanaKomponenta() {
		return getAsString(RBAC_ULOGA__ZADANA_KOMPONENTA);
	}
	public String getOpis() {
		return getAsString(RBAC_ULOGA__OPIS);
	}
	public String getIspravno() {
		return getAsString(RBAC_ULOGA__ISPRAVNO);
	}
	public void setIdUloge(String value) {
		set(RBAC_ULOGA__ID_ULOGE, value);
	}
	public void setAplikacija(String value) {
		set(RBAC_ULOGA__APLIKACIJA, value);
	}
	public void setUloga(String value) {
		set(RBAC_ULOGA__ULOGA, value);
	}
	public void setZadanaKomponenta(String value) {
		set(RBAC_ULOGA__ZADANA_KOMPONENTA, value);
	}
	public void setOpis(String value) {
		set(RBAC_ULOGA__OPIS, value);
	}
	public void setIspravno(String value) {
		set(RBAC_ULOGA__ISPRAVNO, value);
	}

}
