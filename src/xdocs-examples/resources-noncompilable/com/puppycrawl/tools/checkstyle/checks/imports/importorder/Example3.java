/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="groups" value="/^java\./,javax,org,com"/>
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
import static java.io.File.createTempFile;
import static java.lang.Math.abs; // ok, alphabetical case-sensitive ASCII order, 'i' < 'l'
import java.lang.Math.sqrt; // ok, follows property 'Option' value 'above'
import java.io.File; // violation, alphabetical case-sensitive ASCII order, 'i' < 'l'

import java.io.IOException; // violation, extra separation in 'java' import group

import org.albedo.*;

import static javax.WindowConstants.*; // violation, wrong order, 'javax' comes before 'org'
import javax.swing.JComponent;
import org.apache.http.ClientConnectionManager; // violation, must separate from previous import
import org.linux.apache.server.SoapServer;

import com.neurologic.http.HttpClient;
import com.neurologic.http.impl.ApacheHttpClient;
// xdoc section -- end

public class Example3 { }
