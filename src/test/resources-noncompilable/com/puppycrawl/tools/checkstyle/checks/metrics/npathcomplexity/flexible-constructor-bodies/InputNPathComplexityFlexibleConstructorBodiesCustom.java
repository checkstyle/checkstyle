/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NPathComplexity">
      <property name="max" value="10"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

class InputNPathComplexityFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'NPath Complexity is 16 (max allowed is 10).'
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
