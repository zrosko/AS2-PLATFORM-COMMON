package hr.as2.inf.common.data;

import hr.as2.inf.common.annotations.AS2ResultSet;
import hr.as2.inf.common.exceptions.AS2Exception;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

/**
 * Martin Fowler - PEAA page 508. Record Set pattern. Plus DTO page 401.
 * 
 * @author zrosko
 * 
 */
@AS2ResultSet
public class AS2RecordList extends AS2Record {
	private static final long serialVersionUID = 7886048327165244391L;
	ArrayList<String> _columnNames = new ArrayList<>();
	HashMap<String, Integer> _columnSize = new HashMap<String, Integer>();
	ArrayList<AS2Record> _rows = new ArrayList<AS2Record>();
	protected boolean _oneRowOnly = false;
	boolean _moreResultSets = false;
	boolean _value_line_handler_used = false;
	ArrayList<String> _nestedResultSetNames;

	public ArrayList<String>  getNestedResultSetNames() {
		return _nestedResultSetNames;
	}
	public void addResultSet(String resultSetName, AS2RecordList resultSet) {

	    if (resultSetName != null && resultSet != null) {
	        _moreResultSets = true;
	        set(resultSetName, resultSet);
	        if (_nestedResultSetNames == null) {
	            _nestedResultSetNames = new ArrayList<String>();
	        }
	        _nestedResultSetNames.add(resultSetName);

	    }
	}
	public void deleteResultSet(String resultSetName) {
		if(_moreResultSets){
			delete(resultSetName);
			if (_nestedResultSetNames!=null){
				_nestedResultSetNames.remove(resultSetName);
			}
			if (_nestedResultSetNames.size()<=0){
				_nestedResultSetNames=null;
				_moreResultSets=false;
			}
		}
	}
	public AS2RecordList getResultSet(String resultSetName) {
	    if (_moreResultSets)
	        return (AS2RecordList) getAsObject(resultSetName);
	    else
	        return null;
	}
	public boolean hasMoreResultSets(){
		return _moreResultSets;
	}
	// Column names
	public void addColumnName(String name){
		if(name!=null)
			getColumnNames().add(name);
	}
	public void setColumnNames(ArrayList<String> value) {
		_columnNames = value;
	}

	public ArrayList<String> getColumnNames() {
		if (_columnNames == null)
			_columnNames = new ArrayList<String>();

		return _columnNames;
	}
	// Column sizes
	public void setColumnSize(int column, int size)throws AS2Exception{

		if(column==0 || size == 0)
			return;
		if(getColumnCounter() < column)
			throw new AS2Exception("203");
		getColumnSizes().put(new Integer(column).toString(), new Integer(size));
	}
	public int getColumnCounter(){
		if (_columnNames==null)
			_columnNames = new ArrayList<String> ();
		
		return _columnNames.size();
	}
	public void setColumnSizes(HashMap<String, Integer> value){
		_columnSize= value;

	}
	public HashMap<String, Integer> getColumnSizes() {
	    if (_columnSize == null)
	        _columnSize = new HashMap<String, Integer>();

	    return _columnSize;
	}
	public void setOneRowOnly(boolean value) {
		_oneRowOnly = value;
	}

	public boolean isOneRowOnly() {
		return _oneRowOnly;
	}
	public boolean isValueLineHandlerUsed(){
	    return _value_line_handler_used;
	}
	public void setValueLineHandlerUsed(boolean value) {
		_value_line_handler_used = value;
	}
	/*
	 * CRUD functions
	 */
	public void addRow(AS2Record row) {
		if (row != null)
			_rows.add(row);
	}
	public void addRows(ArrayList<AS2Record> rows) {
		for (AS2Record row : rows){
			getRows().add(row);
		}		
	}
	public void addRows(AS2RecordList rows) {
		addRows(rows.getRows());		
	}
	public void updateRow(int index, AS2Record row) {
		if (row != null)
			_rows.set(index, row);
	}

	public void deleteRow(int index) {
		if (index <= _rows.size())
			_rows.remove(index);
	}

