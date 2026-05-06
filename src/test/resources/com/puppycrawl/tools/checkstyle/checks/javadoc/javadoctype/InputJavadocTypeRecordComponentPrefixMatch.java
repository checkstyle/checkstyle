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
 * @param john123 wrong tag for a different component
 */
public record InputJavadocTypeRecordComponentPrefixMatch(String john) {
} // 2 violations: missing @param john tag, unused @param john123 tag