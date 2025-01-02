/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="option" value="inflow"/>
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static java.io.File.createTempFile;
import java.lang.Math.sqrt;

import javax.swing.JComponent; // violation '...separation in import group...'
import static javax.windowConstants.*; // OK
// all static imports are processed like non static imports.
// xdoc section -- end

public class Example5 { }
