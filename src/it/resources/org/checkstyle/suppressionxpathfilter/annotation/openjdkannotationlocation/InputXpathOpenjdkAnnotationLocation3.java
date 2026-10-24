/*
OpenjdkAnnotationLocation
tokens = METHOD_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

class InputXpathOpenjdkAnnotationLocation3 {
    @MethodAnnotation("foo") void foo1() {
    // violation above "Annotations must be on a separate line from 'foo1'."
    }
}
@interface MethodAnnotation {
    String value();
}
