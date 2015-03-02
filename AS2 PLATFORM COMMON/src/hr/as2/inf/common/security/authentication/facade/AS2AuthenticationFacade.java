package hr.as2.inf.common.security.authentication.facade;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.security.user.AS2User;

public interface AS2AuthenticationFacade {
	public abstract AS2User logIn(AS2User value) throws AS2Exception;
	public abstract AS2User logOut(AS2User value) throws AS2Exception;	
	public abstract AS2User changePassword(AS2User value) throws AS2Exception;
}
