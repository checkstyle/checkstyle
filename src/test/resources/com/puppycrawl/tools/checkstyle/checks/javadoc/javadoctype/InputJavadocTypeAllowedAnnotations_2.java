package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


import java.lang.SuppressWarnings;

/* Config:
 *
 * allowedAnnotations = SuppressWarnings, ThisIsOk_2
 */

@ThisIsOk_2 // ok
class InputJavadocTypeAllowedAnnotations_2 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_2 // ok
class InputJavadocTypeAllowedAnnotationsFQN_2 {
}

@SuppressWarnings(value = "some code generator") // ok
class InputJavadocTypeAllowedAnnotationByDefault_2 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk_2 {}
