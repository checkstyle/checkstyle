/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="validateThrows" value="true"/>
      <property name="allowMissingParamTags" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

class InputJavadocMethodFlexibleConstructorBodiesCustom {

    static class Before {
        /**
         * Creates a Before instance.
         *
         * @param stop whether to stop
         */
        Before(boolean stop) throws IllegalStateException {
            // violation above """Expected @throws tag for
            // 'IllegalStateException'."""
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
         * @throws IllegalStateException when stopping fails
         */
        After(boolean stop) throws IllegalStateException {
            super();
            if (stop) {
                return;
            }
        }
    }

    static class Control {
        /**
         * Creates a Control instance.
         */
        Control(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}
