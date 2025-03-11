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
  private void Method3() {} // violation ''Method3' must match the pattern'
  public void Method4() {} // violation ''Method4' must match the pattern'
}
// xdoc section -- end
