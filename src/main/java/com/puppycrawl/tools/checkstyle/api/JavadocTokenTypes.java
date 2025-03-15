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

import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocParser;

/**
 * Contains the constants for all the tokens contained in the Abstract
 * Syntax Tree for the javadoc grammar.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html">
 * javadoc - The Java API Documentation Generator</a>
 */
public final class JavadocTokenTypes {

    // ------------------------------------------------------------------------------------------ //
    // -----------------        JAVADOC TAGS          ------------------------------------------- //
    // ------------------------------------------------------------------------------------------ //

    /**
     * '@return' literal in {@code @return} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @return true if file exists}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--RETURN_LITERAL -> @return
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> true if file exists
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCDBGG">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int RETURN_LITERAL = JavadocParser.RETURN_LITERAL;

    /**
     * '{@literal @}deprecated' literal in {@literal @}deprecated Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @deprecated It is deprecated method}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--DEPRECATED_LITERAL -> @deprecated
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> It is deprecated method
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#deprecated">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int DEPRECATED_LITERAL = JavadocParser.DEPRECATED_LITERAL;

    /**
     * '@since' literal in {@code @since} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @since 3.4 RELEASE}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--SINCE_LITERAL -> @since
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> 3.4 RELEASE
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHGJGD">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SINCE_LITERAL = JavadocParser.SINCE_LITERAL;

    /**
     * '@serialData' literal in {@code @serialData} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @serialData Two values of Integer type}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--SERIAL_DATA_LITERAL -> @serialData
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> Two values of Integer type
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDJBFDB">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_DATA_LITERAL = JavadocParser.SERIAL_DATA_LITERAL;

    /**
     * '@serialField' literal in {@code @serialField} Javadoc tag.
     *
     * <p>Such Javadoc tag can have three arguments:</p>
     * <ol>
     * <li>{@link #FIELD_NAME}</li>
     * <li>{@link #FIELD_TYPE}</li>
     * <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @serialField counter Integer objects counter}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--SERIAL_FIELD_LITERAL -> @serialField
     *  |--WS ->
     *  |--FIELD_NAME -> counter
     *  |--WS ->
     *  |--FIELD_TYPE -> Integer
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> objects counter
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDGHIDG">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_FIELD_LITERAL = JavadocParser.SERIAL_FIELD_LITERAL;

    /**
     * '@param' literal in {@code @param} Javadoc tag.
     *
     * <p>Such Javadoc tag can have two arguments:</p>
     * <ol>
     * <li>{@link #PARAMETER_NAME}</li>
     * <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @param value The parameter of method.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     * JAVADOC_TAG -> JAVADOC_TAG
     *  |--PARAM_LITERAL -> @param
     *  |--WS ->
     *  |--PARAMETER_NAME -> value
     *  |--WS ->
     *  `--DESCRIPTION -> DESCRIPTION
     *      |--TEXT -> The parameter of method.
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHJECF">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int PARAM_LITERAL = JavadocParser.PARAM_LITERAL;

    /**
     * '@see' literal in {@code @see} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #REFERENCE}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG -> JAVADOC_TAG
     *    |--SEE_LITERAL -> @see
     *    |--WS ->
     *    |--REFERENCE -> REFERENCE
     *        |--PACKAGE_CLASS -> org.apache.utils.Lists.Comparator
     *        |--HASH -> #
     *        |--MEMBER -> compare
     *        `--PARAMETERS -> PARAMETERS
     *            |--LEFT_BRACE -> (
     *            |--ARGUMENT -> Object
     *            `--RIGHT_BRACE -> )
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDDIEDI">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SEE_LITERAL = JavadocParser.SEE_LITERAL;

    /**
     * '@serial' literal in {@code @serial} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #REFERENCE} or {@link #LITERAL_EXCLUDE}
     * or {@link #LITERAL_INCLUDE}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @serial include}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG -> JAVADOC_TAG
     *       |--SERIAL_LITERAL -> @serial
     *       |--WS
     *       |--LITERAL_INCLUDE -> include
     * }</pre>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @serial serialized company name}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG-> JAVADOC_TAG
     *       |--SERIAL_LITERAL -> @serial
     *       |--WS
     *       |--DESCRIPTION -> DESCRIPTION
     *           |--TEXT -> serialized company name
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_LITERAL = JavadocParser.SERIAL_LITERAL;

    /**
     * '@version' literal in {@code @version} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @version 1.3}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG -> JAVADOC_TAG
     *    |--VERSION_LITERAL -> @version
     *    |--WS ->
     *    `--DESCRIPTION -> DESCRIPTION
     *        |--TEXT -> 1.3
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCHBAE">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int VERSION_LITERAL = JavadocParser.VERSION_LITERAL;

    /**
     * '@exception' literal in {@code @exception} Javadoc tag.
     *
     * <p>Such Javadoc tag can have two argument - {@link #CLASS_NAME} and {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @exception SQLException if query is not correct}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG -> JAVADOC_TAG
     *    |--EXCEPTION_LITERAL -> @exception
     *    |--WS ->
     *    |--CLASS_NAME -> SQLException
     *    |--WS ->
     *    `--DESCRIPTION -> DESCRIPTION
     *        `--TEXT -> if query is not correct
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCEAHH">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int EXCEPTION_LITERAL = JavadocParser.EXCEPTION_LITERAL;

    /**
     * '@throws' literal in {@code @throws} Javadoc tag.
     *
     * <p>Such Javadoc tag can have two argument - {@link #CLASS_NAME} and {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @throws SQLException if query is not correct}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG -> JAVADOC_TAG
     *      |--THROWS_LITERAL -> @throws
     *      |--WS ->
     *      |--CLASS_NAME -> SQLException
     *      |--WS ->
     *      `--DESCRIPTION -> DESCRIPTION
     *          |--TEXT -> if query is not correct
     *          |--NEWLINE -> \r\n
     *          `--TEXT ->
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCHAHD">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int THROWS_LITERAL = JavadocParser.THROWS_LITERAL;

    /**
     * '@author' literal in {@code @author} Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @author Baratali Izmailov}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG -> JAVADOC_TAG
     *      |--AUTHOR_LITERAL -> @author
     *      |--WS ->
     *      `--DESCRIPTION -> DESCRIPTION
     *          |--TEXT -> Baratali Izmailov
     *          |--NEWLINE -> \r\n
     * }</pre>
     *
     * @see
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCBAHA">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int AUTHOR_LITERAL = JavadocParser.AUTHOR_LITERAL;

    /**
     * Name of custom Javadoc tag (or Javadoc inline tag).
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code @myJavadocTag some magic}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   JAVADOC_TAG --> JAVADOC_TAG
     *       |--CUSTOM_NAME --> @myJavadocTag
     *       |--WS -->
     *       `--DESCRIPTION --> DESCRIPTION
     *           |--TEXT --> some magic
     * }</pre>
     */
    public static final int CUSTOM_NAME = JavadocParser.CUSTOM_NAME;

