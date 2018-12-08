//non-compiled with jkd11: contains dropped packages
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

import javax.annotation.Generated;

@ThisIsOk
class InputJavadocTypeAllowedAnnotations {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk
class InputJavadocTypeAllowedAnnotationsFQN {
}

@Generated(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk {}
