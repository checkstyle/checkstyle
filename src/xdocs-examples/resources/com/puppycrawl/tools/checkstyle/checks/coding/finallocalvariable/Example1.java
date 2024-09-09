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
    return x+y;
  }
  public static void main (String []args) {
    for (String i : args) {
      System.out.println(i);
    }
    int result=foo(1,2); // violation, 'Variable 'result' should be declared final'
  }
}
// xdoc section -- end
