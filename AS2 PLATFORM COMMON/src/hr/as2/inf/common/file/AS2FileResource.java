package hr.as2.inf.common.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * A class to locate resources, retrieve their contents, and determine their
 * last modified time. To find the resource the class searches the CLASSPATH
 * first, then Resource.class.getResource("/" + name). If the Resource finds
 * a "file:" URL, the file path will be treated as a file. Otherwise, the
 * path is treated as a URL and has limited last modified info.
 * Date: 24.4.2003.
 */
public class AS2FileResource implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String name;
  private File file;
  private URL url;
/**
 * J2EEResource constructor comment.
 */
public AS2FileResource() {
	super();
}
  public AS2FileResource(String name) throws IOException {
    this.name = name;
    SecurityException exception = null;

    try {
      // Search using the CLASSPATH. If found, "file" is set and the call
      // returns true.  A SecurityException might bubble up.
      if (tryClasspath(name)) {
        return;
      }
    }
    catch (SecurityException e) {        
      exception = e;  // Save for later.
    }

    try {
      // Search using the classloader getResource(  ). If found as a file,
      // "file" is set; if found as a URL, "url" is set.
      if (tryLoader(name)) {
        return;
      }
    }
    catch (SecurityException e) {        
      exception = e;  // Save for later.
    }

    // If you get here, something went wrong. Report the exception.
    @SuppressWarnings("unused")
	String msg="";
    if (exception != null) {
      msg = ": " + exception;
    }

//	throw new IOException("Resource '" + name + "' could not be found in " +
//      "the CLASSPATH (" + System.getProperty("java.class.path") +
//      "), nor could it be located by the classloader responsible for the " +
//      "web application (WEB-INF/classes)" + msg);

  }
  /**
   * Returns the directory containing the resource, or null if the 
   * resource isn't directly available on the filesystem. 
   * This value can be used to locate the configuration file on disk,
   * or to write files in the same directory.
   */
  public String getAbsolutePath(  ) {
    if (file != null) {
      return file.getAbsolutePath();
    }
    else if (url != null) {
      return null;
    }
    return null;
  }
  /**
   * Returns the directory containing the resource, or null if the 
   * resource isn't directly available on the filesystem. 
   * This value can be used to locate the configuration file on disk,
   * or to write files in the same directory.
   */
  public String getDirectory(  ) {
    if (file != null) {
      return file.getParent(  );
    }
    else if (url != null) {
      return null;
    }
    return null;
  }
  /**
   * Returns an file 
   */
  public File getFile() throws IOException {
    if (file != null) {
      return file;
    }
    else if (url != null) {
        // Try converting from a URL to a File.
        File resFile = urlToFile(url);
        if (resFile != null) {
          file = resFile;
          return file;
        }
    }
    return null;
  }
  /**
   * Returns an input stream to read the resource contents
   */
  public InputStream getInputStream(  ) throws IOException {
    if (file != null) {
      return new BufferedInputStream(new FileInputStream(file));
    }
    else if (url != null) {
      return new BufferedInputStream(url.openStream(  ));
    }
    return null;
  }
  /**
   * Returns the resource name, as passed to the constructor
   */
  public String getName(  ) {
    return name;
  }
  /**
   * Returns the directory containing the resource, or null if the 
   * resource isn't directly available on the filesystem. 
   * This value can be used to locate the configuration file on disk,
   * or to write files in the same directory.
   */
  public String getPath(  ) {
    if (file != null) {
      return file.getPath(  );
    }
    else if (url != null) {
      return null;
    }
    return null;
  }
  /**
   * Returns when the resource was last modified. If the resource 
   * was found using a URL, this method will work only if the URL 
   * connection supports last modified information. If there's no 
   * support, Long.MAX_VALUE is returned. Perhaps this should return 
   * -1, but you should return MAX_VALUE on the assumption that if
   * you can't determine the time, it's maximally new.
   */
  public long lastModified(  ) {
    if (file != null) {
      return file.lastModified(  );
    }
    else if (url != null) {
      try {
        return url.openConnection(  ).getLastModified(  );  // Hail Mary
      }
      catch (IOException e) { return Long.MAX_VALUE; }
    }
    return 0;  // can't happen
  }
  private static File searchDirectories(String[  ] paths, String filename) {
    SecurityException exception = null;
    for (int i = 0; i < paths.length; i++) {
      try {
        File file = new File(paths[i], filename);
        if (file.exists(  ) && !file.isDirectory(  )) {
          return file;
        }
      }
      catch (SecurityException e) {
        // Security exceptions can usually be ignored, but if all attempts
        // to find the file fail, report the (last) security exception.
        exception = e;
      }
    }
    // Couldn't find any match
    if (exception != null) {
      throw exception;
    }
    else {
      return null;
    }
  }
  // Splits a String into pieces according to a delimiter.
  // Uses JDK 1.1 classes for backward compatibility.
  // JDK 1.4 actually has a split(  ) method now.
  @SuppressWarnings("unchecked")
private static String[  ] split(String str, String delim) {
    // Use a Vector to hold the split strings.
    @SuppressWarnings("rawtypes")
	Vector v = new Vector(  );

    // Use a StringTokenizer to do the splitting.
    StringTokenizer tokenizer = new StringTokenizer(str, delim);
    while (tokenizer.hasMoreTokens(  )) {
      v.addElement(tokenizer.nextToken(  ));
    }

    String[  ] ret = new String[v.size(  )];
    v.copyInto(ret);
    return ret;
  }
  public String toString(  ) {
    return "[Resource: File: " + file + " URL: " + url + "]";
  }
  // Returns true if found
  private boolean tryClasspath(String filename) {
    String classpath = System.getProperty("java.class.path");    
    String[  ] paths = split(classpath, File.pathSeparator);
    file = searchDirectories(paths, filename);
    return (file != null);
  }
  // Returns true if found
  private boolean tryLoader(String name) {
    name = "/" + name;
    URL res = AS2FileResource.class.getResource(name);
    if (res == null) {
      return false;
    }
    // Try converting from a URL to a File.
    File resFile = urlToFile(res);
    if (resFile != null) {
      file = resFile;
    }
    else {        
      url = res;
    }    
    return true;
  }
  private static File urlToFile(URL res) {//TODO ne radi niti za properties vec koristimo URL
    String externalForm = res.toExternalForm(  );
//    System.out.println("tryLoader out @@externalForm DANAS= toExternalForm ="+externalForm);
//    System.out.println("tryLoader out @@externalForm DANAS= getFile ="+res.getFile());
//    System.out.println("tryLoader out @@externalForm DANAS= getPath ="+res.getPath());
//    System.out.println("tryLoader out @@externalForm DANAS= getQuery ="+res.getQuery());
    if (externalForm.startsWith("file:")) {
      return new File(externalForm.substring(5));
    }//else if (externalForm.endsWith("xls")) { //TODO novo
//       return new File(res.getFile().substring(5).replaceAll("\\","/")); //TODO drugacije
//    }
//    System.out.println("tryLoader out @@externalForm DANAS PROBLEM ------= 0004 ="+externalForm);
    return null;
  }
}
