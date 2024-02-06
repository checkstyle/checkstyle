/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

// xdoc section -- start
public class Example1
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // OK, 1(&) + 1(|) + 1(^) = 3 (max allowed 3)

    boolean d = (a & b) ^ (a || b) | a;  // violation
    // violation above, 1(&) + 1(^) + 1(||) + 1(|) = 4
  }
}
// xdoc section -- end
