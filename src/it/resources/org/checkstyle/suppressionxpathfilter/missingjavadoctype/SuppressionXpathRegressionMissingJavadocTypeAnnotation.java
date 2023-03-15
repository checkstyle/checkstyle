package org.checkstyle.suppressionxpathfilter.missingjavadoctype;

@TestAnnotation
public class SuppressionXpathRegressionMissingJavadocTypeAnnotation {
    @TestAnnotation2 // warn
    public class innerClass {

    }
}

@interface TestAnnotation {

}

@interface TestAnnotation2 {

}
