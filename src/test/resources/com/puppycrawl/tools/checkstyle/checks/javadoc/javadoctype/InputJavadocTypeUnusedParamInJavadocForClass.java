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
 * InputJavadocTypeUnusedParamInJavadocForClass.
 *
 * @param BAD This is bad.   // violation 'Unused @param tag for 'BAD'.'
 * @param <BAD> This doesn't exist.   // violation 'Unused @param tag for '<BAD>'.'
 * // violation below 'Unused Javadoc tag'
 * @param
 */
public class InputJavadocTypeUnusedParamInJavadocForClass {
}
