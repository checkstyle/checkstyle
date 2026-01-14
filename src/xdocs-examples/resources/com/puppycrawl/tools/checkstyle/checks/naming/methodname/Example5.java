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
  public void method1() {}
  protected void Method2() {} // ok because, 'Property 'applyToProtected' is false'
  private void Method3() {} // violation 'Name 'Method3' must match pattern'
  public void Method4() {} // ok because, 'Property 'applyToPublic' is false'
  void Method5() {} // violation 'Name 'Method5' must match pattern'
}
// xdoc section -- end
