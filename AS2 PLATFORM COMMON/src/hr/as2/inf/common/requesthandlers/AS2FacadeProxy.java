package hr.as2.inf.common.requesthandlers;

//import hr.adriacomsoftware.inf.common.datadictionary.J2EEDataDictionary;
//import hr.adriacomsoftware.inf.common.services.compensation.J2EEConfigurationCTS;
//import hr.adriacomsoftware.inf.common.services.compensation.J2EETransactionAction;
//import hr.adriacomsoftware.inf.common.services.compensation.J2EETransactionCompensation;
//import hr.adriacomsoftware.inf.common.services.compensation.J2EETransactionCompensationFactory;
//import hr.adriacomsoftware.inf.common.transport.J2EETransportManager;
import hr.as2.inf.common.core.AS2Constants;
import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.i18n.AS2DataDictionary;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;
import hr.as2.inf.common.types.AS2Date;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.axis.encoding.Base64;

/**
 * Client-side attribute validation? Caching on the client? J2EEFacadeProxy
 * defines/implements generic Facade Proxy methods used by the client to call
 * the server.
 */
/*
 * A Proxy is a direct stand-in for another class, and it typically has the same
 * interface as that class because it implements a common interface or an
 * abstract class. The client object is not aware that it is using a proxy. A
 * Proxy is used when access to the class the client would like to use must be
 * mediated in a way that is apparent to the client -- because it requires
 * restricted access or is a remote process, for example. Extends Observable so
 * that in the case we keep client cache, the J2EEContext can keep reference on
 * it to clear the case if needed.
 */
public abstract class AS2FacadeProxy implements Observer {
	protected String _component = null;
	protected Hashtable<String, Object> _cache = new Hashtable<String, Object>();

	public void update(Observable o, Object arg) {
		// when observable object get updated
		// in the first case we use this, when J2EEContext
		// get updated we will clear client cache
		_cache = new Hashtable<String, Object>();
	}

	public void resetCache() {
		// cach se brise (sav) ako se pozove ovaj metod. potreno u slucaju
		// da se npr. doda nova sifra u sifarnik
		_cache = new Hashtable<String, Object>();
	}

	// u cache dodaju nasljednici od ove klase, samo metodi koji to explicitno
	// naprave
	public void addToCache(AS2Record req, Object res) throws AS2Exception {
		if (AS2Context.getInstance().CLIENT_CACHING_IND)
			_cache.put(req.getRemoteMethod(), res);
	}

	public void addToCache(String service_name, Object res) throws AS2Exception {
		if (AS2Context.getInstance().CLIENT_CACHING_IND)
			_cache.put(service_name, res);
	}

