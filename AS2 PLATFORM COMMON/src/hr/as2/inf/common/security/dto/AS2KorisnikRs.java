package hr.as2.inf.common.security.dto;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;

public class AS2KorisnikRs extends AS2RecordList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AS2KorisnikRs()	{
		super();
	}
	/**
	 * @param set
	 */ /* constructor comment version 0.01 */
	public AS2KorisnikRs(AS2RecordList set) {
		super(); //rows not set inside super constructor
		setColumnNames(set.getColumnNames());
		setColumnSizes(set.getColumnSizes());	
		setMetaData(set.getMetaData());
		for (AS2Record row : set.getRows()) {
			AS2KorisnikVo vo = new AS2KorisnikVo(row);
			this.addRow(vo);
		}
	}
	public boolean isUserNameBusy(String user_name){
		for (AS2Record vo : getRows()) {
			if(vo.getAsString(AS2KorisnikVo.RBAC_KORISNIK__KORISNIK).equals(user_name)){
				return true;
			}	
		}
		return false;
	}
	public AS2KorisnikVo getUserByName(String user_name){
		for (AS2Record vo : getRows()) {
			if(vo.getAsString(AS2KorisnikVo.RBAC_KORISNIK__KORISNIK).equals(user_name)){
				return new AS2KorisnikVo(vo);
			}	
		}
		return null;
	}
}
