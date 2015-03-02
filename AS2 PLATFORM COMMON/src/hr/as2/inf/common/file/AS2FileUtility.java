package hr.as2.inf.common.file;

import hr.as2.inf.common.types.AS2Date;
import hr.as2.inf.common.types.AS2String;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class AS2FileUtility {
	public static final char PACKAGE_SEPARATOR = '.';
	public static final String PACKAGE_SEPARATOR_STRING = ".";
	public static final char PATH_SEPARATOR = '/';
	public static final String PATH_SEPARATOR_STRING = "/";
	public static final char WINDOWS_SEPARATOR = '\\';
	public static final String DEFAULT_SERVER_DIRECTORY = System.getProperty("catalina.home")+java.io.File.separator+"webapps"+java.io.File.separator+"ROOT"+java.io.File.separator;
	public static final String DEFAULT_SERVER_DIRECTORY_TEMP = System.getProperty("java.io.tmpdir")+java.io.File.separator;
	public static final String DEFAULT_DIRECTORY = System.getProperty("user.home")+ java.io.File.separator + "portal";
	
	public static byte[] readFileToBytes(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] b = new byte[in.available()];
        in.read(b);
        in.close();
        return b;
    }

    public static String readFileToString(File file) throws IOException {
        return new String(readFileToBytes(file));
    }

    public static void writeFileFromBytes(File file, byte[] content) throws IOException {
        /*
         * OutputStreamWriter sw = new OutputStreamWriter(new
         * FileOutputStream(file), "ASCII"); char [] chars = new char[5];
         * chars[0]='a'; chars[1]='\n'; chars[2]='b'; sw.write(chars);
         * sw.close();
         */
        FileOutputStream out = new FileOutputStream(file);
        out.write(content);
        out.close();
    }

    public static void writeFileFromString(File file, String content) throws IOException {
        writeFileFromBytes(file, content.getBytes());
    }

    public static byte[] readFileToBytes(String fileName) throws IOException {
        return readFileToBytes(new File(fileName));
    }
    //3.12.2013.
    public static byte[] readFileToBytesDefault(String fileName) throws IOException {
        return readFileToBytes(new File(prepareFileNameDefault(fileName)));
    }

    public static String readFileToString(String fileName) throws IOException {
        return readFileToString(new File(fileName));
    }
    //3.12.2013.
    public static String readFileToStringDefault(String fileName) throws IOException {
        return readFileToString(new File(prepareFileNameDefault(fileName)));
    }
    public static void writeFileFromBytes(String fileName, byte[] content) throws IOException {        
//        if(!new File(System.getProperty("java.io.tmpdir")).isDirectory())
//            createDirectory(System.getProperty("java.io.tmpdir"));
        AS2FileUtility.createDirectory(AS2FileUtility.DEFAULT_DIRECTORY);
        deleteFile(prepareFileNameDefault(fileName));
        writeFileFromBytes(new File(prepareFileNameDefault(fileName)), content);
    }

    public static void writeFileFromString(String fileName, String content) throws IOException {
        writeFileFromBytes(new File(fileName), content.getBytes());
    }
    //3.12.2013.
    public static void writeFileFromStringDefault(String fileName, String content) throws IOException {
        writeFileFromBytes(new File(prepareFileNameDefault(fileName)), content.getBytes());
    }
    public static void deleteFileOnExit(String fileName) {
        try {
            new File(fileName).deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //3.12.2013.
    public static void deleteFileOnExitDefault(String fileName) {
    	deleteFileOnExit(prepareFileNameDefault(fileName));
    }
    public static void deleteFile(String fileName) {
        try {
            new File(fileName).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //DIRECTORY
    public static void createDirectory(String dirName) {
        try {
            new File(dirName).mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //3.12.2013.
    public static String prepareFileNameDefault(String fileName){
    	AS2FileUtility.createDirectory(AS2FileUtility.DEFAULT_DIRECTORY);
    	return DEFAULT_DIRECTORY+java.io.File.separator+fileName;
    }
    public static String prepareFileNameTimestamp(String fileName){
    	StringBuffer tempFileName = new StringBuffer();
//    	tempFileName.append(AS2FileUtility.DEFAULT_DIRECTORY);
//    	tempFileName.append(java.io.File.separator);
    	tempFileName.append(fileName);
    	tempFileName.append(AS2Date.getCurrentTimeMillisec());
    	return tempFileName.toString();
    }
    public static String appendTimestampToFileName(String fileName){
    	StringBuffer tempFileName = new StringBuffer();
    	tempFileName.append(AS2String.cutTillLastDot(fileName));
    	tempFileName.append("_");
    	tempFileName.append(AS2Date.getCurrentTimeMillisec());
    	tempFileName.append(".");
    	tempFileName.append(AS2String.cutAfterLastDot(fileName));
    	return tempFileName.toString();
    }
    //3.12.2013.
    public static void openFileWithReader(String fileName){
 		try {
 			Runtime.getRuntime().exec("cmd.exe /c start \"\" \"" + fileName+ "\"");
 		} catch (Exception e) {
 			System.out.println("AS2FileUtility:openFileWithDefaultReader:"+fileName+e);
 		}
     }
    //3.12.2013.
    public static void openFileWithReaderDefault(String fileName){
		try {
			StringBuffer tempExcelFileName = new StringBuffer();
	        tempExcelFileName.append(DEFAULT_DIRECTORY+java.io.File.separator+fileName);
	        openFileWithReader(tempExcelFileName.toString());
		} catch (Exception e) {
			System.out.println("AS2FileUtility:openFileWithDefaultReader:"+fileName+e);
		}
    }
    //From Roma Framework
    public static File copyFile(File iOriginalFile, File iNewFile) {
		try {
			org.apache.commons.io.FileUtils.copyFile(iOriginalFile, iNewFile);
			return iNewFile;
		} catch (IOException ioe) {
			System.out.println("Unable to copy file \"" + iOriginalFile.getName() + "\" to \"" + iNewFile.getName() + " cause: " + ioe);
		}
		return null;
	}
	public static StringBuilder readFileAsText(File iOriginalFile) throws Exception {
		try {
			return new StringBuilder(org.apache.commons.io.FileUtils.readFileToString(iOriginalFile));
		} catch (Exception fnfe) {
			System.out.println("Unable to read file \"" + iOriginalFile.getName() + "\" cause: " + fnfe);
		} 
		return null;
	}
	public static String getFileSize(long iSize) {
		StringBuilder size = new StringBuilder();
		boolean stop = (iSize < 1024);
		int i = 0;
		while (!stop) {
			iSize = iSize / 1024;
			stop = (iSize < 1024);
			i++;
		}
		size.append(iSize);
		switch (i) {
		case 0:
			size.append(" bytes");
			break;
		case 1:
			size.append(" Kb");
			break;

		case 2:
			size.append(" Mb");
			break;
		case 3:
			size.append(" Gb");
			break;
		case 4:
			size.append(" Tb");
			break;
		default:
			break;
		}
		return size.toString();
	}
	public static File copy(String fromFileName, String toFileName)
			throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
		return toFile;
	}

	public void setValue(File file, Object newValue) throws Exception {
		Charset charset = null;//TODO test Vadin source
		if (file == null) {
			return;
		}

		try {
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = charset == null ? new OutputStreamWriter(
					fos) : new OutputStreamWriter(fos, charset);
			BufferedWriter w = new BufferedWriter(osw);
			w.append(newValue.toString());
			w.flush();
			w.close();
			osw.close();
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public Object getValue(File file) {
		Charset charset = null;//TODO test Vadin source
        if (file == null) {
            return null;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = charset == null ? new InputStreamReader(fis)
                    : new InputStreamReader(fis, charset);
            BufferedReader r = new BufferedReader(isr);
            StringBuilder b = new StringBuilder();
            char buf[] = new char[8 * 1024];
            int len;
            while ((len = r.read(buf)) != -1) {
                b.append(buf, 0, len);
            }
            r.close();
            isr.close();
            fis.close();
            return b.toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public static String[] getResourceNamesFirstSeparator(String iResourceName,
			String iSeparator, String iDefaultPrefixName) {
		if (iResourceName == null) {
			return null;
		}

		String names[] = new String[2];
		int pos = iResourceName.indexOf(iSeparator);
		names[0] = pos > -1 ? iResourceName.substring(0, pos)
				: iDefaultPrefixName;

		names[1] = iResourceName.substring(pos + 1);
		return names;
	}

	public static String[] getResourceNamesLastSeparator(String iResourceName,
			String iSeparator, String iDefaultPrefixName) {
		if (iResourceName == null) {
			return null;
		}

		String names[] = new String[2];
		int pos = iResourceName.lastIndexOf(iSeparator);
		names[0] = pos > -1 ? iResourceName.substring(0, pos)
				: iDefaultPrefixName;

		names[1] = iResourceName.substring(pos + 1);
		return names;
	}

	public static String removeLastSeparatorIfAny(String entryName) {
		if (entryName.endsWith(PATH_SEPARATOR_STRING)) {
			entryName = entryName.substring(0, entryName.length() - 1);
		}
		return entryName;
	}
}