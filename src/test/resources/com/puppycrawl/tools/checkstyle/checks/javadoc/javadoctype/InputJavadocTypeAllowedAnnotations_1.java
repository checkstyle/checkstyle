package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype; // ok


import java.lang.SuppressWarnings; // ok

/* Config:
 *
 * allowedAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_1
 */

@ThisIsOk_1 // ok
class InputJavadocTypeAllowedAnnotations_1 { // ok
} // ok

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_1 // ok
class InputJavadocTypeAllowedAnnotationsFQN_1 { // ok
} // ok

@SuppressWarnings(value = "some code generator") // ok
class InputJavadocTypeAllowedAnnotationByDefault_1 { // ok
} // ok

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk_1 {} // ok
