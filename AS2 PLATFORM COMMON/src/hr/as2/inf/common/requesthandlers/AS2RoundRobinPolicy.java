package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.types.AS2Queue;
/**
 * A crude Round Robin Load balance policy example.
 */
public class AS2RoundRobinPolicy implements AS2LoadBalancePolicy {
public AS2RoundRobinPolicy() {
	super();
}
public AS2RequestHandler selectTransport(AS2Queue transports)
	throws AS2Exception {
	try {
		Object transport = transports.remove();
		if (transport != null) {
			transports.append(transport);
			return (AS2RequestHandler) transport;
		} else
			return null;
	} catch (Exception e) {
		return null;
		//throw new MMTransportException("508");
	}
}
}
