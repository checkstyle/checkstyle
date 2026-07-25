package org.checkstyle.suppressionxpathfilter.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@EnumAnnotation("bar") enum InputXpathOpenjdkAnnotationLocation2 { //warn

}

@Target({ElementType.FIELD, ElementType.TYPE})
@interface EnumAnnotation  {

    String value() default "";

}
