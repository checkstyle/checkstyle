/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
      <property name="skipAnnotations" value="false"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

@SuppressWarnings("unused") // violation 'should not be line-wrapped'
class Example4 {

  @Deprecated // violation 'should not be line-wrapped'
  public Example4() {
  }

  @SafeVarargs // violation 'should not be line-wrapped'
  public final <T> void doSomething(T... elements) {
  }
}
// xdoc section -- end
