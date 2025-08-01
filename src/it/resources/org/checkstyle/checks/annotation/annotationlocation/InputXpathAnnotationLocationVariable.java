package org.checkstyle.checks.annotation.annotationlocation;

public class InputXpathAnnotationLocationVariable {
    @VariableAnnotation(value = "") public int b; //warn
}
@interface VariableAnnotation {
    String value();
}
