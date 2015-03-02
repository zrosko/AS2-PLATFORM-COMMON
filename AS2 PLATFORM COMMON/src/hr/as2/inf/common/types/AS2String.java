package hr.as2.inf.common.types;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class AS2String {
	private StringBuffer _stringBuffer;
	private String _delimiter;
	
	public AS2String(AS2String value) {
		_stringBuffer = new StringBuffer(value._stringBuffer.toString());
		_delimiter = value._delimiter;
	}
	public AS2String(String _delimiter) {
		this._delimiter = _delimiter;
	}
	public AS2String(String prefix, String _delimiter, String suffix) {
		this(_delimiter);
		add(prefix);
		add(suffix);
	}
	public AS2String(String _delimiter, long[] items) {
		this(_delimiter);
		if (items != null)
			for (int i = 0; i < items.length; i++)
				add(items[i]);
	}

	public AS2String(String _delimiter, Object[] items) {
		this(_delimiter);
		if (items != null)
			for (int i = 0; i < items.length; i++)
				add(items[i]);
	}
	private void delimit() {
		if (_stringBuffer == null)
			_stringBuffer = new StringBuffer();
		else
			_stringBuffer.append(_delimiter);
	}

	public AS2String add(String str) {
		if (str != null) {
			delimit();
			_stringBuffer.append(str);
		}
		return this;
	}

	public AS2String add(Object o) {
		if (o != null) {
			delimit();
			_stringBuffer.append(o);
		}
		return this;
	}

	public AS2String add(long i) {
		delimit();
		_stringBuffer.append(i);
		return this;
	}
	public AS2String set(String s) {
		_stringBuffer = new StringBuffer(s);
		return this;
	}
	public StringBuffer getValue() {
		return _stringBuffer;
	}
	public static boolean ifExists(String source, String target) {	
		if(source==null || target==null)
			return false;
	    int pos = -1;
	    if(source.length()>0){
	       pos = source.indexOf(target);
	    }
	    return pos!=-1;
	}
	public static String reverseString(String source) {
		//can be used StringBuffer.reverse instead
        int i, len = source.length();
        StringBuffer dest = new StringBuffer(len);

        for (i = (len - 1); i >= 0; i--)
            dest.append(source.charAt(i));
        return dest.toString();
    }
	public static String makeTitle(String source) {
		int pos = source.indexOf("__")+2;
		//remove table name prefix
		if(pos!=1)
			source = source.substring(pos);
		source = source.replaceAll("_"," ");
		source = source.replaceAll("-"," ");
		int size = source.length();
		StringBuffer dest = new StringBuffer(size);
		
		boolean seeking = true;
		for (int i = 0; i < size; i++){
			if	(source.charAt(i) <= ' '){
				seeking = true;
				dest.append(source.charAt(i));
			} else if (seeking) {
					seeking = false ; 
					char c = source.charAt(i);
					c = Character.toUpperCase(c);
					dest.append(c);
			} else {
				dest.append(source.charAt(i));
			}
		}  	
        return dest.toString();
    }
	public static String makeMethodName(String source) {
		int pos = source.indexOf("__")+2;
		//remove table name prefix
		if(pos!=1)
			source = source.substring(pos);
		source = source.replaceAll("_"," ");
		source = source.replaceAll("-"," ");
		int size = source.length();
		StringBuffer dest = new StringBuffer(size);
		
		boolean seeking = true;
		for (int i = 0; i < size; i++){
			if	(source.charAt(i) <= ' '){
				seeking = true;
				dest.append(source.charAt(i));
			} else if (seeking) {
					seeking = false ; 
					char c = source.charAt(i);
					c = Character.toUpperCase(c);
					dest.append(c);
			} else {
				dest.append(source.charAt(i));
			}
		}  	
        return dest.toString().replaceAll(" ","");
    }
	/**
	 * Return the number of words in string s.
	 * @param s String - the string to examine.
	 * @return int - the number of words in string s.
	 **/
	public static int wordCount(String s)
	{
		int size = s.length() , count = 0;
		boolean seeking = true;
	
		for	(int i = 0 ; i < size ; ++i)
		{
			if	(s.charAt(i) <= ' ')
				seeking = true;
			else
				if (seeking) {seeking = false ; ++count;}
		}
	
		return count;
	}
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	public static String getAmountFormated(double value){
	    NumberFormat nf = NumberFormat.getInstance();		    
	    nf.setMinimumFractionDigits(2);
	    nf.setMaximumFractionDigits(2);
	    nf.setGroupingUsed(true);
	    return nf.format(new Double(value).doubleValue());
	}
	public static final boolean onlySpaces(String str) {
		for (int i = 0; i < str.length(); ++i) {
			int c = str.charAt(i);
			if ((c != 32) && (c != 9) && (c != 10) && (c != 13)) {
				return false;
			}
		}
		return true;
	}

	public static int countOccurrences(String container, String content) {
		int lastIndex, currIndex = 0, occurrences = 0;
		while (true) {
			lastIndex = container.indexOf(content, currIndex);
			if (lastIndex == -1) {
				break;
			}
			currIndex = lastIndex + content.length();
			occurrences++;
		}
		return occurrences;
	}

	public static final String[] splitString(String str_) {
		int occurance = countOccurrences(str_,","); 
		String _str[] = new String[occurance+1];
		int _end = str_.indexOf(",");
		int _begin = 0;
		int _pos = 0;
		while (_end != -1){
			_str[_pos] = str_.substring(_begin,_end);
			_begin=_end+1;
			_pos++;
			_end = str_.indexOf(",",_begin);
		}
		if(str_.length()>_begin)
			_str[_pos] = str_.substring(_begin);
		return _str;
	}
	public static String cutTillHyphen(String source) {
		int pos = source.indexOf("-");
		if(pos!=1)
			source = source.substring(0,pos);
		return source.trim();
	}
	public static String cutTillLastDot(String source) {
		int pos = source.lastIndexOf(".");
		if(pos!=1)
			source = source.substring(0,pos);
		return source.trim();
	}
	public static String cutAfterLastDot(String source) {
		int pos = source.lastIndexOf(".");
		if(pos!=1)
			source = source.substring(pos+1);
		return source.trim();
	}
	public static String cutAfterUnderscore(String source) {
		int pos = source.indexOf("_");
		if(pos!=1)
			source = source.substring(pos+1);
		return source.trim();
	}
	public static boolean isAmountSQL(String value){
		return value.matches("[-]?[0-9]*[.]?[0-9]*");
	}
	/** NEW
	 * @return If string if null or have no tokens returns empty string.
	 */
	public static String lastToken(String string) {
		if (string == null)
			return "";
		return lastToken(new StringTokenizer(string));
	}

	/**
	 * @return If string if null or have no tokens returns empty string.
	 */
	public static String lastToken(String string, String delim) {
		if (string == null)
			return "";
		if (delim.length() == 1 && string.indexOf(delim) < 0)
			return string; // Only one token
		return lastToken(new StringTokenizer(string, delim));
	}

	private static String lastToken(StringTokenizer st) {
		String r = "";
		while (st.hasMoreTokens())
			r = st.nextToken();
		return r;
	}

	public static String firstToken(String string, String delim) {
		if (string == null)
			return "";
		if (delim.length() == 1 && string.indexOf(delim) < 0)
			return string; // Only one token
		StringTokenizer st = new StringTokenizer(string, delim);
		if (st.hasMoreTokens())
			return st.nextToken().trim();
		return "";
	}

	/**
	 * Change in <code>string</tt> <code>original</code> by
	 * <code>newString</code>.
	 * <p>
	 * 
	 * @param string
	 *            String in which we make the changes. Can be null
	 * @param original
	 *            String to search. Not null
	 * @param newString
	 *            New value to put in place of original. Not null
	 * @return The changed string, if the sent string is null a null is returned
	 */
	public static String change(String string, String original, String newString) {
		if (string == null)
			return null;
		int i = string.indexOf(original);
		if (i < 0)
			return string;
		StringBuffer sb = new StringBuffer(string);
		while (i >= 0) {
			int f = i + original.length();
			sb.replace(i, f, newString);
			i = sb.toString().indexOf(original, i + newString.length());
		}
		return sb.toString();
	}

	/**
	 * Returns a string like the sent one but with the first letter in
	 * uppercase.
	 * <p>
	 * 
	 * If null is sent null is returned.
	 */
	public static String firstUpper(String s) {
		// Character.toUpperCase(s.charAt(0)) + s.substring(1);
		// Character.toLowerCase(iSource.charAt(0)) + iSource.substring(1);

		if (s == null)
			return null;
		if (s.length() == 0)
			return "";
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * Returns a string like the sent one but with the first letter in
	 * lowercase.
	 * <p>
	 * 
	 * If null is sent null is returned.
	 */
	public static String firstLower(String s) {
		if (s == null)
			return null;
		if (s.length() == 0)
			return "";
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public final static String[] toArray(String list) {
		Collection<?> c = toCollection(list);
		String[] rs = new String[c.size()];
		c.toArray(rs);
		return rs;
	}

	public final static Collection<String> toCollection(String list) {
		return toCollection(list, ",");
	}

	public final static Collection<String> toCollection(String list,
			String separator) {
		return toList(list, separator);
	}

	public final static List<String> toList(String list) {
		return toList(list, ",");
	}

	public final static List<String> toList(String list, String separator) {
		List<String> rs = new ArrayList<String>();
		if (list == null)
			return rs;
		fillCollection(rs, list, separator);
		return rs;
	}

	private final static void fillCollection(Collection<String> rs,
			String list, String separator) {
		StringTokenizer st = new StringTokenizer(list, separator);
		while (st.hasMoreTokens()) {
			rs.add(st.nextToken().trim());
		}
	}

	public static String getClearName(String iJavaName) {
		StringBuilder buffer = new StringBuilder();
		char c;
		if (iJavaName != null) {
			buffer.append(Character.toUpperCase(iJavaName.charAt(0)));
			for (int i = 1; i < iJavaName.length(); ++i) {
				c = iJavaName.charAt(i);

				if (Character.isUpperCase(c)) {
					buffer.append(' ');
				}

				buffer.append(c);
			}

		}
		return buffer.toString();
	}
	public String toString() {
		return _stringBuffer == null ? "" : _stringBuffer.toString();
	}
	public static boolean stringArrayContainsValue(String[] array, String value) {
		if (value == null || array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (value.equals(array[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isWhitespace(String str) {
		return str == null || str.length() == 0 || str.trim().length() == 0;
	}

	public static boolean notEmpty(String s) {
		return ((s != null) && (s.length() > 0));
	}

	/**
	 * Splits a list of values separated by a token
	 */
	public static String[] split(String valuesString, String token) {
		String[] values;
		if (valuesString != null) {
			StringTokenizer tokenizer = new StringTokenizer(valuesString, token);

			values = new String[tokenizer.countTokens()];
			int count = 0;
			while (tokenizer.hasMoreTokens()) {
				values[count++] = tokenizer.nextToken();
			}
		} else {
			values = null;
		}
		return values;
	}

	/**
	* Convenience method to convert a String containing numbers (separated by assorted
	* characters) into an int array. The separators can be ' ' '-' ':' '.' ',' etc.
	* @param str The String
	* @return The int array
	*/
	public static int[] convertStringToIntArray(String str) {
		if (str == null) {
			return null;
		}

		int[] values = null;
		ArrayList<Integer> list = new ArrayList<Integer>();

		int start = -1;
		for (int i = 0; i < str.length(); i++) {
			if (start == -1 && Character.isDigit(str.charAt(i))) {
				start = i;
			}
			if (start != i && start >= 0) {
				if (!Character.isDigit(str.charAt(i))) {
					list.add(Integer.valueOf(str.substring(start, i)));
					start = -1;
				}
			}
			if (i == str.length() - 1 && start >= 0) {
				list.add(Integer.valueOf(str.substring(start)));
			}
		}

		if (list.size() > 0) {
			values = new int[list.size()];
			Iterator<Integer> iter = list.iterator();
			int n = 0;
			while (iter.hasNext()) {
				values[n++] = ((Integer) iter.next()).intValue();
			}
		}
		return values;
	}
}
