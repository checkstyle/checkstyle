/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationOnSameLine"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

class InputAnnotationOnSameLineFlexibleConstructorBodies {

    static class Before {
        // violation below 'Annotation 'Deprecated' should be on the same line with its target.'
        @Deprecated
        public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        @Deprecated public Before(int input) {
            String text = "unused";
            super();
        }
    }

    static class After {
        // violation below 'Annotation 'Deprecated' should be on the same line with its target.'
        @Deprecated
        public After(boolean stop) {
            super();
            int value = 1;
            if (stop) {
                return;
            }
        }
    }
}
