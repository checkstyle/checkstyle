/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

import java.lang.SuppressWarnings;

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
