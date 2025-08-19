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
import static javax.swing.WindowConstants.EXIT_ON_CLOSE; // violation, should be separated from previous imports
import static org.apache.commons.lang3.StringUtils.isEmpty; // violation, should be separated from previous imports

import java.net.URL;
// xdoc section -- end

public class Example10 { }
