/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="applyToPackage" value="false"/>
    </module>
  </module>
</module>
*/



package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example7 {
  public void method1() {}
  protected void Method2() {} // violation 'Name 'Method2' must match pattern'
  private void Method3() {} // violation 'Name 'Method3' must match pattern'
  public void Example3() {} // violation 'Name 'Example3' must match pattern'
  void Method5() {} // ok because 'applyToPackage' is false
}
// xdoc section -- end
