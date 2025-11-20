/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="skipLocal" value="false"/>
      <property name="allowEmpty" value="false"/>
      <property name="tokens" value="CLASS_DEF, ENUM_DEF"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// xdoc section -- start
public class Example5 { // violation 'A blank line is required after the opening'
  int a = 0;

  static class Inner {
    int b = 0;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface MyAnnotation {}

  public void myMethod() {
    class LocalClass { // violation 'A blank line is required after the opening'
      int c = 0;
    }
    interface LocalInterface {
      void foo();
    }
    enum LocalEnum { A, B } // violation 'A blank line is required after the opening'
    record LocalRecord(int d) {}
  }
}
// xdoc section -- end
