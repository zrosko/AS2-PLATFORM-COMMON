package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.exceptions.AS2TransportException;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.types.AS2Queue;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * J2EETransportManager handles all client requests and dispatch them to
 * J2EETransportClient, load balancing/fail over is supported.
 */
public class AS2ClientRequestDispatcher {
	private static AS2ClientRequestDispatcher transportManager = null;
	private AS2LoadBalancePolicy policy = null;
	private AS2Queue transportQueue = new AS2Queue();
	private AS2Queue transportQueueBackup = new AS2Queue();
	private boolean readyToServe = false;

	public AS2ClientRequestDispatcher() {
		super();
	}

	public AS2RequestHandler addTransport(String transportType, String url) {
		AS2RequestHandler transport = AS2RequestHandlerFactory.getInstance()
				.createTransport(transportType, url);
		transportQueue.append(transport);
		transportQueueBackup.append(transport);
		return transport;
	}

	public AS2RequestHandler addTransport(String transportType, String url,
			int weight, int tag) {
		AS2RequestHandler transport = AS2RequestHandlerFactory.getInstance()
				.createTransport(transportType, url, weight, tag);
		transportQueue.append(transport);
		transportQueueBackup.append(transport);
		return transport;
	}

	public AS2RequestHandler addTransport(String transportType, String protocol,
			String host, int port, String contextPath, String fileName,
			int weight, int tag) {
		AS2RequestHandler transport = AS2RequestHandlerFactory.getInstance()
				.createTransport(transportType, protocol, host, port,
						contextPath, fileName, weight, tag);
		transportQueue.append(transport);
		transportQueueBackup.append(transport);
		return transport;
	}

	public static AS2ClientRequestDispatcher getInstance() {
		if (transportManager == null) {
			transportManager = new AS2ClientRequestDispatcher();
		}
		return transportManager;
	}

	public AS2LoadBalancePolicy getPolicy() {
		return policy;
	}

	public void init() {
		init(AS2Context.TRANSPORT, AS2Context.getInstance().HOST, null, null,
				"hr.as2.inf.common.requesthandlers.AS2DefaultLoadBalancePolicy");
	}

	public void init(String transportTypeList, String serverHostList) {
		init(transportTypeList, serverHostList, null, null,
				"hr.as2.inf.common.requesthandlers.AS2DefaultLoadBalancePolicy");
	}

	public void init(String transportTypeList, String serverHostList,
			String policy) {
		init(transportTypeList, serverHostList, null, null, policy);
	}

