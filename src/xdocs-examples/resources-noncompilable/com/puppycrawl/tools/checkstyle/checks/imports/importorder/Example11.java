/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="staticGroups" value=""/>
      <property name="option" value="top"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.listRoots;   // OK
import static javax.swing.WindowConstants.*; // OK
import static java.io.File.createTempFile; // OK
import static com.puppycrawl.tools.checkstyle;  // OK

public class Example11 { }
// xdoc section -- end
