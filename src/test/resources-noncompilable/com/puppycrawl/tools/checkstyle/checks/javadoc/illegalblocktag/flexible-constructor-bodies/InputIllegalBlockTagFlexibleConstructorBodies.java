/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalBlockTag"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.illegalblocktag;

class InputIllegalBlockTagFlexibleConstructorBodies {

    static class Before {
        /**
         * Creates a Before instance.
         *
         * @param stop whether to stop
         * @since 1.0
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
         * @since 1.0
         */
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
