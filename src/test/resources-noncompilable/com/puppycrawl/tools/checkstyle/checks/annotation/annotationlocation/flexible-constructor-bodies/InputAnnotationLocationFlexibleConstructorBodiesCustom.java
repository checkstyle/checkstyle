/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
      <property name="allowSamelineParameterizedAnnotation" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

class InputAnnotationLocationFlexibleConstructorBodiesCustom {

    static class Before {
        // violation below 'Annotation 'Deprecated' should be alone on line.'
        @Deprecated public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        @SuppressWarnings("unused") public Before(int input) {
            String text = "unused";
            super();
        }
    }

    static class After {
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
