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
// non-compiled with javac: Compilable with java17
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Math.PI;
import static java.lang.Math.abs; // ok, alphabetical case-sensitive ASCII order, 'P' < 'a'
import static org.abego.treelayout.Configuration.AlignmentInLevel; // ok, alphabetical order

import java.util.Set; // violation, extra separation in import group
import static java.lang.Math.abs; // violation, wrong order, all static imports comes at 'top'
import org.abego.*;
// xdoc section -- end

public class Example6 { }
