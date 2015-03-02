package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.core.AS2Timer;
/**
 * This class is used to call a transport adapter in a
 * new thread.
 */
import hr.as2.inf.common.data.AS2Record;

public class AS2RequestHandlerRunner implements Runnable {
	private AS2ClientRequestHandler _transport = null;
	private AS2Record _request = null;
	private AS2Timer _timer = null;
	private Object _response = null;

	public AS2RequestHandlerRunner(AS2ClientRequestHandler transport,
			AS2Record request, AS2Timer timer) {
		this._transport = transport;
		this._request = request;
		this._timer = timer;
	}

	public Object getResponse() {
		return _response;
	}

	public void run() {
		try {
			_response = _transport.sendToServer(_request);
		} catch (Exception e) {
			_response = e;
		}
		_timer.reset();
	}
}