	/**
	 * initialize J2EETransportManager, parse/create Transports
	 *
	 * @param transportTypeList
	 *            String - A list of transports to be added to transportQueue
	 * @param serverHostList
	 *            String - A list of server Hosts corresponding to the
	 *            transportList
	 * @param weightList
	 *            String - A list of weights (used for load balancing)
	 *            corresponding to the transportList
	 * @param weightList
	 *            String - A list of tags (used for addtional transport info)
	 *            corresponding to the transportList
	 * @param policy
	 *            - load balance policy to use.
	 */
	public void init(String transportTypeList, String serverHostList,
			String weightList, String tagList, String policy) {
		StringTokenizer stTransportTypeList = null;
		StringTokenizer stServerHostList = null;
		Vector<String> serverHosts = new Vector<String>();
		StringTokenizer stWeightList = null;
		Vector<String> weightLists = new Vector<String>();
		StringTokenizer stTagList = null;
		Vector<String> tagLists = new Vector<String>();

		if (transportTypeList == null)
			return;

		if (serverHostList != null) {
			stServerHostList = new StringTokenizer(serverHostList, ",");
			serverHosts = new Vector<String>();
			while (stServerHostList.hasMoreTokens()) {
				String serverHost = stServerHostList.nextToken();
				serverHosts.addElement(serverHost);
			}
		}

		if (weightList != null) {
			stWeightList = new StringTokenizer(weightList, ",");
			while (stWeightList.hasMoreTokens()) {
				String weight = stWeightList.nextToken();
				weightLists.addElement(weight);
			}
		}
		if (tagList != null) {
			stTagList = new StringTokenizer(tagList, ",");
			tagLists = new Vector<String>();
			while (stTagList.hasMoreTokens()) {
				String tag = stTagList.nextToken();
				tagLists.addElement(tag);
			}
		}

		stTransportTypeList = new StringTokenizer(transportTypeList, ",");
		int i = 0;
		int hostToUseIndex = 0;
		int weightToUseIndex = 0;
		int tagToUseIndex = 0;
		String hostToUse = null;
		int weightToUse = 0;
		int tagToUse = 0;

		while (stTransportTypeList.hasMoreTokens()) {
			String transport = stTransportTypeList.nextToken();
			if (i <= serverHosts.size() - 1)
				hostToUseIndex = i;
			else
				hostToUseIndex = serverHosts.size() - 1;

			if (i <= weightLists.size() - 1)
				weightToUseIndex = i;
			else
				weightToUseIndex = weightLists.size() - 1;

			if (i <= tagLists.size() - 1)
				tagToUseIndex = i;
			else
				tagToUseIndex = tagLists.size() - 1;

			if (hostToUseIndex < 0)
				hostToUse = "127.0.0.1";
			else
				hostToUse = serverHosts.elementAt(hostToUseIndex).toString();

			if (weightToUseIndex < 0)
				weightToUse = 0;
			else {
				try {
					weightToUse = Integer.parseInt(serverHosts.elementAt(
							weightToUseIndex).toString());
				} catch (Exception ex) {
				}
			}

			if (tagToUseIndex < 0)
				tagToUse = 0;
			else {
				try {
					tagToUse = Integer.parseInt(serverHosts.elementAt(
							tagToUseIndex).toString());
				} catch (Exception ex) {
				}
			}

			addTransport(transport, hostToUse, weightToUse, tagToUse);
			i++;
		}
		readyToServe = true;

		try {
			AS2LoadBalancePolicy policyToUse = (AS2LoadBalancePolicy) Class
					.forName(policy).newInstance();
			setPolicy(policyToUse);
		} catch (Exception e) {
			AS2Trace.trace(AS2Trace.D, "Unable to load load balance policy - "
					+ policy);
		}

	}

	public static void main(String[] args) {
		getInstance().init("RMI,HTTP,EJB,LOCAL",
				"http://127.0.0.1:8000/TestApp/TestT,www.adriacomsoftware.com");
	}

	/**
	 * Restore the transportQueue.
	 */
	public void reset() {
		transportQueue = (AS2Queue) transportQueueBackup.clone();
	}

	/**
	 * Send the request to the server.
	 */
	public synchronized AS2Record send(AS2Record request) throws AS2Exception,
			Exception {
		AS2Queue tempTransportQueue = new AS2Queue();
		AS2Record mmrp = null;
		AS2TransportException j2e = new AS2TransportException("512");
		boolean success = false;
		boolean moreTransport = true;
		if (!readyToServe)
			init();
		// if one transport failed, use the next transport
		while ((!success) && (moreTransport)) {
			try {
				// use load balancing policy to select transports
				AS2RequestHandler transport = getPolicy().selectTransport(
						transportQueue);
				if (transport == null) {
					moreTransport = false;
				} else {
					mmrp = transport.send(request);
					success = true;
				}
			} catch (AS2TransportException e) {
				Object badTransport = transportQueue.remove();
				tempTransportQueue.append(badTransport);
				j2e.addCauseException(e);
				success = false;
			} catch (AS2Exception e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
		transportQueue.appendQueue(tempTransportQueue);
		if (!success)
			throw j2e;
		return mmrp;
	}

	public void setPolicy(AS2LoadBalancePolicy newPolicy) {
		policy = newPolicy;
	}
}
