/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="useContainerOrderingForStatic" value="true"/>
      <property name="ordered" value="true"/>
      <property name="option" value="top"/>
      <property name="caseSensitive" value="false"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.util.Collections.sort;
import static java.util.Collections.emptyList; // violation, wrong order inside each container group
import static java.lang.Math.sin; // violation, wrong order inside each container group
import static java.lang.Math.abs; // violation, wrong order inside each container group
import static java.lang.Math.E;
import static java.lang.Math.PI;
// xdoc section -- end

public class Example8 { }
