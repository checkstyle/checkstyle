/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity">
      <property name="max" value="2"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

class InputBooleanExpressionComplexityFlexibleConstructorBodiesCustom {

    static class Before {
        Before(boolean first, boolean second, boolean third, boolean fourth) {
            if (first && second && third && fourth) {
                // violation above 'Boolean expression complexity is 3 (max allowed is 2).'
                System.out.println("all true");
            }
            super();
        }
    }

    static class After {
        After(boolean first, boolean second, boolean third) {
            super();
            if (first && second && third) {
                System.out.println("all true");
            }
        }
    }
}
