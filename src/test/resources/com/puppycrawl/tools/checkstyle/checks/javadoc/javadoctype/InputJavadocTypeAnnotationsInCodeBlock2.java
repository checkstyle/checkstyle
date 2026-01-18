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
 * Nested braces inside code block.
 * {@code Map<String, List<@NonNull String>> map = new HashMap<>();}
 */
class InputJavadocTypeAnnotationsInCodeBlock2 {
}

/**
 * Bare HTML pre tag does NOT protect content from parsing.
 * Block tags must appear at line start - this one is at block tag position.
 * <pre>
 * @htmlpretag  // violation 'Unknown tag 'htmlpretag'.'
 * </pre>
 */
class BareHtmlPreTag {
}

/**
 * Correct usage: pre with @code protects content.
 * <pre>{@code
 * @protectedtag class Example {}
 * }</pre>
 */
class PreWithAtCode {
}

/**
 * Non-verbatim inline tag like link does NOT protect content.
 * {@link SomeClass} but then
 * @nonverbatimtag  // violation 'Unknown tag 'nonverbatimtag'.'
 */
class NonVerbatimInlineTag {
}

/**
 * Regular braces (not followed by @) inside code block.
 * {@code Map<K, V> map = new HashMap<K, V>() { }; }
 */
class RegularBracesInCode {
}

/**
 * Code block followed by another unknown tag.
 * {@code @protected} and then
 * @aftercode  // violation 'Unknown tag 'aftercode'.'
 */
class CodeThenTag {
}

/**
 * Literal tag with annotation inside, then unknown tag outside.
 * {@literal @InsideLiteral} text after
 * @afterliteral  // violation 'Unknown tag 'afterliteral'.'
 */
class LiteralThenTag {
}
