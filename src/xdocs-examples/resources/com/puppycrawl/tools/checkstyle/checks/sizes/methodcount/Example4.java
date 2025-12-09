/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxPrivate" value="1"/>
      <property name="maxPackage" value="1"/>
      <property name="maxProtected" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
class Example4 { // violations for private, package-private and protected method limits

  public void publicMethod() {}

  private void privateMethod1() {}
  private void privateMethod2() {}

  void packageMethod1() {}
  void packageMethod2() {}

  protected void protectedMethod1() {}
  protected void protectedMethod2() {}
}
// xdoc section -- end
