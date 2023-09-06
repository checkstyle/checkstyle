/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

class InputAnnotationLocationSingleParameterless {

    @Annotation void singleParameterless() {}

    // violation below 'Annotation 'Annotation' should be alone on line.'
    @Annotation @Annotation void multipleParameterless() {}

    @Annotation("") void parameterized() {} // violation '.* should be alone on line.'

    @Annotation(value = "") void namedParameterized() {} // violation '.* should be alone on line.'

    @Annotation @Annotation("") @Annotation(value = "") void multiple() {} // 2 violations

    @Annotation("") @Annotation(value = "") void multipleParametrized() {} // 2 violations

    void parameterlessSamelineInForEach() {
        for (@Annotation Object o : new Object[0]) break;
        for (@Annotation @Annotation Object o : new Object[0]) break;
        for (@Annotation Object o;;) break;
        for (@Annotation @Annotation Object o;;) break;
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
