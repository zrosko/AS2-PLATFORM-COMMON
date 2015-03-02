package hr.as2.inf.common.security.authentication.facade;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.requesthandlers.AS2FacadeProxy;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.net.InetAddress;

public class AS2AuthenticationFacadeProxy extends AS2FacadeProxy implements
		AS2AuthenticationFacade {
	private static AS2AuthenticationFacadeProxy _instance = null;
	private AS2AuthenticationFacadeProxy(){
		setRemoteObject("hr.as2.inf.server.security.authentication.facade.AS2AuthenticationFacadeServer");
	}

	public static AS2AuthenticationFacadeProxy getInstance()	{
		if (_instance == null)
			_instance = new AS2AuthenticationFacadeProxy();
		return _instance;
	}

	public AS2User logIn(AS2User value) throws AS2Exception {
		try{
		    value.setRemoteMethod("logIn");
		    AS2User user = (AS2User) execute((AS2User)value);
			AS2UserFactory.getInstance().setCurrentUser(user);
			//J2EEUserContext ctx = new J2EEUserContext();
			//ctx.setUser(user);
			//J2EEUserFactory.getInstance().setCurrentUserContext(ctx);
			user.setValid(true);
			return user;
		}catch(Exception e){
			value.setValid(false);
			return value;
		}
	}

	public AS2User logOut(AS2User value) throws AS2Exception	{
		return (AS2User) execute(value, "logOut"); 
	}
	public AS2User changePassword(AS2User value) throws AS2Exception	{
		return (AS2User) execute(value, "changePassword"); 
	}
	public AS2User logIn(AS2User model, String application_){
		//user id must be entered
		if(model.getUserName().length()==0){
			//showMessage(e, "Unesi korisničko ime", TaskMessage.ERROR);
		    //model.setUserName("zrosko");
			//return;
		}
		//password must be entered
		if(model.getPassword().length()==0){
			//showMessage(e, "Unesi zaporku", TaskMessage.ERROR);
		    //model.setPassword("1234");
			//return;
		}
		
		AS2User user = new AS2User();		
		user.setUserName(model.getUserName());		
		user.setPassword(model.getPassword());
		user.setApplication(application_);
	      try {
	            InetAddress thisIp = InetAddress.getLocalHost();
	            user.set("ip_address", thisIp.getHostAddress());
	        } catch (Exception e) {}		
		user = AS2AuthenticationFacadeProxy.getInstance().logIn(user);
		user.set("naziv_uloge", user.get("org_jedinica"));
		
		if(!user.isValid()){
			user.set("@valid", "1");
		    return user;
		}else{
		    //provjeri user,password sa bazom
			if(!(model.getUserName().toLowerCase()).equals(user.getUserName().toLowerCase())){
				//showMessage(e, "Korisnički račun neispravan", TaskMessage.ERROR);
				user.set("@valid", "2");
			    return user;
			}
			//zr.4.10.2011. autorizacija za AD korisnike - nema provjere password-a
			String system_user = (String)System.getProperties().get("user.name");
			if(!system_user.toLowerCase().equals(model.getUserName().toLowerCase())){
				if(!model.getPassword().equals(user.getPassword())){
					//showMessage(e, "Zaporka neispravna", TaskMessage.ERROR);
					user.set("@valid", "3");
				    return user;
				}
			}
		}
		if(user.getUserName().length()==0)
			user.set("@valid", "2");
		else
			user.set("@valid", "0");
	    return user;
	}
}
