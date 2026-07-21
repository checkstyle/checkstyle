/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedLocalVariable">
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;
// xdoc section -- start
public class Example3 {
  sealed abstract static class Shape permits Circle, Rect {}
  static final class Circle extends Shape {}
  static final class Rect extends Shape {}
  void patternVariables(Object obj, Shape s) {
    if (obj instanceof String str) {
    } // violation above, unused named local variable 'str'
    if (obj instanceof String t) { // ok, 't' is used
      System.out.println(t);
    }
    if (obj instanceof String _) { // ok, '_' is unnamed variable
      System.out.println("string");
    }
    switch (s) { // violation below, unused local variable 'c'
      case Circle c -> System.out.println("circle");
      case Rect r   -> System.out.println(r); // ok, 'r' is used
      default       -> { }
    }
  }
}
// xdoc section -- end
