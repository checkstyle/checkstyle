/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="FinalParameters"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

class InputFinalParametersFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop, String log) { // violation 'Parameter stop should be final.'
            // violation above 'Parameter log should be final.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(int value, boolean flag) { // violation 'Parameter value should be final.'
            // violation above 'Parameter flag should be final.'
            super();
            value = 5;
            if (flag) {
                return;
            }
        }
    }
}
