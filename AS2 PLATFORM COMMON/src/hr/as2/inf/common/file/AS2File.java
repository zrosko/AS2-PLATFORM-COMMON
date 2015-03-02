package hr.as2.inf.common.file;

import java.io.InputStream;
import java.io.OutputStream;

public interface AS2File {

	public String getName();

	public boolean isDirectory();

	public boolean exists();

	public AS2File[] listFiles();

	public InputStream getInputStream();

	public OutputStream getOutputStream();

	public long length();

	public String getAbsolutePath();
}
