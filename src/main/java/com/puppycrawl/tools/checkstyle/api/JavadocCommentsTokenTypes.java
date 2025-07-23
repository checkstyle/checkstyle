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
    public static final int JAVADOC = JavadocCommentsLexer.JAVADOC;

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

    /**
     * Whitespace or tab ('\t') symbol.
     */
    public static final int WS = JavadocCommentsLexer.WS;


    // inline tags
    public static final int JAVADOC_INLINE_TAG = JavadocCommentsLexer.JAVADOC_INLINE_TAG;
    public static final int JAVADOC_INLINE_TAG_START = JavadocCommentsLexer.JAVADOC_INLINE_TAG_START;
    public static final int JAVADOC_INLINE_TAG_END = JavadocCommentsLexer.JAVADOC_INLINE_TAG_END;
    public static final int CODE = JavadocCommentsLexer.CODE;
    public static final int LINK = JavadocCommentsLexer.LINK;
    public static final int LINKPLAIN = JavadocCommentsLexer.LINKPLAIN;
    public static final int VALUE = JavadocCommentsLexer.VALUE;
    public static final int INHERIT_DOC = JavadocCommentsLexer.INHERIT_DOC;
    public static final int SYSTEM_PROPERTY = JavadocCommentsLexer.SYSTEM_PROPERTY;
    public static final int LITERAL = JavadocCommentsLexer.LITERAL;
    public static final int RETURN = JavadocCommentsLexer.RETURN;
    public static final int INDEX = JavadocCommentsLexer.INDEX;
    public static final int INDEX_TERM = JavadocCommentsLexer.INDEX_TERM;
    public static final int SNIPPET = JavadocCommentsLexer.SNIPPET;
    public static final int SNIPPET_ATTRIBUTE = JavadocCommentsLexer.SNIPPET_ATTRIBUTE;
    public static final int SNIPPET_ATTRIBUTES = JavadocCommentsLexer.SNIPPET_ATTRIBUTES;
    public static final int SNIPPET_BODY = JavadocCommentsLexer.SNIPPET_BODY;

    public static final int CUSTOM_NAME = JavadocCommentsLexer.CUSTOM_NAME;

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
}
