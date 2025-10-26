///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsLexer;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree for the javadoc grammar.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html">
 *     javadoc - The Java API Documentation Generator</a>
 */
public final class JavadocCommentsTokenTypes {

    /**
     * Root node of any Javadoc comment.
     *
     * <p><b>Tree for example:</b></p>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
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
     * Leading asterisk used to format Javadoc lines.
     */
    public static final int LEADING_ASTERISK = JavadocCommentsLexer.LEADING_ASTERISK;

    /**
     * Newline character in a Javadoc comment.
     */
    public static final int NEWLINE = JavadocCommentsLexer.NEWLINE;

    /**
     * Plain text content within a Javadoc comment.
     */
    public static final int TEXT = JavadocCommentsLexer.TEXT;

    // Block tags

    /**
     * General block tag (e.g. {@code @param}, {@code @return}).
     */
    public static final int JAVADOC_BLOCK_TAG = JavadocCommentsLexer.JAVADOC_BLOCK_TAG;

    /**
     * At-sign {@code @} that starts a block tag.
     */
    public static final int AT_SIGN = JavadocCommentsLexer.AT_SIGN;

    /**
     * {@code @author} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @author name.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--AUTHOR_BLOCK_TAG -> AUTHOR_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> author
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  name.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int AUTHOR_BLOCK_TAG = JavadocCommentsLexer.AUTHOR_BLOCK_TAG;

    /**
     * {@code @deprecated} block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @deprecated deprecated text.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--DEPRECATED_BLOCK_TAG -> DEPRECATED_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> deprecated
     *     `--DESCRIPTION -> DESCRIPTION
     *         `--TEXT ->  deprecated text.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int DEPRECATED_BLOCK_TAG = JavadocCommentsLexer.DEPRECATED_BLOCK_TAG;

    /**
     * {@code @param} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *  <li>{@link #PARAMETER_NAME}</li>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @param value The parameter of method.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--PARAM_BLOCK_TAG -> PARAM_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> param
     *    |--TEXT ->
     *    |--PARAMETER_NAME -> value
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  The parameter of method.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int PARAM_BLOCK_TAG = JavadocCommentsLexer.PARAM_BLOCK_TAG;

    /**
     * {@code @return} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @return The return of method.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--RETURN_BLOCK_TAG -> RETURN_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> return
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  The return of method.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int RETURN_BLOCK_TAG = JavadocCommentsLexer.RETURN_BLOCK_TAG;

    /**
     * {@code @throws} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *  <li>{@link #IDENTIFIER} - the exception class</li>
     *  <li>{@link #DESCRIPTION} - description</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @throws IOException if an I/O error occurs}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--THROWS_BLOCK_TAG -> THROWS_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> throws
     *     |--TEXT ->
     *     |--IDENTIFIER -> IOException
     *     `--DESCRIPTION -> DESCRIPTION
     *         `--TEXT ->  if an I/O error occurs
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int THROWS_BLOCK_TAG = JavadocCommentsLexer.THROWS_BLOCK_TAG;

    /**
     * {@code @exception} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *  <li>{@link #IDENTIFIER}</li>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @exception FileNotFoundException when file is not found.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--EXCEPTION_BLOCK_TAG -> EXCEPTION_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> exception
     *    |--TEXT ->
     *    |--IDENTIFIER -> FileNotFoundException
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT -> when file is not found.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int EXCEPTION_BLOCK_TAG = JavadocCommentsLexer.EXCEPTION_BLOCK_TAG;

    /**
     * {@code @since} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @since 1.0}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--SINCE_BLOCK_TAG -> SINCE_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> since
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  1.0
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int SINCE_BLOCK_TAG = JavadocCommentsLexer.SINCE_BLOCK_TAG;

    /**
     * {@code @version} Javadoc block tag.
     *
     * <p>This tag has only one argument — {@link #TEXT}:</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @version value}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--VERSION_BLOCK_TAG -> VERSION_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> version
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  value
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int VERSION_BLOCK_TAG = JavadocCommentsLexer.VERSION_BLOCK_TAG;

    /**
     * {@code @see} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have three children:</p>
     * <ol>
     *  <li>{@link #REFERENCE}</li>
     *  <li>{@link #DESCRIPTION}</li>
     *  <li>{@link #HTML_ELEMENT}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @see SomeClass#Field}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--SEE_BLOCK_TAG -> SEE_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> see
     *     |--TEXT ->
     *     `--REFERENCE -> REFERENCE
     *         |--IDENTIFIER -> SomeClass
     *         |--HASH -> #
     *         `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *             `--IDENTIFIER -> Field
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int SEE_BLOCK_TAG = JavadocCommentsLexer.SEE_BLOCK_TAG;

    /**
     * {@code @hidden} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #DESCRIPTION} – optional description text</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @hidden value}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--HIDDEN_BLOCK_TAG -> HIDDEN_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> hidden
     *     `--DESCRIPTION -> DESCRIPTION
     *         `--TEXT ->  value
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int HIDDEN_BLOCK_TAG = JavadocCommentsLexer.HIDDEN_BLOCK_TAG;

    /**
     * {@code @uses} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #IDENTIFIER} – the referenced service type</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @uses com.example.app.MyService}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--USES_BLOCK_TAG -> USES_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> uses
     *    |--TEXT ->
     *    `--IDENTIFIER -> com.example.app.MyService
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int USES_BLOCK_TAG = JavadocCommentsLexer.USES_BLOCK_TAG;

    /**
     * {@code @provides} block tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *  <li>{@link #IDENTIFIER}</li>
     *  <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @provides com.example.MyService with com.example.MyServiceImpl}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--PROVIDES_BLOCK_TAG -> PROVIDES_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> provides
     *    |--TEXT ->
     *    |--IDENTIFIER -> com.example.MyService
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  with com.example.MyServiceImpl
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int PROVIDES_BLOCK_TAG = JavadocCommentsLexer.PROVIDES_BLOCK_TAG;

    /**
     * {@code @serial} block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #DESCRIPTION} – optional description text</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @serial include}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--SERIAL_BLOCK_TAG -> SERIAL_BLOCK_TAG
     *   |--AT_SIGN -> @
     *   |--TAG_NAME -> serial
     *   `--DESCRIPTION -> DESCRIPTION
     *       `--TEXT ->  include
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int SERIAL_BLOCK_TAG = JavadocCommentsLexer.SERIAL_BLOCK_TAG;

    /**
     * {@code @serialData} block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #DESCRIPTION} – optional description text</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @serialData data description value}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--SERIAL_DATA_BLOCK_TAG -> SERIAL_DATA_BLOCK_TAG
     *    |--AT_SIGN -> @
     *    |--TAG_NAME -> serialData
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT ->  data description value
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int SERIAL_DATA_BLOCK_TAG = JavadocCommentsLexer.SERIAL_DATA_BLOCK_TAG;

    /**
     * {@code @serialField} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have three children:</p>
     * <ol>
     *   <li>{@link #IDENTIFIER} – field name</li>
     *   <li>{@link #FIELD_TYPE} – field type</li>
     *   <li>{@link #DESCRIPTION} – field description</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @serialField name String The person's full name.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--SERIAL_FIELD_BLOCK_TAG -> SERIAL_FIELD_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> serialField
     *     |--TEXT ->
     *     |--IDENTIFIER -> name
     *     |--TEXT ->
     *     |--FIELD_TYPE -> String
     *     `--DESCRIPTION -> DESCRIPTION
     *         `--TEXT ->  The person's full name.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int SERIAL_FIELD_BLOCK_TAG = JavadocCommentsLexer.SERIAL_FIELD_BLOCK_TAG;

    /**
     * {@code @customBlock} Javadoc block tag.
     *
     * <p>This type represents any block tag that is not explicitly recognized by Checkstyle,
     * such as a project-specific or malformed tag.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @mycustomtag This is a custom block tag.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     * `--CUSTOM_BLOCK_TAG -> CUSTOM_BLOCK_TAG
     *     |--AT_SIGN -> @
     *     |--TAG_NAME -> customBlock
     *     |--TEXT ->
     *     `--DESCRIPTION -> DESCRIPTION
     *         `--TEXT ->  This is a custom block tag.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int CUSTOM_BLOCK_TAG = JavadocCommentsLexer.CUSTOM_BLOCK_TAG;

    // Inline tags

    /**
     * General inline tag (e.g. {@code @link}).
     */
    public static final int JAVADOC_INLINE_TAG = JavadocCommentsLexer.JAVADOC_INLINE_TAG;

