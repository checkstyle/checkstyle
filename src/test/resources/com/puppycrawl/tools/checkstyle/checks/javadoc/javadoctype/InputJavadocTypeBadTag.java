/*
JavadocType
scope = (default)private
excludeScope = (default)null
authorFormat = (default)null
versionFormat = (default)null
allowMissingParamTags = (default)false
allowUnknownTags = (default)false
allowedAnnotations = (default)Generated
tokens = (default)BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/**
 * The following is a bad tag.
 * @mytag Hello   // violation 'Unknown tag 'mytag'.'
 * // violation below 'Unknown tag 'mytag''
 * @mytag
 */
public class InputJavadocTypeBadTag
{
}

// violation below 'Unknown tag 'mytag''
/** @mytag
 */
class InputJavadocTypeBadTagFirstLine {}

// violation 2 lines below 'Unused Javadoc tag'
/**
 * @param
 */
class InputJavadocTypeBadTagEmptyParam {}

// violation 2 lines below 'Unused Javadoc tag'
/**
 * @param <>
 */
class InputJavadocTypeBadTagEmptyTypeParam {}

// violation 2 lines below 'Unused @param tag for '<T''
/**
 * @param <T
 */
class InputJavadocTypeBadTagUnclosedTypeParam {}

/**
 * This javadoc is not attached to a type definition.
 */

class InputJavadocTypeBadTagWithMethod {
    /** method javadoc */
    void method() {}
}

class InputJavadocTypeBadTagFieldJavadoc {
    /**
     * field javadoc
     */
    int field;
}
/**
 * This javadoc is not attached to a type definition.
 */
