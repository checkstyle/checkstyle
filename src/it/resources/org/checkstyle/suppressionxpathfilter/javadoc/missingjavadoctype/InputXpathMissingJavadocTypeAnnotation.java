/*
MissingJavadocType
excludeScope = (default)null
scope = (default)public
skipAnnotations = TestAnnotation
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package org.checkstyle.suppressionxpathfilter.javadoc.missingjavadoctype;

@TestAnnotation
public class InputXpathMissingJavadocTypeAnnotation {
    // violation below 'Missing a Javadoc comment for 'innerClass'.'
    @TestAnnotation2
    public class innerClass {

    }
}

@interface TestAnnotation {

}

@interface TestAnnotation2 {

}
