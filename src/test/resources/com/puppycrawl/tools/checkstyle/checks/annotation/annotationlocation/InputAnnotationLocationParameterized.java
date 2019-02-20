package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.Repeatable;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * allowSamelineSingleParameterlessAnnotation = false
 * allowSamelineParameterizedAnnotation = true
 * allowSamelineMultipleAnnotations = false
 */

class InputAnnotationLocationParameterized {

    @Annotation void singleParameterless() {} //warn

    @Annotation @Annotation void multipleParameterless() {} //warn

    @Annotation("") void parameterized() {}

    @Annotation(value = "") void namedParameterized() {}

    @Annotation @Annotation("") @Annotation(value = "") void multiple() {} //warn

    @Annotation("") @Annotation(value = "") void multipleParametrized() {} //warn

    @Repeatable(Annotations.class)
    @interface Annotation {
        String value() default "";
    }

    @interface Annotations {
        Annotation[] value();
    }

}
