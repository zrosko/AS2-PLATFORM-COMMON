package hr.as2.inf.common.as400.facade;

import hr.as2.inf.common.data.as400.AS2AS400SpoolFileRs;
import hr.as2.inf.common.data.as400.AS2AS400SpoolFileVo;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.requesthandlers.AS2FacadeProxy;

public class AS2AS400FacadeProxy extends AS2FacadeProxy implements AS2AS400Facade {

	private static AS2AS400FacadeProxy _instance = null;
	private AS2AS400FacadeProxy(){
		setRemoteObject("hr.as2.inf.server.as400.facade.AS2AS400FacadeServer");
	}

	public static AS2AS400FacadeProxy getInstance(){
		if (_instance == null)
			_instance = new AS2AS400FacadeProxy();
		return _instance;
	}

	public AS2AS400SpoolFileRs getSpooledFileList(AS2AS400SpoolFileVo value) throws AS2Exception {
        value.setRemoteMethod("getSpooledFileList");
        AS2AS400SpoolFileRs res = (AS2AS400SpoolFileRs) executeQuery(value);
		return res;
	}

	public AS2AS400SpoolFileRs procitajSveJobove(AS2AS400SpoolFileVo value)	throws AS2Exception {
        value.setRemoteMethod("procitajSveJobove");
        AS2AS400SpoolFileRs res = (AS2AS400SpoolFileRs) executeQuery(value);
		return res;
	}

	public AS2AS400SpoolFileVo citajJob(AS2AS400SpoolFileVo value) throws AS2Exception {
		value.setRemoteMethod("citajJob");
		AS2AS400SpoolFileVo res = (AS2AS400SpoolFileVo)execute(value);
		return res;
	}

	public AS2AS400SpoolFileVo azurirajJob(AS2AS400SpoolFileVo value) throws AS2Exception {
		value.setRemoteMethod("azurirajJob");
		AS2AS400SpoolFileVo res = (AS2AS400SpoolFileVo)execute(value);
		return res;		
	}

	public AS2AS400SpoolFileVo dodajJob(AS2AS400SpoolFileVo value) throws AS2Exception {
		value.setRemoteMethod("dodajJob");
		AS2AS400SpoolFileVo res = (AS2AS400SpoolFileVo)execute(value);
		return res;			
	}

	public AS2AS400SpoolFileVo brisiJob(AS2AS400SpoolFileVo value) throws AS2Exception {
		value.setRemoteMethod("brisiJob");
		AS2AS400SpoolFileVo res = (AS2AS400SpoolFileVo)execute(value);
		return res;	
	}

	public AS2AS400SpoolFileVo citajJobSpoolFile(AS2AS400SpoolFileVo value){
		value.setRemoteMethod("citajJobSpoolFile");
		AS2AS400SpoolFileVo res = (AS2AS400SpoolFileVo)execute(value);
		return res;	
	}

	public AS2AS400SpoolFileRs citajJobSpoolFileRecords(AS2AS400SpoolFileVo value) throws AS2Exception {
        value.setRemoteMethod("citajJobSpoolFileRecords");
        AS2AS400SpoolFileRs res = (AS2AS400SpoolFileRs) executeQuery(value);
		return res;
	}
}
