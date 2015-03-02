package hr.as2.inf.common.types;

import hr.as2.inf.common.logging.AS2Trace;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AS2Date extends java.util.Date {

	private static final long serialVersionUID = 1L;
	public static final String AS2_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String AS2_DATE_FORMAT = "yyyy-MM-dd";
	public static final SimpleDateFormat date_time_format = new SimpleDateFormat(
			AS2_DATE_TIME_FORMAT);
	public static final SimpleDateFormat date_format = new SimpleDateFormat(
			AS2_DATE_FORMAT);

	public static final String AS400_TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSSSSS"; //$NON-NLS-1$
	public static final String ZERO_TIME = " 00:00:00.000000"; //$NON-NLS-1$
	public static final String INFINITY_TIMESTAMP = "2999-12-31 00:00:00.000000"; //$NON-NLS-1$
	public static final String INFINITY_DATE = "2999-12-31"; //$NON-NLS-1$
	private static Timestamp _infinityTimestamp = null;
	private static Date _infinityDate = null;
	public final static String DEFAULT_DATE_FORMAT = "dd.MM.yyyy"; //$NON-NLS-1$
	public final static String DEFAULT_DATE_FORMAT_EN = "yyyy-MM-dd"; //$NON-NLS-1$
	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S"; //$NON-NLS-1$
	private static Date _changedCurrentDate = new Date(
			System.currentTimeMillis());

	public AS2Date() {
		super();
	}

	/**
	 * @deprecated
	 * @param year
	 * @param month
	 * @param date
	 */
	/*
	 * constructor comment version 0.01
	 */
	public AS2Date(int year, int month, int date) {
		super(year, month, date);
	}

	/**
	 * @deprecated
	 * @param year
	 * @param month
	 * @param date
	 * @param hrs
	 * @param min
	 */
	/*
	 * constructor comment version 0.01
	 */
	public AS2Date(int year, int month, int date, int hrs, int min) {
		super(year, month, date, hrs, min);
	}

	/**
	 * @deprecated
	 * @param year
	 * @param month
	 * @param date
	 * @param hrs
	 * @param min
	 * @param sec
	 */
	/*
	 * constructor comment version 0.01
	 */
	public AS2Date(int year, int month, int date, int hrs, int min, int sec) {
		super(year, month, date, hrs, min, sec);
	}

	public AS2Date(long date) {
		super(date);
	}

	/**
	 * @deprecated
	 * @param s
	 */
	/*
	 * constructor comment version 0.01
	 */
	public AS2Date(String s) {
		super(s);
	}

	/*
	 * It is used when use wants to set up differnet current date in context.
	 * Then in the code which uses this logic, need to get/set the date using
	 * this methods.
	 */
	public static Date getChangedCurrentDate() {
		return _changedCurrentDate;
	}

	public static void setChangedCurrentDate(Date date) {
		_changedCurrentDate = date;
	}

	public static Calendar getTodayAsCalendar() {
		Calendar _calendar = new GregorianCalendar();
		_calendar.setTime(getCurrentDate());
		return _calendar;
	}

	public static Calendar getTommorowAsCalendar() {
		Calendar _calendar = new GregorianCalendar();
		_calendar.setTime(getNextDate(getCurrentDate()));
		return _calendar;
	}

	public static Calendar getYesterdayAsCalendar() {
		Calendar _calendar = new GregorianCalendar();
		_calendar.setTime(getPreviousDate(getCurrentDate()));
		return _calendar;
	}

	public static Calendar getYearAgoAsCalendar() {
		Calendar _calendar = new GregorianCalendar();
		_calendar = new GregorianCalendar(_calendar.get(Calendar.YEAR) - 1,
				_calendar.get(Calendar.MONTH),
				_calendar.get(Calendar.DAY_OF_MONTH));
		return _calendar;
	}

	public static Calendar getMonthAgoAsCalendar() {
		Calendar _calendar = new GregorianCalendar();
		_calendar = new GregorianCalendar(_calendar.get(Calendar.YEAR),
				_calendar.get(Calendar.MONTH) - 1,
				_calendar.get(Calendar.DAY_OF_MONTH));
		return _calendar;
	}

	/**
	 * Compares two dates. For time comparison use the Calendar or Date object
	 * from java.util package.
	 * 
	 * @param oneDate
	 *            the Date to be compared with twoDate.
	 * @param twoDate
	 *            the Date to be compared with oneDate.
	 * @return true if the oneDate (not time) is equal the twoDate; false
	 *         otherwise.
	 */
	public static boolean areTwoDatesEqual(java.util.Date oneDate,
			java.util.Date twoDate) {

		java.util.GregorianCalendar aCal = new java.util.GregorianCalendar();
		aCal.setTime(oneDate);

		java.util.GregorianCalendar bCal = new java.util.GregorianCalendar();
		bCal.setTime(twoDate);

		return equalsCalendars(aCal, bCal);
	}

	public static java.sql.Date convertDateFromString(String aDate) {
		if (aDate == null || aDate.trim().equals("")) //$NON-NLS-1$
			return null;

		java.sql.Date mDate = null;

		try {
			mDate = java.sql.Date.valueOf(aDate);
		} catch (Exception e) {
			// db - changed to utilize our date formatter
			// mDate = new java.sql.Date(java.util.Date.parse(aDate));
			mDate = new java.sql.Date(convert(aDate).getTime());
			// bd
		}
		return mDate;
	}

	/**
	 * @deprecated Created by: CU {BS} <br>
	 *             Created on: Feb 9, 2005 <br>
	 * <br>
	 *             Description: <br>
	 * <br>
	 * @param aTimestamp
	 * @return <br>
	 * <br>
	 *         ToDo: (change to uppercase for task view) <br>
	 */
	/*
	 * method comment version 0.01
	 */
	public static java.sql.Timestamp convertTimestampFromString(
			String aTimestamp) {
		if (aTimestamp == null || aTimestamp.trim().length() == 0)
			return null;

		java.sql.Timestamp mTimestamp = null;

		try {
			mTimestamp = java.sql.Timestamp.valueOf(aTimestamp);
		} catch (Exception e) {
			try {
				mTimestamp = new java.sql.Timestamp(
						java.sql.Timestamp.parse(aTimestamp));
			} catch (Exception e1) {
				java.sql.Date temp_date = convertDateFromString(aTimestamp);
				mTimestamp = new java.sql.Timestamp(temp_date.getTime());
			}
		}
		return mTimestamp;
	}

	public static String covertDateToString(java.sql.Date aDate) {
		if (aDate instanceof java.sql.Date) {

		}
		return "01-jan-71"; //$NON-NLS-1$

	}

	/**
	 * Compares two Calendars.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @param twoDate
	 *            the Date to be compared with the oneDate.
	 * @return true if the oneDate (not time) is equal the twoDate; false
	 *         otherwise.
	 */
	public static boolean equalsCalendars(java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		// check if dates are equal.

		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900)
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) == twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}

	public static boolean equalsCalendars(Calendar oneDate, Calendar twoDate) {
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900)
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) == twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		return false;
	}

	/**
	 * Compares a Date with the current Date. For time comparison use the
	 * Calendar or Date object from java.util package.
	 * 
	 * @param aDate
	 *            the Date to be compared with the current Date.
	 * @return true if the aDate (not time) is equal the current Date; false
	 *         otherwise.
	 */
	public static boolean equalsToTodayDate(java.util.Date aDate) {

		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(aDate);

		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(new java.util.Date());

		return equalsCalendars(inCal, nowCal);
	}

	public static int getCurrentYear() {
		return (new GregorianCalendar()).get(GregorianCalendar.YEAR);
	}

	public static int getCurrentDayOfMonth() {
		return (new GregorianCalendar()).get(GregorianCalendar.DAY_OF_MONTH);
	}

	public static int getCurrentDayOfWeek() {
		return (new GregorianCalendar()).get(GregorianCalendar.DAY_OF_WEEK);
	}

	public static int getCurrentMonth() {
		return (new GregorianCalendar()).get(GregorianCalendar.MONTH) + 1;
	}

	public static int getCurrentDayOfYear() {
		return (new GregorianCalendar()).get(GregorianCalendar.DAY_OF_YEAR);
	}

	public static java.sql.Date getCurrentDate() {
		java.sql.Date currentDate = new java.sql.Date(
				System.currentTimeMillis());
		return currentDate;
	}

	public static Timestamp getCurrentTime() {
		Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
		return currentTime;
	}

	public static java.util.Calendar getInfinityCalendar() {
		GregorianCalendar newCal = new GregorianCalendar();
		java.sql.Date inifinityDate = getInfinityDate();
		newCal.setTime(inifinityDate);
		return newCal;
	}

	public static java.sql.Date getInfinityDate() {

		if (_infinityDate == null)
			_infinityDate = java.sql.Date.valueOf(INFINITY_DATE);
		return _infinityDate;
	}

	public static Timestamp getInfinityTimestamp() {

		if (_infinityTimestamp == null)
			_infinityTimestamp = Timestamp.valueOf(INFINITY_TIMESTAMP);
		return _infinityTimestamp;
	}

	public static java.sql.Date getNextDate() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		return getNextDate(date);
	}

	public static java.sql.Date getNextDate(java.sql.Date d) {
		GregorianCalendar newCal = new GregorianCalendar();
		newCal.setTime(d);
		newCal.add(Calendar.DATE, 1);
		java.sql.Date nextDate = new java.sql.Date(newCal.getTime().getTime());
		return nextDate;
	}

	public static java.util.Calendar getNextYearAsCalendar() {
		GregorianCalendar newCal = new GregorianCalendar();
		newCal.add(Calendar.YEAR, 1);
		return newCal;
	}

	public static java.sql.Date getPreviousDate() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		return getPreviousDate(date);
	}

	public static java.sql.Date getPreviousDate(java.sql.Date d) {
		GregorianCalendar newCal = new GregorianCalendar();
		newCal.setTime(d);
		newCal.add(Calendar.DATE, -1);
		java.sql.Date previousDate = new java.sql.Date(newCal.getTime()
				.getTime());
		return previousDate;
	}

	public static Timestamp getPreviousTime() {
		Timestamp previousTime = new Timestamp(System.currentTimeMillis() - 1);
		return previousTime;
	}

	public static Timestamp getPreviousTime(Timestamp t) {
		Timestamp previousTime = new Timestamp(t.getTime() - 1);
		return previousTime;
	}

	public static Timestamp getTimestampFromDate(String d) {

		return Timestamp.valueOf(d + ZERO_TIME);
	}

	public static Date getSqlDate(int year, int month, int day) {
		Calendar calendar;
		calendar = new GregorianCalendar(year, month - 1, day);
		return new java.sql.Date(calendar.getTime().getTime());

	}

	public static String getFormattedDateAsString(java.util.Date date,
			String format) {

		SimpleDateFormat formatter1 = new SimpleDateFormat(format);
		String strDay = formatter1.format(date);
		return strDay;

	}

	public static int getYearFromDate(java.util.Date date) {
		Calendar calendar = new GregorianCalendar();
		if (date != null) {
			calendar.setTime(date);
			return calendar.get(Calendar.YEAR);
		} else
			return -1;

	}

	public static int getMonthFromDate(java.util.Date date) {
		Calendar calendar = new GregorianCalendar();
		if (date != null) {
			calendar.setTime(date);
			return calendar.get(Calendar.MONTH) + 1;
		} else
			return -1;

	}

	/**
	 * Compares two Calendars.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @param twoDate
	 *            the Date to be compared with the oneDate.
	 * @return true if the oneDate (not time) is greater the twoDate; false
	 *         otherwise.
	 */
	public static boolean greaterCalendars(java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		if (oneDate.get(Calendar.YEAR) - 1900 > twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) > twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) > twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	public static boolean greaterOrEqualsCalendars(java.util.Calendar oneDate,
			java.util.Calendar twoDate) {
		if (oneDate.get(Calendar.YEAR) - 1900 > twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) > twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) >= twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	/**
	 * Compares two Calendars.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @param twoDate
	 *            the Date to be compared with the oneDate.
	 * @return true if the oneDate (not time) is greater the twoDate; false
	 *         otherwise.
	 */
	public static boolean greaterOrEqualsCalendars(
			java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		if (oneDate.get(Calendar.YEAR) - 1900 > twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) > twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) >= twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	/**
	 * Compares a oneDate with the twoDate. For time comparison use the Calendar
	 * or Date object from java.util package.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @return true if the a oneDate (not time) is after the twoDate; false
	 *         otherwise.
	 */
	public static boolean greaterOrEqualToDate(java.util.Date oneDate,
			java.util.Date twoDate) {

		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(oneDate);

		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(twoDate);

		// check if date is greater than or equals today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return greaterOrEqualsCalendars(inCal, nowCal);

	}

	/**
	 * Compares a Date with the current Date. For time comparison use the
	 * Calendar or Date object from java.util package.
	 * 
	 * @param aDate
	 *            the Date to be compared with the current Date.
	 * @return true if the a aDate (not time) is after the current Date; false
	 *         otherwise.
	 */
	public static boolean greaterOrEqualToTodayDate(java.util.Date aDate) {

		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(aDate);

		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(new java.util.Date());

		// check if date is greater than or equals today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return greaterOrEqualsCalendars(inCal, nowCal);

	}

	/**
	 * Compares two Calendars.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @param twoDate
	 *            the Date to be compared with the oneDate.
	 * @return true if the oneDate (not time) is greater the twoDate; false
	 *         otherwise.
	 */
	public static boolean greaterThanCalendars(
			java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		if (oneDate.get(Calendar.YEAR) - 1900 > twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) > twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) > twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	/**
	 * Compares a oneDate with the twoDate. For time comparison use the Calendar
	 * or Date object from java.util package.
	 * 
	 * @param oneDate
	 *            the Date to be compared with the twoDate.
	 * @return true if the a oneDate (not time) is after the twoDate; false
	 *         otherwise.
	 */
	public static boolean greaterThanDate(java.util.Date oneDate,
			java.util.Date twoDate) {

		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(oneDate);

		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(twoDate);

		// check if date is greater than or equals today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return greaterThanCalendars(inCal, nowCal);

	}

	/**
	 * Compares a Date with the current Date. For time comparison use the
	 * Calendar or Date object from java.util package.
	 * 
	 * @param aDate
	 *            the Date to be compared with the current Date.
	 * @return true if the a aDate (not time) is after the current Date; false
	 *         otherwise.
	 */
	public static boolean greaterThanTodayDate(java.util.Date aDate) {

		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(aDate);

		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(new java.util.Date());

		// check if date is greater than or equals today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return greaterThanCalendars(inCal, nowCal);

	}

	/**
	 * Compares a Timestamp retrieved from Data Base with the Timestamp from the
	 * client Request sent to server.
	 * 
	 * @param fromDB
	 *            a Timestamp to be compared with the client one.
	 * @param fromClient
	 *            a Timestamp to be compared with the DB one.
	 * @return true if the fromDB is not the same as the fromClient. false
	 *         otherwise.
	 */
	public static boolean isTimestampUpdated(Timestamp fromDB, String fromClient) {
		Timestamp mFromClient = Timestamp.valueOf(fromClient);
		if (fromDB.equals(mFromClient))
			return false;
		return true;
	}

	public static boolean lessOrEqualsCalendars(
			java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		if (oneDate.get(Calendar.YEAR) - 1900 < twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) < twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) <= twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	public static boolean lessOrEqualToTodayDate(java.util.Date aDate) {
		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(aDate);
		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(new java.util.Date());

		// check if date is less than or equals today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return lessOrEqualsCalendars(inCal, nowCal);
	}

	public static boolean lessThanCalendars(
			java.util.GregorianCalendar oneDate,
			java.util.GregorianCalendar twoDate) {

		if (oneDate.get(Calendar.YEAR) - 1900 < twoDate.get(Calendar.YEAR) - 1900)
			return true;
		if (oneDate.get(Calendar.YEAR) - 1900 == twoDate.get(Calendar.YEAR) - 1900) {
			if (oneDate.get(Calendar.MONTH) < twoDate.get(Calendar.MONTH))
				return true;
			if (oneDate.get(Calendar.MONTH) == twoDate.get(Calendar.MONTH))
				if (oneDate.get(Calendar.DAY_OF_MONTH) < twoDate
						.get(Calendar.DAY_OF_MONTH))
					return true;
		}
		return false;
	}

	public static boolean lessThanTodayDate(java.util.Date aDate) {
		java.util.GregorianCalendar inCal = new java.util.GregorianCalendar();
		inCal.setTime(aDate);
		java.util.GregorianCalendar nowCal = new java.util.GregorianCalendar();
		nowCal.setTime(new java.util.Date());

		// check if date is less than today's date
		// if(aDate.before(Calendar.getInstance().getTime())){
		return lessThanCalendars(inCal, nowCal);
	}

	/**
	 * <p>
	 * Checks if the field is a valid date. The pattern is used with
	 * <code>java.text.SimpleDateFormat</code>. If strict is true, then the
	 * length will be checked so '2/12/1999' will not pass validation with the
	 * format 'MM/dd/yyyy' because the month isn't two digits. The setLenient
	 * method is set to <code>false</code> for all.
	 * </p>
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param datePattern
	 *            The pattern passed to <code>SimpleDateFormat</code>.
	 * @param strict
	 *            Whether or not to have an exact match of the datePattern.
	 */
	public static java.util.Date formatDate(String value, String datePattern,
			boolean strict) {
		java.util.Date date = null;

		if (value == null || datePattern == null || datePattern.length() == 0) {
			return null;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
			formatter.setLenient(false);

			date = formatter.parse(value);

			if (strict) {
				if (datePattern.length() != value.length()) {
					date = null;
				}
			}
		} catch (ParseException e) {
			// Bad date so return null
			AS2Trace.trace(AS2Trace.E, value + e);
		}

		return date;
	}

	/**
	 * <p>
	 * Checks if the field is a valid date. The <code>Locale</code> is used with
	 * <code>java.text.DateFormat</code>. The setLenient method is set to
	 * <code>false</code> for all.
	 * </p>
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param locale
	 *            The Locale to use to parse the date (system default if null)
	 */
	public static java.util.Date formatDate(String value, Locale locale) {
		java.util.Date date = null;

		if (value == null) {
			return null;
		}

		try {
			DateFormat formatter = null;
			if (locale != null) {
				formatter = DateFormat
						.getDateInstance(DateFormat.SHORT, locale);
			} else {
				formatter = DateFormat.getDateInstance(DateFormat.SHORT,
						Locale.getDefault());
			}

			formatter.setLenient(false);

			date = formatter.parse(value);
		} catch (ParseException e) {
			// Bad date so return null
			AS2Trace.trace(AS2Trace.E, value + e);
		}

		return date;
	}

	public String toString() {
		// Insert code to print the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		return super.toString();
	}

	public static String getDefaultDateFormat() {
		return DEFAULT_DATE_FORMAT;
	}
	
	public static String getDefaultTimestampFormat() {
		return DEFAULT_TIMESTAMP_FORMAT;
	}

	public static String convert(java.util.Date inDate) {
		if (inDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			return sdf.format(inDate);
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	public static String convert2(java.util.Date inDate) {
		if (inDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					DEFAULT_TIMESTAMP_FORMAT);
			String _date;
			try {
				_date = sdf.format(inDate);
				return _date;
			} catch (Exception e) {
				try {
					sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
					_date = sdf.format(inDate);
					return _date;
				} catch (Exception e1) {
					return null;
				}
			}
		} else {
			return null; //$NON-NLS-1$
		}
	}

	public static java.util.Date convertDateOrTimestamp(String inString) {
		java.util.Date _date = convert(inString, DEFAULT_DATE_FORMAT);
		if (_date == null) {
			_date = convert(inString, DEFAULT_TIMESTAMP_FORMAT);
		}
		return _date;
	}

	public static java.util.Date convert(String inString) {
		return convert(inString, DEFAULT_DATE_FORMAT);
	}

	public static java.util.Date convert(String inString, String dateFormat) {
		if (inString != null && !inString.equals("")) //$NON-NLS-1$
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try {
				return sdf.parse(inString);
			} catch (ParseException e) {
				e.printStackTrace();
				// TODO: LOG ERROR
				// J2EETrace.trace(J2EETrace.E, "J2EEDate.convert" + inString +
				// dateFormat + e);
			}
		}
		return null;
	}

	/**
	 * Created by: CU {BS} <br>
	 * Created on: 2005.04.15 <br>
	 * <br>
	 * Description: <br>
	 * <br>
	 * 
	 * @param propertyAsString
	 * @return <br>
	 * <br>
	 *         ToDo: (change to uppercase for task view) <br>
	 */
	/* method comment version 0.02 */
	public static Date convert2DateFromTimeStampString(String propertyAsString) {
		java.util.Date dt = convert(propertyAsString, AS400_TIMESTAMP_FORMAT);
		if (dt != null)
			return new java.sql.Date(dt.getTime());
		return null;
	}

	public static String parseDateFromDDMMYYYY(String ddmmyyyy)
			throws NumberFormatException {
		if (ddmmyyyy.length() != 8) {
			AS2Trace.trace(AS2Trace.E,
					"J2EEDate.parseDateFromDDMMYYYY length is less than 8" //$NON-NLS-1$
							+ ddmmyyyy);
			throw new NumberFormatException();
		}

		String d = ddmmyyyy.substring(0, 2);
		String m = ddmmyyyy.substring(2, 4);
		String y = ddmmyyyy.substring(4, 8);

		return (new StringBuffer()).append(d).append('.').append(m).append('.')
				.append(y).toString();
	}

	public static Date convertCalendarToDate(Calendar calendar) {
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static String convertCalendarTimeToString(Calendar value) {
		try {
			long c_time = value.getTimeInMillis();
			return c_time + "";
		} catch (Exception e) {
			return "";
		}
	}

	public static String convertDateTimeToStringDate(java.util.Date value) {
		try {
			Timestamp ts = new Timestamp(value.getTime());
			return ts.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public static String getCurrentTimeMillisec() {
		return new String(new java.util.Date().getTime() + "");
	}

	public static String getCurrentDateAsString() {
		Timestamp ts = new Timestamp(new java.util.Date().getTime());
		return ts.toString().substring(0, 11);
	}

	public static String getCurrentDateString() {
		Timestamp ts = new Timestamp(new java.util.Date().getTime());
		return ts.toString().substring(0, 10);
	}

	public static String getCurrentTimeAsString() {
		Timestamp ts = new Timestamp(new java.util.Date().getTime());
		return ts.toString().substring(11);
	}

	public static String getCurrentDateTimeAsString() {
		Timestamp ts = new Timestamp(new java.util.Date().getTime());
		return ts.toString();
	}

	public static String convertCalendarDateToString(Calendar value) {
		try {
			Date _date = convertCalendarToDate(value);
			String _value = _date.toString();
			return _value;
		} catch (Exception e) {
			return "";
		}
	}

	public static Calendar convertDateToCalendar(Date value) {
		Calendar _calendar = new GregorianCalendar();
		_calendar.setTime(value);
		return _calendar;
	}

	public static Calendar convertDateToCalendar(java.util.Date value) {
		Calendar _calendar = new GregorianCalendar();
		_calendar.setTime(value);
		return _calendar;
	}

	public static Calendar convertDateStringToCalendar(String value) {
		try {
			Date _date = convertDateFromString(value);
			Calendar _calendar = new GregorianCalendar();
			_calendar.setTime(_date);
			return _calendar;
		} catch (Exception e) {
			return new GregorianCalendar();
		}
	}

	public static boolean isValidDate(String date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		java.util.Date testDate = null;
		try {
			testDate = sdf.parse(date);
		} catch (ParseException e) {
			return false;
		}
		if (!sdf.format(testDate).equals(date)) {
			return false;
		}
		return true;
	}

	public static String formatStringDateToAny(String date_in, String dateFormat) {
		if (date_in == null || date_in.length() == 0) {
			return "";
		}
		Timestamp ts = convertTimestampFromString(date_in);
		if (dateFormat == null)
			dateFormat = "dd.MM.yyyy"; //$NON-NLS-1$
		SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		return shortDateFormat.format(new Date(ts.getTime()));
	}

	public static String formatStringDateToDDMMYYY(String date_in) {
		if (date_in == null || date_in.length() == 0) {
			return "";
		}
		return formatStringDateToAny(date_in, "dd.MM.yyyy");
		// zr.14.10.2010. change to call new formatStringDateToAny
		// Timestamp ts = convertTimestampFromString(date_in);
		//	    String dateFormat = "dd.MM.yyyy"; //$NON-NLS-1$
		// SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		// return shortDateFormat.format(new Date(ts.getTime()));
	}

	public static Calendar convertTimeStringToCalendar(String value) {
		try {
			Calendar _calendar = new GregorianCalendar();
			_calendar.setTimeInMillis(Long.parseLong(value));
			return _calendar;
		} catch (Exception e) {
			return new GregorianCalendar();
		}
	}

	public static Calendar setCalendarTimeToStartOfDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar setCalendarTimeToEndOfDay(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal;
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static int getLastDayOfMonth(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public static int getDaysDiff(Calendar from, Calendar to) {
		long diff = to.getTimeInMillis() - from.getTimeInMillis();
		int diffDays = (int) (diff / (24 * 1000 * 60 * 60));
		return diffDays;
	}

	public static int getDaysBeetween(java.util.Date paramDate1,
			java.util.Date paramDate2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate1);
		localCalendar.set(11, 12);
		localCalendar.set(12, 0);
		int i = (int) Math.floor(localCalendar.getTimeInMillis() / 86400000L);
		localCalendar.setTime(paramDate2);
		localCalendar.set(11, 12);
		localCalendar.set(12, 0);
		int j = (int) Math.floor(localCalendar.getTimeInMillis() / 86400000L);
		return Math.abs(j - i);
	}

	public static void main(String[] args) {

		// if(J2EEDate.greaterOrEqualToDate(J2EEDate.convertDateFromString("28-dec-99"),
		System.out.println(AS2Date.convertDateFromString("2012-12-18"));// "2017-01-01 00:00:00.000"));
		// Timestamp fromDB = new Timestamp(new java.util.Date().getTime());
		// Timestamp fromCL = new Timestamp(new java.util.Date().getTime());

		// if(J2EEDate.isTimestampUpdated(fromDB,"1999-12-14 15:40:03.118"))
		// if(J2EEDate.isTimestampUpdated(fromDB,fromCL.toString()))

		// String s = convertCalendarDateToString(new GregorianCalendar());
		//
		// Calendar c = convertDateStringToCalendar(s);
		// Date d = convertCalendarToDate(c);
		// c = convertDateToCalendar(d);
		// s = convertCalendarTimeToString(c);
		// System.out.println(J2EEDate.getCurrentTimeAsString());
		// System.out.println(J2EEDate.getCurrentDateAsString());
		// System.out.println(J2EEDate.getCurrentDateTimeAsString());
		// Calendar from = J2EEDate.convertDateStringToCalendar("12.11.2011");
		// Calendar to = J2EEDate.convertDateStringToCalendar("12.12.2012");
		// System.out.println(getDaysDiff(from,to));
		// java.util.Date paramDate1 = new java.util.Date();
		// paramDate1.setTime(from.getTimeInMillis());
		// java.util.Date paramDate2 = new java.util.Date();
		// paramDate2.setTime(to.getTimeInMillis());
		// System.out.println(getDaysBeetween(paramDate1,paramDate2));
	}

	// TODO NOVO ISPOD
	/**
	 * With hour to 0. If day, month and year are 0 return null.
	 */
	public static java.util.Date create(int day, int month, int year) {
		return create(day, month, year, 0, 0, 0);
	}

	/**
	 * If day, month and year are 0 return null.
	 */
	public static java.util.Date create(int day, int month, int year,
			int hourofday, int minute, int second) {
		if (day == 0 && month == 0 && year == 0)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hourofday, minute, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Returns the year (4 digits) of date. <o>
	 * 
	 * If date is null returns 0.
	 */
	public static int getYear(Date date) {
		if (date == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Put the year to the date.
	 * <p>
	 * 
	 * If date is null it has no effect (but no exception is thrown)
	 */
	public static void setYear(Date date, int year) {
		if (date == null)
			return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, year);
	}

	/**
	 * Returns the month (1 to 12) of date.
	 * <p>
	 * 
	 * If date is null returns 0.
	 */
	public static int getMonth(Date date) {
		if (date == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * Put the month (1 to 12) to the date.
	 * <p>
	 * 
	 * If date is null it has no effect (but no exception is thrown)
	 */
	public static void setMonth(Date date, int month) {
		if (date == null)
			return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, month - 1);
	}

	/**
	 * Returns the day of date.
	 * <p>
	 * 
	 * If date is null return 0.
	 */
	public static int getDay(Date date) {
		if (date == null)
			return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Put the day to the date.
	 * <p>
	 * 
	 * If date is null it has no effect (but no exception is thrown)
	 */
	public static void setDay(Date date, int day) {
		if (date == null)
			return;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
	}

	/**
	 * Puts hours, minutes, seconds and milliseconds to zero.
	 * <p>
	 * 
	 * @return The same date sent as argument (a new date is not created). If
	 *         null if sent a null is returned.
	 */
	public static java.util.Date removeTime(java.util.Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date.setTime(cal.getTime().getTime());
		return date;
	}

	/**
	 * Returns a clone but without hours, minutes, seconds and milliseconds.
	 * <p>
	 * 
	 * @return If null if sent a null is returned.
	 */
	public static Date cloneWithoutTime(Date date) {
		if (date == null)
			return null;
		Date result = (Date) date.clone();
		removeTime(result);
		return result;
	}

	/**
	 * Creates a java.sql.Date from a java.util.Date.
	 * <p>
	 * 
	 * @param date
	 *            If null returns null
	 */
	public static java.sql.Date toSQL(java.util.Date date) {
		if (date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}

	/**
	 * Compares if 2 dates are equals at day, month and year level, ignoring
	 * time in comparing.
	 * 
	 * @param f1
	 *            Can be null
	 * @param f2
	 *            Can be null
	 */
	public static boolean isDifferentDay(java.util.Date f1, java.util.Date f2) {
		if (f1 == null && f2 == null)
			return false;
		if (f1 == null || f2 == null)
			return true;
		Calendar cal = Calendar.getInstance();
		cal.setTime(f1);
		int dd1 = cal.get(Calendar.DAY_OF_MONTH);
		int mm1 = cal.get(Calendar.MONTH);
		int aa1 = cal.get(Calendar.YEAR);
		cal.setTime(f2);
		int dd2 = cal.get(Calendar.DAY_OF_MONTH);
		int mm2 = cal.get(Calendar.MONTH);
		int aa2 = cal.get(Calendar.YEAR);
		return !(aa1 == aa2 && mm1 == mm2 && dd1 == dd2);
	}

	/**
	 * Difference of 2 dates in years, months and days.
	 * <p>
	 * 
	 * @param f1
	 *            If null returns null
	 * @param f2
	 *            If null returns null
	 */
	public static DateDistance dateDistance(java.util.Date f1,
			java.util.Date f2, boolean includeStartDate) {
		DateDistance df = new DateDistance();
		if (null == f1 || null == f2)
			return null;
		Calendar fmax = Calendar.getInstance(), fmin = Calendar.getInstance();

		f1 = AS2Date.removeTime(f1);
		f2 = AS2Date.removeTime(f2);

		if (f1.after(f2)) {
			fmax.setTime(f1);
			fmin.setTime(f2);
		} else {
			fmin.setTime(f1);
			fmax.setTime(f2);
		}

		int initDay = fmin.get(Calendar.DATE);
		int initMonth = fmin.get(Calendar.MONTH);
		int initYear = fmin.get(Calendar.YEAR);
		int endMonth = fmax.get(Calendar.MONTH);
		int endYear = fmax.get(Calendar.YEAR);
		int finalLimit = fmax.getActualMaximum(Calendar.DATE);
		int initPeak = 0;
		int finalPeak = 0;

		if (initMonth == endMonth && initYear == endYear) {
			while (fmin.getTime().before(fmax.getTime())) {
				fmin.add(Calendar.DATE, 1);
				df.days++;
			}

			if (includeStartDate) {
				df.days++;
			}
			if (df.days >= finalLimit) {
				df.months++;
				df.days = 0;
			}
			return df;
		}

		if (initDay != 1) {
			while (fmin.get(Calendar.DATE) != 1) {
				fmin.add(Calendar.DATE, 1);
				initPeak++;
			}
		}

		while (fmin.get(Calendar.MONTH) != endMonth
				|| fmin.get(Calendar.YEAR) != endYear) {
			fmin.add(Calendar.MONTH, 1);
			df.months++;
			if (df.months == 12) {
				df.years++;
				df.months = 0;
			}
		}

		while (fmin.getTime().before(fmax.getTime())) {
			fmin.add(Calendar.DATE, 1);
			finalPeak++;
		}

		int peak = initPeak + finalPeak;
		if (includeStartDate) {
			peak++;
		}

		if (peak >= finalLimit) {
			peak = peak - finalLimit;
			df.months++;
			if (df.months == 12) {
				df.years++;
				df.months = 0;
			}
		}
		df.days = peak;
		return df;
	}

	/**
	 * Returns number of days between startDate and endDate
	 * <p>
	 * 
	 * @param java
	 *            .util.Date startDate
	 * @param java
	 *            .util.Date endDate
	 * @param boolean includeStartDate
	 *        <p>
	 * 
	 */
	public static int daysInterval(java.util.Date startDate,
			java.util.Date endDate, boolean includeStartDate) {

		startDate = AS2Date.removeTime(startDate);
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);

		endDate = AS2Date.removeTime(endDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		if (includeStartDate) {
			start.add(Calendar.DATE, -1);
		}

		int days = 0;
		while (start.before(end)) {
			days++;
			start.add(Calendar.DATE, 1);
		}
		return days;
	}

	public static class DateDistance {
		public int days;
		public int months;
		public int years;
	}

	public static java.util.Date parseStringDateTimeToDate(String stringDate) {
		try {
			return date_time_format.parse(stringDate);
		} catch (ParseException e) {
			try {
				return date_format.parse(stringDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	public static String formatDateTimeToString(Date date) {
		return date_time_format.format(date);
	}

	public static java.util.Date parseStringDateToDate(String stringDate) {
		try {
			return date_format.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String formatDateToString(Date date) {
		return date_format.format(date);
	}

	public static java.sql.Timestamp parseStringTimestampToTimestamp(
			String value) {
		if (value == null || value.trim().length() == 0)
			return null;

		java.sql.Timestamp timestamp = null;

		try {
			timestamp = java.sql.Timestamp.valueOf(value);
		} catch (Exception e) {
			try {
				timestamp = new java.sql.Timestamp(date_time_format
						.parse(value).getTime());
			} catch (Exception e1) {
				java.util.Date temp_date = parseStringDateTimeToDate(value);
				timestamp = new java.sql.Timestamp(temp_date.getTime());
			}
		}
		return timestamp;
	}

	public static String convertCalendarTimeToStringDate(Calendar value) {
		try {
			java.sql.Timestamp ts = new java.sql.Timestamp(
					value.getTimeInMillis());
			return ts.toString();
		} catch (Exception e) {
			return "";
		}
	}
}
