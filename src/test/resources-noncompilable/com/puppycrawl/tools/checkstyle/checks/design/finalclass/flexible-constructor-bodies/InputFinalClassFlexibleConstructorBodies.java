/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalClass"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

class InputFinalClassFlexibleConstructorBodies {

    static class Before { // violation 'Class Before should be declared as final.'
        private Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After { // violation 'Class After should be declared as final.'
        private After(boolean stop) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }

    static class Extended {
        private Extended() {
            super();
        }
    }

    static class Child extends Extended {
        Child(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class Open {
        Open(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}
