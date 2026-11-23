/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterAssignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;

class InputParameterAssignmentFlexibleConstructorBodies {

    static class Before {
        Before(boolean stop) {
            stop = true; // violation 'Assignment of parameter 'stop' is not allowed.'
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(int value) {
            super();
            value = 5; // violation 'Assignment of parameter 'value' is not allowed.'
            if (value > 0) {
                return;
            }
        }
    }
}
