package hr.as2.inf.common.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class AS2ZipArchiveFile {
	public static final String PREFIX = "jar:";

	protected ZipFile file;

	public AS2ZipArchiveFile(URL url) throws IOException {
		JarURLConnection jarConnection = (JarURLConnection) url
				.openConnection();
		file = jarConnection.getJarFile();
	}

	public AS2ZipArchiveFile(JarURLConnection jarConnection) throws IOException {
		file = jarConnection.getJarFile();
	}

	public boolean exists() {
		return true;
	}

	public AS2File[] listFiles() {
		Enumeration<? extends ZipEntry> e = file.entries();
		ZipEntry entry;
		Set<AS2File> files = new HashSet<AS2File>();
		while (e.hasMoreElements()) {
			entry = e.nextElement();
			files.add(new AS2ZipEntryArchiveFile(entry, file));
		}

		AS2File[] vFile = new AS2File[files.size()];
		files.toArray(vFile);
		return vFile;
	}

	public boolean isDirectory() {
		return true;
	}

	public String getName() {
		return file.getName();
	}

	public InputStream getInputStream() {
		return null;
	}

	public OutputStream getOutputStream() {
		return null;
	}

	public long length() {
		return file.size();
	}

	@Override
	public String toString() {
		return file.getName();
	}

	public String getAbsolutePath() {
		return file.getName();
	}
}
