/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedPrivateField"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

class InputUnusedPrivateFieldFlexibleConstructorBodies {

    static class Before {
        private int unused; // violation 'Unused private field 'unused'.'

        private int used;

        Before(boolean stop) {
            int local = 1;
            super();
            used = local;
            if (stop) {
                return;
            }
        }
    }

    static class After {
        private int used;

        After(boolean stop) {
            super();
            used = 1;
            if (stop) {
                return;
            }
        }
    }
}
