/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MethodName">
       <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
       <property name="applyToPublic" value="false"/>
       <property name="applyToProtected" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

// xdoc section -- start
class Example5 {
  public void Method1() {}
  protected void Method2() {}
  private void Method3() {} // violation 'Name 'Method3' must match pattern'
  void Method4() {} // violation 'Name 'Method4' must match pattern'
}
// xdoc section -- end
