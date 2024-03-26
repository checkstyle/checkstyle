/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor">
      <property name="ignoreAnnotatedBy"
        value="SpringBootApplication, java.lang.Deprecated" />
    </module>
   </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
@java.lang.Deprecated
class Example2 { // OK, skipped by annotation

  public Example2() {
  }

  public static void fun() {
  }
}

class Foo1 { // OK

  private Foo1() {
  }

  static int n;
}

class Bar1 { // OK

  protected Bar1() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@SpringBootApplication
class UtilityClass1 { // OK, skipped by annotation

  static float f;
}
// xdoc section -- end
@interface SpringBootApplication {}
