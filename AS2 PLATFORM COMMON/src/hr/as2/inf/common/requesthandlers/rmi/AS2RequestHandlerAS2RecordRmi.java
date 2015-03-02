package hr.as2.inf.common.requesthandlers.rmi;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AS2RequestHandlerAS2RecordRmi extends Remote {
	public Object serviceAS2Record(AS2Record request) throws RemoteException, AS2Exception, Exception;
}
