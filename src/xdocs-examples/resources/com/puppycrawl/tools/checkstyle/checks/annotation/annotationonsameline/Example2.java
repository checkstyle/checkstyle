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

import javax.annotation.Nullable;

// xdoc section -- start
@Deprecated interface Foo {

  void doSomething();

}

class Example2 implements Foo {

  // violation below, "should be on the same line with its target."
  @SuppressWarnings("deprecation")
  public Example2() {
  }

  @Override
  public void doSomething() {
  }

  // violation below, "should be on the same line with its target."
  @Nullable
  String s;

}
// xdoc section -- end
