package hr.as2.inf.common.core;

import hr.as2.inf.common.file.AS2FileResource;
import hr.as2.inf.common.requesthandlers.AS2ClientRequestDispatcher;
import hr.as2.inf.common.security.encoding.AS2Base64;
import hr.as2.inf.common.security.user.AS2User;
import hr.as2.inf.common.security.user.AS2UserFactory;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class AS2Helper {

	private static final String _STR_EMPTY = "";
	private static String _HOST = "127.0.0.1";
	private static boolean _triedURL = false;
	private static boolean _triedFile = false;
	private static boolean _useEBCIDIC = false;
	public static final boolean _isLicenceRequired = false;
	static char[] _ba;
	private static AS2Helper instance = null;
	private static String _ENVIRONMENT = "P";
	
	static URL createURL(String fileName) {
		URL url = null;
		try {
			url = new URL(fileName);
		} catch (MalformedURLException ex) {
			File f = new File(fileName);
			try {
				String path = f.getAbsolutePath();
				// This is a bunch of weird code that is required to
				// make a valid URL on the Windows platform, due
				// to inconsistencies in what getAbsolutePath returns.
				String fs = System.getProperty("file.separator");
				if (fs.length() == 1) {
					char sep = fs.charAt(0);
					if (sep != '/')
						path = path.replace(sep, '/');
					if (path.charAt(0) != '/')
						path = '/' + path;
				}
				path = "file://" + path;
				url = new URL(path);
			} catch (MalformedURLException e) {
				System.out.println("AS2Helper:createURL Cannot create url for: "+ fileName);
			}
		}
		return url;
	}

	public synchronized static String findPropertyFilePath(String aFileLocation) {
		String filePath = null;

		try {
			AS2FileResource config = new AS2FileResource(aFileLocation);
			filePath = config.getAbsolutePath();
		} catch (Exception e) {
			return null;
		}
		return filePath;
	}

	public static Object generateReversedTimestampKey() throws IOException {
		int AGN_LENGTH = 18;
		java.math.BigDecimal time = null;
		String temp = null;
		char[] temp2 = null;
		String timeStr = null;
		StringBuffer sb = new StringBuffer();

		java.sql.Timestamp ts = new java.sql.Timestamp(
				new java.util.Date().getTime());
		temp = ts.toString();
		temp2 = temp.toCharArray();
		for (int i = 0; i < temp2.length; i++) {
			if (Character.isDigit(temp2[i]))
				sb.append(temp2[i]);
		}
		if (sb.length() < AGN_LENGTH) {
			for (int j = 0; j <= sb.length() - AGN_LENGTH; j++)
				sb.append('0');
		}
		sb.reverse();
		timeStr = sb.toString();
		if (timeStr.length() > AGN_LENGTH) {
			timeStr = timeStr.substring(timeStr.length() - AGN_LENGTH);
		}
		time = new java.math.BigDecimal(timeStr);
		return time;
	}

	public static String getHost() {
		return _HOST;
	}

	/**
	 * This method was added as a substitute for the getResource() method
	 * because Netscape has an implementation bug with getResource(). This will
	 * ensure that all images get loaded correctly when run in an applet.
	 * 
	 * @author Karthik Madhya Creation date: (05/16/2000 2:28:03 PM)
	 */
	public static Image getImageAsStream(String imagePath) {
		Image anImage = null;

		try {
			InputStream in = AS2Helper.getInstance().getClass()
					.getResourceAsStream(imagePath);
			if (in == null) {
				System.err.println("J2EEHelper:Image not found");
				return null;
			}
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			anImage = Toolkit.getDefaultToolkit().createImage(buffer);

		} catch (java.io.IOException e) {
			System.err.println("J2EEHelper:Unable to read image.");
			e.printStackTrace();
		}
		return anImage;
	}

	public static AS2Helper getInstance() {
		if (instance == null)
			instance = new AS2Helper();
		return instance;
	}

	public static int getIntProperty(Properties prop, String name, int def) {
		String s = prop.getProperty(name);
		try {
			return s != null ? Integer.parseInt(s) : def;
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static long getLongProperty(Properties prop, String name, long def) {
		String s = prop.getProperty(name);
		try {
			return s != null ? Long.parseLong(s) : def;
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public static String getStackTraceAsString(Exception e) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(bytes, true);
		e.printStackTrace(writer);
		return bytes.toString();
	}

	public static boolean getUseEBCIDIC() {
		return _useEBCIDIC;
	}

	public static void printScreen(JFrame frame, JPanel panelToPrint) {
		try {
			PrintJob pjob = frame.getToolkit().getPrintJob(frame, "printing",
					null);
			if (pjob != null) {
				Graphics pg = pjob.getGraphics();
				panelToPrint.printAll(pg);
				pg.dispose();
				pjob.end();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Error while printing screen",
					"Print Status", JOptionPane.ERROR_MESSAGE);

		}
	}

	public synchronized static Properties readAndConvertEBCIDICPropertyFileAsURL(
			String aFileLocation) {

		URL aUrl = null;
		Properties prop = new Properties();
		InputStream in = null;
		BufferedReader r = null;
		java.util.StringTokenizer st;

		try {
			if (!_useEBCIDIC) {
				return readPropertyFileAsURL(aFileLocation);
			}

			aUrl = new java.net.URL(aFileLocation);
			URLConnection aURLConnection = aUrl.openConnection();
			aURLConnection.setUseCaches(false);
			aURLConnection.setRequestProperty("content-type", "text/html");
			AS2User aUser = AS2UserFactory.getInstance().getSystemUser();
			if (aUser == null)
				aUser = AS2UserFactory.getInstance().getUser("DEFAULT");
			aURLConnection.setRequestProperty(
					"Authorization",
					"BASIC "
							+ AS2Base64.encode(aUser.getUserId() + ":"
									+ aUser.getPassword()));
			in = aURLConnection.getInputStream();
			if (in == null) {
				System.err
						.println("AS2Helper:readAndConvertEBCIDICPropertyFileAsURL Can not open file: "
								+ aFileLocation);
			}

			r = new BufferedReader(new InputStreamReader(in, "Cp500"));

			int idx;
			String line;
			String key;
			String value;
			byte[] ba = new byte[4096];
			char[] buffer = new char[4096];
			while ((r.read(buffer)) != -1) {
				int kk = 0;
				while (kk < buffer.length) {
					ba[kk] = (byte) buffer[kk];
					int pp = ba[kk];
					if (pp == -123) {
						buffer[kk] = '?';
					}
					kk++;
				}
			}
			line = new String(buffer);

			st = new java.util.StringTokenizer(line, "?");
			while (st.hasMoreTokens()) {
				String n = st.nextToken();

				if (n.startsWith("#") || line.length() < 1)
					continue;
				idx = n.indexOf("=");
				if (idx > 0) {
					key = n.substring(0, idx);
					value = n.substring(idx + 1);
					prop.put(key, value);
				}
			}
			if (prop.size() == 0)
				System.err
						.println("AS2Helper:readAndConvertEBCIDICPropertyFileAsURL size = 0 "
								+ aFileLocation);

		} catch (StringIndexOutOfBoundsException e) {
			System.err
					.println("AS2Helper:readAndConvertEBCIDICPropertyFileAsURL "
							+ aFileLocation);
		} catch (IOException e) {
			_triedURL = true;
			System.err
					.println("AS2Helper:readAndConvertEBCIDICPropertyFileAsURL "
							+ aFileLocation);
			if (!_triedFile)
				prop = readPropertyFile(aFileLocation);

		} finally {
			try {
				_triedFile = false;
				_triedURL = false;

				if (in != null)
					in.close();
				if (r != null)
					r.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return prop;
	}

	public synchronized static Properties readPropertyFile(String aFileLocation) {
		Properties prop = new Properties();
		InputStream in = readResourceToStream(aFileLocation);
		try {
			prop.load(in);
			in.close();
			if (prop.size() == 0)
				System.err.println("AS2Helper:readPropertyFile size = 0 "
						+ aFileLocation);

		} catch (IOException e) {
			_triedFile = true;
			System.err.println("AS2Helper:readPropertyFile " + aFileLocation);
			if (!_triedURL)
				prop = readPropertyFileAsURL(aFileLocation);
		} finally {
			_triedFile = false;
			_triedURL = false;
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	public synchronized static File readResourceToFile(String aFileLocation) {
		File in = null;
		try {
			AS2FileResource config = new AS2FileResource(aFileLocation);
			in = config.getFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return in;
	}

	public synchronized static Serializable readResourceToSerializable(
			String aFileLocation) {
		File in = null;
		try {
			AS2FileResource config = new AS2FileResource(aFileLocation);
			in = config.getFile();
			return (Serializable) in;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized static InputStream readResourceToStream(
			String aFileLocation) {
		InputStream in = null;
		try {
			AS2FileResource config = new AS2FileResource(aFileLocation);
			in = config.getInputStream();
		} catch (IOException e) {
			_triedFile = true;
			if (!_triedURL)
				in = readResourceToStreamAsURL(aFileLocation);
		}
		return in;
	}

	public synchronized static Properties readPropertyFileAsURL(
			String aFileLocation) {

		Properties prop = new Properties();
		InputStream in = readResourceToStreamAsURL(aFileLocation);

		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				_triedFile = false;
				_triedURL = false;
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (prop.size() == 0)
			System.err.println("AS2Helper:readPropertyFileAsURL size = 0 "
					+ aFileLocation);

		return prop;
	}

	public synchronized static InputStream readResourceToStreamAsURL(
			String aFileLocation) {
		URL aUrl = null;
		InputStream in = null;
		try { // need here a code base if not at the local machine
				// if (_useEBCIDIC)
				// {
				// return readAndConvertEBCIDICPropertyFileAsURL(aFileLocation);
				// }
			aUrl = new java.net.URL(aFileLocation);
			URLConnection aURLConnection = aUrl.openConnection();
			// Tell the connection not to cache data.
			aURLConnection.setUseCaches(false);
			aURLConnection.setRequestProperty("content-type", "text/html");

			AS2User aUser = AS2UserFactory.getInstance().getSystemUser();
			if (aUser == null)
				aUser = AS2UserFactory.getInstance().getUser("DEFAULT");

			aURLConnection.setRequestProperty(
					"Authorization",
					"BASIC "
							+ AS2Base64.encode(aUser.getUserId() + ":"
									+ aUser.getPassword()));
			in = aURLConnection.getInputStream();

			if (in == null) {
				System.err.println("Can not open file authorized: "
						+ aFileLocation);
			}
		} catch (IOException e) {
			_triedURL = true;
			if (!_triedFile)
				in = readResourceToStream(aFileLocation);
		}
		return in;
	}

	public static void setHost(String value) {
		if (value != null)
			_HOST = value;
	}

	public static void setUseEBCIDIC(boolean value) {
		_useEBCIDIC = value;
	}

	public static boolean startClient(String[] args) {
		try {
			// ---------------------------

			// String _HOST = "localhost";
			String _TRANSPORT = "LOCAL";
			String _USER = _STR_EMPTY;
			// String _PASSWORD = _STR_EMPTY;
			// String _EBCIDIC = _STR_EMPTY;
			String _SETTINGS_PATH = _STR_EMPTY;
			String _LICENSE = _STR_EMPTY;
			// String _DEVICE = _STR_EMPTY;

			// boolean isLongLauncher = false;

			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-h")) {
					if (args.length > i + 1) {
						_HOST = args[++i];
					} else {
						System.out
								.println("Invalid -h argument. Usage: -h hostname");
					}
				} else if (args[i].equals("-t")) {
					if (args.length > i + 1) {
						_TRANSPORT = args[++i];
					} else {
						System.out
								.println("Invalid -t argument. Usage: -t LOCAL|HTTP|RMI|EJB");
					}
				} else if (args[i].equals("-e")) {
					if (args.length > i + 1) {
						_ENVIRONMENT = args[++i];
					} else {
						System.out
								.println("Invalid -e argument. Usage: -e T|P");
					}
				} else if (args[i].equals("-l")) {
					if (args.length > i + 1) {
						_LICENSE = _LICENSE.concat(args[++i]).concat(" ");
						_LICENSE = _LICENSE.concat(args[++i]).concat(" ");
						_LICENSE = _LICENSE.concat(args[++i]);
					} else {
						System.out
								.println("Invalid -l argument. Usage: -l licence");
					}
				} else if (args[i].equals("-d")) {
					if (args.length > i + 1) {
						// _DEVICE = args[++i];
					} else {
						System.out
								.println("Invalid -d argument. Usage: -d device");
					}
				} else if (args[i].equals("-u")) {
					if (args.length > i + 1) {
						_USER = args[++i];
					} else {
						System.out
								.println("Invalid -u argument. Usage: -u user id");
					}
				} else if (args[i].equals("-p")) {
					if (args.length > i + 1) {
						// _PASSWORD = args[++i];
					} else {
						System.out
								.println("Invalid -p argument. Usage: -p password");
					}
				} else if (args[i].equals("-settings")) {
					if (args.length > i + 1) {
						_SETTINGS_PATH = args[++i];
					} else {
						System.out
								.println("Invalid -settings argument. Usage: -settings files path");
					}
				} else if (args[i].equals("?")) {
					System.out
							.println("Usage [-h hostname -t LOCAL|HTTP|RMI|EJB -e D|T|P -l licence"
									+ " -u user id -p password -settings files path]");
				}
			}
			AS2Context.setPropertiesPath(_SETTINGS_PATH);
			AS2Context.TRANSPORT = _TRANSPORT;
			AS2Context.GUEST_USER = _USER;
			if (isDevelopment()) {
			} else {
				AS2ClientRequestDispatcher.getInstance().init(_TRANSPORT, _HOST);
			}
		} catch (Exception e) {
			System.out.println("ERROR Starting CLIENT:" + e);
			return false;
		}
		return true;
	}

	public static boolean isDevelopment() {
		return _ENVIRONMENT.equalsIgnoreCase("D");
	}

	public static boolean isProductionEnv() {
		return _ENVIRONMENT.equalsIgnoreCase("P");
	}

	public static boolean isTestEnv() {
		return _ENVIRONMENT.equalsIgnoreCase("T");
	}

	public synchronized static boolean writePropertyFile(Properties prop,
			String aFileLocation) {
		boolean done = false;
		try {
			StringBuffer header = new StringBuffer();
			header.append("Property file header");
			FileOutputStream fstream = new FileOutputStream(aFileLocation);
			prop.store(fstream, header.toString());
			fstream.close();
			done = true;
		} catch (IOException e) {
			e.printStackTrace();
			done = false;
		}
		return done;
	}

	public static boolean setLocalDateMSDOS(Date date) {
		String tmps = null;
		boolean retVal = false;
		if (date != null) {
			Calendar cal = new GregorianCalendar();
			NumberFormat nf = new DecimalFormat();
			cal.setTime(date);
			nf.setMinimumIntegerDigits(2);
			nf.setMaximumIntegerDigits(2);
			String command = "cmd /c date "
					+ nf.format(cal.get(Calendar.DAY_OF_MONTH))
					+ "-"
					+ nf.format(cal.get(Calendar.MONTH) + 1)
					+ "-"
					+ new String(_STR_EMPTY + cal.get(Calendar.YEAR))
							.substring(2);

			try {

				// run the DOS time command
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec(command);

				BufferedReader stdInput = new BufferedReader(
						new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(
						new InputStreamReader(p.getErrorStream()));

				// read the output from the command

				System.out
						.println("Here is the standard output of the command:\n");
				while ((tmps = stdInput.readLine()) != null) {
					System.out.println(tmps);
				}
				retVal = true;

				// read any errors from the attempted command

				System.out
						.println("Here is the standard error of the command (if any):\n");
				while ((tmps = stdError.readLine()) != null) {
					retVal = false;
					System.err.println(tmps);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

	public static boolean setLocalTimeMSDOS(Date time) {
		String tmps = null;
		boolean retVal = false;
		if (time != null) {
			Calendar cal = new GregorianCalendar();
			NumberFormat nf = new DecimalFormat();
			cal.setTime(time);
			nf.setMinimumIntegerDigits(2);
			nf.setMaximumIntegerDigits(2);
			String command = "cmd /c time "
					+ nf.format(cal.get(Calendar.HOUR_OF_DAY)) + ":"
					+ nf.format(cal.get(Calendar.MINUTE)) + ":"
					+ nf.format(cal.get(Calendar.SECOND));

			try {

				// run the DOS time command
				Runtime rt = Runtime.getRuntime();
				Process p = rt.exec(command);

				BufferedReader stdInput = new BufferedReader(
						new InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(
						new InputStreamReader(p.getErrorStream()));

				// read the output from the command

				System.out
						.println("Here is the standard output of the command:\n");
				while ((tmps = stdInput.readLine()) != null) {
					System.out.println(tmps);
				}
				retVal = true;

				// read any errors from the attempted command

				System.out
						.println("Here is the standard error of the command (if any):\n");
				while ((tmps = stdError.readLine()) != null) {
					retVal = false;
					System.err.println(tmps);
				}
			} catch (IOException e) {
				System.err.println("exception happened - here's what I know: ");
				e.printStackTrace();
			}
		}
		return retVal;
	}

	public static boolean setLocalDateAndTimeMSDOS(Date date) {
		boolean retVal = true;
		if (date != null) {
			Date currDate = new Date();
			retVal &= setLocalDateMSDOS(date);
			retVal &= setLocalTimeMSDOS(date);

			if (!retVal) {
				setLocalDateMSDOS(currDate);
				setLocalTimeMSDOS(currDate);
			}
		}
		return retVal;
	}

	public static String getTitleFromResource(String title) {
		return getTitleFromResource(title, new Object[] {});
	}

	public static String getTitleFromResource(String title, String field_one) {
		return getTitleFromResource(title, new Object[] { field_one });
	}

	public static String getTitleFromResource(String error_code, Object[] fields) {
		return MessageFormat.format(
				java.util.ResourceBundle.getBundle(AS2Context.TITLES_PATH)
						.getString(error_code), fields);
	}

	public static void runScript(String name) {
		String tmps = null;
		String command = "cmd /c dir ";

		try {
			// run the DOS/UNIX command
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec(command);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			// read the output from the command

			System.out.println("Here is the standard output of the command:\n");
			while ((tmps = stdInput.readLine()) != null) {
				System.out.println(tmps);
			}

			System.out
					.println("Here is the standard error of the command (if any):\n");
			while ((tmps = stdError.readLine()) != null) {
				System.err.println(tmps);
			}
		} catch (IOException e) {
			System.err.println("exception happened - here's what I know: ");
			e.printStackTrace();
		}
	}
}
