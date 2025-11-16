/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="allowEmpty" value="false"/>
      <property name="skipInner" value="false"/>
      <property name="skipLocal" value="false"/>
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
public class Example4 {  // violation 'A blank line is required after the opening'
  int a = 0;

  static class Inner { // violation 'A blank line is required after the opening'
    int b = 0;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface MyAnnotation {} // violation 'A blank line is required after'

  public void myMethod() {
    class LocalClass { // violation 'A blank line is required after the opening'
      int c = 0;
    }
    interface LocalInterface { // violation 'A blank line is required after the'
      void foo();
    }
    enum LocalEnum { A, B } // violation 'A blank line is required after the'
    record LocalRecord(int d) {} // violation 'A blank line is required after the'
  }
}
// xdoc section -- end
