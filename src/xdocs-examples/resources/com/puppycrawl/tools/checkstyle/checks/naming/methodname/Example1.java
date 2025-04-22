/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example1 {
  public void method1() {}
  protected void method2() {}
  private void Method3() {} // violation 'Name 'Method3' must match pattern'
  public void Method4() {} // violation 'Name 'Method4' must match pattern'
}
// xdoc section -- end
