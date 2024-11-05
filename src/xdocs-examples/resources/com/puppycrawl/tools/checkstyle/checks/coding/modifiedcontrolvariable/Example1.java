/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifiedControlVariable"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

// xdoc section -- start
class Example1 {
  void InvalidExample() {
    for(int i=0;i < 8;i++) {
      i++; // violation, control variable modified
    }
    String args1[]={"Coding", "block"};
    for (String arg: args1) {
      arg = arg.trim(); // violation, control variable modified
    }
  }
}
// xdoc section -- end
