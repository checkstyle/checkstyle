/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ReturnCount"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.returncount;

import java.util.function.IntUnaryOperator;

class InputReturnCountFlexibleConstructorBodies {
    static class Before {
        Before(boolean stop) {
            IntUnaryOperator operation = value -> { // violation 'Return count is 3'
                if (value < 0) {
                    return -1;
                }
                if (value == 0) {
                    return 0;
                }
                return 1;
            };
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super();
            IntUnaryOperator operation = value -> { // violation 'Return count is 3'
                if (value < 0) {
                    return -1;
                }
                if (value == 0) {
                    return 0;
                }
                return 1;
            };
            if (stop) {
                return;
            }
        }
    }
}
