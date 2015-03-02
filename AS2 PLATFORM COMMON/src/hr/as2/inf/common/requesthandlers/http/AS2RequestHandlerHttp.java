package hr.as2.inf.common.requesthandlers.http;

import hr.as2.inf.common.core.AS2Constants;
import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.exceptions.AS2TransportException;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.metrics.AS2ArmService;
import hr.as2.inf.common.requesthandlers.AS2ClientRequestHandler;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * -t HTTP -h http://portal:8080/adriacom_isms_1108/adriacomHttp
 */
public final class AS2RequestHandlerHttp extends AS2ClientRequestHandler {
    private URLConnection urlConnection;
    public void connect(AS2User aUser) throws AS2Exception {
        URL aUrl = null;
        try {
            if (aUser == null)
                aUser = AS2UserFactory.getInstance().getUser("DEFAULT");
            aUrl = new URL(getAbsoluteURL());
        } catch (MalformedURLException e) {
            AS2TransportException exc = new AS2TransportException("503");
            exc.addCauseException(e);
            throw exc;
        } catch (Exception e) {
            AS2TransportException exc = new AS2TransportException("504");
            exc.addCauseException(e);
            throw exc;
        }
        // Connect to the URL
        try {
            //Enable the properties used for proxy support
            //System.getProperties().put( "proxySet", "false" );
            //System.getProperties().put( "proxyHost", "192.168.255.5" );
            //System.getProperties().put( "proxyPort", "8080" );
            URLConnection aURLConnection = (URLConnection) aUrl.openConnection();
            aURLConnection.setDoInput(true);
            aURLConnection.setDoOutput(true);
            // Tell the connection not to cache data.
            aURLConnection.setUseCaches(false);
            // Authorization
            //aURLConnection.setRequestProperty(
            //"Authorization",
            //"BASIC " + J2EEBase64.encode(aUser.getUserId() + ":" +
            // aUser.getPassword()));
            // Set the MIME type for binary data
            aURLConnection.setRequestProperty("content-type", "application/octet-stream");
            //aURLConnection.setRequestProperty("User-agent","Mozilla/4.0");
            //int status = _URLConnection.getResponseCode();
            //System.out.println("status = " + status);
            //String statusStr = _URLConnection.getResponseMessage();
            //System.out.println("statusStr = " + statusStr);
            setUrlConnection(aURLConnection);
        } catch (IOException e) {
            AS2TransportException exc = new AS2TransportException("504");
            exc.addCauseException(e);
            throw exc;
        }
    }
    private URLConnection getUrlConnection() {
        if (urlConnection == null)
            throw new Error("urlConnection missing");
        //((HttpURLConnection)urlConnection).setFollowRedirects(true);
        return urlConnection;
    }
    public Object receiveResult() throws AS2Exception {
        ObjectInputStream in = null;
        Exception e = null;
        for (int i = 0; i <= AS2Context.getInstance().HTTP_RETRY_NO; i++) {
            try {
                in = new ObjectInputStream(getUrlConnection().getInputStream());
                break;
            } catch (Exception ex) {
                e = ex;
                AS2Trace.trace(AS2Trace.W, "Can not create input - now retry # " + (i + 1) + " reason: " + ex.toString());
                try {
                    Thread.sleep(AS2Context.getInstance().HTTP_RETRY_SLEEP);
                } catch (Exception sleepExc) {
                    AS2Trace.trace(AS2Trace.W, "Problem while sleeping on input retry");
                }
            }
        }
        if (e != null || in == null) {
            AS2Trace.trace(AS2Trace.E, "Can not create input at all - reason: " + e.toString());
            AS2TransportException ex = new AS2TransportException("505");
            ex.addCauseException(e);
            throw ex;
        }
        // Read an object off the input stream
        Object result = null;
        try {
            result = in.readObject();
        } catch (IOException e1) {
            AS2TransportException exc = new AS2TransportException("506");
            exc.addCauseException(e1);
            throw exc;
        } catch (ClassNotFoundException e2) {
            AS2TransportException mme = new AS2TransportException("507");
            mme.setTechnicalErrorDescription(e2.getClass().getName() + ":" + e2.getMessage());
            mme.addCauseException(e2);
            throw mme;
        } finally {
            try {
                in.close();
            } catch (Exception e2) {
                AS2Trace.trace(AS2Trace.W, "Can not close input from server: " + e2.toString());
            }
        }
        return result;
    }
    protected synchronized AS2Record sendToServer(AS2Record request) throws AS2Exception, Exception {
        Object result = null;
        if (request == null)
            throw new AS2Exception("201");
        try {
            AS2ArmService.armGetId(request);
            AS2ArmService.armStart(AS2ArmService.TRANSPORT);
            connect((AS2User) request.getAsObject(AS2Constants.USER_OBJ));
            transmitMessage(request);
        } catch (AS2Exception mme) {
            throw mme;
        } catch (Exception e) {
            AS2TransportException mme = new AS2TransportException("501");
            mme.addCauseException(e);
            throw mme;
        }
        try {
            result = receiveResult();
        } catch (AS2Exception mme) {
            throw mme;
        } catch (Exception e) {
            AS2TransportException mme = new AS2TransportException("502");
            mme.addCauseException(e);
            throw mme;
        }
        if (result == null)
            throw new AS2TransportException("502");
        if (result instanceof AS2Exception)
            throw (AS2Exception) result;
        else if (result instanceof Exception)
            throw (Exception) result;
        AS2ArmService.armStop(AS2ArmService.TRANSPORT);
        AS2ArmService.armEnd();
        return (AS2Record) result;
    }
    private void setUrlConnection(URLConnection newUrlConnection) {
        urlConnection = newUrlConnection;
    }
    public void transmitMessage(AS2Record req) throws AS2Exception {
        ObjectOutputStream out = null;
        Exception e = null;
        for (int i = 0; i <= AS2Context.getInstance().HTTP_RETRY_NO; i++) {
            try {
                out = new ObjectOutputStream(getUrlConnection().getOutputStream());
                break;
            } catch (Exception ex) {
                e = ex;
                AS2Trace.trace(AS2Trace.W, "Can not create output - now retry # " + (i + 1) + " reason: " + ex.toString());
                try {
                    Thread.sleep(AS2Context.getInstance().HTTP_RETRY_SLEEP);
                } catch (Exception sleepExc) {
                    AS2Trace.trace(AS2Trace.W, "Problem while sleeping on output retry");
                }
            }
        }
        if (e != null || out == null) {
            AS2Trace.trace(AS2Trace.E, "Can not create output at all - reason: " + e.toString());
            AS2TransportException ex = new AS2TransportException("508");
            ex.addCauseException(e);
            throw ex;
        }
        // Write the message onto the stream
        try {
            out.writeObject(req);
            out.flush();
        } catch (IOException e1) {
            AS2TransportException exc = new AS2TransportException("509");
            exc.addCauseException(e1);
            throw exc;
        } finally {
            try {
                out.close();
            } catch (Exception e2) {
                AS2Trace.trace(AS2Trace.W, "Can not close output to server: " + e2.toString());
            }
        }
    }
}