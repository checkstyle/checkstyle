/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount">
      <property name="max" value="1"/>
      <property name="maxForVoid" value="0"/>
      <property name="format" value="^Ignored(?:With|Without)Prologue$"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

import java.util.function.IntUnaryOperator;

class InputReturnCountFlexibleConstructorBodiesCustom {
    static class Before {
        Before() {
        }

        Before(boolean stop) { // violation 'Return count is 1'
            IntUnaryOperator operation = value -> { // violation 'Return count is 2'
                if (value < 0) {
                    return -1;
                }
                return 1;
            };
            this();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After() {
        }

        After(boolean stop) { // violation 'Return count is 1'
            this();
            IntUnaryOperator operation = value -> { // violation 'Return count is 2'
                if (value < 0) {
                    return -1;
                }
                return 1;
            };
            if (stop) {
                return;
            }
        }
    }

    static class IgnoredWithPrologue {
        IgnoredWithPrologue(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class IgnoredWithoutPrologue {
        IgnoredWithoutPrologue(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }
    }
}
