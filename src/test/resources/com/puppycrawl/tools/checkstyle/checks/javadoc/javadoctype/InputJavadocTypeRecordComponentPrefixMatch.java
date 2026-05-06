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

// Java17
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * Example case with prefix match bug.
 *
 * @param valueExtra wrong tag for a different component
 */
public record InputJavadocTypeRecordComponentPrefixMatch(String value) {
} // 2 violations: missing @param value tag, unused @param valueExtra tag
