package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsLexer;

public final class JavadocCommentsTokenTypes {

    /**
     * Root node of any Javadoc comment.
     *
     * <p><b>Tree for example:</b></p>
     * <pre>{@code
     * JAVADOC -> JAVADOC
     * |--LEADING_ASTERISK -> *
     * |--NEWLINE -> \n
     * |--LEADING_ASTERISK -> *
     * |--NEWLINE -> \n
     * |--LEADING_ASTERISK -> *
     * `--NEWLINE -> \n
     * }</pre>
     */
    public static final int JAVADOC_CONTENT = JavadocCommentsLexer.JAVADOC;

    /**
     * Leading asterisk.
     */
    public static final int LEADING_ASTERISK = JavadocCommentsLexer.LEADING_ASTERISK;

    /**
     * Newline symbol - '\n'.
     */
    public static final int NEWLINE = JavadocCommentsLexer.NEWLINE;

    /**
     * Token representing plain text content within a Javadoc comment.
     */
    public static final int TEXT = JavadocCommentsLexer.TEXT;

    // Block tags
    public static final int JAVADOC_BLOCK_TAG = JavadocCommentsLexer.JAVADOC_BLOCK_TAG;
    public static final int AT_SIGN = JavadocCommentsLexer.AT_SIGN;
    public static final int AUTHOR_BLOCK_TAG = JavadocCommentsLexer.AUTHOR_BLOCK_TAG;
    public static final int DEPRECATED_BLOCK_TAG = JavadocCommentsLexer.DEPRECATED_BLOCK_TAG;
    public static final int PARAM_BLOCK_TAG = JavadocCommentsLexer.PARAM_BLOCK_TAG;
    public static final int RETURN_BLOCK_TAG = JavadocCommentsLexer.RETURN_BLOCK_TAG;
    public static final int THROWS_BLOCK_TAG = JavadocCommentsLexer.THROWS_BLOCK_TAG;
    public static final int EXCEPTION_BLOCK_TAG = JavadocCommentsLexer.EXCEPTION_BLOCK_TAG;
    public static final int SINCE_BLOCK_TAG = JavadocCommentsLexer.SINCE_BLOCK_TAG;
    public static final int VERSION_BLOCK_TAG = JavadocCommentsLexer.VERSION_BLOCK_TAG;
    public static final int SEE_BLOCK_TAG = JavadocCommentsLexer.SEE_BLOCK_TAG;
    public static final int HIDDEN_BLOCK_TAG = JavadocCommentsLexer.HIDDEN_BLOCK_TAG;
    public static final int USES_BLOCK_TAG = JavadocCommentsLexer.USES_BLOCK_TAG;
    public static final int PROVIDES_BLOCK_TAG = JavadocCommentsLexer.PROVIDES_BLOCK_TAG;
    public static final int SERIAL_BLOCK_TAG = JavadocCommentsLexer.SERIAL_BLOCK_TAG;
    public static final int SERIAL_DATA_BLOCK_TAG = JavadocCommentsLexer.SERIAL_DATA_BLOCK_TAG;
    public static final int SERIAL_FIELD_BLOCK_TAG = JavadocCommentsLexer.SERIAL_FIELD_BLOCK_TAG;
    public static final int CUSTOM_BLOCK_TAG = JavadocCommentsLexer.CUSTOM_BLOCK_TAG;


    // inline tags
    public static final int JAVADOC_INLINE_TAG = JavadocCommentsLexer.JAVADOC_INLINE_TAG;
    public static final int JAVADOC_INLINE_TAG_START = JavadocCommentsLexer.JAVADOC_INLINE_TAG_START;
    public static final int JAVADOC_INLINE_TAG_END = JavadocCommentsLexer.JAVADOC_INLINE_TAG_END;
    public static final int CODE_INLINE_TAG= JavadocCommentsLexer.CODE_INLINE_TAG;
    public static final int LINK_INLINE_TAG= JavadocCommentsLexer.LINK_INLINE_TAG;
    public static final int LINKPLAIN_INLINE_TAG = JavadocCommentsLexer.LINKPLAIN_INLINE_TAG;
    public static final int VALUE_INLINE_TAG= JavadocCommentsLexer.VALUE_INLINE_TAG;
    public static final int SUMMARY_INLINE_TAG = JavadocCommentsLexer.SUMMARY_INLINE_TAG;
    public static final int INHERIT_DOC_INLINE_TAG = JavadocCommentsLexer.INHERIT_DOC_INLINE_TAG;
    public static final int SYSTEM_PROPERTY_INLINE_TAG = JavadocCommentsLexer.SYSTEM_PROPERTY_INLINE_TAG;
    public static final int LITERAL_INLINE_TAG= JavadocCommentsLexer.LITERAL_INLINE_TAG;
    public static final int RETURN_INLINE_TAG = JavadocCommentsLexer.RETURN_INLINE_TAG;
    public static final int INDEX_INLINE_TAG = JavadocCommentsLexer.INDEX_INLINE_TAG;
    public static final int SNIPPET_INLINE_TAG = JavadocCommentsLexer.SNIPPET_INLINE_TAG;
    public static final int CUSTOM_INLINE_TAG = JavadocCommentsLexer.CUSTOM_INLINE_TAG;

