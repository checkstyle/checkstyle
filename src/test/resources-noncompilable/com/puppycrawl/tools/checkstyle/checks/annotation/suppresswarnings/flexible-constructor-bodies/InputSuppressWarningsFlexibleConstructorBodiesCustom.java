/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWarnings">
      <property name="format" value="checkstyle:.+"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

class InputSuppressWarningsFlexibleConstructorBodiesCustom {

    static class Before {
        // violation 2 lines below """The warning 'checkstyle:RequireThis'
        // cannot be suppressed at this location."""
        @SuppressWarnings("checkstyle:RequireThis")
        public Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        @SuppressWarnings("unused")
        public After(boolean stop) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }
}
