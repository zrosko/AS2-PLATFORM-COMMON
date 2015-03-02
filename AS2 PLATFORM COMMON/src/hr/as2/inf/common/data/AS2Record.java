package hr.as2.inf.common.data;

import hr.as2.inf.common.annotations.AS2ValueObject;
import hr.as2.inf.common.core.AS2Object;
import hr.as2.inf.common.types.AS2Date;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;

@AS2ValueObject
public class AS2Record extends Observable implements AS2RecordInterface {
	private static final long serialVersionUID = -3464838910583253080L;
	protected LinkedHashMap<String, Object> _data = new LinkedHashMap<String, Object>();
	protected LinkedHashMap<String, AS2MetaData> _meta = new LinkedHashMap<String, AS2MetaData>();
	private boolean _dummy = false;
	private String _id = String.valueOf(System.currentTimeMillis());
	private String _remote_object="";
	private String _remote_method="";
	private long _last_access = System.currentTimeMillis();
	/**
     * Field to keep the key of the result set to be cached on the client. It is
     * used on the client cacheing for now.
     */
    private StringBuffer _cacheKey;
    /**
     * Value object setter methods do validation of the values if this indicator
     * is turned on. Validatons are based on the entries in the
     * J2EEValidationService.property file. By default this indicator is FALSE.
     */
    //TODO isValid etc.
    @SuppressWarnings("unused")
	private boolean _validation = false;
    private String _transactionToken;
    private String _transactionAction;
	private final PropertyChangeSupport _property = new PropertyChangeSupport(this);
	//private static Log _log = LogFactory.getLog(AS2Record.class);
	
	public AS2Record() {
	}

	public AS2Record(int size) {
		_data = new LinkedHashMap<String, Object>(size);
	}

	@SuppressWarnings("unchecked")
	public AS2Record(LinkedHashMap<String, Object> properties) {
		this();
		if (properties != null)
			_data = (LinkedHashMap<String, Object>) properties.clone();
	}

	@SuppressWarnings("unchecked")
	public AS2Record(AS2Record value) {
		this();
		if (value != null) {
			_data = (LinkedHashMap<String, Object>) value.getProperties().clone();
		}
	}
    public String getCacheKey() {
        String cache_key = "";
        if (_cacheKey != null)
            cache_key = _cacheKey.toString();
        return cache_key;
    }
    public void setCacheKey(String name, String value) {
        if (_cacheKey == null)
            _cacheKey = new StringBuffer();
        _cacheKey.append(name);
        _cacheKey.append(value);
    }
    public void setValidation(boolean validation) {
        _validation = validation;
    }
	public int size(){
		return _data != null ?_data.size() : 0;
	}
	public void clear(){
		_data.clear();
	}
	public LinkedHashMap<String, Object> getProperties() {
		if (_data == null)
			_data = new LinkedHashMap<String, Object>(
					_DEFAULT_COLLECTION_SIZE);
		return _data;
	}

	public LinkedHashMap<String, Object> getProperties(int size) {
		if (_data == null)
			_data = new LinkedHashMap<String, Object>(size);
		return _data;
	}

	@SuppressWarnings("unchecked")
	public void setProperties(LinkedHashMap<String, Object> value) {
		if (value == null) {
			_data = value;
			getProperties();
		} else
			_data = (LinkedHashMap<String, Object>) value.clone();
	}

	public Set<String> getOrder() {
		return _data.keySet();
	}

