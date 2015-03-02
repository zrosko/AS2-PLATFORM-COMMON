package hr.as2.inf.common.data;

import hr.as2.inf.common.evictor.AS2EvictionInterface;

import java.io.Serializable;
import java.util.Observer;

public interface AS2RecordInterface extends Serializable, Cloneable, Observer, AS2EvictionInterface {
	public final static String _TRANSACTION_TOKEN = "@@transactionToken";
	public final static String _TRANSACTION_ACTION = "@@transactionAction";
    public final static String _COMPONENT_TXN = "@@componentTXN";
    public final static String _COMPONENT_CTS = "@@componentCTS";
    public final static String _SERVICE_TXN = "@@serviceTXN";
    public final static String _SERVICE_CTS = "@@serviceCTS";
    public final static String _REQUEST_ID = "@@requestId"; 
    public final static String _CTS_SERVICE = "@@cts";
	public final static String _WARNING = "@@warning";
	public final static String _ERROR = "@@error";
	public final static String _INFORMATION = "@@information";
	public final static String _MESSAGE = "@@message";
    public final static String _TRANS_MESSAGE = "@@transMessage"; 
	public final static String _SUCCESS = "@@success";
	public final static String _SUCCESS_FAILED = "@@FAILED";
	public final static String _SUCCESS_SUCCESSFUL = "@@SUCCESSFUL";
	public final static String _RESPONSE = "@@RESPONSE";
	public final static String _GET_INSTANCE = "getInstance";
	public final static String _VALUE_LIST_INFO = "@@VALUE_LIST_INFO";
	public final static String _ZERO = "0";
    public final static String INDIKATOR_ONE = "1";
    public final static String INDIKATOR_ZERO = "0";
    public final static String ISPRAVNO = "ispravno";
    public final static String _OBJECT_ID = "id";
    public final static String _OBJECT_NAME = "name";
	//TODO brisi dva reda
    public final static String VALID_IND_DA = "1";
    public final static String VALID_IND_NE = "0";
    public final static String VALID_IND_YES = "Y";
    public final static String VALID_IND_NO = "N";
	public final static int _DEFAULT_COLLECTION_SIZE = 20;
	//TODO test only for Generic Logger test
	public String getAsStringOrEmpty(String value);
	public void set(String name, Object value);
}
