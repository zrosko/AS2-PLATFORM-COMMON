package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.core.AS2Constants;
import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.core.AS2Helper;
import hr.as2.inf.common.core.AS2Timer;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.util.ResourceBundle;

/*  Strategy and Template patterns are similar in that they allow different implementations for a
 fixed set of behaviors. Their intents are different, however.
 Strategy is used to allow different implementations of an algorithm, or operation, to be selected
 dynamically at run time. Typically, any common behavior is implemented in an abstract class
 and concrete subclasses provide the behavior that differs. The client is generally aware of the
 different strategies that are available and can choose between them.
 For example, an abstract class, Sensor, could define taking measurements and concrete subclasses 
 would be required to implement different techniques: one might provide a running
 average, another might provide an instantaneous measurement, and yet another might hold a
 peak (or low) value for some period of time. The intention of the Template pattern is not to 
 allow behavior to be implemented in different
 ways, as in Strategy, but rather to ensure that certain behaviors are implemented. In other
 words, where the focus of Strategy is to allow variety, the focus of Template is to enforce
 consistency.
 The Template pattern is implemented as an abstract class and it is often used to provide a
 blueprint or an outline for concrete subclasses. Sometimes this is used to implement hooks in
 a system, such as an application framework.
 */
public abstract class AS2ClientRequestHandler implements AS2RequestHandler {
	private String _HOST;
	private String protocol;
	private String contextPath;
	private int weight = 0;
	private int tag;
	private String fileName;
	private String transportName;
	private int port = AS2RequestHandler.NOT_SPECIFIED;

	public String getAbsoluteURL() {
		StringBuffer url = new StringBuffer();
		if ((getProtocol() != null) && (getProtocol().length() > 0)) {
			url.append(getProtocol());
			url.append("://");
		}
		if ((getHost() != null) && (getHost().length() > 0)) {
			url.append(getHost());
		}
		if ((getPort() != 80) && (getPort() != AS2RequestHandler.NOT_SPECIFIED)) {
			url.append(":");
			url.append(getPort());
		}
		if ((getContextPath() != null) && (getContextPath().length() > 0)) {
			url.append("/");
			url.append(getContextPath());
		}
		if ((getFileName() != null) && (getFileName().length() > 0)) {
			url.append("/");
			url.append(getFileName());
		}
		return url.toString();
	}

	public java.lang.String getContextPath() {
		return contextPath;
	}

	public String getContextPathFileName() {
		StringBuffer url = new StringBuffer();
		if ((getContextPath() != null) && (getContextPath().length() > 0)) {
			url.append("/");
			url.append(getContextPath());
		}
		if ((getFileName() != null) && (getFileName().length() > 0)) {
			url.append("/");
			url.append(getFileName());
		}
		return url.toString();
	}

	public java.lang.String getFileName() {
		return fileName;
	}

	public String getHost() {
		return _HOST;
	}

	public int getPort() {
		return port;
	}

	public java.lang.String getProtocol() {
		return protocol;
	}

	public String getProtocolHostPort() {
		StringBuffer url = new StringBuffer();
		if ((getProtocol() != null) && (getProtocol().length() > 0)) {
			url.append(getProtocol());
			url.append("://");
		}
		if ((getHost() != null) && (getHost().length() > 0)) {
			url.append(getHost());
		}
		if ((getPort() != 80) && (getPort() != AS2RequestHandler.NOT_SPECIFIED)) {
			url.append(":");
			url.append(getPort());
		}
		return url.toString();
	}

	public int getTag() {
		return tag;
	}

	public java.lang.String getTransportName() {
		return transportName;
	}

	public int getWeight() {
		return weight;
	}

	public static String processErrorCode(int err) {
		String errorCode = "";

		if (err > 0 && err < 100)
			errorCode = "Application problem ! call support ! ";
		if (err >= 100 && err < 150)
			errorCode = "Application Business Logic problem ! call support ! ";
		if (err >= 150 && err < 200)
			errorCode = "Database problem ! call support ! ";
		if (err >= 200 && err < 220)
			errorCode = "Application communication problem ! call support ! ";
		if (err >= 220 && err < 300)
			errorCode = "Database connection problem ! call support ! ";
		if (err >= 300 && err < 500)
			errorCode = "Server problem ! call support ! ";
		if (err >= 500 && err < 550)
			errorCode = "Server not ready ! call support ! ";
		if (err >= 550 && err < 600)
			errorCode = "Security problem ! call support ! ";

		return errorCode;
	}

	@SuppressWarnings("deprecation")
	public AS2Record send(AS2Record request) throws AS2Exception, Exception {
		AS2User aUser = (AS2User) request.getAsObject(AS2Constants.USER_OBJ);
		if (aUser == null) {
			aUser = AS2UserFactory.getInstance().getCurrentUser();
		}
		request.set(AS2Constants.USER_OBJ, aUser);

		AS2Timer timer = new AS2Timer(AS2Context.getInstance().TIMEOUT);
		AS2RequestHandlerRunner runner = new AS2RequestHandlerRunner(this, request,timer);
		Thread runnerTread = new Thread(runner);
		runnerTread.start();
		timer.set();
		runnerTread.stop();
		if (timer.isTimeExpired()) {
			AS2Exception e = new AS2Exception("202");
			e.setErrorDescription("Timeout waiting for server ! ");
			throw e;
		}
		Object res = runner.getResponse();
		if (res instanceof Exception) {
			// add message to exception
			if (res instanceof AS2Exception) {
				AS2Exception j2e = (AS2Exception) res;
				ResourceBundle messages;
				String errorCode = j2e.getErrorCode();
				int err = 0;
				try {
					err = Integer.valueOf(errorCode).intValue();
				} catch (Exception e) {
					AS2Trace.trace(AS2Trace.W,"Error code is invalid");
				}
				errorCode = processErrorCode(err);
				messages = ResourceBundle.getBundle(j2e.getResourceBundle());
				j2e.setErrorDescription(errorCode+ messages.getString(j2e.getErrorCode()));
			}
			throw (Exception) res;
		}
		return (AS2Record) res;
	}

	protected synchronized AS2Record sendToServer(AS2Record request)
			throws AS2Exception, Exception {
		return null;
	}

	public void setContextPath(java.lang.String newContextPath) {
		contextPath = newContextPath;
	}

	public void setFileName(java.lang.String newFileName) {
		fileName = newFileName;
	}

	public void setHost(String value) {
		if (value != null) {
			_HOST = value;
			AS2Helper.setHost(value);
		}
	}

	public void setPort(int newPort) {
		port = newPort;
	}

	public void setProtocol(java.lang.String newProtocol) {
		protocol = newProtocol;
	}

	public void setTag(int newTag) {
		tag = newTag;
	}

	public void setTransportName(java.lang.String newTransportName) {
		transportName = newTransportName;
	}

	public void setWeight(int newWeight) {
		weight = newWeight;
	}
}
