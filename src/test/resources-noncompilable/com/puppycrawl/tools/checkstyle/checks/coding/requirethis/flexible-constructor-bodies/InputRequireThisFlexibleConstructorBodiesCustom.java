/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="checkFields" value="true"/>
      <property name="checkMethods" value="false"/>
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

class InputRequireThisFlexibleConstructorBodiesCustom {

    static class Before {
        private int value;

        Before(boolean stop) {
            value = 1; // violation 'Reference to instance variable 'value' needs "this.".'
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
            this.value = 1;
            compute();
            if (stop) {
                return;
            }
        }

        void compute() {
        }
    }
}
