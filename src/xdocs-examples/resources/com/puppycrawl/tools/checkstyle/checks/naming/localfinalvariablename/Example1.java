/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalFinalVariableName">
       <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

// xdoc section -- start
class Example1{
  void MyMethod() {
    try {
      final int VAR1 = 5; // violation
      final int var1 = 10;
    } catch (Exception ex) {
      final int VAR2 = 15; // violation
      final int var2 = 20;
    }
  }
}
// xdoc section -- end
