/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="AnnotationLocation">
      <property name="allowSamelineMultipleAnnotations" value="true"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

class InputAnnotationLocationFlexibleConstructorBodiesCustomMultiple {

    static class Before {
        @Deprecated @SuppressWarnings("unused") public Before(boolean stop) {
            int value = 1;
            super();
            if (stop) {
                return;
            }
        }

        @Deprecated public Before(int input) {
            int value = 1;
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
