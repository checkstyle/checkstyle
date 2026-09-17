/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="format" value="^[a-z][a-zA-Z0-9]{7,}$"/>
    </module>
  </module>
</module>
*/



package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section - start
class Example2 {
  public void method1() {}    // violation 'Name 'method1' must match pattern'
  protected void Method2() {} // violation 'Name 'Method2' must match pattern'
  private void Method3() {}   // violation 'Name 'Method3' must match pattern'
  public void Example3() {}   // violation 'Name 'Example3' must match pattern'
  void Method5() {} // violation 'Name 'Method5' must match pattern'
}
// xdoc section - end
