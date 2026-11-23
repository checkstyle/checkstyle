/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

class InputJavadocMethodFlexibleConstructorBodies {

    static class Before {
        /**
         * Creates a Before instance.
         */
        Before(boolean stop) { // violation 'Expected @param tag for 'stop'.'
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
         */
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
