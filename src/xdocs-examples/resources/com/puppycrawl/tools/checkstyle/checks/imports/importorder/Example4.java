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

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import com.sun.security.auth.UserPrincipal;
import static java.lang.Math.abs;
import javax.swing.JComponent; // violation, wrong order, 'javax' should come after all '*' group imports
import java.net.URL;
import java.security.KeyManagementException;
import javax.swing.JComponent; // violation, duplicate + wrong position
import com.sun.source.tree.Tree; // violation, wrong order, 'com' imports should be at top
// xdoc section -- end

public class Example4 { }
