/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalFinalVariableName">
      <property name="format" value="^[A-Z][A-Z0-9]*$"/>
      <property name="tokens" value="PARAMETER_DEF,RESOURCE"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

import java.util.Scanner;

// xdoc section -- start
class Example3 {
  void MyMethod() {
    try(Scanner scanner = new Scanner(System.in)) { // violation

      final int VAR1 = 5;
      final int var1 = 10;
    } catch (final Exception ex) { // violation

      final int VAR2 = 15;
      final int var2 = 20;
    }
  }
}
// xdoc section -- end
