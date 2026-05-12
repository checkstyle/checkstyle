/*
JavadocType
scope = protected
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Example case
 *
 * @param valueExtra wrong tag for a different component
 */
// violation 2 lines above 'Unused @param tag for 'valueExtra'.'
public record InputJavadocTypeRecordComponentNameMismatch(String value) { // violation 'Type Javadoc comment is missing @param value tag.'
}
