package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


import java.lang.SuppressWarnings;

/* Config: default*/

@ThisIsOk // ok
class InputJavadocTypeAllowedAnnotations {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk
class InputJavadocTypeAllowedAnnotationsFQN {
}

@SuppressWarnings(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk {}
