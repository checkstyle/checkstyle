package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.Repeatable;


/* Config:
 *
 * allowSamelineSingleParameterlessAnnotation = false
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = true
 *
 */

class InputAnnotationLocationMultiple {

    @Annotation void singleParameterless() {} // ok

    @Annotation @Annotation void multipleParameterless() {}

    @Annotation("") void parameterized() {}

    @Annotation(value = "") void namedParameterized() {}

    @Annotation @Annotation("") @Annotation(value = "") void multiple() {}

    @Annotation("") @Annotation(value = "") void multipleParametrized() {}

    @Repeatable(Annotations.class)
    @interface Annotation {
        String value() default "";
    }

    @interface Annotations {
        Annotation[] value();
    }

}
