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
  public void Method1() {} // violation 'Method1' must match the pattern
  protected void Method2() {} // violation 'Method2' must match the pattern
  private void Method3() {} // violation 'Method3' must match the pattern
  void Method4() {} // violation 'Method4' must match the pattern
}
// xdoc section -- end
