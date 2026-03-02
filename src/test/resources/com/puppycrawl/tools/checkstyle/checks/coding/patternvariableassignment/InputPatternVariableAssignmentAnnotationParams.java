/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentAnnotationParams {

    private static final String DESC = "description";
    private static final boolean FLAG = true;
    private static final int VALUE = 42;

    @interface MyAnnotation {
        String description() default "";
        boolean required() default false;
        int value() default 0;
        String[] tags() default {};
    }

    // Annotation with parameter assignments
    @MyAnnotation(
        description = DESC,
        required = FLAG,
        value = VALUE,
        tags = {"tag1", "tag2"}
    )
    class AnnotatedClass {

        @MyAnnotation(description = "test", required = true, value = 10)
        private String field;

        @MyAnnotation(
            description = DESC,
            required = FLAG
        )
        void method() {}
    }

    // Anonymous class with annotation parameters
    Runnable r = new @MyAnnotation(description = "runnable") Runnable() {
        @Override
        public void run() {}
    };

    // Pattern variable should still work
    void patternTest(Object obj) {
        if (obj instanceof @MyAnnotation(description = "pattern") String s) {
            System.out.println(s);
        }
    }
}
