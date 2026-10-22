/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="format" value="^[a-z][_a-zA-Z0-9]+$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

// xdoc section - start
class Example2 {
  void method0(int v) {}
  void method1(int v1) {}
  void method2(int V2) {}
  @Override
  public boolean equals(Object V3) {
    return true;
  }
}
// violation 8 lines above """Name 'v' must match pattern
//  '\Q^[a-z][_a-zA-Z0-9]+$\E'\."""
// violation 8 lines above """Name 'V2' must match pattern
//  '\Q^[a-z][_a-zA-Z0-9]+$\E'\."""
// violation 8 lines above """Name 'V3' must match pattern
//  '\Q^[a-z][_a-zA-Z0-9]+$\E'\."""
// xdoc section - end
