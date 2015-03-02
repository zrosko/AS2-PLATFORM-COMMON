package hr.as2.inf.common.requesthandlers.local;

import hr.as2.inf.common.core.AS2Constants;
import hr.as2.inf.common.data.AS2InvocationContext;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.exceptions.AS2TransportException;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.requesthandlers.AS2ClientRequestHandler;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

public final class AS2RequestHandlerLocal extends AS2ClientRequestHandler {
	public AS2Record send(AS2Record request) throws AS2Exception {
		try	{
			AS2User aUser = (AS2User) request.getAsObject(AS2Constants.USER_OBJ);
			if (aUser == null) {
				aUser = AS2UserFactory.getInstance().getCurrentUser();
			}
			request.set(AS2Constants.USER_OBJ, aUser);
			Object aResult = callApplicationController(request);
			if (aResult instanceof AS2Exception)
				throw (AS2Exception) aResult;
			else
				return (AS2Record) aResult;
		} catch (AS2Exception e) {
			ResourceBundle messages;
			String errorCode = e.getErrorCode();
			int err = 0;
			try	{
				err = Integer.valueOf(errorCode).intValue();
			} catch (Exception ex) {
				AS2Trace.trace(AS2Trace.W, "AS2Exception error code is invalid");
			}
			errorCode = processErrorCode(err);
			messages = ResourceBundle.getBundle(e.getResourceBundle());
			e.setErrorDescription(errorCode + messages.getString(e.getErrorCode()));
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			AS2TransportException ex = new AS2TransportException("510");
			ex.setErrorDescription("Application problem ! Call support !");
			ex.addCauseException(e);
			throw ex;
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object callApplicationController(AS2Record value) throws Exception {
        Object _returned = null;
		Class factory_class = null;
        Class controller_class = null;
        
        Method get_instance_method = null;
        Method get_app_controller_method = null;
        Method execute_request_method = null;
        
        Class execute_request_parameters[] = null;
        Class get_instance_parameters[] = new Class[0];
        Class get_app_controller_parameters[] = new Class[0];
      
        Object factory_target_object = null;
        Object controller_target_object = null;
        
        Object[] executre_request_arguments = null;
        Object[] get_instance_arguments = new Object[0];
        Object[] get_app_controller_arguments = new Object[0];
        execute_request_parameters = new Class[] { new AS2Record().getClass(), new AS2InvocationContext().getClass() };
        executre_request_arguments = new Object[] { (AS2Record)value, prepareInvocationContext(value,null) };
        
        factory_class = Class.forName("hr.as2.inf.server.invokersAS2InvokerFactory");
        controller_class = Class.forName("hr.as2.inf.server.invokers.AS2InvokerDefault");
        
        get_instance_method = factory_class.getMethod("getInstance", get_instance_parameters);
        factory_target_object = get_instance_method.invoke(factory_target_object, get_instance_arguments);
        get_app_controller_method = factory_class.getMethod("getInvoker", get_app_controller_parameters);
        controller_target_object = get_app_controller_method.invoke(factory_target_object, get_app_controller_arguments);
        execute_request_method = controller_class.getMethod("invoke", execute_request_parameters);
        _returned = execute_request_method.invoke(controller_target_object, executre_request_arguments);  
        return _returned;
    }
	protected AS2InvocationContext prepareInvocationContext(AS2Record as2_request, String srvRemoteAddr){
		AS2InvocationContext as2_context = new AS2InvocationContext();
		as2_context.setThreadId(Thread.currentThread().getName());
		as2_context.setRemoteObject(as2_request.getRemoteObject());
		as2_context.setRemoteMethod(as2_request.getRemoteMethod());
		as2_context.setRemoteAddr(srvRemoteAddr);	
		return as2_context;
	}
}
