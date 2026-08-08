/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="treatUniformSimpleSequentialExpressionsAsOne" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

// xdoc section - start
public class Example4
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // ok, 1(^) = 1 (max allowed 3)

    boolean d = (a & b) | (b ^ a) | (a ^ b);
    // violation above 'Boolean expression complexity is 5 (max allowed is 3)'

    boolean e = a ^ (a || b) ^ (b || a) & (a | b);
    // violation above 'Boolean expression complexity is 6 (max allowed is 3)'
    // 1(^) + 1(||) + 1(^) + 1(||) = 4, & and | are ignored here
  }
}
// xdoc section - end
