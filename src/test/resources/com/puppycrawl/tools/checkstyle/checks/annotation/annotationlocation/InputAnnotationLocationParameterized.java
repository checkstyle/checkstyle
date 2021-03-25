package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.Repeatable;

/*
 * Config :
 *
 * allowSamelineSingleParameterlessAnnotation = false
 * allowSamelineParameterizedAnnotation = true
 * allowSamelineMultipleAnnotations = false
 */

class InputAnnotationLocationParameterized {

    @Annotation void singleParameterless() {} // violation

    @Annotation @Annotation void multipleParameterless() {} // violation

    @Annotation("") void parameterized() {}

    @Annotation(value = "") void namedParameterized() {}

    @Annotation @Annotation("") @Annotation(value = "") void multiple() {} // violation

    @Annotation("") @Annotation(value = "") void multipleParametrized() {} // violation

    @Repeatable(Annotations.class)
    @interface Annotation {
        String value() default "";
    }

    @interface Annotations {
        Annotation[] value();
    }

}
