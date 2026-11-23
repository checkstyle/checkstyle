/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DeclarationOrder"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

class InputDeclarationOrderFlexibleConstructorBodies {

    static class Before {
        void method() {
        }

        // violation below 'Constructor definition in wrong order.'
        Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        void method() {
        }

        // violation below 'Constructor definition in wrong order.'
        After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }
    }

    static class AccessOrder {
        private static int first;

        // violation below 'Variable access definition in wrong order.'
        public static int second;
    }

    static class Ordered {
        public static int count;

        public int value;

        Ordered(boolean stop) {
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