    /**
     * Start of an inline tag  <code>{</code>.
     */
    public static final int JAVADOC_INLINE_TAG_START =
            JavadocCommentsLexer.JAVADOC_INLINE_TAG_START;

    /**
     * End of an inline tag <code>}</code>.
     */
    public static final int JAVADOC_INLINE_TAG_END = JavadocCommentsLexer.JAVADOC_INLINE_TAG_END;

    /**
     * {@code {@code}} Javadoc inline tag.
     *
     * <p>Such Javadoc tag can have no children:</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@code println("Hello");}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     |--CODE_INLINE_TAG -> CODE_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> { @
     *     |--TAG_NAME -> code
     *     |--TEXT ->  println("Hello");
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int CODE_INLINE_TAG = JavadocCommentsLexer.CODE_INLINE_TAG;

    /**
     * {@code {@link}} Javadoc inline tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *   <li>{@link #REFERENCE}</li>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@link Math#max(int, int) label}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * --JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> { @
     *     |--TAG_NAME -> link
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> Math
     *     |   |--HASH -> #
     *     |   `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *     |       |--IDENTIFIER -> max
     *     |       |--LPAREN -> (
     *     |       |--PARAMETER_TYPE_LIST -> PARAMETER_TYPE_LIST
     *     |       |   |--PARAMETER_TYPE -> int
     *     |       |   |--COMMA -> ,
     *     |       |   |--TEXT ->
     *     |       |   `--PARAMETER_TYPE -> int
     *     |       `--RPAREN -> )
     *     |--DESCRIPTION -> DESCRIPTION
     *     |   `--TEXT -> label
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LINK_INLINE_TAG = JavadocCommentsLexer.LINK_INLINE_TAG;

    /**
     * {@code {@linkplain}} Javadoc inline tag.
     *
     * <p>Such Javadoc tag can have two children:</p>
     * <ol>
     *   <li>{@link #REFERENCE}</li>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@linkplain String#indexOf(int, int) label}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * --JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINKPLAIN_INLINE_TAG -> LINKPLAIN_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> { @
     *     |--TAG_NAME -> linkplain
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> String
     *     |   |--HASH -> #
     *     |   `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *     |       |--IDENTIFIER -> indexOf
     *     |       |--LPAREN -> (
     *     |       |--PARAMETER_TYPE_LIST -> PARAMETER_TYPE_LIST
     *     |       |   |--PARAMETER_TYPE -> int
     *     |       |   |--COMMA -> ,
     *     |       |   |--TEXT ->
     *     |       |   `--PARAMETER_TYPE -> int
     *     |       `--RPAREN -> )
     *     |--DESCRIPTION -> DESCRIPTION
     *     |   `--TEXT ->  label
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LINKPLAIN_INLINE_TAG = JavadocCommentsLexer.LINKPLAIN_INLINE_TAG;

    /**
     * {@code {@value}} Javadoc inline tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #REFERENCE}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@value Integer#MAX_VALUE}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * --JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--VALUE_INLINE_TAG -> VALUE_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> { @
     *     |--TAG_NAME -> value
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> Integer
     *     |   |--HASH -> #
     *     |   `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *     |       `--IDENTIFIER -> MAX_VALUE
     *     |--TEXT ->
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int VALUE_INLINE_TAG = JavadocCommentsLexer.VALUE_INLINE_TAG;

    /**
     * {@code {@summary}} inline tag.
     */
    public static final int SUMMARY_INLINE_TAG = JavadocCommentsLexer.SUMMARY_INLINE_TAG;

