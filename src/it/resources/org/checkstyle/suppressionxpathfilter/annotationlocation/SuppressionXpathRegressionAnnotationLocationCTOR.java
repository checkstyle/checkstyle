package org.checkstyle.suppressionxpathfilter.annotationlocation;

public class SuppressionXpathRegressionAnnotationLocationCTOR {
    @CTORAnnotation(value = "") public SuppressionXpathRegressionAnnotationLocationCTOR()//warn
    {
        // comment
    }
}

@interface CTORAnnotation {
    String value();
}
