/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MissingJavadocMethod">
      <property name="scope" value="private"/>
      <property name="excludeScope" value="anoninner"/>
      <property name="minLineCount" value="3"/>
      <property name="allowedAnnotations" value="Override"/>
      <property name="ignoreMethodNamesRegex" value="^$"/>
      <property name="allowMissingPropertyJavadoc" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

class InputMissingJavadocMethodFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'Missing a Javadoc comment for 'Before'.'
        private Before(boolean stop) {
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
        private After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
