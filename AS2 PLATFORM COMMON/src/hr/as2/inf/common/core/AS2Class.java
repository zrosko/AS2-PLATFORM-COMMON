/*
 * Copyright 2015 Adriacom Software Inc.
 */ 
package hr.as2.inf.common.core;

import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.logging.AS2Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * Class utility methods.
 */
public class AS2Class {
	public static final String AS2_PACKAGE = "hr.as2";

	/**
	 * If the class contains the exact method without argumetns.
	 * <p>
	 */
	public static boolean hasMethod(Class<?> theClass, String method) {
		try {
			theClass.getMethod(method, theClass);
			return true;
		} catch (NoSuchMethodException ex) {
			return false;
		}
	}

	public static boolean isAS2Class(Class<?> iClass) {
		return iClass.getName().startsWith(AS2_PACKAGE);
	}

	/**
	 * Convenience accessor for the names of all class files in the jar file
	 * with the specified name. The returned class names are of the form
	 * "org.datanucleus.MyClass".
	 */
	public static String[] getClassNamesForJarFile(String jarFileName) {
		try {
			JarFile jar = new JarFile(jarFileName);
			return getClassNamesForJarFile(jar);
		} catch (IOException ioe) {
			AS2Logger.AS2_GENERAL.warn("Error opening the jar file "
					+ jarFileName );
		}
		return null;
	}
	 /**
	* Convenience accessor for the names of all class files in the jar file with the specified URL.
	* The returned class names are of the form "org.datanucleus.MyClass".
	* @param jarFileURL URL for the jar file
	* @return The class names
	*/
	    public static String[] getClassNamesForJarFile(URL jarFileURL)
	    {
	        File jarFile = new File(jarFileURL.getFile()); // TODO Check for errors
	        try
	        {
	            JarFile jar = new JarFile(jarFile);
	            return getClassNamesForJarFile(jar);
	        }
	        catch (IOException ioe)
	        {
	            AS2Logger.AS2_GENERAL.warn("Error opening the jar file " + jarFileURL.getFile() + " : " + ioe.getMessage());
	        }
	        return null;
	    }
	    /**
	    * Convenience accessor for the names of all class files in the jar file with the specified URL.
	    * The returned class names are of the form "org.datanucleus.MyClass".
	    * @param jarFileURI URI for the jar file
	    * @return The class names
	    */
	        public static String[] getClassNamesForJarFile(URI jarFileURI)
	        {
	            try
	            {
	                return getClassNamesForJarFile(jarFileURI.toURL());
	            }
	            catch (MalformedURLException mue)
	            {
	                throw new AS2Exception("Error opening the jar file " + jarFileURI);
	            }
	        }

	        /**
	    * Convenience method to return the names of classes specified in the jar file.
	    * All inner classes are ignored.
	    * @param jar Jar file
	    * @return The class names
	    */
	        private static String[] getClassNamesForJarFile(JarFile jar)
	        {
	            Enumeration<?> jarEntries = jar.entries();
	            Set<String> classes = new HashSet<String>();
	            while (jarEntries.hasMoreElements())
	            {
	                String entry = ((JarEntry)jarEntries.nextElement()).getName();
	                if (entry.endsWith(".class") && !AS2Class.isInnerClass(entry))
	                {
	                    String className = entry.substring(0, entry.length()-6); // Omit ".class"
	                    className = className.replace(File.separatorChar, '.');
	                    classes.add(className);
	                }
	            }
	            return classes.toArray(new String[classes.size()]);
	        }
	        /**
	        * Method to check whether a classname is for an inner class.
	        * Currently checks for the presence of $ in the name.
	        * @param class_name The class name
	        * @return Whether it is an inner class
	        */
	            public static boolean isInnerClass(String class_name)
	            {
	                if (class_name == null)
	                {
	                    return false;
	                }
	                else if (class_name.indexOf('$') >= 0)
	                {
	                    return true;
	                }
	                return false;
	            }

}