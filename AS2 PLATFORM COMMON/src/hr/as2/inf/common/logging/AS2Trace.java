package hr.as2.inf.common.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
/**
 * AS2Trace logger (java.util.logging) implementation of a AS2Logger. Assumes that
 * all configuration of the loggers are done by external configuration (System
 * property "java.util.logging.config.file"). Maps logging levels as follows :
 * <ul>
 * <li>debug maps to java.util.logging <i>fine</i></li>
 * <li>info maps to java.util.logging <i>info</i></li>
 * <li>warn maps to java.util.logging <i>warning</i></li>
 * <li>error maps to java.util.logging <i>severe</i></li>
 * <li>fatal maps to java.util.logging <i>severe</i></li>
 * </ul>
 */
public final class AS2Trace extends AS2Logger {
	private static Logger logger = Logger.getLogger(AS2Trace.class.getName());;
	public static boolean _useLog4j = false;
	public static boolean _useAS2 = true;
	// Tracing levels
	public static final int N = 0; // 0: None 
	public static final int E = 1; // 1: Error
	public static final int W0 = 2; // 2: Warning
	public static final int W = W0+E; // 3: Warning And Error  
	public static final int D1 = 8; // 8: Debugging Trace 1
	public static final int D2 = 16; // 16: Debugging Trace 2
	public static final int D3 = 32; // 32: Debugging Trace 3
	public static final int DA = D1+D2+D3; // 56: Debugging All Traces
	public static final int D = W+DA; // 63: Debugging All, Information, Warning and Error
	public static final int D1D2 = D1+D2; // 24 
	public static final int WD1 = W+D1; // 11
	public static final int WD1D2 = W+D1D2; // 27
	public static final int I0 = 4; // 4: Information
	public static final int I = I0+D; // 67: Information, Debugging, Warning and Error
	public static final int A = I; // All
	
	private static Hashtable<String,Integer> _indentsTable;
	private static boolean _trace = false;
	private static boolean _methodTrace;
	private static PrintStream _trc;
	private static PrintStream _trc_out;
	private static int _traceLevel;

