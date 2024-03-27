package org.checkstyle.suppressionxpathfilter.annotationlocation;

public class InputXpathAnnotationLocationVariable {
    @VariableAnnotation(value = "") public int b; //warn
}
@interface VariableAnnotation {
    String value();
}
