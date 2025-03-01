/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalMethod">
    </module>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;
import java.io.*;
import java.util.function.Predicate;
// xdoc section -- start
class Example1 {
  void unusedMethod() { // ok, as not private
  }

  private void unusedMethod() { // violation, unused local method 'unusedMethod'
  }
}
// xdoc section -- end
