/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhenShouldBeUsed"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.whenshouldbeused;

// xdoc section -- start
public class Example1 {

  void testNoGuard(Object o) {
    switch (o) {
      // violation below, 'When should be used instead of a single if*.'
      case String s -> {
        if (s.isEmpty()) {
          System.out.println("empty string");
        }
      }
      default -> {}
    }
    switch (o) {
      case String s -> { // ok, not a single if statement inside the case block
        System.out.println("String");
        if (s.isEmpty()) {
          System.out.println("but empty");
        }
      }
      default -> {}
    }
  }

  void testGuardedCaseLabel(Object o) {
    switch (o) {
      case String s when s.isEmpty() -> {
        System.out.println("empty string");
      }
      default -> {}
    }
  }

}
// xdoc section -- end
