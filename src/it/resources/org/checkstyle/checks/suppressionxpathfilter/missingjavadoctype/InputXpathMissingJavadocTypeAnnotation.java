package org.checkstyle.checks.suppressionxpathfilter.missingjavadoctype;

@TestAnnotation
public class InputXpathMissingJavadocTypeAnnotation {
    @TestAnnotation2 // warn
    public class innerClass {

    }
}

@interface TestAnnotation {

}

@interface TestAnnotation2 {

}
