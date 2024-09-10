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
class Example2
{
  static int foo(int x, int y) {
    // 2 violations above:
    //  'Variable 'x' should be declared final'
    //  'Variable 'y' should be declared final'
    return x+y;
  }
  public static void main (String []args) {
    // violation above, 'Variable 'args' should be declared final'
    // ok below, because validateEnhancedForLoopVariable is false by default
    for (String i : args) {
      System.out.println(i);
    }
    int result=foo(1,2); // violation, 'Variable 'result' should be declared final'
  }
}
// xdoc section -- end
