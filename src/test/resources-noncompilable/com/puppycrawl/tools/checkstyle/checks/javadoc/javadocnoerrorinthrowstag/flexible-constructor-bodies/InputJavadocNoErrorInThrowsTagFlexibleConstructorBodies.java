/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocNoErrorInThrowsTag"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocnoerrorinthrowstag;

class InputJavadocNoErrorInThrowsTagFlexibleConstructorBodies {

    static class Before {
        // violation 6 lines below """Error type 'AssertionError' should not be
        // documented in '@throws' tag."""
        /**
         * Creates a Before instance.
         *
         * @param stop whether to stop
         * @throws AssertionError if the check fails
         */
        Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        /**
         * Creates an After instance.
         *
         * @param stop whether to stop
         * @throws AssertionError if the check fails
         */
        After(boolean stop) {
            super();
            if (stop) {
                throw new AssertionError();
            }
        }
    }

    static class PrologueThrow {
        /**
         * Creates a PrologueThrow instance.
         *
         * @param stop whether to stop
         * @throws AssertionError if the check fails
         */
        PrologueThrow(boolean stop) {
            if (stop) {
                throw new AssertionError();
            }
            super();
        }
    }
}
