/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationOnSameLine"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import org.junit.Before;

// xdoc section -- start
class Example1 {

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  public Example1() {
  }

  @SuppressWarnings("unchecked") public void fun2() {  // OK
  }

  public void fun1() {
  }

}

@SuppressWarnings("unchecked") class Test1 extends Example1 {  // OK

  @Deprecated public Test1() {  // OK
  }

  @Override // violation, "should be on the same line with its target."
  public void fun1() {
  }

  @Before // violation, "should be on the same line with its target."
  @Override public void fun2() {  // OK
  }

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  @Before public void fun3() {
  }

}
// xdoc section -- end