    /**
     * {@code {@inheritDoc}} inline tag.
     */
    public static final int INHERIT_DOC_INLINE_TAG = JavadocCommentsLexer.INHERIT_DOC_INLINE_TAG;

    /**
     * {@code {@systemProperty}} inline tag.
     */
    public static final int SYSTEM_PROPERTY_INLINE_TAG =
            JavadocCommentsLexer.SYSTEM_PROPERTY_INLINE_TAG;

    /**
     * {@code {@literal}} inline tag.
     */
    public static final int LITERAL_INLINE_TAG = JavadocCommentsLexer.LITERAL_INLINE_TAG;

    /**
     * {@code {@return}} inline tag.
     */
    public static final int RETURN_INLINE_TAG = JavadocCommentsLexer.RETURN_INLINE_TAG;

    /**
     * {@code {@index}} inline tag.
     */
    public static final int INDEX_INLINE_TAG = JavadocCommentsLexer.INDEX_INLINE_TAG;

    /**
     * {@code @snippet} inline tag.
     */
    public static final int SNIPPET_INLINE_TAG = JavadocCommentsLexer.SNIPPET_INLINE_TAG;

    /**
     * Custom or unrecognized inline tag.
     */
    public static final int CUSTOM_INLINE_TAG = JavadocCommentsLexer.CUSTOM_INLINE_TAG;

