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
@Deprecated interface Foo2 {
  void doSomething();

}

class Example2 implements Foo2 {

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  public Example2() {
  }

  @SuppressWarnings("unchecked") public void fun2() {}

  public void fun1() {}

  // violation below, "should be on the same line with its target."
  @Nullable
  String s;

  @Override
  public void doSomething() {}
}

@SuppressWarnings("unchecked") class Test2 extends Example2 {

  @Deprecated public Test2() {
  }

  @Override
  public void fun1() {}

  @Before
  @Override public void fun2() {}

  @SuppressWarnings("deprecation")
  @Before public void fun3() {
  }

}
// xdoc section -- end
