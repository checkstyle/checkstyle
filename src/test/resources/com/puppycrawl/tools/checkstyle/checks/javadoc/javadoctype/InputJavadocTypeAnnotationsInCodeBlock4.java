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
class InputJavadocTypeAnnotationsInCodeBlock4 {
}

/** @singleline */  // violation 'Unknown tag 'singleline'.'
class SingleLineJavadoc {
}

/**
 * {@code
 * @insidewithclosingbrace text }
 */
class TagInsideWithClosingBraceAfter {
}

/**
 * Brace at end of line (not followed by @).
 * text {
 * @bracealinetag  // violation 'Unknown tag 'bracealinetag'.'
 */
class BraceAtEndNotAtSign {
}

/**
 * Brace followed by letter (not @).
 * text {x
 * @bracelettertag  // violation 'Unknown tag 'bracelettertag'.'
 */
class BraceFollowedByLetter {
}

/**
 * Brace followed by space then @ (not inline tag).
 * text { @notinline
 * @bracespaceattag  // violation 'Unknown tag 'bracespaceattag'.'
 */
class BraceSpaceThenAt {
}

/**
 * Test case specifically for isCodeTag mutation.
 * This tests that a tag inside {@code} block is correctly ignored.
 * {@code
 * @codetagonlytest
 * }
 */
class CodeTagOnlyTest {
}
