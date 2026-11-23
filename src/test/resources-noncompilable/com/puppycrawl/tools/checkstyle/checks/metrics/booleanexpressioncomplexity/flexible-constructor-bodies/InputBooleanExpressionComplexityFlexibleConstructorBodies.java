/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="BooleanExpressionComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.booleanexpressioncomplexity;

class InputBooleanExpressionComplexityFlexibleConstructorBodies {

    static class Before {
        Before(boolean first, boolean second, boolean third, boolean fourth, boolean fifth) {
            if (first && second && third && fourth && fifth) {
                // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
                System.out.println("all true");
            }
            super();
        }
    }

    static class After {
        After(boolean first, boolean second, boolean third, boolean fourth, boolean fifth) {
            super();
            if (first && second && third && fourth && fifth) {
                // violation above 'Boolean expression complexity is 4 (max allowed is 3).'
                System.out.println("all true");
            }
        }
    }
}
