/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

class InputRedundantThisFlexibleConstructorBodies {

    static class Before {
        private int value;

        Before(boolean stop) {
            this.value = 1; // violation 'Redundant "this", field 'value' can be accessed directly.'
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        private int value;

        After(boolean stop) {
            super();
            this.value = 1; // violation 'Redundant "this", field 'value' can be accessed directly.'
            if (stop) {
                return;
            }
        }
    }
}
