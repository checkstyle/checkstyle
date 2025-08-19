/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="staticGroups" value="*,java,javax,org"/>
      <property name="option" value="top"/>
      <property name="separatedStaticGroups" value="true"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Math.PI;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*;// violation, should be separated from previous imports
import com.sun.security.auth.UserPrincipal; // violation, should be separated from previous imports
import com.sun.source.tree.Tree;

import java.net.URL; // violation, , should be separated from previous imports
// xdoc section -- end

public class Example10 { }
