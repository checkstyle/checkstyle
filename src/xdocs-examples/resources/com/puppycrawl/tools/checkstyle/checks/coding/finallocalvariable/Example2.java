/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="tokens" value="VARIABLE_DEF,PARAMETER_DEF"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example2 {
  // violation below 'Variable 'unchangedVariable' should be declared final'
  void bar(int unchangedVariable,int changedVariable) {
    int someVariable = unchangedVariable;
    // violation above 'Variable 'someVariable' should be declared final'
    changedVariable += someVariable;
    final int[] arr =  {1,2,3};
    for(int value : arr){
      System.out.println(value);
    }
  }
}
// xdoc section -- end
