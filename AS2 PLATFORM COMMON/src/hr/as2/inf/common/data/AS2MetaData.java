package hr.as2.inf.common.data;


/**
 * Meta data attributes.
 */
public class AS2MetaData extends AS2Record {
	private static final long serialVersionUID = 1L;
	private String _columnLabel = "";
    private String _columnTypeName = "";
    private int _columnType = 0;
    private int _columnDisplaySize = 10; 
    private int _precision = 0;
    private int _scale = 0;
    private int _sequence = 0;

    public AS2MetaData() {
        super();
    }

    public AS2MetaData(String columnLabel, String columnTypeName, int columnType, 
            int columnDisplaySize, int precision, int scale, int sequence) {
        _columnLabel = columnLabel;
        _columnTypeName = columnTypeName;
        _columnType = columnType;
        _columnDisplaySize = columnDisplaySize;
        _precision = precision;
        _scale = scale;
        _sequence = sequence;
    }

    public String getColumnLabel() {
    	return _columnLabel;
    }
    public String getColumnTypeName() {
    	return _columnTypeName;
    }
    public int getColumnType() {
    	return _columnType;
    }
    public int getColumnDisplaySize() {
    	return _columnDisplaySize;
    }
    public int getPrecision() {
    	return _precision;
    }
    public int getScale() {
    	return _scale;
    }
    public int getSequence() {
    	return _sequence;
    }
    /* </ Setters */
    public void setColumnLabel(String value) {
        _columnLabel = value;
    }
    public void setColumnTypeName(String value) {
        _columnTypeName = value;
    }
    public void setColumnType(int value) {
        _columnType = value;
    }
    public void setColumnDisplaySize(int value) {
        _columnDisplaySize = value;
    }
    public void setPrecision(int value) {
        _precision = value;
    }
    public void setScale(int value) {
        _scale = value;
    }
    public void setSequence(int value) {
        _sequence = value;
    }
    /* toString /> */
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(" _columnLabel= "+_columnLabel);
        sb.append(" _columnTypeName= "+_columnTypeName);
        sb.append(" _columnType= "+_columnType);
        sb.append(" _columnDisplaySize= "+_columnDisplaySize);
        sb.append(" _precision= "+_precision);
        sb.append(" _scale= "+_scale);
        sb.append(" _sequence= "+_sequence);
        return sb.toString();
    }
    public AS2Record prepareRecord(){
    	set("_columnLabel", _columnLabel);
    	set("_columnTypeName", _columnTypeName);
    	set("_columnType", _columnType);
    	set("_columnDisplaySize", _columnDisplaySize);
    	set("_precision", _precision);
    	set("_scale", _scale);
    	set("_sequence", _sequence);
    	return this;
    }
}