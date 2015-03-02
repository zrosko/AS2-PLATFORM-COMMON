package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.core.AS2Context;

public final class AS2RequestHandlerFactory {
 	private static AS2RequestHandlerFactory _instance = null;	
private AS2RequestHandlerFactory() {
	AS2Context.setSingletonReference(this);	
}
/**
 * Any client/server communication has to go through
 * a Transport protocol. Creates different Transport based on 
 * parameter passed.
 * @param String transport - type of transport to create
 * @param String url - URL used if HTTP transport is used, 
 * could use to carry other information for other type of transports.
 */
public  AS2RequestHandler createTransport(String transport, String url) {
	return createTransport(transport,url,0,0);		
}	
/**
 * Any client/server communication has to go through
 * a Transport protocol. Creates different Transport based 
 * on parameter passed.
 * @param String transport - type of transport to create
 * @param String url - URL used if HTTP transport is used, 
 * could use to carry other information for other type of transports
 * @param String weight - weight is used for loadbalance, 
 * the higher the weight for the transport, more often it will be used 
 * in a load balance situation.
 * @param String tag - could be used to carry addtional information 
 * needed by the transport.
 */
public AS2RequestHandler createTransport(String transport, String url, int weight, int tag) {
	String protocol = "HTTP";
	String host = "127.0.0.1";
	int port = AS2RequestHandler.NOT_SPECIFIED;
	String contextPath = "";
	String fileName = "";
	boolean done = false;
	int endIndex = 0;
	//parsing the URL to get protocol, host, port, contextPath, and fileName
	int startIndex = url.indexOf("://");
	if (startIndex != -1) {
		protocol = url.substring(0, startIndex);
		url = url.substring(startIndex + 3);
	}
	startIndex = url.indexOf(':');
	endIndex = url.indexOf('/');
	if (endIndex == -1) {
		endIndex = url.length();
		done = true;
	}
	if (startIndex != -1) {
		try {
			port = Integer.parseInt(url.substring(startIndex + 1, endIndex));
		} catch (Exception e) {
		}
		host = url.substring(0, startIndex);
	} else {
		host = url.substring(0, endIndex);
	}
	url = url.substring(endIndex);
	if (!done) {
		endIndex = url.length();
		startIndex = url.lastIndexOf('/', endIndex);
		if (startIndex != -1) {
			fileName = url.substring(startIndex + 1, endIndex);
			try {
				contextPath = url.substring(1, startIndex);
			} catch (Exception ex) {
			}
		} else {
			fileName = url.substring(1, endIndex);
		}
	}
	return createTransport(transport, protocol, host, port, contextPath, fileName, weight, tag);
}
/**
 * Any client/server communication has to go through
 * a Transport protocol. Creates different Transport based on 
 * parameter passed.
 * @param String transport - type of transport to create
 * @param String protocol - protocol part of URL used if HTTP transport 
 * is used, could use to carry other information for other type of 
 * transports.
 * @param String host - host part of URL used if HTTP transport is used, 
 * could use to carry other information for other type of transports.
 * @param String port - port part of URL used if HTTP transport is used, 
 * could use to carry other information for other type of transports.
 * @param String contextPath - contextPath part of URL used if HTTP 
 * transport is used, could use to carry other information for other 
 * type of transports.
 * @param String fileName - fileName part of URL used if HTTP transport 
 * is used, could use to carry other information for other type of 
 * transports.
 * @param String weight - weight is used for loadbalance, 
 * the higher the weight for the transport, more often it 
 * will be used in a load balance situation.
 * @param String tag - could be used to carry addtional information 
 * needed by the transport.
 */
public  AS2RequestHandler createTransport(String transport, String protocol, String host, int port,  String contextPath, String fileName,  int weight, int tag) {
	AS2RequestHandler j2eeTransport=null;
	//if the transport passed are well known transport, create it
/*	if(transport.equals("HTTP"))
		//J2EETrace.trace("J2EETransportFactory","HTTP Transport not supported");
		//TODO j2eeTransport = new hr.adriacomsoftware.inf.common.transport.http.J2EETransportClientHttp();
	else if(transport.equals("LOCAL"))
		//J2EETrace.trace("J2EETransportFactory","HTTP Transport not supported");
		//TODO j2eeTransport = new hr.adriacomsoftware.inf.common.transport.local.J2EETransportClientLocal();	
	else if(transport.equals("IIOP"))
		J2EETrace.trace("J2EETransportFactory","IIOP Transport not supported");
	//else if(transport.equals("EJB"))
		//j2eeTransport = new hr.banksoft.inf.common.transport.ejb.MMTransportClientEJB();
	else if(transport.equals("RMI"))
		//J2EETrace.trace("J2EETransportFactory","RMI Transport not supported");
		//TODO j2eeTransport = new hr.adriacomsoftware.inf.common.transport.rmi.J2EETransportClientRMI();	
	else if(transport.equals("SOCKET"))
		J2EETrace.trace("J2EETransportFactory","SOCKET Transport not supported");		
	else if(transport.equals("WAP"))
		J2EETrace.trace("J2EETransportFactory","WAP Transport not supported");
	//else try the create j2eeTransport by using Class.forName
	else{
		try{
			j2eeTransport=(J2EETransport)Class.forName(transport).newInstance();
		}catch (Exception e){
			J2EETrace.trace(J2EETrace.E, "Exception happend while loading '"+transport+"' class, J2EETransport specified could not be loaded.");
		}
	}
	if (j2eeTransport!=null){
		if (protocol!=null)
			j2eeTransport.setProtocol(protocol);
		if (host!=null)
			j2eeTransport.setHost(host);
		j2eeTransport.setPort(port);
		if (contextPath!=null)
			j2eeTransport.setContextPath(contextPath);
		if (fileName!=null)
			j2eeTransport.setFileName(fileName);
		j2eeTransport.setWeight(weight);
		j2eeTransport.setTag(tag);
	}*/
	return j2eeTransport;
}	
public static AS2RequestHandlerFactory getInstance(){
	if(_instance == null)
		_instance = new AS2RequestHandlerFactory();	
	return _instance;
}
public static void main(String[] args) {
	getInstance().createTransport("RMI","127.0.0.1", 0,0);}
}
