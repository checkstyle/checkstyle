package org.checkstyle.suppressionxpathfilter.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@ClassAnnotation("bar") class InputXpathOpenjdkAnnotationLocation1 { //warn

}

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@interface ClassAnnotation {

    String value() default "";

}
