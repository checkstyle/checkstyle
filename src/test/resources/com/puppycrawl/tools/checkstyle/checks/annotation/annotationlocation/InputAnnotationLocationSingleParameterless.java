package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.Repeatable;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 */

class InputAnnotationLocationSingleParameterless {

    @Annotation void singleParameterless() {}

    @Annotation @Annotation void multipleParameterless() {} //warn

    @Annotation("") void parameterized() {} //warn

    @Annotation(value = "") void namedParameterized() {} //warn

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
