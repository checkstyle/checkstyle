/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="JavadocMethod">
      <property name="accessModifiers" value="public, protected, package, private"/>
      <property name="allowedAnnotations" value="Override"/>
      <property name="allowInlineReturn" value="false"/>
      <property name="allowMissingReturnTag" value="false"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

class InputJavadocMethodFlexibleConstructorBodiesCustomTags {

    static class Before {
        /**
         * Creates a Before instance.
         *
         * @param stop whether to stop
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
         */
        After(boolean stop) {
            super();
            if (stop) {
                return;
            }
        }
    }
}
