/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = Generated3, ThisIsOk3
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

@ThisIsOk3
class InputMissingJavadocTypeSkipAnnotations3 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk3
class InputJavadocTypeSkipAnnotationsFQN3 {
}

@Generated3(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault3 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk3 {}
/**
 * Annotation for unit tests.
 */
@interface Generated3 {
    String[] value();
}
