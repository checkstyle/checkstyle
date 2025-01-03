/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="staticGroups" value="org,java"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
      <property name="option" value="top"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static org.abego.treelayout.Configuration.AlignmentInLevel; // Group 1
import static java.lang.Math.abs; // Group 2
import static java.lang.String.format; // Group 2
import static com.google.common.primitives.Doubles.BYTES; // Group "everything else"
// xdoc section -- end

public class Example7 { }
