/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalFinalVariableName">
      <property name="format" value="^[A-Z][A-Z0-9]*$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

// xdoc section -- start
class Example2 {
  void MyMethod() {
    try {
      final int VAR1 = 5;
      final int var1 = 10; // violation 'Name 'var1' must match pattern'
    } catch (Exception ex) {
      final int VAR2 = 15;
      final int var2 = 20; // violation 'Name 'var2' must match pattern'
    }
  }
}
// xdoc section -- end
