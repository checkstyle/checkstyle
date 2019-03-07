package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

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

    void parameterlessSamelineInForEach() {
        for (@Annotation Object o : new Object[0]) break; //ok
        for (@Annotation @Annotation Object o : new Object[0]) break; //ok
        for (@Annotation Object o;;) break; // ok
        for (@Annotation @Annotation Object o;;) break; //ok
    }

    @Repeatable(Annotations.class)
    @Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE})
    @interface Annotation {
        String value() default "";
    }

    @Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE})
    @interface Annotations {
        Annotation[] value();
    }

}
