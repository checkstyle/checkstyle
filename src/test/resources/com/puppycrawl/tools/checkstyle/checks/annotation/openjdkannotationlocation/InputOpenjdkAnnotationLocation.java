/*
OpenjdkAnnotationLocation
tokens = ANNOTATION_DEF, ANNOTATION_FIELD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@Annotation2(value = "foo") @Annotation2
// violation below, 'Annotation 'Annotation2' must be alone on a line, or all on one line.'
@Annotation2("bar") @interface InputOpenjdkAnnotationLocation {
// violation above, 'Annotation 'Annotation2' must be on a separate line from target.'

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2("bar") public String value();
    // violation above, 'Annotation 'Annotation2' must be on a separate line from target.'

    // violation below, 'Annotation 'Annotation2' must be on a separate line from target.'
    @Annotation2 @Annotation2("bar") String val();
    // violation above, 'Annotation 'Annotation2' must be on a separate line from target.'

    @Annotation2 @Annotation2("bar")
    String values();

    @Annotation2("bar") String values2();
    // violation above, 'Annotation 'Annotation2' must be on a separate line from target.'

    @Annotation2 String val2();

}

@Repeatable(Annotation1.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@interface Annotation2  {
    String value() default "";
}

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@interface Annotation1 {

    Annotation2[] value();

}
