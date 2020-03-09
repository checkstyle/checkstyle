package org.checkstyle.suppressionxpathfilter.annotationlocation;

public class SuppressionXpathRegressionAnnotationLocationMethod {
     @MethodAnnotation("foo") void foo1() {}//warn
}
@interface MethodAnnotation {
     String value();
}


