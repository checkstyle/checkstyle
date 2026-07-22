/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

public class InputOpenjdkAnnotationLocation4 {

    @Annotation void exampleGood() {}

    // violation below, 'Annotations must be on a separate line from 'method'.'
    @Annotation public
            String method() {
                 return "";
            }

    @Annotation @Annotation
    @Annotation @Annotation
    public void badMethod() {
    // violation above """Annotations on 'badMethod' must be all on one line or all
    // on separate lines."""
    }

    @Annotation @Annotation @Annotation @Annotation
    public void goodMethod() {
    }

    @Annotation @Annotation class Temp {}
    // violation above, 'Annotations must be on a separate line from 'Temp'.'

    void parameterlessSamelineInForEach() {
        for (@Annotation Object o : new Object[0]) break;
        for (@Annotation @Annotation Object o : new Object[0]) break;
        for (@Annotation Object o;;) break;
        for (@Annotation @Annotation Object o;;) break;
    }

    @Repeatable(Annotations.class)
    @Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.TYPE})
    @interface Annotation {
        String value() default "";
    }

    @Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.TYPE})
    @interface Annotations {
        Annotation[] value();
    }

}
