/*
MissingJavadocType
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = Override
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk4 // violation
class InputMissingJavadocTypeSkipAnnotations4 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk4 // violation
class InputJavadocTypeSkipAnnotationsFQN4 {
}

@Generated4(value = "some code generator") // violation
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
