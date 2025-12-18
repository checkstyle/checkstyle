/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalLocalVariable">
      <property name="validateUnnamedVariables" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

// xdoc section -- start
class Example5
{
  static int foo(int x, int y) {
    int _ = 1;
    // violation above, 'Variable '_' should be declared final'
    return x+y;
  }
  public static void main (final String []args) {
    // ok below, because validateEnhancedForLoopVariable is false by default
    for (String i : args) {
      System.out.println(i);
    }
    int result = foo(1, 2);
    // violation above, 'Variable 'result' should be declared final'
  }
}

// xdoc section -- end

