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
class InputJavadocTypeAnnotationsInCodeBlock3 {
}

/**
 * Brace at end of line, then tag on next line tests index + 1 < length.
 * Some text here {
 * @endoflinebrace  // violation 'Unknown tag 'endoflinebrace'.'
 */
class BraceAtEndOfLine {
}

/**
 * Multiple lines with code block spanning lines.
 * Line 1 of javadoc.
 * Line 2 of javadoc.
 * {@code
 * Line 3 inside code.
 * @insidecode
 * Line 4 inside code.
 * }
 * Line 5 after code.
 */
class MultilineCodeBlock {
}

/**
 * Snippet tag with content spanning multiple lines.
 * {@snippet :
 * @insidesnippet
 * void test() { }
 * }
 */
class SnippetMultiline {
}

/**
 * Literal with nested braces.
 * {@literal Map<String, @NonNull List>}
 */
class LiteralWithBraces {
}

/**
 * Multi-line literal block with tag inside on subsequent line.
 * This tests the isLiteralTag branch in isInsideInlineTag method.
 * {@literal
 * @insideliteral
 * some text here
 * }
 */
class MultilineLiteralBlock {
}

/**
 * Just a single open brace not followed by @ at end.
 * {
 * @aftersinglebrace  // violation 'Unknown tag 'aftersinglebrace'.'
 */
class SingleBraceNotAtTag {
}

/**
 * Code block closed, then bare brace, then tag.
 * {@code test} some text {
 * @afterclosedbrace  // violation 'Unknown tag 'afterclosedbrace'.'
 */
class CodeClosedThenBrace {
}

/**
 * Empty code block.
 * {@code}
 * @afteremptycode  // violation 'Unknown tag 'afteremptycode'.'
 */
class EmptyCodeBlock {
}
