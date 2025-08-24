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

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static org.w3c.dom.Node.ELEMENT_NODE; // Group 1
import static java.lang.Math.abs; // Group 2
import static java.lang.String.format; // Group 2
import static javax.swing.WindowConstants.EXIT_ON_CLOSE; // Group "everything else"
// xdoc section -- end

public class Example7 { }
