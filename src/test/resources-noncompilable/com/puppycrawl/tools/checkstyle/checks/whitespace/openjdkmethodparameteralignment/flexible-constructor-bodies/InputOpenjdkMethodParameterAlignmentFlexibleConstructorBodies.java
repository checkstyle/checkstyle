/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodParameterAlignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.whitespace.openjdkmethodparameteralignment;

class InputOpenjdkMethodParameterAlignmentFlexibleConstructorBodies {

    static class Before {
        // violation 2 lines below """Parameter should be aligned vertically or
        // indented 8 spaces relative to the method declaration."""
        Before(boolean stop,
                int value,
            String text) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop,
              int value,
              String text) {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }

    static class Mixed {
        // violation below 'Only one parameter is allowed per line in a vertical list.'
        Mixed(boolean stop,
              int value, String text) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}
