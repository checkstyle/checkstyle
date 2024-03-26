/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
@java.lang.Deprecated // violation
class Example1 {

  public Example1() {
  }

  public static void fun() {
  }
}

class Foo { // OK

  private Foo() {
  }

  static int n;
}

class Bar { // OK

  protected Bar() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@SpringBootApplication // violation
class UtilityClass {

  static float f;
}
// xdoc section -- end
