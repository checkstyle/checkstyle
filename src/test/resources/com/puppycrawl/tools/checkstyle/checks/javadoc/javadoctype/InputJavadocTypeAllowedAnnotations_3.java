package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


import java.lang.SuppressWarnings;

/* Config:
 *
 * allowedAnnotations = Override
 */

@ThisIsOk_3 // ok
class InputJavadocTypeAllowedAnnotations_3 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_3 // ok
class InputJavadocTypeAllowedAnnotationsFQN_3 {
}

@SuppressWarnings(value = "some code generator") // ok
class InputJavadocTypeAllowedAnnotationByDefault_3 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk_3 {}
