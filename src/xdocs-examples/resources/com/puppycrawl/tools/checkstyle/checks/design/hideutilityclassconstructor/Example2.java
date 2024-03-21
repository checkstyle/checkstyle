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

class Foo2 {

  private Foo2() {
  }

  static int n;
}

class Bar2 {

  protected Bar2() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@SpringBootApplication
class ApplicationClass2 { // OK, skipped by annotation

  static float f;
}
// xdoc section -- end
@interface SpringBootApplication {}
