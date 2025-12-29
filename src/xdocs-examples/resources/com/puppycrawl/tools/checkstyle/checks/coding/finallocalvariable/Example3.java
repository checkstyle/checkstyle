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
class Example3
{
  static int foo(int x, int y) {
    // ok, because PARAMETER_DEF is not configured in tokens
    int _  = 1;
    //ok above , validateUnnamedVariable is false by default
    return x+y;
  }
  public static void main (String []args) {
    // ok above, because PARAMETER_DEF is not configured in tokens
    // violation below, 'Variable 'i' should be declared final'
    for (String i : args) {
      System.out.println(i);
    }
    int result=foo(1,2); // violation, 'Variable 'result' should be declared final'
  }
}
// xdoc section -- end
