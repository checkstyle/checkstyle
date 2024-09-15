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

  @SuppressWarnings("deprecation")
  // violation above, "Annotation 'SuppressWarnings'
  // should be on the same line with its target."
  public Example2() {
  }

  @Override  // OK
  public void doSomething() {
  }

  @Nullable // violation, "Annotation 'Nullable'
  // should be on the same line with its target."
  String s;

}
// xdoc section -- end
