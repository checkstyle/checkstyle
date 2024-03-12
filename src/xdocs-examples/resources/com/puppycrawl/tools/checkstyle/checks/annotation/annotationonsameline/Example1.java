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

  @SuppressWarnings("deprecation") // violation
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

  @Override // violation
  public void fun1() {
  }

  @Before // violation
  @Override public void fun2() {  // OK
  }

  @SuppressWarnings("deprecation") // violation
  @Before public void fun3() {
  }

}
// xdoc section -- end
