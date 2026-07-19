/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="applyToProtected" value="false"/>
    </module>
  </module>
</module>
*/



package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example5 {
  public void method1() {}
  protected void Method2() {} // ok because 'applyToProtected' is false
  private void Method3() {} // violation 'Name 'Method3' must match pattern'
  public void Example3() {} // violation 'Name 'Example3' must match pattern'
  void Method5() {} // violation 'Name 'Method5' must match pattern'
}
// xdoc section -- end
