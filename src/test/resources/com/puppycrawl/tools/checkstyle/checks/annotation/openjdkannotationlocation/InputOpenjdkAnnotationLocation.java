/*
OpenjdkAnnotationLocation
tokens = ANNOTATION_DEF, ANNOTATION_FIELD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

// violation 3 lines below """Annotations must be on a separate line
// from 'InputOpenjdkAnnotationLocation'."""
@Annotation2(value = "foo") @Annotation2
@Annotation2("bar") @interface InputOpenjdkAnnotationLocation {
// violation above """Annotations on 'InputOpenjdkAnnotationLocation' must be all on
// one line or all on separate lines."""

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2("bar") public String valueBad();
    // violation above 'Annotations must be on a separate line from 'valueBad'.'

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2("bar")
    public String valueGoodOne();

    @Annotation2(value = "foo") @Annotation2 @Annotation2("bar")
    public String valueGoodTwo();

    @Annotation2(value = "foo") @Annotation2 @Annotation2("bar") String valGoodThree();

    @Annotation2 @Annotation2("bar")
    String values();

    @Annotation2("bar") String values2();

    @Annotation2(value = "foo")
    @Annotation2 @Annotation2(value = "some")
    @Annotation2("bar") public String valueBadTwo();
    // 2 violations above:
    //    'Annotations must be on a separate line from 'valueBadTwo'.'
    //    'Annotations on 'valueBadTwo' must be all on one line or all on separate lines.'

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2(value = "some")
    @Annotation2("bar")
    public String good();

    @Annotation2(value = "foo") @Annotation2 @Annotation2(value = "some") @Annotation2("bar")
    public String good2();

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
