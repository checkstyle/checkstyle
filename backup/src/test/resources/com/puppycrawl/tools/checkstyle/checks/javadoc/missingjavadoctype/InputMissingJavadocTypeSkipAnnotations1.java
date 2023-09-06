/*
MissingJavadocType
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk1 // violation
class InputMissingJavadocTypeSkipAnnotations1 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk1 // violation
class InputJavadocTypeSkipAnnotationsFQN1 {
}

@Generated1(value = "some code generator") // violation
class InputJavadocTypeAllowedAnnotationByDefault1 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk1 {} // ok
/**
 * Annotation for unit tests.
 */
@interface Generated1 { // ok
    String[] value();
}
