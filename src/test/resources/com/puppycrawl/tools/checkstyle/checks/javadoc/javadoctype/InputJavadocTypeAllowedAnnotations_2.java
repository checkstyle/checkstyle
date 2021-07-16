/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = SuppressWarnings, ThisIsOk_2
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


import java.lang.SuppressWarnings;

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
