package org.checkstyle.suppressionxpathfilter.annotationlocation;

public class InputXpathAnnotationLocationCTOR {
    @CTORAnnotation(value = "") public InputXpathAnnotationLocationCTOR()//warn
    {
        // comment
    }
}

@interface CTORAnnotation {
    String value();
}
