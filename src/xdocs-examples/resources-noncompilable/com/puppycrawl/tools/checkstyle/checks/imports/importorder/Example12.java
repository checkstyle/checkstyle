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
// non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.listRoots;
import static javax.swing.WindowConstants.*;
import static java.io.File.createTempFile; // violation, should be before javax
import static com.puppycrawl.tools.checkstyle;
// xdoc section -- end

public class Example12 { }
