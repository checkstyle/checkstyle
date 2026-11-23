/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

class InputAnnotationLocationFlexibleConstructorBodies {

    static class Before {
        @Deprecated public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
        @SuppressWarnings("unused") public Before(int input) {
            String text = "unused";
            super();
        }

        @Deprecated @SuppressWarnings("unused") public Before(String text) {
            // violation above 'Annotation 'SuppressWarnings' should be alone on line.'
            int value = 1;
            super();
        }
        @Deprecated
        public Before(double input) {
            int value = 1;
            super();
        }
    }

    static class After {
        // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
        @SuppressWarnings("unused") public After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }
    }
}
