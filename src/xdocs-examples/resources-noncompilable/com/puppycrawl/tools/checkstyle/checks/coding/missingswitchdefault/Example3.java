/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingSwitchDefault"/>
  </module>
</module>
*/
//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;
// xdoc section -- start
sealed interface S permits A, B, C {}
final class A implements S {}
final class B implements S {}
record C(int i) implements S {}  // Implicitly final
class Example3 {
  static void showSealedCompleteness(S s) {
    switch (s) {
      case A a:
        System.out.println("A");
        break;
      case B b:
        System.out.println("B");
        break;
      case C c:
        System.out.println("C");
        break;
    }
  }
  static void showTotality(String s) {
    switch (s) {
      case Object o: // total type pattern
        System.out.println("o!");
    }
  }
  enum Color {RED, GREEN, BLUE}
  static int showSwitchExpressionExhaustiveness(Color color) {
    switch (color) {    // violation 'switch without "default" clause'
      case RED:
        System.out.println("RED");
        break;
      case BLUE:
        System.out.println("BLUE");
        break;
      case GREEN:
        System.out.println("GREEN");
        break;
    }
    return switch (color) {
      case RED:
        yield 1;
      case GREEN:
        yield 2;
      case BLUE:
        yield 3;
    };
  }
}
// xdoc section -- end
