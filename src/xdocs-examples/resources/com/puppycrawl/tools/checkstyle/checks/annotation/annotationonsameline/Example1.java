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

  @SuppressWarnings("deprecation")
  // violation above, "Annotation 'SuppressWarnings'
  // should be on the same line with its target."
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

  @Override // violation, "Annotation 'Override'
  // should be on the same line with its target."
  public void fun1() {
  }

  @Before // violation, "Annotation 'Before'
  // should be on the same line with its target."
  @Override public void fun2() {  // OK
  }

  @SuppressWarnings("deprecation")
  // violation above, "Annotation 'SuppressWarnings'
  // should be on the same line with its target."
  @Before public void fun3() {
  }

}
// xdoc section -- end
