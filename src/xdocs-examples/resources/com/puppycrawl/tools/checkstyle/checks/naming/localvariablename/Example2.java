/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalVariableName">
      <property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

// xdoc section -- start
class Example2 {
  void MyMethod() {
    for (int var = 1; var < 10; var++) {}
    for (int VAR = 1; VAR < 10; VAR++) {} // violation
    for (int i = 1; i < 10; i++) {}
    for (int var_1 = 0; var_1 < 10; var_1++) {}
  }
}
// xdoc section -- end