	public AS2Record clone() {
		try {
			return (AS2Record)AS2Object.deepClone(this);
		} catch (Exception e) {
			return this;
		}
	}
	// Meta data
    public void setMetaData(LinkedHashMap<String, AS2MetaData> value) {
        _meta = value;
    }
    public AS2MetaData getMetaData(String name) {
        if (_meta == null)
            _meta = new LinkedHashMap<String, AS2MetaData>();
        return (AS2MetaData) _meta.get(name);
    }
    public LinkedHashMap<String, AS2MetaData> getMetaData() {
    	if (_meta == null)
            _meta = new LinkedHashMap<String, AS2MetaData>();
        return _meta;
    }
    public AS2MetaData getMetaDataForName(String name) {
    	AS2MetaData meta = (AS2MetaData) getMetaData().get(name);
        if (meta == null)
            meta = new AS2MetaData();
        return meta;
    }
    public void addMetaData(String name, AS2MetaData value) {
        _meta.put(name, value);
    }
	public void append(AS2Record addition) {
		this.getProperties().putAll(addition.getProperties());
	}

	public void clearAll(LinkedHashMap<String, Object> addition) {
		Set<String> E = addition.keySet();
		for (String name: E){
			this.delete(name);
		}
	}

	public void setId() {
		setId(Long.toString((new java.util.Date()).getTime()));
	}

	public void setId(String value) {
		this._id = value;
	}

	public String getId() {
		return _id;
	}

	public void setRemoteObject(String value) {
		_remote_object = value;
	}

	public void setRemoteMethod(String value) {
		_remote_method = value;
	}

	public String getRemoteObject() {
		return _remote_object == null ? "":_remote_object;
	}

	public String getRemoteMethod() {
		return _remote_method == null ? "":_remote_method;
	}
    public String getTransactionToken() {
        return _transactionToken;
    }
    public String getTransactionAction() {
        return _transactionAction;
    }
    public void setTransactionToken(String value) {
        _transactionToken = value;
        set(_TRANSACTION_TOKEN, value);
    }
    public void setTransactionAction(String value) {
        _transactionAction = value;
        set(_TRANSACTION_ACTION, value);
    }
	public String getComponentAndService() {
		StringBuffer sb = new StringBuffer();
		sb.append(_remote_object);
		sb.append(".");
		sb.append(_remote_method);
		return sb.toString();
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
	}

	// getters and setters
	public Object getAsObject(String name) {
		return getProperties().get(name);
	}
	public Object getProperty(String name) {
		return getProperties().get(name);
	}
	// Byte
	public byte[] getAsBytes(String name) {
		return (byte[]) getProperties().get(name);
	}
	public void setBytes(String name, byte[] value) {
		set(name, value);
	}
	public void appendBytes(String name, byte[] value){
		byte[] old_value = getAsBytes(name);
		if (old_value == null) {
			setBytes(name, value);
		} else if (value != null) {
			byte[] novi = new byte[old_value.length + value.length];
			System.arraycopy(old_value, 0, novi, 0, old_value.length);
			System.arraycopy(value, 0, novi, old_value.length, value.length);
			setBytes(name,novi);
		}
	}
	// String
	public String getAsStringRoman(String name) {
		String numberRoman = "";
		try {
			int number_ = getAsInt(name);			
			switch (number_) {
            case 1:  numberRoman = "I";
                     break;
            case 2:  numberRoman = "II";
                     break;
            case 3:  numberRoman = "III";
                     break;
            case 4:  numberRoman = "IV";
                     break;
            case 5:  numberRoman = "V";
                     break;
            case 6:  numberRoman = "VI";
                     break;
            case 7:  numberRoman = "VII";
                     break;
            case 8:  numberRoman = "VIII";
                     break;
            case 9:  numberRoman = "IX";
                     break;
            case 10: numberRoman = "X";
                     break;
            case 11: numberRoman = "XI";
                     break;
            case 12: numberRoman = "XII";
                     break;
            default: numberRoman = "XIII";
                     break;
			}
			return numberRoman;
		} catch(Exception e){
			return numberRoman;
		}
	}
	public String get(String name) {
		return getAsStringOrEmpty(name);
	}
	public String getAsStringOrEmpty(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return "";
		else
			return o.toString();
	}
	public String getAsString(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return "";
		else
			return o.toString();
	}

