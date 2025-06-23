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
// non-compiled with javac: Compilable with Java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Math.PI;
import static java.io.File.createTempFile;
import static javax.swing.JComponent; // violation, should be separated from previous imports
import static javax.WindowConstants.*;

import java.net.URL;
// xdoc section -- end

public class Example10 { }
