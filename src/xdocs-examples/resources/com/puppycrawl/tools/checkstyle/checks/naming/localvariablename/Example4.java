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
    int g = 0; // violation
    for (int v = 1; v < 10; v++) {
      int a = 1; // violation
    }
    for (int V = 1; V < 10; V++) {
      int I = 1; // violation
    }
    List list = new ArrayList();
    for (Object o : list) {
      String a = ""; // violation
    }
    for (Object O : list) {
      String A = ""; // violation
    }
  }
}
// xdoc section -- end


