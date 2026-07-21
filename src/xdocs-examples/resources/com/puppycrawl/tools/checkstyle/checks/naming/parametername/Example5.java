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
  void method0(int v) {}
  void method1(int v1) {}
  void method2(int V2) {}
  @Override              // violation above "Parameter name 'V2' must match pattern"
  public boolean equals(Object V3) {
    return true;         // violation above "Parameter name 'V3' must match pattern"
  }
}
// xdoc section -- end
