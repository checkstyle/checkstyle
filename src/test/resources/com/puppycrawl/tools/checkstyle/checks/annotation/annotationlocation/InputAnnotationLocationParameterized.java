/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = false
allowSamelineParameterizedAnnotation = true
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.Repeatable;

class InputAnnotationLocationParameterized {

    @Annotation void singleParameterless() {} // violation

    @Annotation @Annotation void multipleParameterless() {} // 2 violations

    @Annotation("") void parameterized() {}

    @Annotation(value = "") void namedParameterized() {}

    @Annotation @Annotation("") @Annotation(value = "") void multiple() {} // 3 violations

    @Annotation("") @Annotation(value = "") void multipleParametrized() {} // violation

    @Repeatable(Annotations.class)
    @interface Annotation {
        String value() default "";
    }

    @interface Annotations {
        Annotation[] value();
    }

}
