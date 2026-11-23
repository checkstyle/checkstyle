/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineSeparator"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorFlexibleConstructorBodies {

    static class Before {
        private int value;
        Before(boolean stop) { // violation ''CTOR_DEF' should be separated from previous line.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        void method() {
        }
        After(boolean stop) { // violation ''CTOR_DEF' should be separated from previous line.'
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }

    static class Separated {
        private int value;

        Separated(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }

        void method() {
        }
    }
}
