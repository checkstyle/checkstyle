/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CyclomaticComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

class InputCyclomaticComplexityFlexibleConstructorBodies {

    static class Before {
        // violation below 'Cyclomatic Complexity is 12 (max allowed is 10).'
        Before(boolean first, boolean second, boolean third, boolean fourth, boolean fifth,
                boolean sixth, boolean seventh, boolean eighth, boolean ninth, boolean tenth,
                boolean eleventh) {
            if (first && second && third && fourth && fifth && sixth && seventh && eighth
                    && ninth && tenth && eleventh) {
                System.out.println("all true");
            }
            super();
        }
    }

    static class After {
        // violation below 'Cyclomatic Complexity is 12 (max allowed is 10).'
        After(boolean first, boolean second, boolean third, boolean fourth, boolean fifth,
                boolean sixth, boolean seventh, boolean eighth, boolean ninth, boolean tenth,
                boolean eleventh) {
            super();
            if (first && second && third && fourth && fifth && sixth && seventh && eighth
                    && ninth && tenth && eleventh) {
                System.out.println("all true");
            }
        }
    }
}
