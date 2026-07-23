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

import java.util.ArrayList;
import java.util.List;

// xdoc section -- start
class Example2 {
  void MyMethod() {
    int good = 1;
    int g = 0;
    for (int var = 1; var < 10; var++) {}
    for (int VAR = 1; VAR < 10; VAR++) {}
    // violation above 'Name 'VAR' must match pattern*'
    for (int i = 1; i < 10; i++) {}
    for (int var_1 = 0; var_1 < 10; var_1++) {}

    for (int v = 1; v < 10; v++) {
      int a = 1;
    }
    for (int V = 1; V < 10; V++) {
      // violation above 'Name 'V' must match pattern*'
      int I = 1; // violation 'Name 'I' must match pattern*'
    }
    List list = new ArrayList();
    for (Object o : list) {
      String a = "";
    }
    for (Object O : list) {
      // violation above 'Name 'O' must match pattern*'
      String A = ""; // violation 'Name 'A' must match pattern*'
    }
  }
}
// xdoc section -- end
