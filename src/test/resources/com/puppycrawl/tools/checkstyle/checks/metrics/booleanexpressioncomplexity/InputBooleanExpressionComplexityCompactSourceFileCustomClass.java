/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="max" value="1"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityCompactSourceFileCustomClass {

    void logicalOperators(boolean a, boolean b, boolean c) {
        // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
        if (a && b || c) {
        }
    }

    void bitwiseOperators(boolean a, boolean b, boolean c) {
        // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
        if (a & b | c) {
        }
    }
}
