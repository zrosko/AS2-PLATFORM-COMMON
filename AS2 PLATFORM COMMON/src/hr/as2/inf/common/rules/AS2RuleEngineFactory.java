package hr.as2.inf.common.rules;
/* Draft:
 * Primjer upotrebe je CreditApplicationUI koji poziva CreditApplicationProxy.addCreditApplication.
 * Ulazni parametri su: iznos, placa, hipoteka (sa ekrana).
 * 
 *
 */
import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.logging.AS2Trace;

import java.util.ArrayList;
import java.util.List;

//import com.yasutech.qrules.engine.*;
//import com.yasutech.qrules.io.FileRulePersistenceHandler;
//https://www.google.hr/search?q=rule+engine+editor+sample&biw=1280&bih=899&tbm=isch&imgil=M9Wbw6mTrteuUM%253A%253B_KGJm8mYByK6gM%253Bhttp%25253A%25252F%25252Fkarlreinsch.com%25252F2010%25252F10%25252F18%25252Fwf-rules-unleashing-the-rule-engine-within-dot-net%25252F&source=iu&pf=m&fir=M9Wbw6mTrteuUM%253A%252C_KGJm8mYByK6gM%252C_&usg=__KwEGjBHCzIwjQXGTkZinu771aTE%3D&ved=0CD8Qyjc&ei=lQp_VLq3NoS17gbZl4CoBg#facrc=_&imgdii=_&imgrc=b--jG3Ops_wNoM%253A%3BjY4ARmLHvK9SoM%3Bhttp%253A%252F%252Fdocs.jboss.org%252Fdrools%252Frelease%252F5.2.0.Final%252Fdrools-expert-docs%252Fhtml%252Fimages%252FChapter-IDE%252Fguidededitor1.png%3Bhttp%253A%252F%252Fdocs.jboss.org%252Fdrools%252Frelease%252F5.2.0.Final%252Fdrools-expert-docs%252Fhtml%252Fch08.html%3B653%3B449

public class AS2RuleEngineFactory {
	private static AS2RuleEngineFactory _instance = null;
	//private static String RULESET_DIR;
	
	private AS2RuleEngineFactory() throws AS2Exception{
		initialize();
		AS2Context.setSingletonReference(this);
	}
	public static AS2RuleEngineFactory getInstance() {
		if (_instance == null)
				_instance = new AS2RuleEngineFactory();
		return _instance;
	}
	private void initialize() throws AS2Exception {
		try {
			//RULESET_DIR = AS2Context.getInstance().RULESET_DIR;
			// This is how you get an instance of RuleEngineFactory. 
			// factory = RuleEngineFactory.getFactory();
			// PersistenceHandler can not be reset once it is set 
			// Check whether the persistence Handler is set or not. 
			// if it is not set then set it. 
			// if (!factory.isPersistenceHandlerSet())
				//factory.setPersistenceHandler(new FileRulePersistenceHandler(RULESET_DIR));
		} catch (Exception e) {
			AS2Exception ex = new AS2Exception("351");
			ex.addCauseException(e);
			throw ex;
		}
	}
	public List<AS2Record> invokeRuleset(String RULESETNAME, List<AS2Record> businessObjects) {
		try {
			return new ArrayList<AS2Record>(1);
			//return factory.getRuleEngine().invokeRuleset(RULESETNAME, businessObjects); 
		//} catch (NoSuchRuleException e1) {
			//J2EETrace.trace(J2EETrace.E, e1, "J2EERuleService.NoSuchRuleException ?"+RULESETNAME);
		//} catch (RuleException e2) {
			//J2EETrace.trace(J2EETrace.E, e2, "J2EERuleService.RuleException ?"+RULESETNAME);
		} catch (Exception e) {
			AS2Trace.trace(AS2Trace.E, e, "AS2RuleEngineFactory.Exception ?"+RULESETNAME);
		}
		return new ArrayList<AS2Record>(1);
	}
}
