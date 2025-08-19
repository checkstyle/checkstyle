/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="staticGroups" value="java, javax"/>
      <property name="option" value="top"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.listRoots;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static java.io.File.createTempFile; // violation, should be before javax
import static org.apache.commons.lang3.StringUtils.isEmpty;
// xdoc section -- end

public class Example12 { }
