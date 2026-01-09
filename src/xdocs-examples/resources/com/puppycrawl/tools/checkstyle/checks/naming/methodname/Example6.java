/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
       <property name="applyToPrivate" value="false"/>
    </module>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example6 {
  public void Method1() {} // violation 'Name 'Method1' must match pattern'
  protected void Method2() {} // violation 'Name 'Method2' must match pattern'
  private void Method3() {}
  public void method4() {}
  void Method5() {} // violation 'Name 'Method5' must match pattern'
}
// xdoc section -- end
