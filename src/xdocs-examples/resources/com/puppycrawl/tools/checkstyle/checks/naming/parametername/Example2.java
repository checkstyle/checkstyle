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

// xdoc section -- start
class Example2 {
  void method1(int v1) {}
  void method2(int v_2) {}
  @Override
  public boolean equals(Object V3) { // violation 'Name 'V3' must match pattern'
    return true;
  }
}
// xdoc section -- end
