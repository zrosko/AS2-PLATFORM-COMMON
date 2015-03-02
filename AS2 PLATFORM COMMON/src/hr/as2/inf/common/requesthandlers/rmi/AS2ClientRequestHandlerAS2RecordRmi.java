package hr.as2.inf.common.requesthandlers.rmi;

import hr.as2.inf.common.core.AS2Constants;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.exceptions.AS2TransportException;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.requesthandlers.AS2RequestHandler;
import hr.as2.inf.common.requesthandlers.AS2ClientRequestHandler;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AS2ClientRequestHandlerAS2RecordRmi extends AS2ClientRequestHandler {
	AS2RequestHandlerAS2RecordRmi transport = null;
	public int getPort() {
		int port=super.getPort();
		if (port==AS2RequestHandler.NOT_SPECIFIED)
			// Fallback choice is the default registry port (1099)
			port=Registry.REGISTRY_PORT;
		return port;
	}
	public String getRemoteTransportName() {
		// second name choice is the getFileName
		String name = getFileName();
		if ((name!=null) && (name.length()>0))
			return name;
		
		// second name choice is the "RMI_REGISTRY_NAME" init parameter
		//name = J2EEContext.getInstance().getProperties().getProperty("RMI_REGISTRY_NAME");
		if (name != null)
			return name;

		// Fallback choice is the name of this class
		return "AS2ServerRequestHandlerRmi";
	}
	public Object sendToRMIServer(AS2Record request) throws AS2Exception, Exception {
		try {
			if (transport == null) {
				Registry registry = LocateRegistry.getRegistry(getHost(), getPort());
				transport = (AS2RequestHandlerAS2RecordRmi) registry.lookup(getRemoteTransportName());
			}
			return transport.serviceAS2Record(request);
		} catch (ClassCastException e) {
			AS2Trace.trace(AS2Trace.E, "Retrieved object was not a J2EETransportServerRMI: " + e.getMessage());
		} catch (NotBoundException e) {
			AS2Trace.trace(AS2Trace.E, getRemoteTransportName() + " not bound: " + e.getMessage());
		} catch (RemoteException e) {
			AS2Trace.trace(AS2Trace.E, "RMI Transport remote exception: " + e.getMessage());
		} catch (Exception e){
			AS2Trace.trace(AS2Trace.E, "Unknown RMI Transport remote exception: " + e.getMessage());
		}
		transport = null;
		throw new AS2TransportException("514");
	}
	public AS2Record sendToServer(AS2Record request) throws AS2Exception, Exception {
		Object result=null;
		try {
			Object aUser = request.getProperty(AS2Constants.USER_OBJ);
			if (aUser == null)
				request.set(AS2Constants.USER_OBJ, AS2UserFactory.getInstance().getCurrentUser());
			result = sendToRMIServer(request);
			if (result == null) {
			    AS2TransportException e = new AS2TransportException("502");
				e.setErrorDescription("Server not ready ! call support ! ");
				throw e;
			}
			if (result instanceof AS2Exception)
				throw (AS2Exception) result;
			else if (result instanceof Exception)
				throw (Exception) result;
			else return (AS2Record)result;
			
		} catch (AS2Exception e) {
			//ResourceBundle messages;
			String errorCode = e.getErrorCode();
			int err = 0;
			try{
				err = Integer.valueOf(errorCode).intValue();
			}catch(Exception ex){
				AS2Trace.trace(AS2Trace.W, "AS2Exception error code is invalid");
			}
			errorCode = processErrorCode(err);

			//messages = ResourceBundle.getBundle(e.getResourceBundle());
			//e.setErrorDescription(errorCode + messages.getString(e.getErrorCode()));
			
			throw e;
		}catch(Exception e){
		    AS2TransportException ex = new AS2TransportException("510");
			ex.setErrorDescription("Application problem ! Call support !");
			ex.addCauseException(e);
			throw ex;
			
		}
	}
}
