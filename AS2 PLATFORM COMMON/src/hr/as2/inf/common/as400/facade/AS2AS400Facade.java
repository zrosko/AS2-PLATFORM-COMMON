package hr.as2.inf.common.as400.facade;

import hr.as2.inf.common.data.as400.AS2AS400SpoolFileRs;
import hr.as2.inf.common.data.as400.AS2AS400SpoolFileVo;

public interface AS2AS400Facade {
	public abstract AS2AS400SpoolFileRs getSpooledFileList(AS2AS400SpoolFileVo value) throws Exception;
	public abstract AS2AS400SpoolFileRs procitajSveJobove(AS2AS400SpoolFileVo value) throws Exception;
	public abstract AS2AS400SpoolFileVo citajJob(AS2AS400SpoolFileVo value) throws Exception;
	public abstract AS2AS400SpoolFileVo azurirajJob(AS2AS400SpoolFileVo value) throws Exception;	
	public abstract AS2AS400SpoolFileVo dodajJob(AS2AS400SpoolFileVo value) throws Exception;
	public abstract AS2AS400SpoolFileVo brisiJob(AS2AS400SpoolFileVo value) throws Exception;	
	public abstract AS2AS400SpoolFileVo citajJobSpoolFile(AS2AS400SpoolFileVo value) throws Exception;
	public abstract AS2AS400SpoolFileRs citajJobSpoolFileRecords(AS2AS400SpoolFileVo value) throws Exception;
}
