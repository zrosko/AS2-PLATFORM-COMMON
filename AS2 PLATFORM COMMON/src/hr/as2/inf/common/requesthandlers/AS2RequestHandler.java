package hr.as2.inf.common.requesthandlers;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;

/**
 * Interface responsible for connections between client and server.
 */
public interface AS2RequestHandler {
	public static final int NOT_SPECIFIED = -1;

	public String getContextPath();

	public String getFileName();

	public String getHost();

	public int getPort();

	public String getProtocol();

	public int getTag();

	public String getTransportName();

	public int getWeight();

	public AS2Record send(AS2Record request) throws AS2Exception, Exception;

	public void setContextPath(String value);

	public void setFileName(String value);

	public void setHost(String value);

	public void setPort(int value);

	public void setProtocol(String value);

	public void setTag(int value);

	public void setWeight(int value);
}
