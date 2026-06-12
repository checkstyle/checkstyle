/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

public class InputOpenjdkAnnotationLocation4 {

    @Annotation void singleParameterless() {}

    // violation below, 'Annotation 'Annotation' must be on a separate line from target.'
    @Annotation @Annotation void multipleParameterless() {}
    // violation above, 'Annotation 'Annotation' must be on a separate line from target.'

    @Annotation
    // violation below, 'Annotation 'Annotation' must be alone on a line, or all on one line.'
    @Annotation("") @Annotation(value = "")
    // violation above, 'Annotation 'Annotation' must be alone on a line, or all on one line.'
    @Annotation(value = "foo")
    // violation above, 'Annotation 'Annotation' must be alone on a line, or all on one line.'
    void multiple() {}

    // violation below, 'Annotation 'Annotation' must be on a separate line from target.'
    @Annotation public
            String method() {
                 return "";
            }

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
