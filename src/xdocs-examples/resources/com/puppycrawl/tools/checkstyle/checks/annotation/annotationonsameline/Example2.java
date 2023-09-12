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
@Deprecated interface Foo {  // OK

  void doSomething();

}

class Example2 implements Foo {

  @SuppressWarnings("deprecation") // violation
  public Example2() {
  }

  @Override  // OK
  public void doSomething() {
  }

  @Nullable // violation
  String s;

}
// xdoc section -- end
