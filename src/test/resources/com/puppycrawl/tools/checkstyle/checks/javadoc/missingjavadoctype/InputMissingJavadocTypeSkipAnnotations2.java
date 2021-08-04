/*
MissingJavadocType
scope = private
excludeScope = (default)null
skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk2
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk2
class InputMissingJavadocTypeSkipAnnotations2 { // violation
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk2
class InputJavadocTypeSkipAnnotationsFQN2 { // violation
}

@Generated2(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault2 { // violation
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
