/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable"/>
  </module>
</module>
*/






package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example1
{
  static int foo(int x, int y) {
    // ok above , because PARAMETER_DEF is not configured in tokens
    int _  = 1;

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
