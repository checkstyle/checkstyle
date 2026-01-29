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

/**
 * Test case for multiline code block with tag on later lines.
 * This tests the index < lines.length condition in the for loop.
 * {@code
 * @Override
 * public void method() {
 * }
 * }
 */
class InputJavadocTypeAnnotationsInCodeBlock4 {}

// violation 1 lines below 'Unknown tag 'unknown'.'
/** @unknown */
class SingleLineJavadoc {}

/**
 * {@code
 * @insidewithclosingbrace text }
 */
class TagInsideWithClosingBraceAfter {}

// violation 4 lines below 'Unknown tag 'unknown'.'
/**
 * Brace at end of line (not followed by @).
 * text {
 * @unknown
 */
class BraceAtEndNotAtSign {}

// violation 4 lines below 'Unknown tag 'unknown'.'
/**
 * Brace followed by letter (not @).
 * text {x
 * @unknown
 */
class BraceFollowedByLetter {}

// violation 4 lines below 'Unknown tag 'unknown'.'
/**
 * Brace followed by space then @ (not inline tag).
 * text { @notinline
 * @unknown
 */
class BraceSpaceThenAt {}

/**
 * Test case specifically for isCodeTag mutation.
 * This tests that a tag inside {@code} block is correctly ignored.
 * {@code
 * @codetagonlytest
 * }
 */
class CodeTagOnlyTest {}
