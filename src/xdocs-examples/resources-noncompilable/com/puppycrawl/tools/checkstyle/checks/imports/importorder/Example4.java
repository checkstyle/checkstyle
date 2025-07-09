/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="groups" value="*,javax,java"/>
      <property name="ordered" value="true"/>
      <property name="separated" value="false"/>
      <property name="option" value="bottom"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ImportOrder"/>
      <property name="message" value="^'java\..*'.*"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import com.neurologic.http.impl.ApacheHttpClient;
import static java.awt.Button.A;
import javax.swing.JComponent;
// violation above 'Wrong order for 'javax.swing.JComponent' import'
// caused by the above static import (all static imports should be at the bottom)
import java.net.URL; // violation, extra separation in import group
import java.security.KeyManagementException;
import javax.swing.JComponent; // violation, wrong order, 'javax' should be above 'java' imports
import com.neurologic.http.HttpClient; // violation, wrong order, 'com' imports should be at top
// xdoc section -- end

public class Example4 { }
