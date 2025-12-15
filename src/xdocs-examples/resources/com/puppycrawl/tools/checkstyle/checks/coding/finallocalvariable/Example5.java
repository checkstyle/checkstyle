/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="tokens" value="VARIABLE_DEF"/>
      <property name="validateUnnamedVariables" value="true"/>
    </module>
  </module>
</module>
*/
// With validateUnnamedVariables enabled, unnamed variables (e.g. '_')
// are also subject to final local variable validation.

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example5
{
  static int foo(int x, int y) {
    // ok, because PARAMETER_DEF is not configured in tokens
    return x+y;
  }
  public static void main (String []args) {
    // ok above, because PARAMETER_DEF is not configured in tokens
    // ok below, because validateEnhancedForLoopVariable is false by default
    for (String i : args) {
      System.out.println(i);
    }
    int result=foo(1,2); // violation, 'Variable 'result' should be declared final'
  }
}
// xdoc section -- end
