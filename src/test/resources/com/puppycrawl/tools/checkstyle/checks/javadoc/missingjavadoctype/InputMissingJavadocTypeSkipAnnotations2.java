package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/*
 * Config:
 * scope = private
 * excludeScope = null
 * skipAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk2
 */
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