    // Components

    /**
     * Identifier token.
     */
    public static final int IDENTIFIER = JavadocCommentsLexer.IDENTIFIER;

    /**
     * Hash symbol {@code #} used in references.
     */
    public static final int HASH = JavadocCommentsLexer.HASH;

    /**
     * Left parenthesis {@code ( }.
     */
    public static final int LPAREN = JavadocCommentsLexer.LPAREN;

    /**
     * Right parenthesis {@code ) }.
     */
    public static final int RPAREN = JavadocCommentsLexer.RPAREN;

    /**
     * Comma symbol {@code , }.
     */
    public static final int COMMA = JavadocCommentsLexer.COMMA;

    /**
     * Slash symbol {@code / }.
     */
    public static final int SLASH = JavadocCommentsLexer.SLASH;

    /**
     * Question mark symbol {@code ? }.
     */
    public static final int QUESTION = JavadocCommentsLexer.QUESTION;

    /**
     * Less-than symbol {@code < }.
     */
    public static final int LT = JavadocCommentsLexer.LT;

    /**
     * Greater-than symbol {@code > }.
     */
    public static final int GT = JavadocCommentsLexer.GT;

    /**
     * Keyword {@code extends} in type parameters.
     */
    public static final int EXTENDS = JavadocCommentsLexer.EXTENDS;

    /**
     * Keyword {@code super} in type parameters.
     */
    public static final int SUPER = JavadocCommentsLexer.SUPER;

    /**
     * Parameter type reference.
     */
    public static final int PARAMETER_TYPE = JavadocCommentsLexer.PARAMETER_TYPE;

    /**
     * General reference within Javadoc.
     */
    public static final int REFERENCE = JavadocCommentsLexer.REFERENCE;

    /**
     * Type name reference.
     */
    public static final int TYPE_NAME = JavadocCommentsLexer.TYPE_NAME;

    /**
     * Member reference (e.g. method or field).
     */
    public static final int MEMBER_REFERENCE = JavadocCommentsLexer.MEMBER_REFERENCE;

    /**
     * List of parameter types in a reference.
     */
    public static final int PARAMETER_TYPE_LIST = JavadocCommentsLexer.PARAMETER_TYPE_LIST;

    /**
     * Type arguments in generics.
     */
    public static final int TYPE_ARGUMENTS = JavadocCommentsLexer.TYPE_ARGUMENTS;

    /**
     * Single type argument in generics.
     */
    public static final int TYPE_ARGUMENT = JavadocCommentsLexer.TYPE_ARGUMENT;

    /**
     * Description part of a Javadoc tag.
     */
    public static final int DESCRIPTION = JavadocCommentsLexer.DESCRIPTION;

    /**
     * Format specifier inside Javadoc content.
     */
    public static final int FORMAT_SPECIFIER = JavadocCommentsLexer.FORMAT_SPECIFIER;

    /**
     * Attribute name in a {@code @snippet}.
     */
    public static final int SNIPPET_ATTR_NAME = JavadocCommentsLexer.SNIPPET_ATTR_NAME;

    /**
     * Equals sign {@code = }.
     */
    public static final int EQUALS = JavadocCommentsLexer.EQUALS;

