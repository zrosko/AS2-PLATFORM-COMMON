package hr.as2.inf.common.types;

import hr.as2.inf.common.exceptions.AS2Exception;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AS2Type {
	/** Used to zero-fill the fractional seconds to nine digits. */
	private static final String ZEROES = "000000000";

	/**
	 * Convert the value to a instance of the given type. The value is converted
	 * only if the type can't be assigned from the current type of the value
	 * instance.
	 */
	public static Object convertTo(Object value, Class<?> type) {
		if (!type.isAssignableFrom(value.getClass())) {
			if (type == short.class || type == Short.class) {
				return Short.valueOf(value.toString());
			} else if (type == char.class || type == Character.class) {
				return Character.valueOf(value.toString().charAt(0));
			} else if (type == int.class || type == Integer.class) {
				return Integer.valueOf(value.toString());
			} else if (type == long.class || type == Long.class) {
				return Long.valueOf(value.toString());
			} else if (type == boolean.class || type == Boolean.class) {
				return Boolean.valueOf(value.toString());
			} else if (type == byte.class || type == Byte.class) {
				return Byte.valueOf(value.toString());
			} else if (type == float.class || type == Float.class) {
				return Float.valueOf(value.toString());
			} else if (type == double.class || type == Double.class) {
				return Double.valueOf(value.toString());
			} else if (type == BigDecimal.class) {
				return new BigDecimal(value.toString());
			} else if (type == BigInteger.class) {
				return new BigInteger(value.toString());
			} else if (type == String.class) {
				return value.toString();
			} else {
				return null;
			}
		}
		return value;
	}

	/**
	 * Utility to convert an int into a byte-generated String
	 */
	public static String getStringFromInt(int val) {
		byte[] arr = new byte[4];
		for (int i = 3; i >= 0; i--) {
			arr[i] = (byte) ((0xFFl & val) + Byte.MIN_VALUE);
			val >>>= 8;
		}
		return new String(arr);
	}

	/**
	 * Converts a string in JDBC timestamp escape format to a Timestamp object.
	 * To be precise, we prefer to find a JDBC escape type sequence in the
	 * format "yyyy-mm-dd hh:mm:ss.fffffffff", but this does accept other
	 * separators of fields, so as long as the numbers are in the order year,
	 * month, day, hour, minute, second then we accept it.
	 */
	public static Timestamp stringToTimestamp(String s) {
		return stringToTimestamp(s, new GregorianCalendar());
	}

	public static Timestamp stringToTimestamp(String s, Calendar cal) {
		int[] numbers = AS2String.convertStringToIntArray(s);
		if (numbers == null || numbers.length < 6) {
			throw new AS2Exception();// IllegalArgumentException(Localiser.msg("030003",
										// s));
		}

		int year = numbers[0];
		int month = numbers[1];
		int day = numbers[2];
		int hour = numbers[3];
		int minute = numbers[4];
		int second = numbers[5];
		int nanos = 0;
		if (numbers.length > 6) {
			String zeroedNanos = "" + numbers[6];
			if (zeroedNanos.length() < 9) {
				// Add trailing zeros
				int numZerosToAdd = 9 - zeroedNanos.length();
				for (int i = 0; i < numZerosToAdd; i++) {
					zeroedNanos += "0";
				}
				nanos = Integer.valueOf(zeroedNanos);
			} else {
				nanos = numbers[6];
			}
		}

		Calendar thecal = cal;
		if (cal == null) {
			thecal = new GregorianCalendar();
		}
		thecal.set(Calendar.ERA, GregorianCalendar.AD);
		thecal.set(Calendar.YEAR, year);
		thecal.set(Calendar.MONTH, month - 1);
		thecal.set(Calendar.DATE, day);
		thecal.set(Calendar.HOUR_OF_DAY, hour);
		thecal.set(Calendar.MINUTE, minute);
		thecal.set(Calendar.SECOND, second);
		Timestamp ts = new Timestamp(thecal.getTime().getTime());
		ts.setNanos(nanos);

		return ts;
	}

	/**
	 * Formats a timestamp in JDBC timestamp escape format using the timezone of
	 * the passed Calendar.
	 */
	public static String timestampToString(Timestamp s) {
		return timestampToString(s, new GregorianCalendar());
	}

	public static String timestampToString(Timestamp ts, Calendar cal) {

		cal.setTime(ts);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1; // Months are zero based in
													// Calendar
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		String yearString = Integer.toString(year);
		String monthString = month < 10 ? "0" + month : Integer.toString(month);
		String dayString = day < 10 ? "0" + day : Integer.toString(day);
		String hourString = hour < 10 ? "0" + hour : Integer.toString(hour);
		String minuteString = minute < 10 ? "0" + minute : Integer
				.toString(minute);
		String secondString = second < 10 ? "0" + second : Integer
				.toString(second);
		String nanosString = Integer.toString(ts.getNanos());

		if (ts.getNanos() != 0) {
			// Add leading zeroes
			nanosString = ZEROES.substring(0,
					ZEROES.length() - nanosString.length())
					+ nanosString;

			// Truncate trailing zeroes
			int truncIndex = nanosString.length() - 1;
			while (nanosString.charAt(truncIndex) == '0') {
				--truncIndex;
			}

			nanosString = nanosString.substring(0, truncIndex + 1);
		}

		return (yearString + "-" + monthString + "-" + dayString + " "
				+ hourString + ":" + minuteString + ":" + secondString + "." + nanosString);
	}

}
