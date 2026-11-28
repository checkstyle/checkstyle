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

// xdoc section - start
public class Example3
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // ok, 1(^) = 1 (max allowed 3)

    boolean d = (a & b) | (b ^ a) | (a ^ b);
    // ok above, 1(^) + 1(^) = 2, & and | are ignored here

    boolean e = a ^ (a || b) ^ (b || a) & (a | b);
    // violation above 'Boolean expression complexity is 5 (max allowed is 3)'
    // 1(^) + 1(||) + 1(^) + 1(||) = 4, & and | are ignored here
  }

  public static boolean isType(int type)
  {
    return type == 1
            || type == 2
            || type == 3
            || type == 4
            || type == 5;
  }

  public static boolean isValid(boolean a, boolean b, boolean c, boolean d)
  {
    return a && b && c && d;
  }

  public static boolean isExpressionAsOne(boolean a, boolean b, boolean c,
           boolean d, Object leftType, Object rightType)
  {
    return a && b && c &&
            d && leftType != null && rightType != null;
  }

}
// xdoc section - end
