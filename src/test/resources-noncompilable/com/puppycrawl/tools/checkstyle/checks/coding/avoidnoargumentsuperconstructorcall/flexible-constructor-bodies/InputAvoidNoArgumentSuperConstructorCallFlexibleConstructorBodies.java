/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AvoidNoArgumentSuperConstructorCall"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

class InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop) {
            super(); // violation 'Unnecessary call to superclass constructor with no arguments.'
            int value = 1;
            if (stop) {
                return;
            }
        }
    }

    static class Implicit {
        Implicit(boolean stop) {
            int value = 1;
            if (stop) {
                return;
            }
        }
    }
}