    /**
     * First child of {@link #JAVADOC_INLINE_TAG} that represents left curly brace '{'.
     *
     * <p><b>Example:</b></p>
     * <pre><code>{&#64;code Comparable&lt;E&gt;}</code></pre>
     * <b>Tree:</b>
     * <pre>
     * <code> JAVADOC_INLINE_TAG --&gt; JAVADOC_INLINE_TAG
     *         |--JAVADOC_INLINE_TAG_START --&gt; {
     *         |--CODE_LITERAL --&gt; @code
     *         |--WS --&gt;
     *         |--TEXT --&gt; Comparable&lt;E&gt;
     *         `--JAVADOC_INLINE_TAG_END --&gt; }
     * </code>
     * </pre>
     *
     * @noinspection HtmlTagCanBeJavadocTag
     * @noinspectionreason HtmlTagCanBeJavadocTag - encoded symbols were not decoded when
     *      replaced with Javadoc tag
     */
    public static final int JAVADOC_INLINE_TAG_START = JavadocParser.JAVADOC_INLINE_TAG_START;

    /**
     * Last child of {@link #JAVADOC_INLINE_TAG} that represents right curly brace '}'.
     *
     * <p><b>Example:</b></p>
     * <pre><code>{&#64;code Comparable&lt;E&gt;}</code></pre>
     * <b>Tree:</b>
     * <pre>
     * <code>JAVADOC_INLINE_TAG --&gt; JAVADOC_INLINE_TAG
     *        |--JAVADOC_INLINE_TAG_START --&gt; {
     *        |--CODE_LITERAL --&gt; @code
     *        |--WS --&gt;
     *        |--TEXT --&gt; Comparable&lt;E&gt;
     *        `--JAVADOC_INLINE_TAG_END --&gt; }
     *
     * </code>
     * </pre>
     *
     * @noinspection HtmlTagCanBeJavadocTag
     * @noinspectionreason HtmlTagCanBeJavadocTag - encoded symbols were not decoded when
     *      replaced with Javadoc tag
     */
    public static final int JAVADOC_INLINE_TAG_END = JavadocParser.JAVADOC_INLINE_TAG_END;

/**
 * '@code' literal in {&#64;code} Javadoc inline tag.
 *
 * <p>Such Javadoc inline tag can have such child nodes:</p>