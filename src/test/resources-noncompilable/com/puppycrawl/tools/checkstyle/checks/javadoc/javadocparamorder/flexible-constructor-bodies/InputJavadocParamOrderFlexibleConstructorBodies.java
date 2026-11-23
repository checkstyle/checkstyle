/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocParamOrder"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

class InputJavadocParamOrderFlexibleConstructorBodies {

    static class Before {
        // violation 5 lines below '@param tag for 'stop' should be in declaration order.'
        /**
         * Creates a Before instance.
         *
         * @param value the value
         * @param stop whether to stop
         */
        Before(boolean stop, int value) {
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
         * @param value the value
         */
        After(boolean stop, int value) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
