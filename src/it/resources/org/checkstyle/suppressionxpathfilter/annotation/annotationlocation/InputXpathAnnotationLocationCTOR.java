package org.checkstyle.suppressionxpathfilter.annotation.annotationlocation;

public class InputXpathAnnotationLocationCTOR {
    @CTORAnnotation(value = "") public InputXpathAnnotationLocationCTOR()//warn
    {
        // comment
    }
}

@interface CTORAnnotation {
    String value();
}
