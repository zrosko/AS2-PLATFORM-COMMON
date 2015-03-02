package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.types.AS2Queue;
/**
 * Load balance / Fail over policy
 */
public interface AS2LoadBalancePolicy {
/**
 * Defines policy to select a handler from a transport Queue.
 */
AS2RequestHandler selectTransport(AS2Queue transports)
	throws AS2Exception;
}
