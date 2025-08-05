package org.checkstyle.suppressionxpathfilter.annotation.annotationlocation;

public class InputXpathAnnotationLocationMethod {
     @MethodAnnotation("foo") void foo1() {}//warn
}
@interface MethodAnnotation {
     String value();
}
