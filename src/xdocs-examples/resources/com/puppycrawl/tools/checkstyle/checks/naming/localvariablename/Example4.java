/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalVariableName">
      <property name="format" value="^[a-z][_a-zA-Z0-9]+$"/>
      <property name="allowOneCharVarInForLoop" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

import java.util.ArrayList;
import java.util.List;

// xdoc section -- start
class Example4 {
  void MyMethod() {
    int good = 1;
    int g = 0; // violation, 'Name 'g' must match pattern*'
    for (int var = 1; var < 10; var++) {}
    for (int VAR = 1; VAR < 10; VAR++) {}
    // violation above, 'Name 'VAR' must match pattern*'
    for (int i = 1; i < 10; i++) {}
    for (int var_1 = 0; var_1 < 10; var_1++) {}

    for (int v = 1; v < 10; v++) {
      int a = 1; // violation, 'Name 'a' must match pattern*'
    }
    for (int V = 1; V < 10; V++) {

      int I = 1; // violation, 'Name 'I' must match pattern*'
    }
    List list = new ArrayList();
    for (Object o : list) {
      String a = ""; // violation, 'Name 'a' must match pattern*'
    }
    for (Object O : list) {

      String A = ""; // violation, 'Name 'A' must match pattern*'
    }
  }
}
// xdoc section -- end
