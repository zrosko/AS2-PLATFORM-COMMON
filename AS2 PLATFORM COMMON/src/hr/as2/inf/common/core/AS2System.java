/* (c) Adriacom Software d.o.o.
 * 22211 Vodice, Croatia
 * Created by Z.Rosko (zrosko@yahoo.com)
 * Date 2010.08.04 
 * Time: 15:16:14
 */
package hr.as2.inf.common.core;


public class AS2System {
    public String javaVersion(){
        return System.getProperty("java.vm.version");
        //version.startsWith("1.4");
    }
}
