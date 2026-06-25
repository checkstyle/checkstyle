/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationOnSameLine"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import org.junit.Before;

import javax.annotation.Nullable;

// xdoc section -- start
class Example1 {

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  public Example1() {
  }

  @SuppressWarnings("unchecked") public void fun2() {}

  public void fun1() {}

  // violation below, "should be on the same line with its target."
  @Nullable
  String s;
}

@SuppressWarnings("unchecked") class Test1 {

  @Deprecated public Test1() {}
  // violation below, "should be on the same line with its target."
  @Before
  public void fun1() {}
  // violation below, "should be on the same line with its target."
  @Before
  @SuppressWarnings("unchecked") public void fun2() {}
  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  @Before public void fun3() {
  }

}
// xdoc section -- end
