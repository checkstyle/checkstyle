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
@java.lang.Deprecated // ok, skipped by annotation
class Example2 {

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

@Deprecated // violation, should not have a public or default constructor
class UtilityClass2 {

  static float f;
}

@SpringBootApplication // ok, skipped by annotation
class Application2 {

  public static void main(String[] args) {
  }
}
// xdoc section -- end
