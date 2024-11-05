/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ModifiedControlVariable">
      <property name="skipEnhancedForLoopVariable" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

// xdoc section -- start
class Example2 {
  void InvalidExample() {
    for(int i=0;i < 8;i++) {
      i++; // violation, control variable modified
    }
    String args1[]={"Coding", "block"};
    for (String arg: args1) {
      arg = arg.trim();
    }
  }
}
// xdoc section -- end
