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
  void method1(int v1) {}
  private void method2(int V) {} // violation "Parameter name 'V' must match pattern"
  private void method3(int a) {}
  public void method4(int b) {} // violation "Parameter name 'b' must match pattern"
}
// xdoc section -- end
