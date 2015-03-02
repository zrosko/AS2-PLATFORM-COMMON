package hr.as2.inf.common.cache;

import java.util.Calendar;
import java.util.Date;

public class AS2CachedObject implements AS2Cacheable {

	private Date _dateOfExpiration = null;
	private String _identifier = null;
	private Object _object = null;

	// max time to keep an object in cache (hours)
	final int MAX_TIME_IN_CACHE = 24;

	public AS2CachedObject(String id, Object obj, int minutesToLive) {
		this._object = obj;
		this._identifier = id;

		// minutesToLive of 0 means it lives on indefinitely (maxixum value is
		// set to 8 hours).
		_dateOfExpiration = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(_dateOfExpiration);

		if (minutesToLive != 0) {
			cal.add(Calendar.MINUTE, minutesToLive);
			_dateOfExpiration = cal.getTime();
		} else {
			// if minutesToLive = 0 then expirationDate is max value
			cal.add(Calendar.HOUR, MAX_TIME_IN_CACHE);
			_dateOfExpiration = cal.getTime();
		}
	}

	public Object getCachedObject() {
		return _object;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public boolean isExpired() {
		if (_dateOfExpiration.before(new java.util.Date())) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return _identifier;
	}
}
