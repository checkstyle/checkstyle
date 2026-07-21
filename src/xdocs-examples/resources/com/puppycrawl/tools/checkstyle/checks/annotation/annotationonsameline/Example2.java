/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationOnSameLine">
      <property name="tokens"
                value="INTERFACE_DEF, VARIABLE_DEF, CTOR_DEF"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import org.junit.Before;

import javax.annotation.Nullable;

// xdoc section -- start
class Example2 {

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  public Example2() {
  }

  @SuppressWarnings("unchecked") public void fun2() {}

  public void fun1() {}

  // violation below, "should be on the same line with its target."
  @Nullable
  String s;
}

@SuppressWarnings("unchecked") class Test2 {

  @Deprecated public Test2() {}
  // ok below, @Before is not in tokens to check
  @Before
  public void fun1() {}
  // ok below, @Before is not in tokens to check
  @Before
  @SuppressWarnings("unchecked") public void fun2() {}
  // ok below, @SuppressWarnings is not in tokens to check
  @SuppressWarnings("deprecation")
  @Before public void fun3() {
  }

}
// xdoc section -- end
