package org.checkstyle.suppressionxpathfilter.annotationlocation;

public class SuppressionXpathRegressionAnnotationLocationVariable {
    @VariableAnnotation(value = "") public int b; //warn
}
@interface VariableAnnotation {
    String value();
}
