/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
// violation below 'should not have a public or default constructor'
@java.lang.Deprecated
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

@Deprecated // violation 'should not have a public or default constructor'
class UtilityClass {

  static float f;
}
// violation below 'should not have a public or default constructor'
@SpringBootApplication
class Application1 {

  public static void main(String[] args) {
  }
}
// xdoc section -- end
@interface SpringBootApplication {}
