/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

class InputNPathComplexityFlexibleConstructorBodies {

    static class Before {
        // violation below 'NPath Complexity is 256 (max allowed is 200).'
        Before(boolean stop) {
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            if (stop) {
                return;
            }
            super();
        }
    }

    static class After {
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
