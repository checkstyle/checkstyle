/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkMethodThrowsAlignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdkmethodthrowsalignment;

class InputOpenjdkMethodThrowsAlignmentFlexibleConstructorBodies {

    static class Before {
        // violation 3 lines below """The 'throws' clause should be on a new line
        // when the method declaration is wrapped."""
        Before(boolean stop,
              int value) throws IllegalStateException {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        After(boolean stop,
              int value)
                throws IllegalStateException {
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }

    static class WrongIndent {
        // violation 5 lines below """The 'throws' clause should be indented 8 spaces
        // relative to the method declaration or the previous line and should not
        // align with the previous line."""
        WrongIndent(boolean stop,
              int value)
              throws IllegalStateException {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}
