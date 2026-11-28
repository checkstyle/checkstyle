/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodFlexibleConstructorBodies {

    public static class Before {
        // violation below 'Missing a Javadoc comment for 'Before'.'
        public Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    public static class After {
        /**
         * Creates an After instance.
         *
         * @param stop whether to stop
         */
        public After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
