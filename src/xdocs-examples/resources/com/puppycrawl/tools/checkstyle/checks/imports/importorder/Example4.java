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
import javax.swing.JComponent; // violation 'Import 'javax.swing.JComponent' violates the configured relative order between static and non-static imports.'
import java.net.URL;
import java.security.KeyManagementException;
import javax.swing.JComponent; // violation 'Import statement for 'javax.swing.JComponent' violates the configured import group order.'
import com.sun.source.tree.Tree; // violation 'Import statement for 'com.sun.source.tree.Tree' violates the configured import group order.'
// xdoc section -- end

public class Example4 { }
