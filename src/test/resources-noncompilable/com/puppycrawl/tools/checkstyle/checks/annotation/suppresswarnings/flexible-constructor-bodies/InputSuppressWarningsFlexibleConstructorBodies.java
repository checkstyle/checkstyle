/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarnings"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

class InputSuppressWarningsFlexibleConstructorBodies {

    static class Before {
        @SuppressWarnings("unused")
        public Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        // violation below 'The warning '' cannot be suppressed at this location.'
        @SuppressWarnings("")
        public After(boolean stop) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }
}
