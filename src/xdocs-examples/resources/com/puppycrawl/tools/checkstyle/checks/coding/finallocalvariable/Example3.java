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
    return x+y;
  }
  public static void main (String []args) {
    // violation below, 'Variable 'i' should be declared final'
    for (String i : args) {
      System.out.println(i);
    }
    int result=foo(1,2); // violation, 'Variable 'result' should be declared final'
  }
}
// xdoc section -- end
