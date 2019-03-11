package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;



@ThisIsOk
class InputMissingJavadocTypeSkipAnnotations {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype.ThisIsOk
class InputJavadocTypeSkipAnnotationsFQN {
}

@Generated(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk {}
/**
 * Annotation for unit tests.
 */
@interface Generated {
    String[] value();
}