	public AS2Record getRowAt(int index) {
		if (_rows.size() > index)
			return _rows.get(index);
		else
			return null;
	}
	public void deleteRowWithKey(String key, String value) {
		if (key == null || value == null)
			return;
		key = key.trim();
		value = value.trim();

		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			AS2Record row = it.next();
			if (row.getAsStringOrEmpty(key).equals(value))
				it.remove();
		}
	}
	// Read
	public String rsReadIdForName(String value){
	    return rsReadIdForName(value, AS2Record._OBJECT_NAME,AS2Record._OBJECT_ID);
	}	
	public String rsReadIdForName(String value,String name, String id){
	    value = value.trim();
	    if(value.length()<1)
	        return value;
		for(AS2Record vo : getRows()){
			if(vo.getAsStringOrEmpty(name).equals(value)){
				return vo.getAsString(id);
			}	
		}
		return "";
	}
	public String rsReadNameForId(String value){
	    String a = rsReadIdForName(value,AS2Record._OBJECT_ID, AS2Record._OBJECT_NAME);
	    return a;
	}
	public String rsReadNameForId(String value,String id, String name){
        for(AS2Record vo : getRows()){
            if (vo.getAsStringOrEmpty(id).equals(value)) {
                return vo.getAsString(name);
            }
        }
        return "";
	}
	public String rsReadIdForId(String value, String id){
		for(AS2Record vo : getRows()){
            if (vo.getAsStringOrEmpty(id).equals(value)) {
                return vo.getAsString(id);
            }
        }
        return "";
	}
	public String getValueList(String propertyName_){
		return getValueList(propertyName_,null);
	}
	//retrns a list of values for an property e.g. EUR,USD,AUD,CAD
	public String getValueList(String propertyName_, String _char){
	    propertyName_ = propertyName_.trim();	    
	    if(propertyName_ == null)
	        return "";
	    StringBuffer sb = new StringBuffer();
	    boolean _first = true;
	    for(AS2Record vo : getRows()){
		    if(vo.exists(propertyName_)){
		        if(!_first)
		            sb.append(",");
		        if(_char!=null){
		        	sb.append(_char);
		        	sb.append(vo.get(propertyName_));
		        	sb.append(_char);
		        }else
		        	sb.append(vo.get(propertyName_));
		        _first = false;
		    }
		}
		return sb.toString();
	}
	/*
	 * 0 is the first row
	 */
	public ArrayList<AS2Record> getRows() {
		return _rows;
	}
	public Iterator<AS2Record> iteratorRows() {
		return _rows.iterator();
	}
	public void appendRowsOnly(AS2RecordList addition) {
		if (addition != null) {
			_rows.addAll(addition.getRows());
		}
	}
	/* Returns just attributes required in value input parameter. TODO test*/
	public AS2RecordList getAttributesRs (AS2Record value ){
	    AS2RecordList ret_rs = new AS2RecordList();
	    Iterator<AS2Record> E = getRows().iterator();
		while(E.hasNext()){
			AS2Record vo = E.next();
		    ret_rs.addRow(vo.getAttributes(value));
		}
		return ret_rs;
	}

	/*
	 * GLOBAL functions
	 */
	public void addValue(String key, String value) {
		key = key.trim();
		value = value.trim();
		if (key == null || value == null)
			return;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			it.next().set(key, value);
		}
	}
	
	public void addValue(String key, Calendar value){
        key = key.trim();
        if(key == null || value==null)
            return;
        
        Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			it.next().setCalendarAsDateString(value, key);
		}
    }

	public void deleteValue(String key, String value) {
		key = key.trim();
		value = value.trim();
		if (key == null || value == null)
			return;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			it.next().delete(key);
		}
		_columnNames.remove(key);
		_columnSize.remove(key);
        //TODO getMetaData().remove(key);
	}

	public void updateValue(String key, String oldValue, String newValue) {
		key = key.trim();
		oldValue = oldValue.trim();
		newValue = newValue.trim();
		if (key == null || oldValue == null || newValue == null)
			return;

		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			AS2Record row = it.next();
			if (row.getAsStringOrEmpty(key).equals(oldValue))
				row.set(key, newValue);
		}
	}

	/*
	 * AGREGATE functions
	 */
	public int max(String key) {
		int counter = 0;
		int max = 0;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			int value = it.next().getAsInt(key);
			if (counter == 0)
				max = value;
			if (value > max)
				max = value;
			counter++;
		}
		return max;
	}

	public int min(String key) {
		int counter = 0;
		int min = 0;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			int value = it.next().getAsInt(key);
			if (counter == 0)
				min = value;
			if (value < min)
				min = value;
			counter++;
		}
		return min;
	}

	public double sum(String key) {
		double sum = 0;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			double value = it.next().getAsDouble(key);
			sum = sum + value;
		}
		return sum;
	}

	// TODO test
	public int count2(String key) {
		return Collections.frequency(_rows, key);
	}

	public int count(String key) {
		Collections.frequency(_rows, key);
		int count = 0;
		Iterator<AS2Record> it = _rows.iterator();
		while (it.hasNext()) {
			Object value = it.next().get(key);
			if (value != null)
				count++;
		}
		return count;
	}

	public double avg(String key) {
		int count = count(key);
		double sum = sum(key);
		double avg = 0;
		if (count > 0)
			avg = sum / count;
		return avg;
	}

	public int size() {
		return _rows.size();
	}
	
	public AS2RecordList doSearch(String name, String operator, String value){
	    return doSearch(name,operator,value, 0, 0, null);
	}
	//iz FILTER uzmi polje NAME i sa njegovom vrijednoscu pokupi sve iz rs i vrati nazad
	//npr za broj partije (name) iz skupa filter uzmi pronadi sve u rs i vrati nazad
	public AS2RecordList doSearch(String name, String operator, AS2RecordList filter){
		AS2RecordList valueList = new AS2RecordList();
		for(AS2Record vo : filter.getRows()){
			AS2RecordList rs_upit = doSearch(name,operator,vo.get(name));
			valueList.addRows(rs_upit);
		}
		return valueList;
	}
	/**
	 * Return the list of value objects that satisfy the search criteria.
	 * @param name of the column
	 * @param operator (=,<>,like%%)
	 * @param value to compare column with
	 * @param start from the positon in the result set 
	 * @param maxResults return maximum value object from the query
	 * TODO @param String regex to add later
	 * http://www.oreilly.com/catalog/regex2/
	 */
	public AS2RecordList doSearch(String name, String operator, String value, int start, int maxResults, String restrict){
	    name = name.trim();
	    if(value != null)value = value.trim().toUpperCase();
	    operator = operator.trim();	    
	    if(restrict != null) restrict = restrict.trim().toUpperCase();
	    Object inValue;
	    String nameValue;
	    AS2RecordList valueList = new AS2RecordList();
	    valueList.setColumnNames(this.getColumnNames());
	    valueList.setMetaData(this.getMetaData());
	    int rowCounter=0,inMmaxResults=0;
	    boolean isFound = false;
		for(AS2Record vo : getRows()){
			for(String inName : vo.keys()){
				if(inName instanceof java.lang.String && inName.equals(name)){
				    inValue = vo.getAsObject(name);
				    if(inValue instanceof java.lang.String){
				        nameValue = inValue.toString().toUpperCase();
				        isFound = false;
				        if(operator.equals("=")){
				            if(nameValue.equalsIgnoreCase(value)){
				                if(!nameValue.equalsIgnoreCase(restrict)){
				                    isFound = true;
				                }
				            }
				        }else if(operator.equals("IN")){
				            if(nameValue.equalsIgnoreCase(value)){
				                if(!nameValue.equalsIgnoreCase(restrict)){
				                    isFound = true;
				                }
				            }
				        }else if(operator.equals("<>")){
				            if(!nameValue.equalsIgnoreCase(value)){
				                if(!nameValue.equalsIgnoreCase(restrict)){
				                    isFound = true;
				                }
				            }
				        }else if(operator.equals("like")){
				            //TODO
				        }else if(operator.equals("%like")){
				            if(nameValue.endsWith(value)){
				                isFound = true;
				                if(restrict != null && nameValue.endsWith(restrict)){
				                    isFound = false;
				                }
				            }
				        }else if(operator.equals("like%")){
				            if(nameValue.startsWith(value)){
				                isFound = true;
				                if(restrict != null && nameValue.startsWith(restrict)){
				                    isFound = false;
				                }
				            }
				        }else if(operator.equals("%like%")){
				            if(nameValue.indexOf(value)>-1){
				                isFound = true;
				                if(restrict != null && nameValue.indexOf(restrict)>-1){
				                    isFound = false;
				                }
				            }
				        }
				        if(isFound){
				            rowCounter++;
				            if(start<=rowCounter){
				                inMmaxResults++;
				                if(maxResults>0){
				                    if(maxResults>=inMmaxResults)
				                        valueList.addRow(vo); 
				                }else {
				                    valueList.addRow(vo);  
				                }
				            }
				            
				        }
				    }
				}
			}
		}
		return valueList;
	}
	// JSON
	public List<LinkedHashMap<String, Object>> getRowsForJSON() {
		Vector<LinkedHashMap<String, Object>> _json_rows = new Vector<LinkedHashMap<String, Object>>();
    	if(_rows!=null && _rows.iterator()!=null){
    		for(AS2Record row : _rows){
    			_json_rows.addElement(row.getProperties());
    		}
    	}
    	return _json_rows;
    }
    public Vector<LinkedHashMap<String, Object>> getMetaDataForJSON() {
    	Vector<LinkedHashMap<String, Object>> _json_rows = new Vector<LinkedHashMap<String, Object>>();
    	return _json_rows;
    }
	/*
	 * CONVERTERS String, JSON, XML
	 */
	public String toString() {
		if (_oneRowOnly) {
			return super.toString();
		}
		StringBuffer buf = new StringBuffer(1000);
		int size = 0;
		int counter = 0;
		if (_rows != null) {
			Iterator<AS2Record> it = _rows.iterator();
			while (it.hasNext()) {
				buf.append("\nRow " + ++counter
						+ " -----------------------------");
				buf.append(it.next().toString());
			}
			size = _rows.size();
		}
		buf.append("\n-----------------------------\n");
		buf.append("Total rows = ");
		buf.append(size);
		return buf.toString();
	}
}
