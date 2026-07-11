/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk2
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
@ThisIsOk2
class InputMissingJavadocTypeSkipAnnotations2 {
}

// violation below 'Missing a Javadoc comment.'
@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk2
class InputJavadocTypeSkipAnnotationsFQN2 {
}

// violation below 'Missing a Javadoc comment.'
@Generated2(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault2 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk2 {}
/**
 * Annotation for unit tests.
 */
@interface Generated2 {
    String[] value();
}
