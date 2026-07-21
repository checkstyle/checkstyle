/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
@ThisIsOk1
class InputMissingJavadocTypeSkipAnnotations1 {
}

// violation below 'Missing a Javadoc comment.'
@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk1
class InputJavadocTypeSkipAnnotationsFQN1 {
}

// violation below 'Missing a Javadoc comment.'
@Generated1(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault1 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk1 {}
/**
 * Annotation for unit tests.
 */
@interface Generated1 {
    String[] value();
}
