/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="sortStaticImportsAlphabetically" value="true"/>
      <property name="option" value="top"/>
    </module>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Math.PI;
import static java.lang.Math.abs; // ok, alphabetical case-sensitive ASCII order, 'P' < 'a'
import static java.util.Collections.emptyList; // ok, alphabetical after Math.*

import java.util.Set; // violation, extra separation in import group
import static java.lang.Math.sin; // violation, wrong order, all static imports must be at 'top'
import org.w3c.dom.*;
// xdoc section -- end

public class Example6 { }
