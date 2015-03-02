package hr.as2.inf.common.logging;

import hr.as2.inf.common.exceptions.AS2Exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
/**
 * http://tutorials.jenkov.com/java-logging/overview.html
 */
public abstract class AS2Logger {
	/** Implementation of AS2Logger providing the logger. */
	private static Class<?> LOGGER_CLASS = null;
	/** Log for Business Logic interface issues */
	public static final AS2Logger AS2_FACADE;
	/** Log for Business Logic issues */
	public static final AS2Logger AS2_BUSINESS_LOGIC;
	/** Log for Persistence issues */
	public static final AS2Logger AS2_DATA_ACCESS;
	/** Log for General issues */
	public static final AS2Logger AS2_GENERAL;

	static {
		// Set the log type to be used based on what is available from this
		// ClassLoader
		// Note that we could have registered in the PluginManager but that
		// needs to log too
		Class<?> loggerClass = null;
		try {
			AS2Logger.class.getClassLoader().loadClass(
					"java.util.logging.Logger");
			loggerClass = hr.as2.inf.common.logging.AS2Trace.class;
		} catch (Exception e) {
			loggerClass = AS2Trace.class;
		}
		LOGGER_CLASS = loggerClass;

		// Create the Loggers for our predefined categories
		AS2_FACADE = getLoggerInstance("AS2Facade");
		AS2_BUSINESS_LOGIC = getLoggerInstance("AS2BusinessLogic");
		AS2_DATA_ACCESS = getLoggerInstance("AS2DataAccessLogic");
		AS2_GENERAL = getLoggerInstance("AS2General");
	}

	/**
	 * Method to create a logger instance.
	 * 
	 * @param logCategory
	 *            The category (or null)
	 * @return The logger
	 */
	public static AS2Logger getLoggerInstance(String logCategory) {
		// Note that this uses reflection directly rather than ClassUtils since
		// we don't want to cause
		// initialization of any other class before we get our loggers installed
		Object obj;
		Class<?>[] ctrTypes = new Class[] { String.class };
		Object[] ctrArgs = new Object[] { logCategory };
		try {
			Constructor<?> ctor = LOGGER_CLASS.getConstructor(ctrTypes);
			obj = ctor.newInstance(ctrArgs);
		} catch (NoSuchMethodException e) {
			throw new AS2Exception("Missing constructor in class "
					+ LOGGER_CLASS.getName());
		} catch (IllegalAccessException e) {
			throw new AS2Exception("Failed attempting to access class "
					+ LOGGER_CLASS.getName());
		} catch (InstantiationException e) {
			throw new AS2Exception("Failed instantiating a new object of type "
					+ LOGGER_CLASS.getName());
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			} else if (t instanceof Error) {
				throw (Error) t;
			} else {
				throw new AS2Exception(
						"Unexpected exception thrown by constructor for "
								+ LOGGER_CLASS.getName());
			}
		}
		return (AS2Logger) obj;
	}

	public abstract void debug(Object msg);

	public abstract void debug(Object msg, Throwable thr);

	public abstract void info(Object msg);

	public abstract void info(Object msg, Throwable thr);

	public abstract void warn(Object msg);

	public abstract void warn(Object msg, Throwable thr);

	public abstract void error(Object msg);

	public abstract void error(Object msg, Throwable thr);

	public abstract void fatal(Object msg);

	public abstract void fatal(Object msg, Throwable thr);

	public abstract boolean isDebugEnabled();

	public abstract boolean isInfoEnabled();
}
