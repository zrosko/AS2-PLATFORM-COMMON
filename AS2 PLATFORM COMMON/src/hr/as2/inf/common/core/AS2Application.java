package hr.as2.inf.common.core;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *  @author zrosko@gmail.com
 *	@description	
 *	Pattern: Application Type
 *	Mohamed E. Fayad page: 258
 *	Problem : How does a development team properly segregate the different kinds 
 *	of applications in the system and capture the unique information and
 *	behavior associated with each?
 *	Solution: Create one or more levels of application subclasses to capture the 
 *	attributes and behavior associated with each type of program in the system.
 *	In a three-tier model. a project might define a client application that runs
 *	on the desktop and a server application that lives at the middle or third tier.
 *
 *	There could be more than one object (application) running at the sam time within 
 *	the same process.
 */
public abstract class AS2Application {
	// private static Log _log = LogFactory.getLog(AS2Application.class);
	protected static String _encoding = null;
	private static boolean _onServer = true;
	protected String _APPLLICATION = null;
	protected String _user = null;

	/**
	 * If we are in a server.
	 */
	public static boolean onServer() {
		return _onServer;
	}

	public static boolean onClient() {
		return !onServer();
	}

	public static void setOnClient() {
		_onServer = false;
	}

	protected void setApplication(String value) {
		_APPLLICATION = value;
	}

	protected void setUser(String value) {
		_user = value;
	}

	protected abstract void init(String[] args);

	protected abstract void run();

	protected abstract void shoutDown();

	// TODO
	public static void setLogLevel(Level value) {
		// _log.info("Log leve = "+value);
		Logger rootLogger = Logger.getLogger("");
		Handler[] rootHandler = rootLogger.getHandlers();
		for (int i = 0; i < rootHandler.length; i++) {
			rootHandler[i].setLevel(value);
		}
	}

	/**
	 * To use for XML encoding and for web requests and responses encoding.
	 */
	public static String getEncoding() {
		if (_encoding == null) {
			if ("OS/400".equalsIgnoreCase(System.getProperty("os.name"))
					&& System.getProperties().containsKey("was.install.root"))
				_encoding = "ISO-8859-1";
			else {
				_encoding = System.getProperty("ibm.system.encoding");
				if (_encoding == null)
					_encoding = System.getProperty("file.encoding");
				if (_encoding == null)
					_encoding = "ISO-8859-1";
				else if ("Cp1252".equalsIgnoreCase(_encoding))
					_encoding = "ISO-8859-1";
				else if ("utf8".equalsIgnoreCase(_encoding))
					_encoding = "UTF-8";
				else if ("iso8859-1".equalsIgnoreCase(_encoding))
					_encoding = "ISO-8859-1";
				else if ("MS874".equalsIgnoreCase(_encoding))
					_encoding = "ISO-8859-11";
			}
		}
		return _encoding;
	}
}