	static {
		_trc = System.out;
		_trc_out = System.out;
		_traceLevel = E;
	}
public AS2Trace() {
	logger = Logger.getLogger(AS2Trace.class.getName());
}
public AS2Trace(String logName) {
	// Logging assumed to be configured by user via
	// "java.util.logging.config.file"
	logger = Logger.getLogger(AS2Trace.class.getName()+"."+logName);
}
public static final synchronized void dataTrace(
    int i, 
    Object obj, 
    byte abyte0[]) {
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(false);
    if (_trace && ((_traceLevel & i) == i)) {
        _trc.print("Thread: " + Thread.currentThread().getName() + ", ");
        if (obj != null)
            _trc.print("Object: " + obj);
        _trc.println(" Data trace, " + abyte0.length + " bytes of data follow: \n");
        for (int j = 0; j < abyte0.length; j += 19) {
            int k = Math.min(j + 19, abyte0.length);
            String s = "";
            for (int l = j; l < k; l++) {
                int i1 = abyte0[l];
                if (i1 < 0)
                    i1 += 256;
                String s1 = Integer.toString(i1, 16);
                if (i1 < 16)
                    s1 = "0" + s1;
                _trc.print(s1 + " ");
                if (i1 >= 32 && i1 <= 126)
                    s += new Character((char) i1);
                else
                    s += 46;
            }

            int j1 = 19 - k - j;
            for (int k1 = 0; k1 < j1; k1++)
                _trc.print("   ");

            _trc.println("  " + s);
        }

        _trc.println();
    }
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(true);
}
private static final void decrementIndents() {
    int i = getIndentationLevel();
    if (i > 0)
        i--;
    _indentsTable.put(Thread.currentThread().getName(), new Integer(i));
}
public static final synchronized void entry(Object obj, String s) {
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(false);
    if (_trace) {
        String s1 = "Thread: " + Thread.currentThread().getName();
        _trc.println(s1 + ", Object: " + obj + " ==> " + s + "() entry");
        incrementIndents();
    }
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(true);
}
public static final synchronized void entry(String s, String s1) {
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(false);
    if (_trace) {
        String s2 = "Thread: " + Thread.currentThread().getName();
        _trc.println(s2 + " ==> " + s + "::" + s1 + "() entry");
        incrementIndents();
    }
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(true);
}
public static final synchronized void exit(Object obj, String s) {
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(false);
    if (_trace) {
        decrementIndents();
        String s1 = "Thread: " + Thread.currentThread().getName();
        _trc.println(s1 + ", Object: " + obj + " <== " + s + "() exit");
    }
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(true);
}
public static final synchronized void exit(String s, String s1) {
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(false);
    if (_trace) {
        decrementIndents();
        String s2 = "Thread: " + Thread.currentThread().getName();
        _trc.println(s2 + " <== " + s + "::" + s1 + "() exit");
    }
    if (_methodTrace)
        Runtime.getRuntime().traceMethodCalls(true);
}
private static final int getIndentationLevel() {
    int i = 0;
    if (_indentsTable.containsKey(Thread.currentThread().getName())) {
        Integer integer = (Integer) _indentsTable.get(Thread.currentThread().getName());
        i = integer.intValue();
    }
    return i;
}
public static final int getTraceLevel() {
	return _traceLevel;
}
private static final void incrementIndents() {
    int i = getIndentationLevel();
    i++;
    _indentsTable.put(Thread.currentThread().getName(), new Integer(i));
}
public static boolean isTraceOn() {
	return _trace;
}
public static final synchronized void log(String s){
	StringBuffer b = new StringBuffer();
	b.append((new java.sql.Timestamp(new java.util.Date().getTime()).toString()));
	b.append(" Thread: ");
	b.append(Thread.currentThread().getName());
	b.append(" LOG: ");
	b.append(s);
	_trc.println(b.toString());
	 AS2Logger.AS2_GENERAL.debug(b);
}
public static final synchronized void setTraceLevel(int i) {
	_traceLevel = i;
	/*if(i==E)
		_logger.setLevel(Level.ERROR);
	else if(i==W)
		_logger.setLevel(Level.WARN);
	else if(i==I)
		_logger.setLevel(Level.INFO);
	else
		_logger.setLevel(Level.DEBUG);*/
}
public static final synchronized void setTraceStream(OutputStream outputstream) {
	if (outputstream != null)
		_trc = System.out;
}
public static final synchronized void setUseLog4j(boolean useLog4j) {
	_useLog4j = useLog4j;
}
public static final synchronized void trace(int i, Object obj) {
		if(_useLog4j){
			/*if(i==E)
				_logger.error(obj);
			else if(i==W)
				_logger.warn(obj);
			else if(i==I)
				_logger.info(obj);
			else
				_logger.debug(obj);*/
		}else if ((_traceLevel & i)==i){		
			if(i==E)
				trace(obj, "ERROR" );
			else if(i==W)
				trace(obj, "WARNING");
			else if(i==I)
				trace(obj, "INFO");
			else if(i==D1)
				trace(obj, "DEBUG LEVEL 1");
			else if(i==D2)
				trace(obj, "DEBUG LEVEL 2");
			else if(i==D3)
				trace(obj, "DEBUG LEVEL 3");
			else if(i==D)
				trace(obj, "DEBUG LEVEL ALL");
			else if(i==A)
				trace(obj, "ALL");
			}	
	}
public static final synchronized void trace(int i, Object obj, String s){
		if(_useLog4j){
			/*if(i==E)
				_logger.error(obj);
			else if(i==W)
				_logger.warn(obj);
			else if(i==I)
				_logger.info(obj);
			else
				_logger.debug(obj);*/
		}else if ((_traceLevel & i)==i){		
			if(i==E)
				trace(obj, "ERROR: " + s);
			else if(i==W)
				trace(obj, "WARNING: " + s);
			else if(i==I)
				trace(obj, "INFO: " + s);
			else if(i==D1)
				trace(obj, "DEBUG LEVEL 1: " + s);
			else if(i==D2)
				trace(obj, "DEBUG LEVEL 2: " + s);
			else if(i==D3)
				trace(obj, "DEBUG LEVEL 3: " + s);
			else if(i==D)
				trace(obj, "DEBUG LEVEL ALL: " + s);
			else if(i==A)
				trace(obj, "ALL: " + s);
			}
	}
public static final synchronized void trace(int i, String s){
		if(_useAS2){
			if(i==AS2Trace.I)
				AS2Trace.logger.info(s);
			else if(i==AS2Trace.W)
				AS2Trace.logger.warning(s);
			else if(i==AS2Trace.E)
				AS2Trace.logger.severe(s);//TODO error or severe
		}else if(_useLog4j){
			/*if(i==E)
				_logger.error(s);
			else if(i==W)
				_logger.warn(s);
			else if(i==I)
				_logger.info(s);
			else
				_logger.debug(s);*/
		}else if ((_traceLevel & i)==i){		
			if(i==E)
				trace("ERROR: ",s);
			else if(i==W)
				trace("WARNING: ", s);
			else if(i==I)
				trace("INFO: ", s);
			else if(i==D1)
				trace("DEBUG LEVEL 1: ",s);
			else if(i==D2)
				trace("DEBUG LEVEL 2: ",s);
			else if(i==D3)
				trace("DEBUG LEVEL 3: ",s);
			else if(i==D)
				trace("DEBUG LEVEL ALL: ",s);
			else if(i==A)
				trace("ALL: ",s);
			}
	}
public static final synchronized void trace(int i, String s, String s1)	{
		if(_useLog4j){
			/*if(i==E)
				_logger.error(s+s1);
			else if(i==W)
				_logger.warn(s+s1);
			else if(i==I)
				_logger.info(s+s1);
			else
				_logger.debug(s+s1);*/
		}else if ((_traceLevel & i)==i){		
			if(i==E)
				trace("ERROR: " + s,s1);
			else if(i==W)
				trace("WARNING: " + s, s1);
			else if(i==I)
				trace("INFO: " + s, s1);
			else if(i==D1)
				trace("DEBUG LEVEL 1: " + s,s1);
			else if(i==D2)
				trace("DEBUG LEVEL 2: " + s,s1);
			else if(i==D3)
				trace("DEBUG LEVEL 3: " + s,s1);
			else if(i==D)
				trace("DEBUG LEVEL ALL: " + s,s1);
			else if(i==A)
				trace("ALL: " + s,s1);
			}
		
		
	}
public static final synchronized void trace(Object obj, String s) {
	if (_methodTrace)
		Runtime.getRuntime().traceMethodCalls(false);
	if (_trace) {
		StringBuffer b = new StringBuffer();
		b.append((new java.sql.Timestamp(new java.util.Date().getTime()).toString()));
		b.append(" Thread: ");
		b.append(Thread.currentThread().getName());
		b.append(", Object: ");
		b.append(obj);
		b.append(" ");
		b.append(s);
		_trc.println(b.toString());
	}
	if (_methodTrace)
		Runtime.getRuntime().traceMethodCalls(true);
}
private static final synchronized void trace(String s, String s1) {
	if (_methodTrace)
		Runtime.getRuntime().traceMethodCalls(false);
	if (_trace) {
		StringBuffer b = new StringBuffer();
		b.append((new java.sql.Timestamp(new java.util.Date().getTime()).toString()));
		b.append(" Thread: ");
		b.append(Thread.currentThread().getName());
		b.append(" ");
		b.append(s);
		b.append(" ");
		b.append(s1);
		_trc.println(b.toString());
	}
	if (_methodTrace)
		Runtime.getRuntime().traceMethodCalls(true);
}
public static final synchronized void traceString(int i, String s) {
		if(_useLog4j){
			/*if(i==E)
				_logger.error(s);
			else if(i==W)
				_logger.warn(s);
			else if(i==I)
				_logger.info(s);
			else
				_logger.debug(s);*/
		}else if ((_traceLevel & i)==i){		
		if (_trace) 
			_trc.println(s);
	}
}
public static final synchronized void traceStringOut(int i, String s) {
	if(_useLog4j){
		/*if(i==E)
			_logger.error(s);
		else if(i==W)
			_logger.warn(s);
		else if(i==I)
			_logger.info(s);
		else
			_logger.debug(s);*/
	}else if ((_traceLevel & i)==i){		
		if (_trace) 
			_trc_out.println(s);
	}
}
public static final synchronized void turnMethodTracingOff() {
    Runtime.getRuntime().traceMethodCalls(false);
    _methodTrace = false;
}
public static final synchronized void turnMethodTracingOn() {
    Runtime.getRuntime().traceMethodCalls(true);
    _methodTrace = true;
}
public static final synchronized void turnTracingOff() {
    _trace = false;
}
public static final synchronized void turnTracingOn() {
    _trace = true;
    _indentsTable = new Hashtable<String,Integer>();
}
@Override
public void debug(Object msg) {
	log(Level.FINE, msg, null);
}
@Override
public void debug(Object msg, Throwable thr) {
	log(Level.FINE, msg, thr);
}
@Override
public void info(Object msg) {
	log(Level.INFO, msg, null);
}
@Override
public void info(Object msg, Throwable thr) {
	log(Level.INFO, msg, thr);
}
@Override
public void warn(Object msg) {
	log(Level.WARNING, msg, null);
}
@Override
public void warn(Object msg, Throwable thr) {
	log(Level.WARNING, msg, thr);
}
@Override
public void error(Object msg) {
	log(Level.SEVERE, msg, null);
}
@Override
public void error(Object msg, Throwable thr) {
	log(Level.SEVERE, msg, thr);
}
@Override
public void fatal(Object msg) {
	log(Level.SEVERE, msg, null);
}
@Override
public void fatal(Object msg, Throwable thr) {
	log(Level.SEVERE, msg, thr);
}
@Override
public boolean isDebugEnabled() {
	return logger.isLoggable(java.util.logging.Level.FINE);
}
@Override
public boolean isInfoEnabled() {
	return logger.isLoggable(java.util.logging.Level.INFO);
}

private void log(Level level, Object msg, Throwable thrown) {
	if (msg == null) {
		level = Level.SEVERE;
		msg = "Missing [msg] parameter";
	}

	if (logger.isLoggable(level)) {
		LogRecord result = new LogRecord(level, String.valueOf(msg));
		if (thrown != null)
			result.setThrown(thrown);
		StackTraceElement[] stacktrace = new Throwable().getStackTrace();
		for (int i = 0; i < stacktrace.length; i++) {
			StackTraceElement element = stacktrace[i];
			if (!element.getClassName().equals(AS2Trace.class.getName())) {
				result.setSourceClassName(element.getClassName());
				result.setSourceMethodName(element.getMethodName());
				break;
			}
		}
		logger.log(result);
	}
}
}