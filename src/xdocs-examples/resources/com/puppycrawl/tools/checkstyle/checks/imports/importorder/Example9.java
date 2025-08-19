/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="useContainerOrderingForStatic" value="false"/>
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
import static java.lang.Math.sin;
import static java.lang.Math.PI; // violation, 'Wrong order for...'
import static java.lang.Math.abs; // violation, 'Wrong order for...'
import static java.util.Collections.emptyList;
// xdoc section -- end

public class Example9 { }
