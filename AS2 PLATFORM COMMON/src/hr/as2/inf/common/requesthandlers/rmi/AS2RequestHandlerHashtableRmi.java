package hr.as2.inf.common.requesthandlers.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
/**
 * RMI server interface, to be used by older versions of client.
 * This interface is used by clients sending Hashtable instead of
 * using old value object (J2EEValueObject) classes. New applications are using AS2Record
 * value object.
 * The client side need to have included this interface (AS2RequestHandlerHashtableRmi), 
 * Stub class (AS2ServerRequestHandlerRmi_Stub) and AS2Exception in its class path.
 **/
public interface AS2RequestHandlerHashtableRmi extends Remote {
	@SuppressWarnings("rawtypes")
	public Object serviceHashtable(Hashtable request) throws RemoteException, Exception;
}
