package hr.as2.inf.common.data;

import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.core.AS2Helper;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.logging.AS2Trace;

import java.util.Properties;

/**
 * The AS2RecordFactory holds a mapping from XML message type to a Value Object
 * class. XML parsing layer queries the factory to get the Value Object type
 * that is used as a Value Object for XML message processed by XML parsing
 * layer. The mapping is written in a resource file named
 * /settings/J2EEValueObjectFactory.properties. The file format is the format
 * used by the java.util.Properties class.
 * 
 * @version 1.0
 * @date Apr 4, 2003.
 * @author zrosko@yahoo.com
 */
public final class AS2RecordFactory {
	/**
	 * Singleton reference.
	 */
	private static AS2RecordFactory _instance = null;
	/**
	 * Properties file name
	 */
	private static final String PROPERTIES_FILE = "AS2RecordFactory.properties";

	/**
	 * Map from XML message name to a J2EEValueObject type
	 */
	private static Properties _xmlNameToValueObjectMap;

	private AS2RecordFactory() {
		initialize();
		AS2Context.setSingletonReference(this);
	}

	public AS2Record create(String valueObjectClassName) {
		Object ob = null;
		String className = _xmlNameToValueObjectMap
				.getProperty(valueObjectClassName);
		try {
			ob = Class.forName(className).newInstance();
		} catch (Exception e) {
			AS2Trace.trace(AS2Trace.E,
					"Can not create event object by Factory:" + e);
			// J2EEException j2e = new J2EEException("2005");
			// j2e.addCauseException(e);
			// throw jte;
		}
		return (AS2Record) ob;
	}

	/**
	 * Returns the singleton instance.
	 */
	public static AS2RecordFactory getInstance() {
		if (_instance == null)
			_instance = new AS2RecordFactory();
		return _instance;
	}

	protected void initialize() {
		_xmlNameToValueObjectMap = AS2Helper.readPropertyFileAsURL(AS2Context
				.getPropertiesPath() + PROPERTIES_FILE);
		_xmlNameToValueObjectMap.setProperty("AS2Record",
				_xmlNameToValueObjectMap.getProperty("AS2Record",
						"hr.as2.inf.common.data.AS2Record"));
	}
}
