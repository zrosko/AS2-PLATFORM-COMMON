package hr.as2.inf.common.format;

import hr.as2.inf.common.logging.AS2Trace;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class contains common methods used to copy objects to byte array.
 */
public final class AS2Format
{
	public static final String UTF_8 = "UTF-8"; //$NON-NLS-1$
	public static final String UTF_16 = "UTF-16"; //$NON-NLS-1$
	public static final String CP1250 = "CP1250"; //$NON-NLS-1$
	public static final String CP1252 = "CP1252"; //$NON-NLS-1$
	public static final String ISO_8859_1 = "ISO-8859-1"; //$NON-NLS-1$
	public static final String ISO_8859_2 = "ISO-8859-2"; //$NON-NLS-1$
	public static String defaultFromCharsetName = UTF_8;
	public static String defaultToCharsetName = UTF_8;

	/**
	 * Copy Boolean to byte array as Y for true and N for false.
	 * @return int
	 * @param value Boolean
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyBooleanToByteArrayAsYN(Boolean value, byte holder[], int offset,
			int length)
	{

		if (value == null)
			return offset + length;

		if (value.booleanValue() == true)
			holder[offset] = (byte) 'Y';
		else if (value.booleanValue() == false)
			holder[offset] = (byte) 'N';
		return offset + length;
	}

	/**
	 * Copy Date to byte array as YYYMMDD.
	 * @return int
	 * @param value Date
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyDateToByteArrayAsYYYYMMDD(Date value, byte holder[], int offset,
			int length)
	{

		if (value == null)
			return offset + length;

		//??offset = copyIntToByteArray((value.getField(Calendar.YEAR) - 1900),
		// holder, offset, 4);
		holder[offset] = (byte) '-';
		offset = offset + 1;
		//??offset = copyIntToByteArray((value.getMonth()+1), holder, offset,
		// 2);
		holder[offset] = (byte) '-';
		offset = offset + 1;
		//??return copyIntToByteArray(value.getDate(), holder, offset, 2);
		return 0;
	}

	/**
	 * Copy Date to byte array as YYYYMMDDHHMMSS.
	 * @return int
	 * @param value Date
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyDateToByteArrayAsYYYYMMDDHHMMSS(Date value, byte[] holder, int offset,
			int length)
	{

		if (value == null)
			return offset + length;

		//??offset = copyIntToByteArray((value.getYear()+1900), holder, offset,
		// 4);
		holder[offset] = (byte) '-';
		offset = offset + 1;
		//??offset = copyIntToByteArray((value.getMonth()+1), holder, offset,
		// 2);
		holder[offset] = (byte) '-';
		offset = offset + 1;
		//??offset = copyIntToByteArray(value.getDate(), holder, offset, 2);
		holder[offset] = (byte) '-';
		offset = offset + 1;
		//??offset = copyIntToByteArray(value.getHours(), holder, offset, 2);
		holder[offset] = (byte) '.';
		offset = offset + 1;
		//??offset = copyIntToByteArray(value.getMinutes(), holder, offset, 2);
		holder[offset] = (byte) '.';
		offset = offset + 1;
		//??return copyIntToByteArray(value.getSeconds(), holder, offset, 2);
		return 0; //??
	}

	/**
	 * Copy Integer to byte array.
	 * @return int
	 * @param value Integer
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyIntegerToByteArray(Integer value, byte holder[], int offset, int length)
	{

		if (value == null)
			return offset + length;

		return copyIntToByteArray(value.intValue(), holder, offset, length);
	}

	/**
	 * Copy int to byte array.
	 * @return int
	 * @param value int
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyIntToByteArray(int value, byte holder[], int offset, int length)
	{
		int i;
		int pad = 0;

		String stringValue = value + "";

		if (length > stringValue.length())
			pad = length - stringValue.length();

		for (i = 0; i < pad; i++)
			holder[offset + i] = (byte) '0';

		for (; i < length; i++)
			holder[offset + i] = (byte) stringValue.charAt(i - pad);
		return offset + length;
	}

	/**
	 * Copy String from serarch criteria to byte array. It substitutes blank,
	 * null and * with % and pads the string with blanks.
	 * @return int
	 * @param value String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copySearchCriteriaStringToByteArray(String value, byte holder[], int offset,
			int length)
	{
		int i;
		int copyLength;

		if (value == null)
		{
			holder[offset] = (byte) '%';
			i = 1;

		}
		else if (value.trim().length() == 0)
		{
			holder[offset] = (byte) '%';
			i = 1;

		}
		else
		{
			value = value.replace('*', '%');

			if (value.length() < length)
				copyLength = value.length();
			else
				copyLength = length;

			for (i = 0; i < copyLength; i++)
				holder[offset + i] = (byte) value.charAt(i);

		}
		// pad with blanks
		for (; i < length; i++)
			holder[offset + i] = (byte) ' ';

		return offset + length;
	}

	/**
	 * Copy String to byte array. It pads the string with blanks.
	 * @return int
	 * @param value String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyStringToByteArray(String value, byte holder[], int offset, int length)
	{
		int i;
		int copyLength;

		if (value == null)
			return offset + length;

		if (value.length() < length)
			copyLength = value.length();
		else
			copyLength = length;

		//db-- added support for croatian letters
		//TODO: CODEPAGE MUST BE OBTAINED IN ANOTHER WAY!!!
		//		for (i = 0; i < copyLength; i++)
		//			holder[offset + i] = (byte) value.charAt(i);
		try
		{
			byte[] valBytes = value.getBytes("Cp1250");
			for (i = 0; i < copyLength; i++)
				holder[offset + i] = valBytes[i]; //(byte) value.charAt(i);
			//--bd

			for (; i < length; i++)
				holder[offset + i] = (byte) ' ';
		}
		catch (UnsupportedEncodingException e)
		{
			//TODO {YYY}Auto-generated catch block
			/* catch block body code version 0.02 */
			e.printStackTrace();
		}
		return offset + length + 1;
	}

	/**
	 * Copy String to byte array. It inserts leading zeros.
	 * @return int
	 * @param value String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyStringToByteArrayLeadWithZero(String value, byte holder[], int offset,
			int length)
	{
		int startPos = 0;
		int j = 0;
		int pos = 0;
		int copyLength;

		if (value == null)
			return offset + length;

		if (value.length() < length)
			copyLength = value.length();
		else
			copyLength = length;

		startPos = (length - copyLength);

		if (startPos < length)
		{
			for (j = startPos; j < length; j++)
			{
				holder[offset + j] = (byte) value.charAt(pos);
				pos++;
			}
		}
		else
		{
			for (j = 0; j < copyLength; j++)
				holder[offset + j] = (byte) value.charAt(j);
		}

		if (startPos < length)
		{
			for (j = 0; j < startPos; j++)
				holder[offset + j] = (byte) '0';
		}
		return offset + length + 1;
	}
	public static int copyStringToByteArrayLeadWithBlank(String value, byte holder[], int offset,
			int length)
	{
		int startPos = 0;
		int j = 0;
		int pos = 0;
		int copyLength;

		if (value == null)
			return offset + length;

		if (value.length() < length)
			copyLength = value.length();
		else
			copyLength = length;

		startPos = (length - copyLength);

		if (startPos < length)
		{
			for (j = startPos; j < length; j++)
			{
				holder[offset + j] = (byte) value.charAt(pos);
				pos++;
			}
		}
		else
		{
			for (j = 0; j < copyLength; j++)
				holder[offset + j] = (byte) value.charAt(j);
		}

		if (startPos < length)
		{
			for (j = 0; j < startPos; j++)
				holder[offset + j] = (byte) ' ';
		}
		return offset + length + 1;
	}
	/**
	 * Copy String to byte array. It pads the string with zeros.
	 * @return int
	 * @param value String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyStringToByteArrayPadWithZero(String value, byte holder[], int offset,
			int length)
	{
		int i;
		int copyLength;

		if (value == null)
			return offset + length;

		if (value.length() < length)
			copyLength = value.length();
		else
			copyLength = length;

		for (i = 0; i < copyLength; i++)
			holder[offset + i] = (byte) value.charAt(i);

		for (; i < length; i++)
			holder[offset + i] = (byte) '0';
		return offset + length + 1;
	}

	/**
	 * Copy String to byte array. It pads the string with nulls.
	 * @return int
	 * @param value String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int copyStringToByteArrayPadWithNull(String value, byte holder[], int offset,
			int length)
	{
		int i;
		int copyLength;

		if (value == null)
			return offset + length;

		if (value.length() < length)
			copyLength = value.length();
		else
			copyLength = length;
		try
		{
			//db-- added support for croatian letters
			//TODO: CODEPAGE MUST BE OBTAINED IN ANOTHER WAY!!!
			byte[] valBytes = value.getBytes("Cp1250");
			for (i = 0; i < copyLength; i++)
				holder[offset + i] = valBytes[i]; //(byte) value.charAt(i);
			//--bd
			for (; i < length; i++)
				holder[offset + i] = (byte) 0;
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO {BS}Auto-generated catch block
			/* catch block body code version 0.01 */
			AS2Trace.trace(AS2Trace.E, e, "Problem J2EEFornat.copyStringToByteArrayPadWithNull");
		}
		return offset + length + 1;
	}

	/**
	 * Return Boolean from byte array as true for Y and false for N.
	 * @return Boolean
	 * @param holder byte[]
	 * @param offset int
	 */
	public static Boolean getBooleanFromByteArrayAsYN(byte holder[], int offset)
	{
		if (holder[offset] == (byte) 'Y')
			return new Boolean(true);
		else if (holder[offset] == (byte) 'N')
			return new Boolean(false);
		else
			return null;

	}

	/**
	 * Return Date from byte array converting from MMDDYYYY.
	 * @return Date
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static Date getDateFromByteArrayAsMMDDYYYY(byte holder[], int offset, int length)
	{
		String strValue = new String(holder, offset, length);

		if (strValue.trim().length() == 10)
		{
			String dat = "";
			Date value = new Date(0);
			int pos = 0;
			dat = new String(holder, offset, length).trim();
			int mm_pos = dat.indexOf('-');
			if (mm_pos == 4)
			{
				return getDateFromByteArrayAsYYYYMMDD(holder, offset, length);
			}
			else
			{
//				Integer mm = new Integer(dat.substring(pos, mm_pos));
//				int dd_pos = dat.indexOf('-', mm_pos + 1);
//				Integer dd = new Integer(dat.substring(mm_pos + 1, dd_pos));
//				Integer yy = new Integer(dat.substring(dd_pos + 1));
				//??value.setYear(yy.intValue()-1900);
				//??value.setMonth(mm.intValue()-1);
				//??value.setDate(dd.intValue());
				return value;
			}
		}
		return null;
	}

	/**
	 * Return Date from byte array converting from YYYYMMDD.
	 * @return Date
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static Date getDateFromByteArrayAsYYYYMMDD(byte holder[], int offset, int length)
	{
		String strValue = new String(holder, offset, length);

		if (strValue.trim().length() == 10)
		{
			String dat = "";
			Date value = new Date(0);
			int pos = 0;
			dat = new String(holder, offset, length).trim();
			int yy_pos = dat.indexOf('-');
			if (yy_pos == 2)
			{
				return getDateFromByteArrayAsMMDDYYYY(holder, offset, length);
			}
			else
			{
				Integer yy = new Integer(dat.substring(pos, yy_pos));
				int mm_pos = dat.indexOf('-', yy_pos + 1);
				Integer mm = new Integer(dat.substring(yy_pos + 1, mm_pos));
				Integer dd = new Integer(dat.substring(mm_pos + 1));
				//?? value.setYear(yy.intValue()-1900);
				//?? value.setMonth(mm.intValue()-1);
				//?? value.setDate(dd.intValue());
				return value;
			}
		}
		return null;
	}

	/**
	 * Return Date from byte array converting from YYYYMMDDHHMMSS.
	 * @return Date
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static Date getDateFromByteArrayAsYYYYMMDDHHMMSS(byte[] holder, int offset, int length)
	{

		String strDate = new String(holder, offset, length);
		if (strDate.trim().length() >= 19)
		{
			int year = 0;
			int month = 0;
			int date = 0;
			int hrs = 0;
			int min = 0;
			int sec = 0;
			year = (new Integer(strDate.substring(0, 4)).intValue() - 1900);
			month = (new Integer(strDate.substring(5, 7)).intValue() - 1);
			date = (new Integer(strDate.substring(8, 10)).intValue());
			hrs = (new Integer(strDate.substring(11, 13)).intValue());
			min = (new Integer(strDate.substring(14, 16)).intValue());
			sec = (new Integer(strDate.substring(17, 19)).intValue());
			//?? return new Date(year,month,date,hrs,min, sec);
		}
		return null;
	}

	/**
	 * Return Integer from byte array.
	 * @return Integer.
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static Integer getIntegerFromByteArray(byte holder[], int offset, int length)
	{
		return new Integer(getIntFromByteArray(holder, offset, length));

	}

	/**
	 * Return int from byte array.
	 * @return int
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static int getIntFromByteArray(byte holder[], int offset, int length)
	{
		int rc = 0;
		int sum = 0;
		int index;

		for (int i = 0; i < length; i++)
		{
			sum *= 10; // We have gone round another base

			index = (int) (holder[offset + i] - (byte) '0');
			if (index < 0 || index > 9)
			{
				index = 0;
				rc = 1; // We ought to classify this error
			}
			sum += index;
		}

		return sum;
	}

	/**
	 * Return String from byte array.
	 * @return String
	 * @param holder byte[]
	 * @param offset int
	 * @param length int
	 */
	public static String getStringFromByteArray(byte holder[], int offset, int length)
	{
		String str = new String(holder, offset, length).trim();

		if (str.length() != 0)
			return str;
		else
			return "";
	}

	public static void main(String[] args)
	{
		String date = "09.11.2001";
		System.out.println(date);
		date = transformDate(date);
		System.out.println(date);

	}

	public static java.lang.String stringRemoveBlanks(java.lang.String originalString)
	{
		if (originalString == null)
		{
			return originalString;
		}

		int stringLength = originalString.length();

		int count = 0;

		for (int i = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == ' '))
				count++;
		}

		byte string[] = new byte[count];
		for (int i = 0, j = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == ' '))
			{
				string[j] = (byte) originalString.charAt(i);
				j++;
			}
		}
		return new String(string);
	}
	public static java.lang.String stringRemoveDots(java.lang.String originalString)
	{
		if (originalString == null)
		{
			return originalString;
		}

		int stringLength = originalString.length();

		int count = 0;

		for (int i = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == '.'))
				count++;
		}

		byte string[] = new byte[count];
		for (int i = 0, j = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == '.'))
			{
				string[j] = (byte) originalString.charAt(i);
				j++;
			}
		}
		return new String(string);
	}

	public static java.lang.String stringReplace(java.lang.String originalString,
			java.lang.String toBeReplaced, java.lang.String replaceWith)
	{
		java.lang.String s = originalString;
		java.lang.String t = toBeReplaced;
		java.lang.String c = replaceWith;
		int tlen = t.length();
		int clen = c.length();
		if (t == null || c == null)
			return s;
		else if (t.equals(""))
			return s;
		int pos = s.indexOf(t);
		while (pos != -1)
		{
			java.lang.String result = s.substring(0, pos) + c + s.substring(pos + tlen);
			s = result;
			int next = s.substring(pos + clen).indexOf(t);
			if (next != -1)
				pos += next + clen;
			else
				pos = -1;
		}
		return s;
	}

	public static java.lang.String stringReplace(java.lang.String originalString,
			java.lang.String toBeReplaced, java.lang.String replaceWith, boolean _ignoreCase)
	{
		java.lang.String s = originalString;
		java.lang.String t = toBeReplaced;
		java.lang.String c = replaceWith;
		int tlen = t.length();
		int clen = c.length();
		if (t == null || c == null)
			return s;
		else if (t.equals(""))
			return s;
		int pos;
		if (_ignoreCase)
			pos = s.toUpperCase().indexOf(t.toUpperCase());
		else
			pos = s.indexOf(t);
		while (pos != -1)
		{
			java.lang.String result = s.substring(0, pos) + c + s.substring(pos + tlen);
			s = result;
			int next;
			if (_ignoreCase)
				next = s.substring(pos + clen).toUpperCase().indexOf(t.toUpperCase());
			else
				next = s.substring(pos + clen).indexOf(t);
			if (next != -1)
				pos += next + clen;
			else
				pos = -1;
		}
		return s;
	}

	/**
	 * mijenja datum iz oblika 20.7.2003 u 20-JUL-2003 potrebno jer Xng baza
	 * razumije samo ovaj drugi oblik datuma
	 */
	public static String transformDate(String inDate)
	{
		String month[] = new String[13];
		month[0] = "";
		month[1] = "JAN";
		month[2] = "FEB";
		month[3] = "MAR";
		month[4] = "APR";
		month[5] = "MAY";
		month[6] = "JUN";
		month[7] = "JUL";
		month[8] = "AUG";
		month[9] = "SEP";
		month[10] = "OCT";
		month[11] = "NOV";
		month[12] = "DEC";

		StringBuffer buf = new StringBuffer();
		StringTokenizer st = new StringTokenizer(inDate, ".");

		int count = 1;
		while (st.hasMoreTokens())
		{
			String _temp = st.nextToken();
			if (count == 2)
			{
				_temp = month[Integer.parseInt(_temp)];
				buf.append("-" + _temp + "-");
			}
			else
			{
				buf.append(_temp);
			}
			count++;
		}
		return buf.toString();
	}

	public static java.lang.String replaceCharacterWithinTheString(java.lang.String originalString,
			java.lang.String toBeReplaced, java.lang.String replaceWith, boolean _ignoreCase)
	{
		java.lang.String s = originalString;
		java.lang.String t = toBeReplaced;
		java.lang.String c = replaceWith;
		int tlen = t.length();
		int clen = c.length();
		if (t == null || c == null)
			return s;
		else if (t.equals(""))
			return s;
		int pos;
		if (_ignoreCase)
			pos = s.toUpperCase().indexOf(t.toUpperCase());
		else
			pos = s.indexOf(t);
		while (pos != -1)
		{
			java.lang.String result = s.substring(0, pos) + c + s.substring(pos + tlen);
			s = result;
			int next;
			if (_ignoreCase)
				next = s.substring(pos + clen).toUpperCase().indexOf(t.toUpperCase());
			else
				next = s.substring(pos + clen).indexOf(t);
			if (next != -1)
				pos += next + clen;
			else
				pos = -1;
		}
		return s;
	}

	public static java.lang.String replaceCharacterWithinTheString(java.lang.String originalString,
			java.lang.String toBeReplaced, java.lang.String replaceWith)
	{
		//return originalString.replace('0','7'));
		java.lang.String s = originalString;
		java.lang.String t = toBeReplaced;
		java.lang.String c = replaceWith;
		int tlen = t.length();
		int clen = c.length();
		if (t == null || c == null)
			return s;
		else if (t.equals(""))
			return s;
		int pos = s.indexOf(t);
		while (pos != -1)
		{
			java.lang.String result = s.substring(0, pos) + c + s.substring(pos + tlen);
			s = result;
			int next = s.substring(pos + clen).indexOf(t);
			if (next != -1)
				pos += next + clen;
			else
				pos = -1;
		}
		return s;
	}

	public static java.lang.String removeAllLeadingBlanksFromString(String originalString)
	{
		if (originalString == null)
		{
			return originalString;
		}

		int stringLength = originalString.length();
		int leadingBlanksLength = 0;

		for (int i = 0; i < stringLength; i++)
		{
			if (originalString.charAt(i) == ' ')
				leadingBlanksLength++;
			else
				break;
		}

		return originalString.substring(leadingBlanksLength);
	}

	public static java.lang.String removeAllLeadingBlanksAndTabs(String originalString)
	{
		if (originalString == null)
		{
			return originalString;
		}

		int stringLength = originalString.length();
		int leadingBlanksLength = 0;

		for (int i = 0; i < stringLength; i++)
		{
			if (originalString.charAt(i) == ' ' || originalString.charAt(i) == '\t')
				leadingBlanksLength++;
			else
				break;
		}

		return originalString.substring(leadingBlanksLength);
	}

	public static java.lang.String removeAllBlanksFromString(java.lang.String originalString)
	{
		if (originalString == null)
		{
			return originalString;
		}

		int stringLength = originalString.length();

		int count = 0;

		for (int i = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == ' '))
				count++;
		}

		byte string[] = new byte[count];
		for (int i = 0, j = 0; i < stringLength; i++)
		{
			if (!(originalString.charAt(i) == ' '))
			{
				string[j] = (byte) originalString.charAt(i);
				j++;
			}
		}
		return new String(string);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a short primitive.
	 * </p>
	 * @param value The value validation is being performed on.
	 */
	public static boolean isShort(String value)
	{
		return (formatShort(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a int primitive.
	 * </p>
	 * @param value The value validation is being performed on.
	 */
	public static boolean isInt(String value)
	{
		return (formatInt(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a long primitive.
	 * </p>
	 * @param value The value validation is being performed on.
	 */
	public static boolean isLong(String value)
	{
		return (formatLong(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a float primitive.
	 * </p>
	 * @param value The value validation is being performed on.
	 */
	public static boolean isFloat(String value)
	{
		return (formatFloat(value) != null);
	}

	/**
	 * <p>
	 * Checks if the value can safely be converted to a double primitive.
	 * </p>
	 * @param value The value validation is being performed on.
	 */
	public static boolean isDouble(String value)
	{
		return (formatDouble(value) != null);
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public static boolean isInRange(int value, int min, int max)
	{
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public static boolean isInRange(float value, float min, float max)
	{
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public static boolean isInRange(short value, short min, short max)
	{
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public static boolean isInRange(long value, long min, long max)
	{
		return ((value >= min) && (value <= max));
	}

	/**
	 * <p>
	 * Checks if a value is within a range (min &amp; max specified in the vars
	 * attribute).
	 * </p>
	 * @param value The value validation is being performed on.
	 * @param min The minimum value of the range.
	 * @param max The maximum value of the range.
	 */
	public static boolean isInRange(double value, double min, double max)
	{
		return ((value >= min) && (value <= max));
	}

	/**
	 * Checks if the value can safely be converted to a short primitive.
	 * @param value The value validation is being performed on.
	 */
	private static Short formatShort(String value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Short(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a int primitive.
	 * @param value The value validation is being performed on.
	 */
	private static Integer formatInt(String value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Integer(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a long primitive.
	 * @param value The value validation is being performed on.
	 */
	private static Long formatLong(String value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Long(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a float primitive.
	 * @param value The value validation is being performed on.
	 */
	private static Float formatFloat(String value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Float(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

	}

	/**
	 * Checks if the value can safely be converted to a double primitive.
	 * @param value The value validation is being performed on.
	 */
	private static Double formatDouble(String value)
	{
		if (value == null)
		{
			return null;
		}

		try
		{
			return new Double(value);
		}
		catch (NumberFormatException e)
		{
			return null;
		}

	}

	public static final String discardNonNumbers(String s)
	{
		String ret = ""; //$NON-NLS-1$
		for (int i = 0; i < s.length(); i++)
		{
			if ("0123456789".indexOf(s.charAt(i)) >= 0) //$NON-NLS-1$
			{
				ret += s.charAt(i);
			}
		}
		return ret;
	}

	/**
	 * Splits a String into lines of length <i>length <i>. If length is 2 or
	 * less the method will use 3 for line length!
	 * @param text
	 * @param length duljina linije
	 * @param align je CENTER, RIGHT ili LEFT
	 * @return
	 * @author Tomo Krajina
	 */
	public static Vector splitStringToLines(String text, int length)
	{
		Vector lines = new Vector();
		length = Math.abs(length);
		if (length < 3)
		{
			length = 3;
		}

		try
		{
			String[] words = text.split(" ");

			String line = null;
			int i = 0;
			while (i < words.length)
			{
				line = "";
				//if (i < words.length) {
				while (line.length() + words[i].length() < length)
				{
					line += " " + words[i];
					++i;
					if (i >= words.length)
					{
						break;
					}
				}
				if (line.length() > 0)
				{
					lines.add(line.trim());
				}
				try
				{
					if (words[i].length() >= length)
					{
						line = words[i].substring(0, length - 1);
						lines.add(line.trim());
						words[i] = words[i].substring(length - 1);
					}
					else
					{
						;
					}
				}
				catch (Exception e)
				{
					AS2Trace.trace(AS2Trace.E, e, "Problem spliting text: " + text);
				}
			}
		}
		catch (Exception e)
		{
			AS2Trace.trace(AS2Trace.E, e, "Problem spliting text: " + text);
		}
		return lines;
	}

	/**
	 * Connverts byte arrays between diferent charsets <br/>
	 * @param from
	 * @return
	 */
	public static byte[] convertToBytes(byte[] data, String fromCharsetName, String toCharsetName)
	{

		try
		{

			// converting to unicode:
			Charset charset = Charset.forName(fromCharsetName);
			CharBuffer cb = charset.decode(MappedByteBuffer.wrap(data));
			// converting data unicode:
			cb.array(); // tu su sad podacu u UTF8, sad to treba u zeljeni
			charset = Charset.forName(toCharsetName);
			ByteBuffer bb = charset.encode(cb);
			int i = 0;
			try
			{
				// sometimes at the end of the array charset.encode() leaves
				// null bytes;
				while ((int) bb.array()[i] != 0)
				{
					++i;
				}
				byte[] result = new byte[i];
				System.arraycopy(bb.array(), 0, result, 0, i);
				return result;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				AS2Trace.trace(AS2Trace.E, e, "Problem (1) coverting bytearray: "
						+ fromCharsetName + "to " + toCharsetName);
				return bb.array();
			}
		}
		catch (Exception e)
		{
			AS2Trace.trace(AS2Trace.E, e, "Problem (2) coverting bytearray: " + fromCharsetName
					+ "to " + toCharsetName);
			return new byte[1]; //default
		}
	}

	public static String convertToString(byte[] data, String fromCharsetName, String toCharsetName)
	{
		return new String(convertToBytes(data, fromCharsetName, toCharsetName));
	}
}
