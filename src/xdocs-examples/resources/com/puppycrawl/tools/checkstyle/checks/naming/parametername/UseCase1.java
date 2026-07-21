/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterName">
      <property name="format" value="^[a-z][a-zA-Z0-9]+$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

// xdoc section -- start
class UseCase1 {
  void method0(int v) {}             // violation 'Name 'v' must match pattern'
  void method1(int v1) {}
  void method2(int V2) {}            // violation 'Name 'V2' must match pattern'
  @Override
  public boolean equals(Object V3) { // violation 'Name 'V3' must match pattern'
    return true;
  }
}
// xdoc section -- end
