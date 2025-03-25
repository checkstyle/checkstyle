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
  private void Method3() {} // violation
  public void Method4() {} // violation
}
// xdoc section -- end
