/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantModifier"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

class InputRedundantModifierFlexibleConstructorBodies {

    static class Before {
        public Before(boolean stop) { // violation 'Redundant 'public' modifier.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    enum After {
        ONE(true), TWO(false);

        private After(boolean stop) { // violation 'Redundant 'private' modifier.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    public static class Clean {
        Clean(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}
