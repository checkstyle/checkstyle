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
// Java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.createTempFile;
import static java.lang.Math.abs; // ok, alphabetical case-sensitive ASCII order, 'i' < 'l'

import java.io.File; // violation, alphabetical case-sensitive ASCII order, 'i' < 'l'

import java.io.IOException; // violation, extra separation in 'java' import group

import org.w3c.dom.Document;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE; // violation, wrong order, 'javax' must come before 'org'
import javax.swing.JComponent;
import org.w3c.dom.Element; // violation, must be separated from previous 'javax' import

import com.sun.security.auth.UserPrincipal;
import com.sun.source.tree.Tree;
// xdoc section -- end

public class Example3 { }
