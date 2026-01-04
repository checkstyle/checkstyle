/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// xdoc section -- start
public class Example1 { // violation 'A blank line is required after the opening'
  int a = 0;

  static class Inner {
    int b = 0;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface MyAnnotation {}

  public void myMethod() {
    class LocalClass {
      int c = 0;
    }
    interface LocalInterface {
      void foo();
    }
    enum LocalEnum { A, B }
    record LocalRecord(int d) {}
  }
}
// xdoc section -- end
