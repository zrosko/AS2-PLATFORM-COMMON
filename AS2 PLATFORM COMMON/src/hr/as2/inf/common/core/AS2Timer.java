package hr.as2.inf.common.core;

/**
 * This class is used to prevent a client side locking when 
 * a server does not response in a specified time limit.
 */
public class AS2Timer {
	//Timeout value. The timer is enforced to be reset after 
	//this value and the thread waiting for the timer is 
	//released to continue execution.
	private long _timeout;	// default value in milliseconds
	//Flag to determine whether the timer is reset by reset() 
	//method or it is reset because the timeout value is expired.
	private boolean _timeExpired = true;

public AS2Timer(long timeout) {
	this._timeout = timeout;
}
public boolean isTimeExpired() {
	return _timeExpired;
}
/**
 * Resets the timer. A thread waiting for the timer 
 * (by calling set() method) is released to continue its execution.
 */
public synchronized void reset() {
	_timeExpired = false;
	notify();
}
/**
 * Sets the timer and wait until it is reset. It is reset
 * either when timeout period is expired or when reset() 
 * method is called.
 * @see #reset()
 */
public synchronized void set() {
	try {
		wait(_timeout);
	} catch (InterruptedException e) {
		/* Nothing to do for now. */
	}
}
}
