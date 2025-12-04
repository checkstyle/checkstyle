/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoLineWrap">
      <property name="tokens" value="CLASS_DEF, METHOD_DEF, CTOR_DEF"/>
    </module>
  </module>
</module>
*/

// xdoc section -- start
package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;

@SuppressWarnings("unused") // ok, skipAnnotations property is true (default)
class Example5 {

  @Deprecated // ok, skipAnnotations property is true (default)
  public Example5() {
  }

  @SafeVarargs // ok, skipAnnotations property is true (default)
  public final <T> void doSomething(T... elements) {
  }
}
// xdoc section -- end
