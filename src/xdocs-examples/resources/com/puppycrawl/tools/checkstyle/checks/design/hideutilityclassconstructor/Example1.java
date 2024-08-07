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

class Foo1 {

  private Foo1() {
  }

  static int n;
}

class Bar1 {

  protected Bar1() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@SpringBootApplication // violation
class ApplicationClass1 {

  static float f;
}
// xdoc section -- end
