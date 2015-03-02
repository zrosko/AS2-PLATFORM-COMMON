package hr.as2.inf.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.Signature;
//declare precedence AS2LoggingAspect, *;
//TODO dodati ARM u logging
public aspect AS2LoggingAspect {
	private Logger _logger = Logger.getLogger("AS2Logging");
	
	AS2LoggingAspect() {
		_logger.setLevel(Level.INFO);
	}

	public pointcut traceMethodsFACADE()
	: (execution(* hr.adriacomsoftware.app.server.*.facade.*.*(..))
	|| execution(* hr.adriacomsoftware.app.server.*.*.facade.*.*(..))	
	|| execution(* hr.as2.inf.server.*.facade.*.*(..))
	|| execution(* test.logging.facade.*.*(..))) 
	&& !execution(* hr.adriacomsoftware.app.server.*.*.facade.*.getInstance(..))
	&& !execution(* hr.adriacomsoftware.app.server.*.facade.*.getInstance(..))
	&& !within(AS2LoggingAspect);

	public pointcut traceMethodsDAO()
	: (execution(* hr.adriacomsoftware.app.server.*.da.*.*.*(..))
	|| execution(* hr.as2.inf.server.*.da.*.*.*(..)) 
	|| execution(* hr.adriacomsoftware.app.server.*.*.da.*.*.*(..))) 
	&& !within(AS2LoggingAspect);
	
	public pointcut traceMethodsBO()
	: (execution(* hr.adriacomsoftware.app.server.*.bl.*.*(..))
	|| execution(* hr.as2.inf.server.*.bl.*.*(..)) 
	|| execution(* hr.adriacomsoftware.app.server.*.*.bl.*.*(..))) 
	&& !within(AS2LoggingAspect);

	before() : traceMethodsFACADE() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(), "FACADE begin");
		}
	}
	before() : traceMethodsDAO() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(), "DAO begin");
		}
	}
	before() : traceMethodsBO() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(),"BO begin");
		}
	}
	after() : traceMethodsFACADE() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(), "FACADE end");
		}
	}
	after() : traceMethodsDAO() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(), "DAO end");
		}
	}
	after() : traceMethodsBO() {
		if (_logger.isLoggable(Level.INFO)) {
			Signature sig = thisJoinPointStaticPart.getSignature();
			_logger.logp(Level.INFO, sig.getDeclaringType().getName(),
					sig.getName(),"BO end");
		}
	}
}