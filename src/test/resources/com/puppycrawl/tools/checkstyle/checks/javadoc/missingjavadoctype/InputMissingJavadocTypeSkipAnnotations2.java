/*
MissingJavadocType
scope = PRIVATE
excludeScope = (default)null
skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk2
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk2 // violation
class InputMissingJavadocTypeSkipAnnotations2 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk2 // violation
class InputJavadocTypeSkipAnnotationsFQN2 {
}

@Generated2(value = "some code generator") // violation
class InputJavadocTypeAllowedAnnotationByDefault2 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk2 {} // ok
/**
 * Annotation for unit tests.
 */
@interface Generated2 { // ok
    String[] value();
}
