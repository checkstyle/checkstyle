/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkAnnotationLocation"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

class InputOpenjdkAnnotationLocationFlexibleConstructorBodies {

    static class Before {
        @AnotherAnnotation("unused")
        @Deprecated @SuppressWarnings("rawtypes")
        @CustomAnnotation("all") public Before(boolean stop) {
            // 2 violations above:
            //    'Annotations must be on a separate line from 'Before'.'
            //    'Annotations on 'Before' must be all on one line or all on separate lines.'
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }

    static class After {
        @Deprecated public After(boolean stop) {
            // violation above 'Annotations must be on a separate line from 'After'.'
            super();
            int local = 1;
            if (stop) {
                return;
            }
        }
    }

    static class Separate {
        @Deprecated
        public Separate(boolean stop) {
            int local = 1;
            super();
            if (stop) {
                return;
            }
        }
    }
}

@interface CustomAnnotation {
    String value();
}

@interface AnotherAnnotation {
    String value();
}