	public String getAsString(String name, String default_) {
		Object o = getProperties().get(name);
		if (o != null)
			return o.toString();
		return default_;
	}
	public String getAsStringOrNull(String name) {
		Object o = getProperties().get(name);
		if (o == null)
			return null;
		else
			return o.toString();
	}
	public String getAsStringOrBlank(String name, int len) {
		Object o = getProperties().get(name);
        if (o == null)
            return " "; 
        else {
            String s = o.toString();
            if (s.length() < len)
                return s;
            return s.substring(0, len);
        }
	}
	public String getAsStringOrBlank(String name) {
		Object o = getProperties().get(name);
        if (o == null)
            return " "; 
        else {
             return o.toString();
        }
	}
	public String getAsStringOrZero(String propertyName) {
        Object o = getProperties().get(propertyName);
        if (o != null) {
            String s = o.toString().trim();
            if (s.length() > 0)
                return o.toString();
            else
                return _ZERO; 
        } else
            return _ZERO; 
    }

	public static String getFormatedAmount(double value) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);
		return nf.format(value);
	}
	// Date
	public String getDateAsStringOrCurrenDate(String propName) {
        String value = get(propName);
        if (value.length() == 0)
            value = AS2Date.getCurrentDateTimeAsString();
        return value;
    }
	public java.sql.Date getAsSqlDate(String name) {
		if(getAsObject(name)==null)
			return null;
		else{
			java.util.Date date = AS2Date.parseStringDateTimeToDate(getAsString(name));
			if(date==null)
				return null;
			return new java.sql.Date(date.getTime());
		}
	}
	public java.util.Date getAsUtilDate(String name) {
		if(getAsObject(name)==null)
			return null;
		else{
			java.util.Date date = AS2Date.parseStringDateTimeToDate(getAsString(name));
			if(date==null)
				return null;
			return date;
		}
	}
	 public String getHHMMSS(String name) {
	        if (get(name).length() <= 0)
	            return "";
	        StringBuffer sb = new StringBuffer();
	        sb.append(get(name).substring(11, 13));
	        sb.append(":");
	        sb.append(get(name).substring(14, 16));
	        sb.append(":");
	        sb.append(get(name).substring(17, 19));
	        return sb.toString();
	    }
	    public String getDDMMYYYY(String name) {
	        if (get(name).length() <= 0)
	            return "";
	        StringBuffer sb = new StringBuffer();
	        sb.append((get(name)).substring(8, 10));
	        sb.append(".");
	        sb.append((get(name)).substring(5, 7));
	        sb.append(".");
	        sb.append((get(name)).substring(0, 4));
	        return sb.toString();
	    }
	    public String getDDMMYY(String name) {
	        try {
	            StringBuffer sb = new StringBuffer();
	            sb.append((get(name)).substring(8, 10));
	            //sb.append(".");
	            sb.append((get(name)).substring(5, 7));
	            //sb.append(".");
	            sb.append((get(name)).substring(2, 4));
	            return sb.toString();
	        } catch (Exception e) {
	            return "";
	        }
	    }

	public void concatinateTimeToExistingDate(Calendar timeValue,
			String propName) {
		String date = getAsString(propName);
		String time = AS2Date.convertCalendarTimeToStringDate(timeValue);
		String date_time;
		if (date != null) {
			date = date.substring(0, 11);
		} else {
			date = AS2Date.getCurrentDateAsString();
		}
		time = time.substring(11);
		date_time = date + time;
		set(propName, date_time);
	}
	// Timestamp
	public java.sql.Timestamp getAsSqlTimestamp(String name) {
		return AS2Date.parseStringTimestampToTimestamp(get(name));
	}
	// Calendar
	public Calendar getAsCalendar(String name) {
        Calendar _calendar = new GregorianCalendar();
        String temp = getAsString(name);
        if (temp != null) {
            java.sql.Timestamp ts = getAsSqlTimestamp(temp);
            _calendar.setTimeInMillis(ts.getTime());
        } else {
            _calendar.setTimeInMillis(new java.util.Date().getTime());
        }
        return _calendar;
    }
	public void setCalendarAsDateString(Calendar value, String name) {
        if(value==null)
            return;
        String date = AS2Date.convertCalendarTimeToStringDate(value);
        set(name, date);
    }
	// Integer
	public int getAsInt(String name) {
        try {
            return Integer.parseInt(getAsString(name,_ZERO));
        } catch (Exception e) {
            return 0;
        }
    }
	public int getAsInt(String name, int default_value) {
		int value;
        try {
            value = Integer.parseInt(getAsString(name,_ZERO));
            if(value == 0)
            	return default_value;
        } catch (Exception e) {
        	value = 0;
        }
        return value;
    }
    public Integer getAsIntegerObject(String name) {
    	try {
            return new Integer(Integer.parseInt(getAsStringOrZero(name)));
        } catch (Exception e) {
            return new Integer(0);
        }
    }
    // Short
    public short getAsShort(String propertyName) {
        try {
            return Short.parseShort(getAsString(propertyName,_ZERO));
        } catch (Exception e) {
            return 0;
        }
    }
    public Short getAsShortObject(String name) {
        try {
            return new Short(Short.parseShort(getAsStringOrZero(name)));
        } catch (Exception e) {
            return new Short((short) 0);
        }
    }
    // Long
    public long getAsLong(String name) {
        return Long.parseLong(getAsStringOrZero(name));
    }
    public Long getAsLongObject(String name) {
        return new Long(getAsStringOrZero(name));
    }
    // Float
    public float getAsFloat(String name) {
        return Float.parseFloat(getAsStringOrZero(name));
    }
    public Float getAsFloatObject(String name) {
        return new Float(getAsStringOrZero(name));
    }
	// Double
	public double getAsDouble(String propertyName) {
        try {
            //Double.parseDouble(getAsString(propertyName,_ZERO));
            return new Double(getAsString(propertyName,_ZERO)).doubleValue();
        } catch (Exception e) {
            return 0;
        }
    }
	 public Double getAsDoubleObject(String name) {
	        return new Double(getAsStringOrZero(name));
	    }
	 // BigDecimal
	 public BigDecimal getAsBigDecimal(String name) {
	        return new BigDecimal(getAsStringOrZero(name));
	    }
	 // Boolean
	public boolean getAsBoolean(String propertyName, boolean b) {
		try {
			b = new Boolean(getAsString(propertyName)).booleanValue();
		} catch (Exception e) {
		}
		return b;
	}
	// AS2Record
	public AS2Record getAsJ2EEValueObjectOrNull(String propertyName) {
        Object o = getProperties().get(propertyName);
        if ((o != null) && (o instanceof AS2Record)) {
            return (AS2Record) o;
        } else
            return null;
    }
	public boolean getAsBooleanOrFalse(String propertyName) {
        return getAsBoolean(propertyName, false);
    }
    public boolean getAsBooleanOrTrue(String propertyName) {
        boolean b = true;
        if (getAsString(propertyName) == null)
            return b;
        return getAsBoolean(propertyName, false);
    }
	public void delete(String name) {
		getProperties().remove(name);
	}

	public void deleteAndNotify(String name) {
		delete(name);
		setChanged();
		notifyObservers();
	}

	public void set(String name, Object value) {
		if (name != null && value != null) {
			getProperties().put(name, value);
		}else if (value == null){
			getProperties().remove(name);

		}
	}

	public void setAndNotify(String name, Object value) {
		set(name, value);
		setChanged();
		notifyObservers();
	}

	public void set(String name, int value) {
		set(name, "" + value);
	}

	public void set(String name, long value) {
		set(name, "" + value);
	}

	public void set(String name, float value) {
		set(name, "" + value);
	}

	public void set(String name, short value) {
		set(name, "" + value);
	}

	public void set(String name, boolean value) {
		set(name, "" + value);
	}
    public void setReplaceComma(String name, String value) {
        value = value.replace(',', '.');
        set(name, value);
    }
	public boolean containsValue(String value){
		return _data.containsValue(value);
	}
	public boolean containsKey(String value){
		return _data.containsKey(value);
	}
	public boolean exists(String name) {
		Object value = getAsObject(name);
		if (value == null)
			return false;
		else
			return true;
	}

	public void setDummy(boolean dummy) {
		_dummy = dummy;
	}

	public boolean isDummy() {
		return _dummy;
	}

	public void setSuccess(boolean success) {
		if (success) {
			set(_SUCCESS, _SUCCESS_SUCCESSFUL);
		} else {
			set(_SUCCESS, _SUCCESS_FAILED);
		}
	}

	public boolean isSuccess() {
		if (_SUCCESS_SUCCESSFUL.equals(getAsStringOrEmpty(_SUCCESS))) {
			return true;
		}
		return false;
	}

	public String getMessage() {
		return getAsStringOrEmpty(_MESSAGE);
	}

	public void setMessage(String message) {
		set(_MESSAGE, message);
	}

	public Set<String> keys() {
		if (_data != null)
			return _data.keySet();
		else
			return null;
	}
	public Set<Entry<String,Object>> datas() {
		if (_data != null)
			return _data.entrySet();
		else
			return null;
	}
	public Iterator<String> iteratorKeys() {
		if(keys()!=null)
			return keys().iterator();
		else
			return new LinkedHashMap<String, Object>().keySet().iterator();//Dummy 
	}
	public void clearEmptyFields() {
		Set<String> E = keys();
		for (String name : E) {
			Object value = get(name);
			if (value instanceof java.lang.String) {
				String string_value = (String) value;
				if (string_value.length() <= 0) {
					this.delete(name);
				}
			} else if (value == null) {
				this.delete(name);
			}
		}
	}
	public void fromHashtable(Hashtable<String, Object> table) {
		Set<String> E = table.keySet();
		for (String name : E) {
			Object value = table.get(name);
			_data.put(name, value);
		}
	}
	public Hashtable<String, Object> toHashtable() {
		Hashtable<String, Object> ht = new Hashtable<String, Object>();
		Set<String> E = _data.keySet();
		for (String name : E) {
			Object value = _data.get(name);
			ht.put(name, value);
		}
		return ht;
	}
	public Object getAsJavaType(String type, String key) {
        if (type.equals("java.lang.Double")) { 
            return getAsDoubleObject(key);
        } else if (type.equals("java.lang.String")) {
            return getAsString(key);
        } else if (type.equals("java.util.Date")) { 
            java.sql.Timestamp t = getAsSqlTimestamp(key);
            return new java.util.Date(t.getTime());
        } else if (type.equals("java.sql.Timestamp")) {
            return getAsSqlTimestamp(key);
        } else if (type.equals("java.lang.Integer")) {
            return getAsIntegerObject(key);
        } else if (type.equals("java.lang.Short")) {
            return getAsShortObject(key);
        } else if (type.equals("java.lang.Float")) {
            return getAsFloatObject(key);
        }else if (type.equals("java.math.BigDecimal")) {
        	return getAsBigDecimal(key);
        }else if (type.equals("java.lang.Long")) {
        	return getAsLongObject(key);
        }
        return "";
    }
	public String toString() {
		StringBuffer buf = new StringBuffer(9000);
		buf.append("\nAS2Data=");
		Set<?> entrySet = _data.entrySet();
		Iterator<?> it = entrySet.iterator();
		while(it.hasNext()){
			buf.append(it.next().toString());
			buf.append(";");
		}
		return buf.toString();
	}
	public AS2Record changeKeys(String prefix, String suffix){
		AS2Record new_record = new AS2Record();
		Set<?> keySet = _data.keySet();
		Iterator<?> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			StringBuffer sb = new StringBuffer();
			sb.append(prefix);
			sb.append(key);
			sb.append(suffix); //key
			new_record.set(sb.toString(), this.getAsObject(key).toString());
		}
		return new_record;
	}
	   /* Returns just required fields. */
    public AS2Record getAttributes(AS2Record _filter) {
    	AS2Record ret = new AS2Record();
        for(String name : _filter.keys()) {
            Object value = this.get(name);
            ret.set(name,value);            
        }
        return ret;
    }
	public String toString2() {
		StringBuffer buf = new StringBuffer(9000);
		buf.append("\nAS2=");
		buf.append("\nRemote object =" + _remote_object);
		buf.append("\nRemote method =" + _remote_method);
		buf.append("\n");
		
		Set<String> E = keys();
		for(String name: E){
			Object value = get(name);
			if (value instanceof java.lang.String) {
				if (buf.length() < 9000) {
					buf.append(name);
					buf.append("=");
					buf.append(value.toString());
					buf.append("\n");
				}
			}
		}
		return buf.toString();
	}

	// **************************** JFace data binding
	// *****************************************
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		_property.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		_property.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		_property.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		_property.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		_property.firePropertyChange(propertyName, oldValue,
				newValue);
	}

	// COMMAND design pattern
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//TODO zamjenici AS2Record povrat sa Object
	public AS2Record execute() throws Exception {
		Class facade_class = null;
		Method get_instance_method = null;
		Method regular_facade_method = null;
		Method dummy_facade_method = null;
		Class regular_parameters[] = null;
		Class get_instance_parameters[] = new Class[0];
		Class dummy_parameters[] = new Class[0];
		Object target_object = null;
		Object[] arguments = null;
		Object[] get_instance_arguments = new Object[0];
		AS2Record response = new AS2Record();
		regular_parameters = new Class[] { this.getClass() };
		arguments = new Object[] { this };

		/***************** CALL Service ***********************/
		facade_class = Class.forName((String) this.getRemoteObject());
		get_instance_method = facade_class.getMethod(_GET_INSTANCE,
				get_instance_parameters);
		target_object = get_instance_method.invoke(target_object,
				get_instance_arguments);
		Object _returned = null;
		if (this.isDummy()) {
			dummy_facade_method = facade_class.getMethod(
					(String) this.getRemoteMethod(), dummy_parameters);
			_returned = dummy_facade_method
					.invoke(target_object, new Object[0]);
			response.set(_RESPONSE, _returned);
		} else {
			String _transform_to = this.get("@@transform_to");
        	//parameter class name specified, in case it it different on Proxy than on Server facade
        	if(_transform_to!=null && _transform_to.length()>0) { 
        		AS2Record vo_out = new AS2Record();
        		Class vo_class = null;
    			vo_class = Class.forName(_transform_to);
    			vo_out = (AS2Record)vo_class.newInstance();
    			vo_out.setProperties(this.getProperties());
    			//TODO vo_out.setOrder(this.getOrder());
    			vo_out.setMetaData(this.getMetaData());
    			vo_out.setRemoteObject(this.getRemoteObject());
    			vo_out.setRemoteMethod(this.getRemoteMethod());
    			vo_out.setId(this.getId());
    			
    			regular_parameters = new Class[] { vo_out.getClass() };
        		arguments = new Object[] { vo_out };
        	}
			regular_facade_method = facade_class.getMethod(
					(String) this.getRemoteMethod(), regular_parameters);
			_returned = regular_facade_method.invoke(target_object, arguments);
			response.set(_RESPONSE, _returned);
		}
		return response;
	}

	@Override
	public boolean isEvictable() {
		return true;
	}

	@Override
	public long info() {
		return _last_access;
	}

	@Override
	public void beforeEviction() {
		//notifyAll();//TODO test
	}
}
