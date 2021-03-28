package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/*
 * Config:
 * scope = public
 * excludeScope = null
 * skipAnnotations = Generated3, ThisIsOk3
 */
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
