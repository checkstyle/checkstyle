/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="groups" value="/^java\./,javax,org"/>
      <property name="ordered" value="true"/>
      <property name="separated" value="true"/>
      <property name="option" value="above"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.System.out;
import static java.lang.Math; // violation, alphabetical case-sensitive ASCII order, 'M' < 'S'
import java.io.IOException;

import java.net.URL; // violation, extra separation before import
import java.security.KeyManagementException;

import javax.net.ssl.TrustManager;

import javax.net.ssl.X509TrustManager; // violation, groups should not be separated internally

import org.apache.http.conn.ClientConnectionManager;
// xdoc section -- end

public class Example2 { }
