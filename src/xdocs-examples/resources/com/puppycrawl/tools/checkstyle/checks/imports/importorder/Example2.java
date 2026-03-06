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

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.System.out;
import static java.lang.Math.PI; // violation 'Wrong lexicographical order for 'java.lang.Math.PI' import. Should be before 'java.lang.System.out'.'
import java.io.IOException;

import java.net.URL; // violation 'Extra separation in import group before 'java.net.URL''
import java.security.KeyManagementException;

import javax.net.ssl.TrustManager;

import javax.net.ssl.X509TrustManager; // violation 'Extra separation in import group before 'javax.net.ssl.X509TrustManager''

import org.w3c.dom.Document;
// xdoc section -- end

public class Example2 { }
