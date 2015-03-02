package hr.as2.inf.common.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Utility class to work with objects.
 * <p>
 */
public class AS2Object implements InvocationHandler {
	//	private static Log _log = LogFactory.getLog(AS2Object.class);
	/**
	 * Does an deep clone of the sent object.
	 * <p>
	 * 
	 * @param source
	 *            Must be serializable. It's not necessary that it implements
	 *            <code>Cloneable</code>.
	 * @return Clone of the sent object. Does an clon of all levels, complete,
	 *         deep.
	 */
	public static Object deepClone(Object source) throws Exception {
		try {
			if (source == null)
				return null;
			// reading
			ByteArrayOutputStream byteout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(byteout);
			out.writeObject(source);
			byte[] buffer = byteout.toByteArray();
			byteout.close();
			out.close();
			// saving
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(buffer));
			Object result = in.readObject();
			in.close();
			return result;
		} catch (Exception e) {
			// _log.error(e.getMessage(), e);
			return null;
		}
	}

	public static Object execute(Object o, String methodName) throws Exception {
		return o; // TODO
	}

	public Object invoke(Object instance, Method method, Object[] args)
			throws Throwable {
		try {
			return method.invoke(instance, args);
		} catch (Exception e) {
			// These are not considered fatal.
//			_log.warn("Caught exception in callback " + method.getName(), e);
		}
		return null;
	}
}
