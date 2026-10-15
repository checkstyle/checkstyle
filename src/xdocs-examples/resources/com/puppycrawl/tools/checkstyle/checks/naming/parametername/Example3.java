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

// xdoc section - start
class Example3 {
  void method0(int v) {}
  void method1(int v1) {}
  void method2(int V2) {}
  @Override
  public boolean equals(Object V3) {
    return true;
  }
}
// violation 6 lines above """Name 'V2' must match pattern
//  '\Q^[a-z][a-zA-Z0-9]*$\E'\."""
// xdoc section - end
