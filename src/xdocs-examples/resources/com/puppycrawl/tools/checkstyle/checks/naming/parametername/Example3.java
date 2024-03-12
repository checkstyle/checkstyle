/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="ignoreOverridden" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

// xdoc section -- start
class Example3 {
  void method1(int v1) {}
  void method2(int V2) {} // violation
  @Override
  public boolean equals(Object V3) { // OK
    return true;
  }
}
// xdoc section -- end
