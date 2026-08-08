/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="max" value="5"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

// xdoc section - start
public class Example2
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a); // ok, 1(&) + 1(|) + 1(^) = 3 (max allowed 5)

    boolean d = (a & b) | (b ^ a) | (a ^ b);
    // ok above, 1(&) + 1(|) + 1(^) + 1(|) + 1(^) = 5

    boolean e = a ^ (a || b) ^ (b || a) & (a | b);
    // violation above 'Boolean expression complexity is 6 (max allowed is 5)'
    // 1(^) + 1(||) + 1(^) + 1(||) + 1(&) + 1(|) = 6
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

}
// xdoc section - end
