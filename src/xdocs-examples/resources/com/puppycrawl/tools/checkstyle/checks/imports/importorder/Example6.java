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

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.lang.Math.PI;
import static java.lang.Math.abs; // ok, alphabetical case-sensitive ASCII order, 'P' < 'a'
import static java.util.Collections.emptyList; // ok, alphabetical after Math.*

import java.util.Set; // violation 'Extra separation in import group before 'java.util.Set''
import static java.lang.Math.sin; // violation 'Import 'java.lang.Math.sin' violates the configured relative order between static and non-static imports.'
import org.w3c.dom.*;
// xdoc section -- end

public class Example6 { }
