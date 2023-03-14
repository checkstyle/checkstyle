/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_1
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype; // ok

import java.lang.SuppressWarnings; // ok

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
