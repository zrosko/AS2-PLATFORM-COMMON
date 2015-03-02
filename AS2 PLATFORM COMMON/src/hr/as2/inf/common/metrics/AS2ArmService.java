package hr.as2.inf.common.metrics;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.logging.AS2Trace;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Response time measurement.
 * Creation date: (4/19/00 8:20:52 PM)
 * @author: Zdravko Rosko
 */
@SuppressWarnings("rawtypes")
public final class AS2ArmService {
	private static AS2ArmService _instance = null;
	protected static Hashtable _servicesStatistics = new Hashtable(200);
	protected static Hashtable _transportStartTime = new Hashtable(200);
	protected static Hashtable _businessLogicStartTime = new Hashtable(200);
	protected static Hashtable _dataAccessStartTime = new Hashtable(200);
	protected static Hashtable _currentTransactionName = new Hashtable(200);

	public static final String SERVICE_NAME  = "service_name";
	public static final String SERVICE_COUNT = "service_count";
	public static final String TRANSPORT_TIME = "transport_time";
	public static final String BUSINESS_LOGIC_TIME = "business_logic_time";
	public static final String DATA_ACCESS_TIME = "data_access_time";
	public static final short TRANSPORT = 0;
	public static final short BUSINESS_LOGIC = 1;
	public static final short DATA_ACCESS = 2;
private AS2ArmService() {
	super();
}
public static void armEnd() {

	//transaction finished
	try {
		Hashtable<String,Object> theServiceStatistics = (Hashtable) _servicesStatistics.get((String) _currentTransactionName.get(Thread.currentThread().getName()));
		if (theServiceStatistics != null) {
			Long aServiceCount = (Long) theServiceStatistics.get(SERVICE_COUNT);
			aServiceCount = new Long(aServiceCount.longValue() + 1);
			theServiceStatistics.put(SERVICE_COUNT, aServiceCount);
		}
		_transportStartTime.remove(Thread.currentThread().getName());
		_businessLogicStartTime.remove(Thread.currentThread().getName());
		_dataAccessStartTime.remove(Thread.currentThread().getName());
		_currentTransactionName.remove(Thread.currentThread().getName());
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.I," armEnd: "+e);
	}
}
public static void armGetId(AS2Record req) {
	/**
	 * Transaction is probably called first time.
	 * Put the transaction name for the current thread.
	 **/

	try {
		StringBuffer aTxn = new StringBuffer();
		aTxn.append(req.getRemoteObject());
		aTxn.append(".");
		aTxn.append(req.getRemoteMethod());
		_currentTransactionName.put(Thread.currentThread().getName(), aTxn.toString());
		Hashtable theServiceStatistics = (Hashtable) _servicesStatistics.get(aTxn.toString());
		if (theServiceStatistics == null) {
			theServiceStatistics = new Hashtable();
			theServiceStatistics.put(SERVICE_NAME, aTxn.toString());
			theServiceStatistics.put(SERVICE_COUNT, new Long(0));
			theServiceStatistics.put(TRANSPORT_TIME, new Long(0));
			theServiceStatistics.put(BUSINESS_LOGIC_TIME, new Long(0));
			theServiceStatistics.put(DATA_ACCESS_TIME, new Long(0));
			_servicesStatistics.put(aTxn.toString(), theServiceStatistics);
		}
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.I," armGetId: "+e);
	}
}
public static void armStart(short aLayer){

	try{
		if(aLayer == TRANSPORT){
			_transportStartTime.put(Thread.currentThread().getName(), new Long(System.currentTimeMillis()));
		}else if(aLayer == BUSINESS_LOGIC){
			_businessLogicStartTime.put(Thread.currentThread().getName(), new Long(System.currentTimeMillis()));
		}else if(aLayer == DATA_ACCESS){
			_dataAccessStartTime.put(Thread.currentThread().getName(), new Long(System.currentTimeMillis()));
		}
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.I," armStart: "+e);
	}

}
public static void armStop(short aLayer){

	Long aStart = null;
	long bStart = 0;
	String aTime = "";

	try{
		if(aLayer == TRANSPORT){
			aStart = (Long)_transportStartTime.get(Thread.currentThread().getName());
			_transportStartTime.remove(Thread.currentThread().getName());
			aTime = TRANSPORT_TIME;
		}else if(aLayer == BUSINESS_LOGIC){
			aStart = (Long)_businessLogicStartTime.get(Thread.currentThread().getName());
			_businessLogicStartTime.remove(Thread.currentThread().getName());
			aTime = BUSINESS_LOGIC_TIME;
		}else if(aLayer == DATA_ACCESS){
			aStart = (Long)_dataAccessStartTime.get(Thread.currentThread().getName());
			_dataAccessStartTime.remove(Thread.currentThread().getName());
			aTime = DATA_ACCESS_TIME;
		}
	
		bStart = aStart.longValue();	
		
		Hashtable theServiceStatistics = (Hashtable) _servicesStatistics.get((String)_currentTransactionName.get(Thread.currentThread().getName()));

		if(theServiceStatistics!=null){
			Long aTotal = (Long)theServiceStatistics.get(aTime);
			long bTotal = aTotal.longValue();
			theServiceStatistics.put(aTime, new Long(bTotal+(System.currentTimeMillis()-bStart)));
		}
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.I," armStop: "+e);
	}
}
public static AS2ArmService getInstance(){
	if(_instance == null)
		_instance = new AS2ArmService();
	return _instance;
}
public static void main(String[] args) throws Exception{

AS2Record r1 = new AS2Record();
r1.setRemoteMethod("getAllDefaulta");
r1.setRemoteObject("Abc");

AS2ArmService.armGetId(r1);

AS2ArmService.armStart(TRANSPORT);
Thread.sleep(100);
AS2ArmService.armStop(TRANSPORT);
AS2ArmService.armStart(TRANSPORT);
Thread.sleep(100);
AS2ArmService.armStop(TRANSPORT);

AS2ArmService.armStart(BUSINESS_LOGIC);
Thread.sleep(500);
AS2ArmService.armStop(BUSINESS_LOGIC);
AS2ArmService.armStart(TRANSPORT);
Thread.sleep(500);
AS2ArmService.armStop(TRANSPORT);
//2
AS2ArmService.armEnd();

AS2ArmService.armGetId(r1);
AS2ArmService.armStart(TRANSPORT);
Thread.sleep(100);
AS2ArmService.armStop(TRANSPORT);
AS2ArmService.armStart(TRANSPORT);
Thread.sleep(100);
AS2ArmService.armStop(TRANSPORT);

AS2ArmService.armStart(BUSINESS_LOGIC);
Thread.sleep(500);
AS2ArmService.armStop(BUSINESS_LOGIC);
AS2ArmService.armStart(TRANSPORT);
Thread.sleep(500);
AS2ArmService.armStop(TRANSPORT);
AS2ArmService.armEnd();	

AS2ArmService.printStatistics(r1);
//J2EEArmService.printStatisticsUsingInputData(mServicesStatistics);
	
}
public static AS2Record printStatistics(AS2Record req){
	AS2Record res = new AS2Record();
	AS2Trace.traceStringOut(AS2Trace.W,"-- ***** -- B e g i n  S t a t i s t i c s -- ***** --");	
	
	try{
		res.set("@@statistics", _servicesStatistics);
		printStatisticsUsingInputData(_servicesStatistics);
	
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.W, " Problem printing statistics");
	}
		
	AS2Trace.traceStringOut(AS2Trace.W,"-- ***** -- E n d  S t a t i s t i c s  -- ***** --");
	return res;	
}
public static void printStatisticsUsingInputData(Hashtable aServicesStatistics){

	AS2Trace.traceStringOut(AS2Trace.W, "-- ***** -- D e t a i l s  B e g i n  S t a t i s t i c s -- ***** --");	
	
	try{
		long sc_total = 0;
		long tt_total = 0;
		long blt_total = 0;
		long dat_total = 0;
		
		Enumeration E = aServicesStatistics.elements(); 

		while(E.hasMoreElements()){
			Hashtable aElement = (Hashtable)E.nextElement();
		
			Long aServiceCount = (Long)aElement.get(SERVICE_COUNT);
			long sc = aServiceCount.longValue();
			sc_total = sc_total + sc;
			
			Long aTransportTime = (Long)aElement.get(TRANSPORT_TIME);
			long tt = aTransportTime.longValue();
			tt_total = tt_total + tt;
			
			Long aBusinessLogicTime = (Long)aElement.get(BUSINESS_LOGIC_TIME);
			long blt = aBusinessLogicTime.longValue();
			blt_total = blt_total + blt;
			
			Long aDataAccessTime = (Long)aElement.get(DATA_ACCESS_TIME);
			long dat = aDataAccessTime.longValue();
			dat_total = dat_total + dat;
			
			if(sc!=0){	
				AS2Trace.traceStringOut(AS2Trace.W, "== Service Name    		: "+ aElement.get(SERVICE_NAME));
				AS2Trace.traceStringOut(AS2Trace.W, "-- Service Count   		: "+ aElement.get(SERVICE_COUNT));
				AS2Trace.traceStringOut(AS2Trace.W, "-- Transport Time  		: "+ aElement.get(TRANSPORT_TIME) +
				" -- Transport Avg Time  	: "+ (tt/sc));
				AS2Trace.traceStringOut(AS2Trace.W, "-- Business Logic Time	: "+ aElement.get(BUSINESS_LOGIC_TIME)+ 
				" -- Business Logic Avg Time: "+ (blt/sc));
				AS2Trace.traceStringOut(AS2Trace.W, "-- Data Access Time   	: "+ aElement.get(DATA_ACCESS_TIME) +
				" -- Data Access Avg Time   : "+ (dat/sc));
			}
		}
		
		AS2Trace.traceStringOut(AS2Trace.W, "-- ***** -- D e t a i l s  E n d  S t a t i s t i c s  -- ***** --");
		
		if(sc_total!=0){
			AS2Trace.traceStringOut(AS2Trace.W, "\n-- ***** -- T O T A L  S t a t i s t i c s -- ***** --");	
			AS2Trace.traceStringOut(AS2Trace.W, "-- Total Service Count   		: "+ sc_total);
			AS2Trace.traceStringOut(AS2Trace.W, "-- Total Transport Time  		: "+ tt_total +
			" -- Total Transport Avg Time  	: "+ (tt_total/sc_total));
			AS2Trace.traceStringOut(AS2Trace.W, "-- Total Business Logic Time	: "+ blt_total+ 
			" -- Total Business Logic Avg Time: "+ (blt_total/sc_total));
			AS2Trace.traceStringOut(AS2Trace.W, "-- Total Data Access Time   	: "+ dat_total +
			" -- Total Data Access Avg Time   : "+ (dat_total/sc_total));
			AS2Trace.traceStringOut(AS2Trace.W, "-- ***** -- T O T A L  E n d  S t a t i s t i c s  -- ***** --");
		}
	
	}catch(Exception e){
		AS2Trace.trace(AS2Trace.W, " Problem printing statistics");
	}
}
}
