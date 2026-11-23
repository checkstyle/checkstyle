/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RedundantThis">
      <property name="checkMethods" value="true"/>
      <property name="allowAdjacentToRequiredThis" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

class InputRedundantThisFlexibleConstructorBodiesCustom {

    static class Before {
        private int value;

        Before(boolean stop) {
            this.value = 1; // violation 'Redundant "this", field 'value' can be accessed directly.'
            super();
            if (stop) {
                return;
            }
        }

        void compute() {
        }
    }

    static class After {
        private int value;

        After(boolean stop) {
            super();
            value = 1;
            this.compute();
            // violation above """Redundant "this", method 'compute' can
            // be accessed directly."""
            if (stop) {
                return;
            }
        }

        void compute() {
        }
    }
}