    // components
    public static final int IDENTIFIER = JavadocCommentsLexer.IDENTIFIER;
    public static final int HASH = JavadocCommentsLexer.HASH;
    public static final int LPAREN = JavadocCommentsLexer.LPAREN;
    public static final int RPAREN = JavadocCommentsLexer.RPAREN;
    public static final int COMMA = JavadocCommentsLexer.COMMA;
    public static final int SLASH = JavadocCommentsLexer.SLASH;
    public static final int QUESTION = JavadocCommentsLexer.QUESTION;
    public static final int LT = JavadocCommentsLexer.LT;
    public static final int GT = JavadocCommentsLexer.GT;
    public static final int EXTENDS = JavadocCommentsLexer.EXTENDS;
    public static final int SUPER = JavadocCommentsLexer.SUPER;
    public static final int PARAMETER_TYPE = JavadocCommentsLexer.PARAMETER_TYPE;
    public static final int REFERENCE = JavadocCommentsLexer.REFERENCE;
    public static final int TYPE_NAME = JavadocCommentsLexer.TYPE_NAME;
    public static final int MEMBER_REFERENCE = JavadocCommentsLexer.MEMBER_REFERENCE;
    public static final int PARAMETER_TYPE_LIST = JavadocCommentsLexer.PARAMETER_TYPE_LIST;
    public static final int TYPE_ARGUMENTS = JavadocCommentsLexer.TYPE_ARGUMENTS;
    public static final int TYPE_ARGUMENT = JavadocCommentsLexer.TYPE_ARGUMENT;
    public static final int DESCRIPTION = JavadocCommentsLexer.DESCRIPTION;
    public static final int FORMAT_SPECIFIER = JavadocCommentsLexer.FORMAT_SPECIFIER;
    public static final int SNIPPET_ATTR_NAME = JavadocCommentsLexer.SNIPPET_ATTR_NAME;
    public static final int EQUALS = JavadocCommentsLexer.EQUALS;
    public static final int ATTRIBUTE_VALUE = JavadocCommentsLexer.ATTRIBUTE_VALUE;
    public static final int COLON = JavadocCommentsLexer.COLON;
    public static final int INDEX_TERM = JavadocCommentsLexer.INDEX_TERM;
    public static final int SNIPPET_ATTRIBUTE = JavadocCommentsLexer.SNIPPET_ATTRIBUTE;
    public static final int SNIPPET_ATTRIBUTES = JavadocCommentsLexer.SNIPPET_ATTRIBUTES;
    public static final int SNIPPET_BODY = JavadocCommentsLexer.SNIPPET_BODY;
    public static final int FIELD_TYPE = JavadocCommentsLexer.FIELD_TYPE;
    public static final int PARAMETER_NAME = JavadocCommentsLexer.PARAMETER_NAME;
    public static final int STRING_LITERAL = JavadocCommentsLexer.STRING_LITERAL;

    // HTML
    public static final int HTML_ELEMENT = JavadocCommentsLexer.HTML_ELEMENT;
    public static final int VOID_ELEMENT = JavadocCommentsLexer.VOID_ELEMENT;
    public static final int HTML_CONTENT = JavadocCommentsLexer.HTML_CONTENT;
    public static final int HTML_ATTRIBUTE = JavadocCommentsLexer.HTML_ATTRIBUTE;
    public static final int HTML_ATTRIBUTES = JavadocCommentsLexer.HTML_ATTRIBUTES;
    public static final int HTML_TAG_START = JavadocCommentsLexer.HTML_TAG_START;
    public static final int HTML_TAG_END = JavadocCommentsLexer.HTML_TAG_END;
    public static final int TAG_OPEN = JavadocCommentsLexer.TAG_OPEN;
    public static final int TAG_NAME = JavadocCommentsLexer.TAG_NAME;
    public static final int TAG_CLOSE = JavadocCommentsLexer.TAG_CLOSE;
    public static final int TAG_SLASH_CLOSE = JavadocCommentsLexer.TAG_SLASH_CLOSE;
    public static final int TAG_SLASH = JavadocCommentsLexer.TAG_SLASH;
    public static final int TAG_ATTR_NAME = JavadocCommentsLexer.TAG_ATTR_NAME;
    public static final int HTML_COMMENT = JavadocCommentsLexer.HTML_COMMENT;
    public static final int HTML_COMMENT_START = JavadocCommentsLexer.HTML_COMMENT_START;
    public static final int HTML_COMMENT_END = JavadocCommentsLexer.HTML_COMMENT_END;
    public static final int HTML_COMMENT_CONTENT = JavadocCommentsLexer.HTML_COMMENT_CONTENT;
    
    /** Empty private constructor of the current class. */
    private JavadocCommentsTokenTypes() {
    }
}
