/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LocalVariableName">
      <property name="format" value="^[a-z][_a-zA-Z0-9]{2,}$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

// xdoc section -- start
class Example5 {
  void MyMethod() {
    int goodName = 0;
    int i = 1; // violation, 'Name 'i' must match pattern*'
    for (int var = 1; var < 10; var++) {
      int j = 1; // violation, 'Name 'j' must match pattern*'
    }
  }
}
// xdoc section -- end



