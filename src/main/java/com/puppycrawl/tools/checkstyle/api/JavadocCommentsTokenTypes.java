///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * /**
     *  * This is a Javadoc line.
     *  * /
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * --BLOCK_COMMENT_BEGIN -> /**
     *    |--COMMENT_CONTENT -> *\r\n * This is a Javadoc line.\r\n
     *    |   `--JAVADOC_CONTENT -> JAVADOC_CONTENT
     *    |       |--NEWLINE -> \r\n
     *    |       |--LEADING_ASTERISK ->  *
     *    |       |--TEXT ->  This is a Javadoc line.
     *    |       |--NEWLINE -> \r\n
     *    |       `--TEXT ->
     * `   --BLOCK_COMMENT_END -> *
     * }</pre>
     */
    public static final int LEADING_ASTERISK = JavadocCommentsLexer.LEADING_ASTERISK;

    /**
     * Newline character in a Javadoc comment.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * /**
     * * This is a Javadoc line.
     * * /
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * --BLOCK_COMMENT_BEGIN -> /**
     * |--COMMENT_CONTENT -> *\r\n * This is a Javadoc line.\r\n
     * |   `--JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |       |--NEWLINE -> \r\n
     * |       |--LEADING_ASTERISK ->  *
     * |       |--TEXT ->  This is a Javadoc line.
     * |       |--NEWLINE -> \r\n
     * |       `--TEXT ->
     * `   --BLOCK_COMMENT_END -> *
     * }</pre>
     *
     * @see #JAVADOC_CONTENT
     */
    public static final int NEWLINE = JavadocCommentsLexer.NEWLINE;

    /**
     * Plain text content within a Javadoc comment.
     *
     * <p>This node represents any plain text that appears in a Javadoc comment,
     * including spaces and punctuation.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * /**
     *  * This is plain text content.
     *  * /
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * --BLOCK_COMMENT_BEGIN -> /**
     *    |--COMMENT_CONTENT -> *\r\n * This is plain text content.\r\n
     *    |   `--JAVADOC_CONTENT -> JAVADOC_CONTENT
     *    |       |--NEWLINE -> \r\n
     *    |       |--LEADING_ASTERISK ->  *
     *    |       |--TEXT ->  This is plain text content.
     *    |       |--NEWLINE -> \r\n
     *    |       `--TEXT ->
     *    `--BLOCK_COMMENT_END -> * /
     * }</pre>
     *
     * @see #JAVADOC_CONTENT
     */
    public static final int TEXT = JavadocCommentsLexer.TEXT;

    // Block tags

    /**
     * General block tag (e.g. {@code @param}, {@code @return}).
     */
    public static final int JAVADOC_BLOCK_TAG = JavadocCommentsLexer.JAVADOC_BLOCK_TAG;

    /**
     * At-sign {@code @} that starts a block tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @author name}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--AUTHOR_BLOCK_TAG -> AUTHOR_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> author
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  name
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int AT_SIGN = JavadocCommentsLexer.AT_SIGN;

    /**
     * {@code @author} Javadoc block tag.
     *
     * <p>Such Javadoc tag can have one child:</p>
     * <ol>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @author name.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--AUTHOR_BLOCK_TAG -> AUTHOR_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> author
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  name.
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
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @deprecated deprecated text.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--DEPRECATED_BLOCK_TAG -> DEPRECATED_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> deprecated
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  deprecated text.
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
     *   <li>{@link #PARAMETER_NAME}</li>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @param value The parameter of method.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--PARAM_BLOCK_TAG -> PARAM_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> param
     *         |--TEXT ->
     *         |--PARAMETER_NAME -> value
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  The parameter of method.
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
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @return The return of method.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--RETURN_BLOCK_TAG -> RETURN_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> return
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  The return of method.
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
     *   <li>{@link #IDENTIFIER} - the exception class</li>
     *   <li>{@link #DESCRIPTION} - description</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @throws IOException if an I/O error occurs}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--THROWS_BLOCK_TAG -> THROWS_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> throws
     *         |--TEXT ->
     *         |--IDENTIFIER -> IOException
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  if an I/O error occurs
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
     *   <li>{@link #IDENTIFIER}</li>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @exception FileNotFoundException when file is not found.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--EXCEPTION_BLOCK_TAG -> EXCEPTION_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> exception
     *         |--TEXT ->
     *         |--IDENTIFIER -> FileNotFoundException
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  when file is not found.
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--SINCE_BLOCK_TAG -> SINCE_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> since
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  1.0
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
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--VERSION_BLOCK_TAG -> VERSION_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> version
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  value
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
     *   <li>{@link #REFERENCE}</li>
     *   <li>{@link #DESCRIPTION}</li>
     *   <li>{@link #HTML_ELEMENT}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @see SomeClass#Field}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--SEE_BLOCK_TAG -> SEE_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> see
     *         |--TEXT ->
     *         `--REFERENCE -> REFERENCE
     *             |--IDENTIFIER -> SomeClass
     *             |--HASH -> #
     *             `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *                 `--IDENTIFIER -> Field
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--HIDDEN_BLOCK_TAG -> HIDDEN_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> hidden
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  value
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--USES_BLOCK_TAG -> USES_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> uses
     *         |--TEXT ->
     *         `--IDENTIFIER -> com.example.app.MyService
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
     *   <li>{@link #IDENTIFIER}</li>
     *   <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * @provides com.example.MyService with com.example.MyServiceImpl}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--PROVIDES_BLOCK_TAG -> PROVIDES_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> provides
     *         |--TEXT ->
     *         |--IDENTIFIER -> com.example.MyService
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  with com.example.MyServiceImpl
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--SERIAL_BLOCK_TAG -> SERIAL_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> serial
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  include
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--SERIAL_DATA_BLOCK_TAG -> SERIAL_DATA_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> serialData
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  data description value
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
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--SERIAL_FIELD_BLOCK_TAG -> SERIAL_FIELD_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> serialField
     *         |--TEXT ->
     *         |--IDENTIFIER -> name
     *         |--TEXT ->
     *         |--FIELD_TYPE -> String
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  The person's full name.
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
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_BLOCK_TAG -> JAVADOC_BLOCK_TAG
     *     `--CUSTOM_BLOCK_TAG -> CUSTOM_BLOCK_TAG
     *         |--AT_SIGN -> @
     *         |--TAG_NAME -> mycustomtag
     *         `--DESCRIPTION -> DESCRIPTION
     *             `--TEXT ->  This is a custom block tag.
     * }</pre>
     *
     * @see #JAVADOC_BLOCK_TAG
     */
    public static final int CUSTOM_BLOCK_TAG = JavadocCommentsLexer.CUSTOM_BLOCK_TAG;

    // Inline tags

    /**
     * General inline tag (e.g. {@code @link}).
     *
     * <p>Such Javadoc tag can have these children:</p>
     * <ol>
     * <li>{@link #CODE_INLINE_TAG}</li>
     * <li>{@link #LINK_INLINE_TAG}</li>
     * <li>{@link #VALUE_INLINE_TAG}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * /**
     * * {@code code}
     * &#42;/
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--TEXT -> /**
     * |--NEWLINE -> \n
     * |--LEADING_ASTERISK ->   *
     * |--TEXT ->
     * |--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * |   `--CODE_INLINE_TAG -> CODE_INLINE_TAG
     * |       |--JAVADOC_INLINE_TAG_START -> { @
     * |       |--TAG_NAME -> code
     * |       |--TEXT ->   code
     * |       `--JAVADOC_INLINE_TAG_END -> }
     * |--NEWLINE -> \n
     * |--LEADING_ASTERISK ->   *
     * |--TEXT -> /
     * |--NEWLINE -> \n
     * |--TEXT -> public class Test {}
     * `--NEWLINE -> \n
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
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
     * Inline {@code {@summary ...}} tag inside Javadoc.
     *
     * <p>This node represents an inline {@code {@summary ...}} tag used to provide a
     * short summary description within a Javadoc sentence.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * Example showing {@summary This is a short summary.}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->  Example showing
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--SUMMARY_INLINE_TAG -> SUMMARY_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> summary
     *         |--DESCRIPTION -> DESCRIPTION
     *         |   `--TEXT ->  This is a short summary.
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */

    public static final int SUMMARY_INLINE_TAG = JavadocCommentsLexer.SUMMARY_INLINE_TAG;

    /**
     * {@code {@inheritDoc}} inline tag.
     *
     * <p>This node models the inline {@code {@inheritDoc}} tag that instructs Javadoc
     * to inherit documentation from the corresponding element in a parent class or interface.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@inheritDoc}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK ->      *
     * |--TEXT ->
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--INHERIT_DOC_INLINE_TAG -> INHERIT_DOC_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> inheritDoc
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int INHERIT_DOC_INLINE_TAG = JavadocCommentsLexer.INHERIT_DOC_INLINE_TAG;

    /**
     * {@code {@systemProperty}} inline tag.
     *
     * <p>Such Javadoc tag is used to reference a system property.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * This method uses {@systemProperty user.home} system property.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK ->      *
     * |--TEXT ->  This method uses
     * |--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * |   `--SYSTEM_PROPERTY_INLINE_TAG -> SYSTEM_PROPERTY_INLINE_TAG
     * |       |--JAVADOC_INLINE_TAG_START -> { @
     * |       |--TAG_NAME -> systemProperty
     * |       |--TEXT ->
     * |       |--IDENTIFIER -> user.home
     * |       `--JAVADOC_INLINE_TAG_END -> }
     * |--TEXT ->  system property.
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int SYSTEM_PROPERTY_INLINE_TAG =
            JavadocCommentsLexer.SYSTEM_PROPERTY_INLINE_TAG;

    /**
     * {@code {@literal}} inline tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * {@literal @Override}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--LITERAL_INLINE_TAG -> LITERAL_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> literal
     *         |--TEXT ->  @Override
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LITERAL_INLINE_TAG = JavadocCommentsLexer.LITERAL_INLINE_TAG;

    /**
     * Inline {@code return} tag inside Javadoc.
     *
     * <p>This node represents an inline {@code {@return ...}} tag used to
     * describe the returned value directly within a Javadoc sentence.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code Example showing result {@return The computed value.}}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->  Example showing result
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--RETURN_INLINE_TAG -> RETURN_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> return
     *         |--DESCRIPTION -> DESCRIPTION
     *         |   `--TEXT ->  The computed value.
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */

    public static final int RETURN_INLINE_TAG = JavadocCommentsLexer.RETURN_INLINE_TAG;

    /**
     * {@code {@index}} inline tag.
     *
     * <p>This node represents an inline {@code {@index ...}} tag used to mark an
     * index term inside a Javadoc sentence.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * Example showing {@index keyword description of the index term}.}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->  Example showing
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--INDEX_INLINE_TAG -> INDEX_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> index
     *         |--TEXT ->
     *         |--INDEX_TERM -> keyword
     *         |--DESCRIPTION -> DESCRIPTION
     *         |   `--TEXT ->  description of the index term
     *         `--JAVADOC_INLINE_TAG_END -> }
     * |--TEXT -> .
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */

    public static final int INDEX_INLINE_TAG = JavadocCommentsLexer.INDEX_INLINE_TAG;

    /**
     * {@code @snippet} inline tag.
     *
     * <p>This node represents an inline { @code { @snippet :}} tag used to embed
     * code snippets directly inside a Javadoc sentence.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{ @code * Example showing { @snippet :java |
     * System.out.println("hello");
     * }}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT -> Example showing
     * `--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--SNIPPET_INLINE_TAG -> SNIPPET_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--COLON -> :
     *         |--SNIPPET_BODY -> SNIPPET_BODY
     *         |   |--TEXT -> java |
     *         |   |--NEWLINE -> \n
     *         |   |--LEADING_ASTERISK -> *
     *         |   |--TEXT -> System.out.println("hello");
     *         |   |--NEWLINE -> \n
     *         |   |--LEADING_ASTERISK -> *
     *         |   `--TEXT ->
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int SNIPPET_INLINE_TAG = JavadocCommentsLexer.SNIPPET_INLINE_TAG;

    /**
     * {@code @custom} inline tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * Example showing {@custom This is a Custom Inline Tag}.}</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * |--LEADING_ASTERISK ->      *
     * |--TEXT ->  Example showing
     * |--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * |   `--CUSTOM_INLINE_TAG -> CUSTOM_INLINE_TAG
     * |       |--JAVADOC_INLINE_TAG_START -> { @
     * |       |--TAG_NAME -> custom
     * |       |--DESCRIPTION -> DESCRIPTION
     * |       |   `--TEXT ->  This is a Custom Inline Tag
     * |       `--JAVADOC_INLINE_TAG_END -> }
     * |--TEXT -> .
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
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
     * {@code extends} keyword inside type arguments of a Javadoc inline tag.
     *
     * <p>This node represents the {@code extends} bound used inside a
     * parameterized type within an inline Javadoc tag.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * * {@link java.util.List&lt;? extends Number&gt; list of any subtype of Number}
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * |--JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     *     `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START -> { @
     *         |--TAG_NAME -> link
     *         |--TEXT ->
     *         |--REFERENCE -> REFERENCE
     *         |   |--IDENTIFIER -> java.util.List
     *         |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *         |       |--LT -> <
     *         |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *         |       |   |--QUESTION -> ?
     *         |       |   |--TEXT ->
     *         |       |   |--EXTENDS -> extends
     *         |       |   |--TEXT ->
     *         |       |   `--IDENTIFIER -> Number
     *         |       `--GT -> >
     *         |--DESCRIPTION -> DESCRIPTION
     *         |   `--TEXT ->  list of any subtype of Number
     *         `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int EXTENDS = JavadocCommentsLexer.EXTENDS;

    /**
     * {@code SUPER} represents the {@code super} keyword inside a generic
     * wildcard bound (e.g., {@code ? super Number}).
     *
     * <p><b>Example:</b> {@code {@link java.util.List <? super Integer> list}
     * of any supertype of Integer}</p>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> { @
     *     |--TAG_NAME -> link
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> java.util.List
     *     |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *     |       |--LT -> <
     *     |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *     |       |   |--QUESTION -> ?
     *     |       |   |--TEXT ->
     *     |       |   |--SUPER -> super
     *     |       |   |--TEXT ->
     *     |       |   `--IDENTIFIER -> Integer
     *     |       `--GT -> >
     *     |--DESCRIPTION -> DESCRIPTION
     *     |   `--TEXT ->  list of any supertype of Integer
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #PARAMETER_TYPE
     */
    public static final int SUPER = JavadocCommentsLexer.SUPER;

    /**
     * {@code PARAMETER_TYPE} Parameter type reference.
     *
     * <p>Represents a type used in a method parameter.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code {@link java.util.List#add(Object)}} </pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> &#123;@
     *     |--TAG_NAME -> link
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> List
     *     |   |--HASH -> #
     *     |   `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *     |       |--IDENTIFIER -> add
     *     |       |--LPAREN -> (
     *     |       |--PARAMETER_TYPE_LIST -> PARAMETER_TYPE_LIST
     *     |       |   `--PARAMETER_TYPE -> Object
     *     |       `--RPAREN -> )
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #REFERENCE
     */
    public static final int PARAMETER_TYPE = JavadocCommentsLexer.PARAMETER_TYPE;

    /**
     * {@code REFERENCE} General reference within Javadoc.
     *
     * <p>Represents the target of an inline reference tag such as
     * {@code {@link String#length()}}.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * {@link String#length()}
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * |--LINK_INLINE_TAG -> LINK_INLINE_TAG
     * |   |--JAVADOC_INLINE_TAG_START -> &#123;@
     * |   |--TAG_NAME -> link
     * |   `--REFERENCE -> String#length()
     * }</pre>
     *
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int REFERENCE = JavadocCommentsLexer.REFERENCE;

    /**
     * {@code MEMBER_REFERENCE} Member reference (method or field).
     *
     * <p>Represents a field or method in a type reference.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * {@link String#length()}
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> &#123;@
     *     |--TAG_NAME -> link
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> String
     *     |   |--HASH -> #
     *     |   `--MEMBER_REFERENCE -> MEMBER_REFERENCE
     *     |       |--IDENTIFIER -> length
     *     |       |--LPAREN -> (
     *     |       `--RPAREN -> )
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #REFERENCE
     */
    public static final int MEMBER_REFERENCE = JavadocCommentsLexer.MEMBER_REFERENCE;

    /**
     * {@code PARAMETER_TYPE_LIST} represents the list of parameter types inside a
     * member reference within a Javadoc inline {@code @link} tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * {@link Math#max(int, int)}
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> {\@
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
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #PARAMETER_TYPE
     */
    public static final int PARAMETER_TYPE_LIST = JavadocCommentsLexer.PARAMETER_TYPE_LIST;

    /**
     * {@code TYPE_ARGUMENTS} Type arguments in generics.
     *
     * <p>Represents the type arguments inside a generic type reference.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code {@link java.util.List<String>}}</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * JAVADOC_INLINE_TAG -> JAVADOC_INLINE_TAG
     * `--LINK_INLINE_TAG -> LINK_INLINE_TAG
     *     |--JAVADOC_INLINE_TAG_START -> &#123;@
     *     |--TAG_NAME -> link
     *     |--TEXT ->
     *     |--REFERENCE -> REFERENCE
     *     |   |--IDENTIFIER -> java.util.List
     *     |   `--TYPE_ARGUMENTS -> TYPE_ARGUMENTS
     *     |       |--LT -> <
     *     |       |--TYPE_ARGUMENT -> TYPE_ARGUMENT
     *     |       |   `--IDENTIFIER -> String
     *     |       `--GT -> >
     *     `--JAVADOC_INLINE_TAG_END -> }
     * }</pre>
     *
     * @see #TYPE_ARGUMENT
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
     * {@code ATTRIBUTE_VALUE} Value assigned to an attribute.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code <a href="example">text</a>}</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> a
     * |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     * |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |       |--TEXT ->
     * |   |       |--TAG_ATTR_NAME -> href
     * |   |       |--EQUALS -> =
     * |   |       `--ATTRIBUTE_VALUE -> "example"
     * |   `--TAG_CLOSE -> >
     * |--HTML_CONTENT -> HTML_CONTENT
     * |   `--TEXT -> text
     * `--HTML_TAG_END -> HTML_TAG_END
     * |--TAG_OPEN -> <
     * |--TAG_SLASH -> /
     * |--TAG_NAME -> a
     * `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ATTRIBUTE
     * @see #TAG_ATTR_NAME
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
     *
     * <p>This node represents the textual content between an HTML start tag and
     * the corresponding end tag inside a Javadoc comment.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <a href="https://example.com">link</a>}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * `--HTML_ELEMENT -> HTML_ELEMENT
     *     |--HTML_TAG_START -> HTML_TAG_START
     *     |   |--TAG_OPEN -> <
     *     |   |--TAG_NAME -> a
     *     |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     *     |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *     |   |       |--TEXT ->   (whitespace)
     *     |   |       |--TAG_ATTR_NAME -> href
     *     |   |       |--EQUALS -> =
     *     |   |       `--ATTRIBUTE_VALUE -> "https://example.com"
     *     |   `--TAG_CLOSE -> >
     *     |--HTML_CONTENT -> HTML_CONTENT
     *     |   `--TEXT -> link
     *     `--HTML_TAG_END -> HTML_TAG_END
     *         |--TAG_OPEN -> <
     *         |--TAG_SLASH -> /
     *         |--TAG_NAME -> a
     *         `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ELEMENT
     */

    public static final int HTML_CONTENT = JavadocCommentsLexer.HTML_CONTENT;

    /**
     * {@code HTML_ATTRIBUTE} Single HTML attribute.
     *
     * <p>Represents one attribute inside an HTML tag.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <input type="text">
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * `--VOID_ELEMENT -> VOID_ELEMENT
     *     `--HTML_TAG_START -> HTML_TAG_START
     *         |--TAG_OPEN -> <
     *         |--TAG_NAME -> input
     *         |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     *         |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *         |       |--TEXT ->
     *         |       |--TAG_ATTR_NAME -> type
     *         |       |--EQUALS -> =
     *         |       `--ATTRIBUTE_VALUE -> "text"
     *         `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ATTRIBUTES
     */
    public static final int HTML_ATTRIBUTE = JavadocCommentsLexer.HTML_ATTRIBUTE;

    /**
     * {@code HTML_ATTRIBUTES} represents a collection of HTML attributes
     * inside an HTML tag.
     *
     * <p>Appears in Javadoc comments when documenting HTML elements that contain
     * multiple attributes.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <div lang="en" custom-attr="value"></div>
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> div
     * |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     * |   |   |--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |   |   |--TEXT ->
     * |   |   |   |--TAG_ATTR_NAME -> lang
     * |   |   |   |--EQUALS -> =
     * |   |   |   `--ATTRIBUTE_VALUE -> "en"
     * |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |       |--TEXT ->
     * |   |       |--TAG_ATTR_NAME -> custom-attr
     * |   |       |--EQUALS -> =
     * |   |       `--ATTRIBUTE_VALUE -> "value"
     * |   `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ATTRIBUTE
     */
    public static final int HTML_ATTRIBUTES = JavadocCommentsLexer.HTML_ATTRIBUTES;

    /**
     * Start of an HTML tag (the opening tag node).
     *
     * <p>This node represents the opening part of an HTML element and contains
     * the opening delimiter, tag name, optional attributes, and the closing
     * delimiter of the opening tag.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <a href="https://example.com">link</a>}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * `--HTML_ELEMENT -> HTML_ELEMENT
     *     `--HTML_TAG_START -> HTML_TAG_START
     *         |--TAG_OPEN -> <
     *         |--TAG_NAME -> a
     *         |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     *         |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *         |       |--TEXT ->
     *         |       |--TAG_ATTR_NAME -> href
     *         |       |--EQUALS -> =
     *         |       `--ATTRIBUTE_VALUE -> "https://example.com"
     *         `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ELEMENT
     */

    public static final int HTML_TAG_START = JavadocCommentsLexer.HTML_TAG_START;

    /**
     * End of an HTML tag (the closing tag node).
     *
     * <p>This node represents the closing part of an HTML element and contains the
     * closing delimiter, optional slash, and the tag name.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <a href="https://example.com">link</a>}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * `--HTML_ELEMENT -> HTML_ELEMENT
     *     |--HTML_TAG_START -> HTML_TAG_START
     *     |   |--TAG_OPEN -> <
     *     |   |--TAG_NAME -> a
     *     |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     *     |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *     |   |       |--TEXT ->   (whitespace)
     *     |   |       |--TAG_ATTR_NAME -> href
     *     |   |       |--EQUALS -> =
     *     |   |       `--ATTRIBUTE_VALUE -> "https://example.com"
     *     |   `--TAG_CLOSE -> >
     *     |--HTML_CONTENT -> HTML_CONTENT
     *     |   `--TEXT -> link
     *     `--HTML_TAG_END -> HTML_TAG_END
     *         |--TAG_OPEN -> <
     *         |--TAG_SLASH -> /
     *         |--TAG_NAME -> a
     *         `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ELEMENT
     */

    public static final int HTML_TAG_END = JavadocCommentsLexer.HTML_TAG_END;

    /**
     * Represents the opening {@literal "<"} symbol of an HTML start tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <div class="container" lang="en"></div>
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> div
     * |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     * |   |   |--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |   |   |--TAG_ATTR_NAME -> class
     * |   |   |   |--EQUALS -> =
     * |   |   |   `--ATTRIBUTE_VALUE -> "container"
     * |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |       |--TAG_ATTR_NAME -> lang
     * |   |       |--EQUALS -> =
     * |   |       `--ATTRIBUTE_VALUE -> "en"
     * |   `--TAG_CLOSE -> >
     * `--HTML_TAG_END -> HTML_TAG_END
     *     |--TAG_OPEN -> <
     *     |--TAG_SLASH -> /
     *     |--TAG_NAME -> div
     *     `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_TAG_START
     */
    public static final int TAG_OPEN = JavadocCommentsLexer.TAG_OPEN;

    /**
     * {@code TAG_NAME} Name of an HTML element.
     *
     * <p>Appears inside an HTML tag within Javadoc comments.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <div class="container">
     *     Content
     * </div>
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> div
     * |   |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     * |   |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     * |   |       |--TAG_ATTR_NAME -> class
     * |   |       |--EQUALS -> =
     * |   |       `--ATTRIBUTE_VALUE -> "container"
     * |   `--TAG_CLOSE -> >
     * |--HTML_CONTENT -> HTML_CONTENT
     * |   `--TEXT ->      Content
     * `--HTML_TAG_END -> HTML_TAG_END
     *     |--TAG_OPEN -> <
     *     |--TAG_SLASH -> /
     *     |--TAG_NAME -> div
     *     `--TAG_CLOSE -> >
     * }</pre>
     *
     * <p>Here {@code TAG_NAME} corresponds to {@code "div"}.</p>
     */
    public static final int TAG_NAME = JavadocCommentsLexer.TAG_NAME;

    /**
     * {@code TAG_CLOSE} represents the closing {@literal ">"} symbol
     * of an HTML tag.
     *
     * <p>Appears in Javadoc comments when documenting HTML elements.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <p>Some text</p>
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> p
     * |   `--TAG_CLOSE -> >
     * |--HTML_CONTENT -> HTML_CONTENT
     * |   `--TEXT -> Some text
     * `--HTML_TAG_END -> HTML_TAG_END
     *     |--TAG_OPEN -> <
     *     |--TAG_SLASH -> /
     *     |--TAG_NAME -> p
     *     `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_TAG_START
     */
    public static final int TAG_CLOSE = JavadocCommentsLexer.TAG_CLOSE;

    /**
     * {@code />} Self-closing tag delimiter.
     *
     * <p>Used for void HTML elements.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <br />}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * VOID_ELEMENT -> VOID_ELEMENT
     * |--TAG_OPEN -> <
     * |--TAG_NAME -> br
     * `--TAG_SLASH_CLOSE -> />
     * }</pre>
     *
     * @see #HTML_ELEMENT
     */
    public static final int TAG_SLASH_CLOSE = JavadocCommentsLexer.TAG_SLASH_CLOSE;

    /**
     * {@code TAG_SLASH} represents the slash {@literal "/"} used
     * inside an HTML closing tag.
     *
     * <p>Appears in Javadoc comments when closing HTML elements.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <p>Paragraph text</p>
     * }</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * |--HTML_TAG_START -> HTML_TAG_START
     * |   |--TAG_OPEN -> <
     * |   |--TAG_NAME -> p
     * |   `--TAG_CLOSE -> >
     * |--HTML_CONTENT -> HTML_CONTENT
     * |   `--TEXT -> Paragraph text
     * `--HTML_TAG_END -> HTML_TAG_END
     *     |--TAG_OPEN -> <
     *     |--TAG_SLASH -> /
     *     |--TAG_NAME -> p
     *     `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_TAG_END
     */
    public static final int TAG_SLASH = JavadocCommentsLexer.TAG_SLASH;

    /**
     * {@code TAG_ATTR_NAME} represents the name of an attribute inside an
     * HTML element within a Javadoc comment.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * <img src="logo.png" alt="Site logo">
     * }</pre>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * HTML_ELEMENT -> HTML_ELEMENT
     * `--VOID_ELEMENT -> VOID_ELEMENT
     *     `--HTML_TAG_START -> HTML_TAG_START
     *         |--TAG_OPEN -> <
     *         |--TAG_NAME -> img
     *         |--HTML_ATTRIBUTES -> HTML_ATTRIBUTES
     *         |   |--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *         |   |   |--TEXT ->
     *         |   |   |--TAG_ATTR_NAME -> src
     *         |   |   |--EQUALS -> =
     *         |   |   `--ATTRIBUTE_VALUE -> "logo.png"
     *         |   `--HTML_ATTRIBUTE -> HTML_ATTRIBUTE
     *         |       |--TEXT ->
     *         |       |--TAG_ATTR_NAME -> alt
     *         |       |--EQUALS -> =
     *         |       `--ATTRIBUTE_VALUE -> "Site logo"
     *         `--TAG_CLOSE -> >
     * }</pre>
     *
     * @see #HTML_ATTRIBUTES
     */
    public static final int TAG_ATTR_NAME = JavadocCommentsLexer.TAG_ATTR_NAME;

    /**
     * Start of an HTML comment node.
     *
     * <p>This node represents a full HTML comment inside Javadoc.</p>
     *
     * <p>This node has three children:</p>
     * <ol>
     *   <li>{@link #HTML_COMMENT_START}</li>
     *   <li>{@link #HTML_COMMENT_CONTENT}</li>
     *   <li>{@link #HTML_COMMENT_END}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <!-- Hello World! -->}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_CONTENT -> JAVADOC_CONTENT
     * |--TEXT -> /**
     * |--NEWLINE -> \r\n
     * |--LEADING_ASTERISK ->  *
     * |--TEXT ->
     * |--HTML_COMMENT -> HTML_COMMENT
     *     |--HTML_COMMENT_START -> <!--
     *     |--HTML_COMMENT_CONTENT -> HTML_COMMENT_CONTENT
     *     |   `--TEXT ->  Hello World!
     *     `--HTML_COMMENT_END -> -->
     * |--NEWLINE -> \r\n
     * |--LEADING_ASTERISK ->  *
     * |--TEXT -> /
     * }</pre>
     *
     * @see #HTML_COMMENT
     */
    public static final int HTML_COMMENT = JavadocCommentsLexer.HTML_COMMENT;

    /**
     * {@code HTML_COMMENT_START} represents the beginning of an HTML comment,
     * i.e., the {@literal "<!--"} sequence inside a Javadoc comment.
     *
     * <p>HTML comments occasionally appear in Javadoc to add internal notes or
     * explanations without affecting the rendered output.</p>
     * Example: {@code <!-- Note: This method is for demonstration purposes only. -->}
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * HTML_COMMENT -> HTML_COMMENT
     * |--HTML_COMMENT_START -> <!--
     * |--HTML_COMMENT_CONTENT -> HTML_COMMENT_CONTENT
     * |   `--TEXT ->  Note: This method is for demonstration purposes only.
     * `--HTML_COMMENT_END -> -->
     * }</pre>
     *
     * @see #HTML_COMMENT_END
     */
    public static final int HTML_COMMENT_START = JavadocCommentsLexer.HTML_COMMENT_START;

    /**
     * Closing part of an HTML comment.
     *
     * <p>This node represents the closing delimiter of an HTML comment in
     * Javadoc (for example {@code -->}).</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code * <!-- hidden comment -->}</pre>
     *
     * <b>Tree:</b>
     * <pre>{@code
     * |--LEADING_ASTERISK -> *
     * |--TEXT ->
     * |--HTML_COMMENT -> HTML_COMMENT
     * |   |--HTML_COMMENT_START -> <!--
     * |   |--HTML_COMMENT_CONTENT -> HTML_COMMENT_CONTENT
     * |   |   `--TEXT ->  hidden comment
     * |   `--HTML_COMMENT_END -> -->
     * }</pre>
     *
     * @see #HTML_COMMENT
     */

    public static final int HTML_COMMENT_END = JavadocCommentsLexer.HTML_COMMENT_END;

    /**
     * {@code HTML_COMMENT_CONTENT} Content inside an HTML comment.
     *
     * <p>Text within an HTML comment.</p>
     *
     * <p><b>Example:</b> {@code <!-- This is a comment -->}</p>
     *
     * <p><b>Tree:</b></p>
     * <pre>{@code
     * HTML_COMMENT -> HTML_COMMENT
     * |--HTML_COMMENT_START -> <!--
     * |--HTML_COMMENT_CONTENT -> HTML_COMMENT_CONTENT
     * |   `--TEXT ->  This is a comment
     * `--HTML_COMMENT_END -> -->
     * }</pre>
     *
     * @see #HTML_COMMENT
     */
    public static final int HTML_COMMENT_CONTENT = JavadocCommentsLexer.HTML_COMMENT_CONTENT;

    /** Empty private constructor of the current class. */
    private JavadocCommentsTokenTypes() {
    }
}