	/**
	 * Call Front Controller Servlet - receive file
	 */
	public byte[] executeGetFile(AS2Record req) throws AS2Exception {
		// if component is set, using it, otherwise assume it is passed in
		// request
		if (_component != null)
			req.setRemoteObject(_component);

		byte[] res = new byte[1];

		try {
			prepareRequest(req);
			URL gwtServlet = null;
			String _url = req.get("@URL");
			gwtServlet = new URL(_url);
			// "http://192.168.0.225:8080/front?fileName=Upitnik_GR&fileLocation=C:\\Program%20Files\\Apache%20Software%20Foundation\\Tomcat%207.0\\webapps\\ROOT\\PDF\\&@@component=hr.adriacomsoftware.app.server.pranjenovca.gr.facade.PranjeNovcaFacadeServer&@@service=izvjestajiFizickeOsobe&@@transform_to=hr.adriacomsoftware.app.common.gradani.dto.OsobaVo&org_jedinica=5594&@@report_selected=prn_gr_upitnik_izvjestaj&@@Output=File");
			HttpURLConnection servletConnection = (HttpURLConnection) gwtServlet
					.openConnection();
			servletConnection.setRequestMethod("POST");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					servletConnection.getInputStream()));
			String inputLine;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				buffer.append(inputLine);
			}
			try {
				res = Base64.decode(buffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			in.close();
		} catch (AS2Exception e) {
			throw e;
		} catch (Exception e) {
			AS2Exception me = new AS2Exception("204");
			me.addCauseException(e);
			throw me;
		}
		return res;
	}

	/**
	 * z.r. dodatak za compensation transaction service
	 */
	public String executeXML(String req) throws AS2Exception {
		try {
			AS2Record _txn_vo = new AS2Record();
			_txn_vo.setRemoteMethod(getRemoteMethodMethodName());
			_txn_vo.set("@@XML", req);
			AS2Record _res = execute(_txn_vo);
			if (_res.exists("@@returned_code"))
				return _res.get("@@returned_code");
			else
				return "0";
		} catch (AS2Exception e) {
			return e.getErrorCode();
		}
	}

	// TODO CTS
	// public String executeCTSXML(String req) throws AS2Exception {
	// try {
	// AS2Record _cts_vo = new AS2Record();
	// _cts_vo.setRemoteMethod(getRemoteMethodMethodName());
	// _cts_vo.set("@@XML", req);
	// AS2Record _res = executeCTS(_cts_vo);
	// if(_res.exists("@@returned_code"))
	// return _res.get("@@returned_code");
	// else
	// return "0";
	// } catch (AS2Exception e) {
	// return e.getErrorCode();
	// }
	// }
	public AS2Record executeCTS(AS2Record req) throws AS2Exception {
		req.set(AS2Record._CTS_SERVICE, "@@CTS"); // used by server
		/*
		 * J2EETransactionCompensation tran =
		 * J2EETransactionCompensationFactory.
		 * getInstance().currentTransaction(); //set transaction id
		 * req.setTransactionToken(tran.getTransactionId());
		 * req.set(AS2Record._REQUEST_ID, req.getId());
		 * 
		 * if (J2EEConfigurationCTS.isLONGTransaction()) {
		 * if(J2EEConfigurationCTS.isCACHEAtClientLocation()){
		 * J2EETransactionCompensation.addStep(req);//TRANSACTIONS BEGIN return
		 * req; //cache on the client }else { if
		 * (!req.exists(AS2Record._TRANSACTION_ACTION))
		 * req.set(AS2Record._TRANSACTION_ACTION, J2EETransactionAction._STEP);
		 * return execute(req); } }TODO
		 */
		return req;
	}

	// ne radi cache
	public AS2Record execute(AS2Record req) throws AS2Exception {
		// if component is set, using it, otherwise assume it is passed in
		// request
		if (req.getRemoteObject() == null
				|| (req.getRemoteObject() != null && req.getRemoteObject()
						.length() < 1)) {
			if (_component != null)
				req.setRemoteObject(_component);
		}
		AS2Record res = new AS2Record();
		try {
			prepareRequest(req);
			res = getTransportManager().send(req);
		} catch (AS2Exception e) {
			throw e;
		} catch (Exception e) {
			AS2Exception me = new AS2Exception("204");
			me.addCauseException(e);
			throw me;
		}
		return (AS2Record) res.getAsObject(AS2Record._RESPONSE);
	}

	// ako se pozove ide u cache po rezultat
	public AS2Record execute(AS2Record req, String service) throws AS2Exception {
		req.setRemoteMethod(service);
		AS2Record res = getFromCache(req);
		if (res != null)
			return res;
		return execute(req);
	}

	/**
	 * Wehen there is no parameter when calling Facade, use this method.
	 */
	public AS2Record execute(String service) throws AS2Exception {
		AS2Record req = new AS2Record();
		req.setDummy(true);// dummy vo
		req.setRemoteMethod(service);
		// J2EEValueObject res = getFromCache(req);
		// if (res != null)
		// return res;
		return execute(req);
	}

	// samo za pozive koji ne vracaju J2EEValueObject
	public Object executeGeneric(AS2Record req) throws AS2Exception {
		// if component is set, using it, otherwise assume it is passed in
		// request
		if (_component != null)
			req.setRemoteObject(_component);
		AS2Record res = new AS2Record();
		try {
			prepareRequest(req);
			res = getTransportManager().send(req);
		} catch (AS2Exception e) {
			throw e;
		} catch (Exception e) {
			AS2Exception me = new AS2Exception("204");
			me.addCauseException(e);
			throw me;
		}
		return res.getAsObject(AS2Record._RESPONSE);
	}

	// ide u cache za one metode koji vracu Object
	public Object executeGeneric(AS2Record req, String aService)
			throws AS2Exception {
		req.setRemoteMethod(aService);
		Object res = getGenericFromCache(req);
		if (res != null)
			return res;
		return executeGeneric(req);
	}

	// ne ide ucache a varaca RS
	public AS2RecordList executeQuery(AS2Record req) throws AS2Exception {
		// if component is set, using it, otherwise assume it is passed in
		// request
		if (_component != null)
			req.setRemoteObject(_component);

		AS2Record res = new AS2Record();

		try {
			prepareRequest(req);
			res = getTransportManager().send(req);
		} catch (AS2Exception e) {
			throw e;
		} catch (Exception e) {
			AS2Exception me = new AS2Exception("204");
			me.addCauseException(e);
			throw me;
		}
		AS2User user = AS2UserFactory.getInstance().getCurrentUser();
		user.set(AS2User._VALUE_LIST_INFO,
				res.getAsObject(AS2User._VALUE_LIST_INFO));
		return (AS2RecordList) res.getAsObject(AS2Record._RESPONSE);
	}

	// ide u cache
	public AS2RecordList executeQuery(AS2Record req, String aService)
			throws AS2Exception {
		req.setRemoteMethod(aService);
		AS2RecordList res = getQueryFromCache(req);
		if (res != null)
			return res;
		return executeQuery(req);
	}

	// ide u cache
	public AS2RecordList executeQuery(String aService) throws AS2Exception {
		AS2Record req = new AS2Record();
		req.setDummy(true);// dummy vo
		req.setRemoteMethod(aService);
		// J2EEResultSet res = getQueryFromCache(req);
		// if (res != null)
		// return res;
		return executeQuery(req);
	}

	// vraca VO iz cache
	public AS2Record getFromCache(AS2Record req) {

		if (AS2Context.getInstance().CLIENT_CACHING_IND) {
			AS2Trace.trace(AS2Trace.I, "J2EEFacadeProxy.getFromCache On.");

			AS2Record res = (AS2Record) _cache.get(req.getRemoteMethod());

			if (res != null) {
				if (res.hasChanged()) {
					AS2Trace.trace(AS2Trace.I,
							"J2EEFacadeProxy.getFromCache - Has Changed");
					return null;
				} else {
					AS2Trace.trace(AS2Trace.I,
							"J2EEFacadeProxy.getFromCache - Found");
					// return (J2EEValueObject) res.clone();
					return res;
				}
			}
			return res;
		} else
			return null;
	}

	// vraca Object iz cache
	public Object getGenericFromCache(AS2Record req) {

		if (AS2Context.getInstance().CLIENT_CACHING_IND) {
			AS2Trace.trace(AS2Trace.I, "J2EEFacadeProxy.getFromCache On.");
			Object res = _cache.get(req.getRemoteMethod());
			if (res != null)
				return res;
		}
		return null;
	}

	// vraca RS iz cache
	public AS2RecordList getQueryFromCache(AS2Record req) {
		if (AS2Context.getInstance().CLIENT_CACHING_IND) {
			AS2RecordList rs = (AS2RecordList) _cache.get(req.getRemoteMethod()
					+ req.getCacheKey());
			if (rs != null) {

				if (rs.hasChanged()) {
					AS2Trace.trace(AS2Trace.I,
							"J2EEFacadeProxy.getQueryCache - Has Changed");
					return null;
				} else {
					AS2Trace.trace(AS2Trace.I,
							"J2EEFacadeProxy.getQueryCache - Found");
					// return (J2EEResultSet) rs.clone();
					return rs;
				}
			}
			return rs;
		} else
			return null;
	}

	private void prepareRequest(AS2Record req) {
		// disable potential validation indicator,
		// object is ready to get to Logical Server
		req.setValidation(false);
		AS2User user = AS2UserFactory.getInstance().getCurrentUser();
		req.set(AS2Constants.USER_OBJ, user);
		req.set(AS2DataDictionary.ZADNJA_PROMJENA_USER_ID, user.getUserId());
		req.set(AS2DataDictionary.ZADNJA_PROMJENA_VRIJEME, AS2Date
				.getCurrentTime().toString());
		req.set(AS2DataDictionary.VALID_IND, AS2DataDictionary.VALID_IND_YES);
	}

	/**
	 * get the TransportManager which sends the message to the server Creation
	 * date: (03/09/2001 11:52:57 AM)
	 * 
	 * @return com.adriacomsoftware.inf.transport.client.J2EETransportManager
	 */
	public AS2ClientRequestDispatcher getTransportManager() {
		return AS2ClientRequestDispatcher.getInstance();
	}

	protected void setRemoteObject(String value) {
		_component = value;
	}

	// z.r.12.5.2010. Name of the method
	public String getRemoteMethodMethodName() {
		Throwable t = new Throwable();
		StackTraceElement[] es = t.getStackTrace();
		StackTraceElement e = es[2];
		return e.getMethodName();
	}
}
