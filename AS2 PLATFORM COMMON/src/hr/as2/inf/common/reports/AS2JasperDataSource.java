package hr.as2.inf.common.reports;

import hr.as2.inf.common.data.AS2Record;

import java.util.ArrayList;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Jasper data source.
 */
public class AS2JasperDataSource implements JRDataSource {
	private static final String REPORT_COUNTER = "Counter";
	private Object[] beans = null;
	private boolean _ready = false;
	private int counter = -1;
	/**
	 * If true it will return null instead of throwing an Exception when
	 * encounters an error in the bean.
	 */
	private boolean returnNullOnError = false;

	public AS2JasperDataSource(Object[] beans) {
		super();
		this.beans = beans;
		reset();
	}

	public AS2JasperDataSource(Vector<?> beans) {
		super();
		this.beans = beans.toArray();
		reset();
	}

	public AS2JasperDataSource(ArrayList<?> beans) {
		super();
		this.beans = beans.toArray();
		reset();
	}

	public AS2JasperDataSource(Object bean) {
		super();
		beans = new Object[1];
		beans[0] = bean;
		reset();
	}
	@Override
	public boolean next() {
		++counter;
		return counter < beans.length;
	}
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		String fieldName = field.getName();
		// Uz sve podatke koji se nalaze u beanu ponekad nam u reportu treba
		// informacija o tome na kojem smo rednom broju:
		if (fieldName.equalsIgnoreCase(REPORT_COUNTER)) {
			return new Integer(counter + 1);
		}
		AS2Record vo = null;
		try {
			vo = (AS2Record) beans[counter];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			return getFieldValue(fieldName, field.getValueClassName(), vo);
		} catch (Exception e) {
			if (returnNullOnError) {
				return null;
			} else {
				e.printStackTrace();
				throw new JRException("...");
			}
		}
	}
	public void reset() {
		counter = -1;
	}

	public boolean isReady() {
		return _ready;
	}

	public void setReady(boolean value) {
		_ready = value;
	}


	public static Object getFieldValue(String fieldConstant, String fieldType,
			AS2Record vo) throws Exception {
		try {
			return vo.getAsJavaType(fieldType, fieldConstant);
		} catch (Exception e) {
			return null;
		}
	}
}
