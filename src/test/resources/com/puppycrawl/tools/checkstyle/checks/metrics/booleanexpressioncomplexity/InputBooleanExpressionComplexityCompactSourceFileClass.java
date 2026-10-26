/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

public class InputBooleanExpressionComplexityCompactSourceFileClass {

    void logicalOperators(boolean a, boolean b, boolean c, boolean d, boolean e) {
        // violation below 'Boolean expression complexity is 4 (max allowed is 3).'
        if (a && b || c && d || e) {
        }
    }

    void bitwiseOperators(boolean a, boolean b, boolean c, boolean d, boolean e) {
        // violation below 'Boolean expression complexity is 4 (max allowed is 3).'
        if (a & b | c ^ d & e) {
        }
    }
}
