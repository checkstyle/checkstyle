/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = Override
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
@ThisIsOk4
class InputMissingJavadocTypeSkipAnnotations4 {
}

// violation below 'Missing a Javadoc comment.'
@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk4
class InputJavadocTypeSkipAnnotationsFQN4 {
}

// violation below 'Missing a Javadoc comment.'
@Generated4(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault4 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk4 {}
/**
 * Annotation for unit tests.
 */
@interface Generated4 {
    String[] value();
}
