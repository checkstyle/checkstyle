package org.checkstyle.suppressionxpathfilter.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class InputXpathOpenjdkAnnotationLocation3 {
    @MethodAnnotation("foo") void foo1() {}//warn
}
@interface MethodAnnotation {
    String value();
}
