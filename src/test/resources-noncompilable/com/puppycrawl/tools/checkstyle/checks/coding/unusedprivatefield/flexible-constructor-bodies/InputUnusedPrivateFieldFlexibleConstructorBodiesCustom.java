/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnusedPrivateField">
      <property name="ignoredFieldPattern" value="^ignored.*"/>
      <property name="ignoreAnnotationCanonicalNames" value="java.lang.Deprecated"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

class InputUnusedPrivateFieldFlexibleConstructorBodiesCustom {

    static class Before {
        private int ignoredField;

        private int unused; // violation 'Unused private field 'unused'.'

        Before(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        private int used;

        After(boolean stop) {
            super();
            used = 1;
            if (stop) {
                return;
            }
        }
    }
}
