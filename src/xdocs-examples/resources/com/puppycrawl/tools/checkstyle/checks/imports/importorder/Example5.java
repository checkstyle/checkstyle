/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="option" value="inflow"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.createTempFile;
import static java.lang.Math.sqrt;

import javax.swing.JComponent; // violation 'Extra separation in import group'
import static javax.swing.JTree.*;
// all static imports are processed like non static imports.
// xdoc section -- end

public class Example5 { }
