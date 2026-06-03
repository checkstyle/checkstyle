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

// violation 4 lines below 'Unknown tag 'mytag'.'
// violation 4 lines below 'Unknown tag 'mytag''
/**
 * The following is a bad tag.
 * @mytag Hello
 * @mytag
 */
public class InputJavadocTypeBadTag
{
}

// violation below 'Unknown tag 'mytag''
/** @mytag
 */
class InputJavadocTypeBadTagFirstLine {}
