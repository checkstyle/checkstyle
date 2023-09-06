/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = Generated3, ThisIsOk3
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk3
class InputMissingJavadocTypeSkipAnnotations3 { // ok
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk3
class InputJavadocTypeSkipAnnotationsFQN3 { // ok
}

@Generated3(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault3 { // ok
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk3 {} // ok
/**
 * Annotation for unit tests.
 */
@interface Generated3 { // ok
    String[] value();
}
