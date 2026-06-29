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

// violation 4 lines below 'Unused @param tag for 'valueExtra'.'
/**
 * Example case
 *
 * @param valueExtra wrong tag for a different component
 */
public record InputJavadocTypeRecordComponentNameMismatch(String value) {
    // violation above 'Type Javadoc comment is missing @param 'value' tag.'
}
