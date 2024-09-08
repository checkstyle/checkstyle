/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="tokens" value="VARIABLE_DEF"/>
      <property name="validateEnhancedForLoopVariable" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example3 {
  void foo(){
    final int[] myNumbers = {1,2,3};
    // violation below, 'Variable 'number' should be declared final'
    for (int number : myNumbers) {
      System.out.println(number);
    }
  }
}
// xdoc section -- end
