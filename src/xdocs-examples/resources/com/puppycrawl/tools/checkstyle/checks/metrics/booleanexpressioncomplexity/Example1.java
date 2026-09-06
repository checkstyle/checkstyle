/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

// xdoc section - start
public class Example1
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // ok, 1(&) + 1(|) + 1(^) = 3 (max allowed 3)

    boolean d = (a & b) | (b ^ a) | (a ^ b);
    // violation above 'Boolean expression complexity is 5 (max allowed is 3)'

    boolean e = a ^ (a || b) ^ (b || a) & (a | b);
    // violation above 'Boolean expression complexity is 6 (max allowed is 3)'
    // 1(^) + 1(||) + 1(^) + 1(||) + 1(&) + 1(|) = 6
  }

  public static boolean isType(int type)
  {// violation below 'Boolean expression complexity is 4 (max allowed is 3)'
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

}
// xdoc section - end
