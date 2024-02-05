/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="tokens" value="BXOR,LAND,LOR"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

// xdoc section -- start
public class Example3
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // OK, 1(^) = 1 (max allowed 3)

    boolean d = (a & b) | (b ^ a) | (a ^ b);
    // OK above, 1(^) + 1(^) = 2, & and | are ignored here

    boolean e = a ^ (a || b) ^ (b || a) & (a | b);
    // violation above, 'Boolean expression complexity is 4 (max allowed is 3)'
    // 1(^) + 1(||) + 1(^) + 1(||) = 4, & and | are ignored here
  }
}
// xdoc section -- end
