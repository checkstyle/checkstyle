/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor">
      <property name="ignoreAnnotatedBy"
        value="SpringBootApplication, Deprecated" />
    </module>
   </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
@java.lang.Deprecated  // violation
class Example3 {

  public Example3() {
  }

  public static void fun() {
  }
}

class Foo3 {

  private Foo3() {
  }

  static int n;
}

class Bar3 {

  protected Bar3() {
    // prevents calls from subclass
    throw new UnsupportedOperationException();
  }
}

@SpringBootApplication
class ApplicationClass3 { // OK, skipped by annotation

  static float f;
}
// xdoc section -- end
