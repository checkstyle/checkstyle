/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodCount">
      <property name="maxProtected" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

// xdoc section -- start
class Example6 { // violation, 'Number of protected methods is 2 (max allowed is 1)'

  public void publicMethod1() {}
  public void publicMethod2() {}

  private void privateMethod1() {}
  private void privateMethod2() {}

  void packageMethod1() {}
  void packageMethod2() {}

  protected void protectedMethod1() {}
  protected void protectedMethod2() {}
}
// xdoc section -- end
