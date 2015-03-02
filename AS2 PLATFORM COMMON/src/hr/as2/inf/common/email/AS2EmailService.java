package hr.as2.inf.common.email;

import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.format.AS2Format;
import hr.as2.inf.common.logging.AS2Trace;

import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class AS2EmailService {
   // private final String PROPERTIES_FILE = "/common/J2EEEmailService.properties";
    private static AS2EmailService _instance = null;
    
    private String MAILER_SMTP_SERVER = "smtp.gmail.com";
    private String MAILER_MAIL_FROM = "adriacom.software@yahoo.com";
    private String MAILER_TO = "zrosko@yahoo.com";
    private String MAILER_CC = "";//"zdravko.rosko@zd.t-com.hr";
    private String MAILER_BCC = "";//"zdravkorosko@yahoo.com";
    public InternetAddress fromAddress = null;
	public InternetAddress iAddressTo[] = null;
	public InternetAddress iAddressCc[] = null;
	public InternetAddress iAddressBcc[] = null;

	public String smtpHost = null;

    private AS2EmailService() {
        initialize();
        AS2Context.setSingletonReference(this);
    }
    public static AS2EmailService getInstance() {

        if (_instance == null)
            _instance = new AS2EmailService();
        return _instance;
    }

    protected void initialize() {
        Properties prop = new Properties();//J2EEHelper.readPropertyFileAsURL(J2EEContext.getPropertiesPath()+ PROPERTIES_FILE);
        MAILER_SMTP_SERVER = prop.getProperty("MAILER_SMTP_SERVER", "smtp.gmail.com");
        MAILER_MAIL_FROM = prop.getProperty("MAILER_MAIL_FROM", "adriacom.software@yahoo.com");
        MAILER_TO = prop.getProperty("MAILER_TO", "zrosko@yahoo.com");
        //MAILER_CC = prop.getProperty("MAILER_CC", "zdravko.rosko@zd.t-com.hr");
        //MAILER_BCC = prop.getProperty("MAILER_BCC", "zdravkorosko@yahoo.com");
        
        smtpHost = MAILER_SMTP_SERVER;	
        try{
			setFromAddress(MAILER_MAIL_FROM);
			setAddressTo(parseAddress(MAILER_TO));
			setAddressCc(parseAddress(MAILER_CC));
			setAddressBcc(parseAddress(MAILER_BCC));
        }catch(Exception e){
            AS2Trace.trace(AS2Trace.E, e);
        }
    }
    public InternetAddress getFromAddress() {
		return fromAddress;
	}
	public InternetAddress[] getAddressBcc() {
		return iAddressBcc;
	}
	public InternetAddress[] getAddressCc() {
		return iAddressCc;
	}
	public InternetAddress[] getAddressTo() {
		return iAddressTo;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setFromAddress(String fromAddress) throws Exception {
		if(fromAddress != null)
			this.fromAddress = new InternetAddress(fromAddress);
	}
	public void setAddressBcc(String[] addressBcc) throws Exception {
		if(addressBcc != null) {
			iAddressBcc = new InternetAddress[addressBcc.length];
			for(int i=0;i<addressBcc.length;i++) {
				iAddressBcc[i] = new InternetAddress(addressBcc[i]);				
			}
		}
	}
	public void setAddressCc(String[] addressCc) throws Exception {
		if(addressCc != null) {
			iAddressCc = new InternetAddress[addressCc.length];
			for(int i=0;i<addressCc.length;i++) {
				iAddressCc[i] = new InternetAddress(addressCc[i]);				
			}
		}
	}
	public void setAddressTo(String[] addressTo) throws Exception {
		if(addressTo != null) {
			iAddressTo = new InternetAddress[addressTo.length];
			for(int i=0;i<addressTo.length;i++) {
				iAddressTo[i] = new InternetAddress(addressTo[i]);				
			}
		}
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	private String[] parseAddress(String address) {
		String adr[] = null;
		if(address != null) {
			//izbaci praznine 
			StringTokenizer st = new StringTokenizer(
					AS2Format.stringRemoveBlanks(address), ";");
			adr = new String[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens()) {
				adr[i] = st.nextToken();
				i++;
			}
		}
		return adr;
	}   
	public void sendEmail(String from, String to, String cc, String bcc, String subject, String body){
	       try{
		        Properties props = System.getProperties();
				props.put("mail.smtp.host", AS2EmailService.getInstance().getSmtpHost());
				Session session = Session.getInstance(props, null);	
				//option A
				Store store = session.getStore();
				store.connect("imap.gmail.com","zrosko@gmail.com", "password");
				// create a message
				MimeMessage msg = new MimeMessage(session);
				setFromAddress(from);
				//setAddressTo(to);
				msg.setFrom(AS2EmailService.getInstance().getFromAddress());
				msg.setRecipients(Message.RecipientType.TO, AS2EmailService.getInstance().getAddressTo());
				msg.setRecipients(Message.RecipientType.CC, AS2EmailService.getInstance().getAddressCc());
				msg.setRecipients(Message.RecipientType.BCC, AS2EmailService.getInstance().getAddressBcc());
				msg.setSubject(subject);
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText(body);
				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp1);
				msg.setContent(mp);
				msg.setSentDate(new Date());
				Transport.send(msg);
				System.out.println(msg);
	        }catch(Exception e){
	            System.out.println(e);
	        }  
	}
	public void sendEmail(String from, String to, String subject, String body){
	    sendEmail(from,to,null,null,"Subjct 1", "Body 1");
	}
	public void readEmail(){
		Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", "zrosko@gmail.com", "Jelena2012");
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message msg = inbox.getMessage(10);//inbox.getMessageCount());
            Address[] in = msg.getFrom();
            for (Address address : in) {
                System.out.println("FROM:" + address.toString());
            }
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println("CONTENT:" + bp.getContent());
        } catch (Exception mex) {
            mex.printStackTrace();
        }  
	}
}