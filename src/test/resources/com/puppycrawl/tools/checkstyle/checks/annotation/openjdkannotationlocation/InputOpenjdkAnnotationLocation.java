/*
OpenjdkAnnotationLocation
tokens = ANNOTATION_DEF, ANNOTATION_FIELD_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@Annotation2(value = "foo") @Annotation2
@Annotation2("bar") @interface InputOpenjdkAnnotationLocation {
    // 2 violations above:
    //      'Annotation 'Annotation2' must be alone on a line, or all on one line.'
    //      'Annotation 'Annotation2' must be on a separate line from target.'

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2("bar") public String valueBad();
    // violation above, 'Annotation 'Annotation2' must be alone on a line, or all on one line.'

    @Annotation2(value = "foo")
    @Annotation2
    @Annotation2("bar")
    public String valueGoodOne();

    @Annotation2(value = "foo") @Annotation2 @Annotation2("bar")
    public String valueGoodTwo();

    @Annotation2 @Annotation2("bar") String val();

    @Annotation2 @Annotation2("bar")
    String values();

    @Annotation2("bar") String values2();

    // 2 violations 4 lines below:
    //      'Annotation 'Annotation2' must be alone on a line, or all on one line.'
    //      'Annotation 'Annotation2' must be alone on a line, or all on one line.'
    @Annotation2(value = "foo")
    @Annotation2 @Annotation2(value = "some")
    @Annotation2("bar") public String valueBadTwo();
    // violation above 'Annotation 'Annotation2' must be alone on a line, or all on one line.'

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
