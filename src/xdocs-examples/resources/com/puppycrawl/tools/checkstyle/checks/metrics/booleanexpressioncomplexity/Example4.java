/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="ignoreUniformChains" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;
// xdoc section -- start
public class Example4
{
  int type;
  boolean isDefToken() {
    return type == 1 || type == 2 || type == 3 || type == 4 || type == 5;
  }
  boolean mixed(boolean a, boolean b, boolean c, boolean d, boolean e, boolean f) {
    return a && b || c && d || e && f;
    // violation above, 'Boolean expression complexity is 4 (max allowed is 3)'
  }
}
// xdoc section -- end
