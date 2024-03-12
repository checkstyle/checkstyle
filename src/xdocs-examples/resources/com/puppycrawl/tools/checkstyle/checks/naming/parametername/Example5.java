/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="format" value="^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"/>
      <property name="accessModifiers" value="protected, package, private"/>
      <message key="name.invalidPattern"
        value="Parameter name ''{0}'' must match pattern ''{1}''"/>
    </module>
    <module name="ParameterName">
      <property name="format" value="^[a-z][a-z0-9][a-zA-Z0-9]*$"/>
      <property name="accessModifiers" value="public"/>
      <message key="name.invalidPattern"
        value="Parameter name ''{0}'' must match pattern ''{1}''"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

// xdoc section -- start
class Example5 {
  void fn1(int v1) {}
  protected void fn2(int V2) {} // violation "Parameter name 'V2' must match pattern"
  private void fn3(int a) {}
  public void fn4(int b) {} // violation "Parameter name 'b' must match pattern"
}
// xdoc section -- end
