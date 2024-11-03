/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingCtor"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.missingctor;

// xdoc section -- start
public class Example1 {
  private int a;
  Example1(int a) {
    this.a = a;
  }
}
class ExampleDefaultCtor {
  private String s;
  ExampleDefaultCtor() {
    s = "foobar";
  }
}
class InvalidExample { // violation, 'Class should define a constructor'
  public void test() {}
}
abstract class AbstractExample {
  public abstract void test();
}
// xdoc section -- end
