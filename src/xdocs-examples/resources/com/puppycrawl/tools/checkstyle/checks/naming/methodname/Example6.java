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
  public void method1() {}
  protected void Method2() {} // violation 'Name 'Method2' must match pattern'
  private void Method3() {}
  public void Method4() {} // violation 'Name 'Method4' must match pattern'
  void Method5() {} // violation 'Name 'Method5' must match pattern'
}
// xdoc section -- end
