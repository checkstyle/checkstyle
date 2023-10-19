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
class Example3 {
  void myMethod () {
    for(int i = 1; i < 10; i++) {}
    for(int K = 1; K < 10; K++) {} // violation
    List list = new ArrayList();
    for (Object o : list) {}
    for (Object O : list) {} // violation
  }
}
// xdoc section -- end