    /**
     * Value assigned to an attribute.
     */
    public static final int ATTRIBUTE_VALUE = JavadocCommentsLexer.ATTRIBUTE_VALUE;

    /**
     * Colon symbol {@code : }.
     */
    public static final int COLON = JavadocCommentsLexer.COLON;

    /**
     * Term used in {@code {@index}} tag.
     */
    public static final int INDEX_TERM = JavadocCommentsLexer.INDEX_TERM;

    /**
     * Single snippet attribute.
     */
    public static final int SNIPPET_ATTRIBUTE = JavadocCommentsLexer.SNIPPET_ATTRIBUTE;

    /**
     * Collection of snippet attributes.
     */
    public static final int SNIPPET_ATTRIBUTES = JavadocCommentsLexer.SNIPPET_ATTRIBUTES;

    /**
     * Body content of a {@code @snippet}.
     */
    public static final int SNIPPET_BODY = JavadocCommentsLexer.SNIPPET_BODY;

    /**
     * Field type reference.
     */
    public static final int FIELD_TYPE = JavadocCommentsLexer.FIELD_TYPE;

    /**
     * Parameter name reference.
     */
    public static final int PARAMETER_NAME = JavadocCommentsLexer.PARAMETER_NAME;

    /**
     * String literal inside Javadoc.
     */
    public static final int STRING_LITERAL = JavadocCommentsLexer.STRING_LITERAL;

    // HTML

    /**
     * General HTML element.
     */
    public static final int HTML_ELEMENT = JavadocCommentsLexer.HTML_ELEMENT;

    /**
     * Void HTML element (self-closing).
     */
    public static final int VOID_ELEMENT = JavadocCommentsLexer.VOID_ELEMENT;

    /**
     * Content inside an HTML element.
     */
    public static final int HTML_CONTENT = JavadocCommentsLexer.HTML_CONTENT;

    /**
     * Single HTML attribute.
     */
    public static final int HTML_ATTRIBUTE = JavadocCommentsLexer.HTML_ATTRIBUTE;

    /**
     * List of HTML attributes.
     */
    public static final int HTML_ATTRIBUTES = JavadocCommentsLexer.HTML_ATTRIBUTES;

    /**
     * Start of an HTML tag.
     */
    public static final int HTML_TAG_START = JavadocCommentsLexer.HTML_TAG_START;

    /**
     * End of an HTML tag.
     */
    public static final int HTML_TAG_END = JavadocCommentsLexer.HTML_TAG_END;

    /**
     * Opening tag delimiter {@code < }.
     */
    public static final int TAG_OPEN = JavadocCommentsLexer.TAG_OPEN;

    /**
     * HTML tag name.
     */
    public static final int TAG_NAME = JavadocCommentsLexer.TAG_NAME;

    /**
     * Closing tag delimiter {@code > }.
     */
    public static final int TAG_CLOSE = JavadocCommentsLexer.TAG_CLOSE;

    /**
     * Self-closing tag delimiter {@code /> }.
     */
    public static final int TAG_SLASH_CLOSE = JavadocCommentsLexer.TAG_SLASH_CLOSE;

    /**
     * Slash symbol inside a closing tag.
     */
    public static final int TAG_SLASH = JavadocCommentsLexer.TAG_SLASH;

    /**
     * Attribute name inside an HTML tag.
     */
    public static final int TAG_ATTR_NAME = JavadocCommentsLexer.TAG_ATTR_NAME;

    /**
     * Full HTML comment.
     */
    public static final int HTML_COMMENT = JavadocCommentsLexer.HTML_COMMENT;

    /**
     * Opening part of an HTML comment.
     */
    public static final int HTML_COMMENT_START = JavadocCommentsLexer.HTML_COMMENT_START;

    /**
     * Closing part of an HTML comment.
     */
    public static final int HTML_COMMENT_END = JavadocCommentsLexer.HTML_COMMENT_END;

    /**
     * Content inside an HTML comment.
     */
    public static final int HTML_COMMENT_CONTENT = JavadocCommentsLexer.HTML_COMMENT_CONTENT;

    /** Empty private constructor of the current class. */
    private JavadocCommentsTokenTypes() {
    }
}
