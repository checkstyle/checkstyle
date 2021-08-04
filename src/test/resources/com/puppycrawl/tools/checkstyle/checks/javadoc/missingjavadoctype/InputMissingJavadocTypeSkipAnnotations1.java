/*
MissingJavadocType
scope = private
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk1
class InputMissingJavadocTypeSkipAnnotations1 { // violation
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk1
class InputJavadocTypeSkipAnnotationsFQN1 { // violation
}

@Generated1(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault1 { // violation
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
