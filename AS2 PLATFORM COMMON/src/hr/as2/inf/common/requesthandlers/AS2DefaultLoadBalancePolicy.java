package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.types.AS2Queue;

/**
 * Default Load Balance policy - always return the first J2EETransport in the
 * Queue
 */
public class AS2DefaultLoadBalancePolicy implements AS2LoadBalancePolicy {
	public AS2DefaultLoadBalancePolicy() {
		super();
	}

	/**
	 * Return the first element in the queue.
	 */
	public AS2RequestHandler selectTransport(AS2Queue transports)
			throws AS2Exception {
		try {
			AS2RequestHandler transport = null;
			if (transports.elementAt(0) != null)
				transport = (AS2RequestHandler) transports.elementAt(0);
			return transport;
		} catch (Exception e) {
			return null;
			// throw new MMTransportException("508");
		}
	}
}
