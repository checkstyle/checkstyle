/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = Override
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;


import java.lang.SuppressWarnings;

@ThisIsOk_3
class InputJavadocTypeAllowedAnnotations_3 {
}

@com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_3
class InputJavadocTypeAllowedAnnotationsFQN_3 {
}

@SuppressWarnings(value = "some code generator")
class InputJavadocTypeAllowedAnnotationByDefault_3 {
}

/**
 * Annotation for unit tests.
 */
@interface ThisIsOk_3 {}

/** */
@Generated // violation 'Type Javadoc comment is missing @param <T> tag'
class Application<T> {}

@interface Generated {
}
