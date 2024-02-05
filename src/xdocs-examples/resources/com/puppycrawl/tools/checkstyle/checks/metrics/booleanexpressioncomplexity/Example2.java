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

// xdoc section -- start
public class Example2
{
  public static void main(String ... args)
  {
    boolean a = true;
    boolean b = false;

    boolean c = (a & b) | (b ^ a) | (a ^ b);   // OK, 1(&)+1(|)+1(^)+1(|)+1(^)=5

    boolean d = (a | b) ^ (a | b) ^ (a || b) & b; // violation,
    // 1(|)+1(^)+1(|)+1(^)+1(||)+1(&)=6
  }
}
// xdoc section -- end
