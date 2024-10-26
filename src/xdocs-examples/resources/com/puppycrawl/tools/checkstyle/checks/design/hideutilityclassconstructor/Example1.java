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

class Foo {

  private Foo() {
  }

  static int n;
}

class Bar {

  protected Bar() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@Deprecated // violation
class UtilityClass {

  static float f;
}

@SpringBootApplication // violation
class Application1 {

  public static void main(String[] args) {
  }
}
// xdoc section -- end
@interface SpringBootApplication {}
