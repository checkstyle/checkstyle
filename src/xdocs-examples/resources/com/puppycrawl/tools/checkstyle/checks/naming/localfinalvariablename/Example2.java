/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalFinalVariableName">
       <property name="format" value="^[a-z][a-zA-Z0-9]{4,}$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

import java.util.Scanner;

// xdoc section - start
class Example2 {
  void MyMethod() {
    try (Scanner scanner = new Scanner(System.in)) {

      final int VAR1 = 5; // violation 'Name 'VAR1' must match pattern'
      final int var1 = 10; // violation 'Name 'var1' must match pattern'
    }
    catch (final Exception ex) { // violation 'Name 'ex' must match pattern'

      final int VAR2 = 15; // violation 'Name 'VAR2' must match pattern'
      final int var2 = 20; // violation 'Name 'var2' must match pattern'
    }
  }
}
// xdoc section - end
