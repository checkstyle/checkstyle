////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import org.antlr.v4.runtime.Recognizer;

import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;

/**
 * @author Baratali Izmailov
 * @see <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html">
 * javadoc - The Java API Documentation Generator</a>
 */
public final class JavadocTokenTypes {
    /** Rule types offset. */
    private static final int RULE_TYPES_OFFSET = 10000;

    /**
     * Root node of any Javadoc comment.
     * Last child is always {@link #EOF}.
     *
     * <p>
     * <b>Tree for example:</b>
     * <pre>{@code
     * JAVADOC[3x0]
     *   |--NEWLINE[3x0] : [\n]
     *   |--LEADING_ASTERISK[4x0] : [ *]
     *   |--WS[4x2] : [ ]
     *   |--JAVADOC_TAG[4x3] : [@param T The bar.\n ]
     *       |--PARAM_LITERAL[4x3] : [@param]
     *       |--WS[4x9] : [ ]
     *       |--PARAMETER_NAME[4x10] : [T]
     *       |--WS[4x11] : [ ]
     *       |--DESCRIPTION[4x12] : [The bar.\n ]
     *           |--TEXT[4x12] : [The bar.]
     *           |--NEWLINE[4x20] : [\n]
     *           |--TEXT[5x0] : [ ]
     *   |--EOF[5x1] : [<EOF>]
     * }</pre>
     */
    public static final int JAVADOC = JavadocParser.RULE_javadoc + RULE_TYPES_OFFSET;

    //--------------------------------------------------------------------------------------------//
    //------------------        JAVADOC TAGS          --------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    /**
     * Javadoc tag.
     *
     * <p>Type of Javadoc tag is resolved by literal node that is first child of this node.
     *
     * <p>As literal could be:
     * <ul>
     * <li>{@link #RETURN_LITERAL}</li>
     * <li>{@link #DEPRECATED_LITERAL}</li>
     * <li>{@link #SINCE_LITERAL}</li>
     * <li>{@link #SERIAL_DATA_LITERAL}</li>
     * <li>{@link #SERIAL_FIELD_LITERAL}</li>
     * <li>{@link #PARAM_LITERAL}</li>
     * <li>{@link #SEE_LITERAL}</li>
     * <li>{@link #SERIAL_LITERAL}</li>
     * <li>{@link #VERSION_LITERAL}</li>
     * <li>{@link #EXCEPTION_LITERAL}</li>
     * <li>{@link #THROWS_LITERAL}</li>
     * <li>{@link #AUTHOR_LITERAL}</li>
     * <li>or {@link #CUSTOM_NAME} if it is custom Javadoc tag.</li>
     * </ul>
     *
     * <p>
     * <b>Example</b>
     * <pre>{@code &#64;param T The bar.}</pre>
     * <b>Tree</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[4x3] : [@param T The bar.]
     *       |--PARAM_LITERAL[4x3] : [@param]
     *       |--WS[4x9] : [ ]
     *       |--PARAMETER_NAME[4x10] : [T]
     *       |--WS[4x11] : [ ]
     *       |--DESCRIPTION[4x12] : [The bar.]
     *           |--TEXT[4x12] : [The bar.]
     * }</pre>
     */
    public static final int JAVADOC_TAG = JavadocParser.RULE_javadocTag + RULE_TYPES_OFFSET;

    /**
     * Javadoc inline tag.
     *
     * <p>Type of Javadoc inline tag is resolved by literal node that is second child of this node.
     * First child is always {@link #JAVADOC_INLINE_TAG_START} and last node is always
     * {@link #JAVADOC_INLINE_TAG_END}.
     *
     * <p>As literal could be:
     * <ul>
     * <li>{@link #CODE_LITERAL}</li>
     * <li>{@link #DOC_ROOT_LITERAL}</li>
     * <li>{@link #LINK_LITERAL}</li>
     * <li>{@link #INHERIT_DOC_LITERAL}</li>
     * <li>{@link #LINKPLAIN_LITERAL}</li>
     * <li>{@link #LITERAL_LITERAL}</li>
     * <li>{@link #VALUE_LITERAL}</li>
     * <li>or {@link #CUSTOM_NAME} if it is custom Javadoc inline tag.</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;link String}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[4x3] : [{&#64;link String}]
     *        |--JAVADOC_INLINE_TAG_START[4x3] : [{]
     *        |--LINK_LITERAL[4x4] : [@link]
     *        |--WS[4x9] : [ ]
     *        |--REFERENCE[4x10] : [String]
     *            |--CLASS[4x10] : [String]
     *        |--JAVADOC_INLINE_TAG_END[4x16] : [}]
     * }
     * </pre>
     */
    public static final int JAVADOC_INLINE_TAG = JavadocParser.RULE_javadocInlineTag
            + RULE_TYPES_OFFSET;

    /**
     * '@return' literal in @return Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @return true if file exists}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[4x3] : [@return true if file exists]
     *       |--RETURN_LITERAL[4x3] : [@return]
     *       |--WS[4x10] : [ ]
     *       |--DESCRIPTION[4x11] : [true if file exists]
     *           |--TEXT[4x11] : [true if file exists]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCDBGG">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int RETURN_LITERAL = JavadocParser.RETURN_LITERAL;

    /**
     * '@deprecated' literal in @deprecated Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @deprecated it is deprecated method}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@deprecated it is deprecated method]
     *   |--DEPRECATED_LITERAL[3x0] : [@deprecated]
     *   |--WS[3x11] : [ ]
     *   |--DESCRIPTION[3x12] : [it is deprecated method]
     *       |--TEXT[3x12] : [it is deprecated method]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#deprecated">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int DEPRECATED_LITERAL = JavadocParser.DEPRECATED_LITERAL;

    /**
     * '@since' literal in @since Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @since 3.4 RELEASE}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@since 3.4 RELEASE]
     *       |--SINCE_LITERAL[3x0] : [@since]
     *       |--WS[3x6] : [ ]
     *       |--DESCRIPTION[3x7] : [3.4 RELEASE]
     *           |--TEXT[3x7] : [3.4 RELEASE]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHGJGD">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SINCE_LITERAL = JavadocParser.SINCE_LITERAL;

    /**
     * '@serialData' literal in @serialData Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serialData two values of Integer type}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@serialData two values of Integer type ]
     *       |--SERIAL_DATA_LITERAL[3x0] : [@serialData]
     *       |--WS[3x11] : [ ]
     *       |--DESCRIPTION[3x12] : [two values of Integer type ]
     *           |--TEXT[3x12] : [two values of Integer type ]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDJBFDB">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_DATA_LITERAL = JavadocParser.SERIAL_DATA_LITERAL;

    /**
     * '@serialField' literal in @serialField Javadoc tag.
     *
     * <p>Such Javadoc tag can have three arguments:
     * <ol>
     * <li>{@link #FIELD_NAME}</li>
     * <li>{@link #FIELD_TYPE}</li>
     * <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serialField counter Integer objects counter}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@serialField counter Integer objects counter]
     *       |--SERIAL_FIELD_LITERAL[3x0] : [@serialField]
     *       |--WS[3x12] : [ ]
     *       |--FIELD_NAME[3x13] : [counter]
     *       |--WS[3x20] : [ ]
     *       |--FIELD_TYPE[3x21] : [Integer]
     *       |--WS[3x28] : [ ]
     *       |--DESCRIPTION[3x29] : [objects counter]
     *           |--TEXT[3x29] : [objects counter]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDGHIDG">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_FIELD_LITERAL = JavadocParser.SERIAL_FIELD_LITERAL;

    /**
     * '@param' literal in @param Javadoc tag.
     *
     * <p>Such Javadoc tag can have two arguments:
     * <ol>
     * <li>{@link #PARAMETER_NAME}</li>
     * <li>{@link #DESCRIPTION}</li>
     * </ol>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @param T The bar.}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[4x3] : [@param T The bar.]
     *       |--PARAM_LITERAL[4x3] : [@param]
     *       |--WS[4x9] : [ ]
     *       |--PARAMETER_NAME[4x10] : [T]
     *       |--WS[4x11] : [ ]
     *       |--DESCRIPTION[4x12] : [The bar.]
     *           |--TEXT[4x12] : [The bar.]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHJECF">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int PARAM_LITERAL = JavadocParser.PARAM_LITERAL;

    /**
     * '@see' literal in @see Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #REFERENCE}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *       |--SEE_LITERAL[3x0] : [@see]
     *       |--WS[3x4] : [ ]
     *       |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *           |--PACKAGE[3x5] : [org.apache.utils]
     *           |--DOT[3x21] : [.]
     *           |--CLASS[3x22] : [Lists]
     *           |--DOT[3x27] : [.]
     *           |--CLASS[3x28] : [Comparator]
     *           |--HASH[3x38] : [#]
     *           |--MEMBER[3x39] : [compare]
     *           |--PARAMETERS[3x46] : [(Object)]
     *               |--LEFT_BRACE[3x46] : [(]
     *               |--ARGUMENT[3x47] : [Object]
     *               |--RIGHT_BRACE[3x53] : [)]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDDIEDI">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SEE_LITERAL = JavadocParser.SEE_LITERAL;

    /**
     * '@see' literal in @see Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #REFERENCE} or {@link #LITERAL_EXCLUDE}
     * or {@link #LITERAL_INCLUDE}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serial include}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@serial include]
     *       |--SERIAL_LITERAL[3x0] : [@serial]
     *       |--WS[3x7] : [ ]
     *       |--LITERAL_INCLUDE[3x8] : [include]
     * }</pre>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serial serialized company name}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@serial serialized company name]
     *       |--SERIAL_LITERAL[3x0] : [@serial]
     *       |--WS[3x7] : [ ]
     *       |--DESCRIPTION[3x8] : [serialized company name]
     *           |--TEXT[3x8] : [serialized company name]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int SERIAL_LITERAL = JavadocParser.SERIAL_LITERAL;

    /**
     * '@version' literal in @version Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @version 1.3}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@version 1.3]
     *       |--VERSION_LITERAL[3x0] : [@version]
     *       |--WS[3x8] : [ ]
     *       |--DESCRIPTION[3x9] : [1.3]
     *           |--TEXT[3x9] : [1.3]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCHBAE">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int VERSION_LITERAL = JavadocParser.VERSION_LITERAL;

    /**
     * '@exception' literal in @exception Javadoc tag.
     *
     * <p>Such Javadoc tag can have two argument - {@link #CLASS_NAME} and {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @exception SQLException if query is not correct}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@exception SQLException if query is not correct]
     *       |--EXCEPTION_LITERAL[3x0] : [@exception]
     *       |--WS[3x10] : [ ]
     *       |--CLASS_NAME[3x11] : [SQLException]
     *       |--WS[3x23] : [ ]
     *       |--DESCRIPTION[3x24] : [if query is not correct]
     *           |--TEXT[3x24] : [if query is not correct]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCEAHH">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int EXCEPTION_LITERAL = JavadocParser.EXCEPTION_LITERAL;

    /**
     * '@throws' literal in @throws Javadoc tag.
     *
     * <p>Such Javadoc tag can have two argument - {@link #CLASS_NAME} and {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @throws SQLException if query is not correct}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@throws SQLException if query is not correct]
     *       |--THROWS_LITERAL[3x0] : [@throws]
     *       |--WS[3x7] : [ ]
     *       |--CLASS_NAME[3x8] : [SQLException]
     *       |--WS[3x20] : [ ]
     *       |--DESCRIPTION[3x21] : [if query is not correct]
     *           |--TEXT[3x21] : [if query is not correct]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCHAHD">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int THROWS_LITERAL = JavadocParser.THROWS_LITERAL;

    /**
     * '@author' literal in @author Javadoc tag.
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @author Baratali Izmailov}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@author Baratali Izmailov]
     *       |--AUTHOR_LITERAL[3x0] : [@author]
     *       |--WS[3x7] : [ ]
     *       |--DESCRIPTION[3x8] : [Baratali Izmailov]
     *           |--TEXT[3x8] : [Baratali Izmailov]
     * }</pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCBAHA">
     * Oracle Docs</a>
     * @see #JAVADOC_TAG
     */
    public static final int AUTHOR_LITERAL = JavadocParser.AUTHOR_LITERAL;

    /**
     * Name of custom Javadoc tag (or Javadoc inline tag).
     *
     * <p>Such Javadoc tag can have one argument - {@link #DESCRIPTION}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @myJavadocTag some magic}</pre>
     * <b>Tree:</b>
     * <pre>{@code
     *   |--JAVADOC_TAG[3x0] : [@myJavadocTag some magic]
     *       |--CUSTOM_NAME[3x0] : [@myJavadocTag]
     *       |--WS[3x13] : [ ]
     *       |--DESCRIPTION[3x14] : [some magic]
     *           |--TEXT[3x14] : [some magic]
     * }</pre>
     */
    public static final int CUSTOM_NAME = JavadocParser.CUSTOM_NAME;

    /**
     * First child of {@link #JAVADOC_INLINE_TAG} that represents left curly brace '{'.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;code Comparable&lt;E&gt;}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[3x0] : [{&#64;code Comparable&lt;E&gt;}]
     *         |--JAVADOC_INLINE_TAG_START[3x0] : [{]
     *         |--CODE_LITERAL[3x1] : [@code]
     *         |--WS[3x6] : [ ]
     *         |--TEXT[3x7] : [Comparable&lt;E&gt;]
     *         |--JAVADOC_INLINE_TAG_END[3x21] : [}]
     * }</pre>
     */
    public static final int JAVADOC_INLINE_TAG_START = JavadocParser.JAVADOC_INLINE_TAG_START;

    /**
     * Last child of {@link #JAVADOC_INLINE_TAG} that represents right curly brace '}'.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;code Comparable&lt;E&gt;}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[3x0] : [{&#64;code Comparable&lt;E&gt;}]
     *         |--JAVADOC_INLINE_TAG_START[3x0] : [{]
     *         |--CODE_LITERAL[3x1] : [@code]
     *         |--WS[3x6] : [ ]
     *         |--TEXT[3x7] : [Comparable&lt;E&gt;]
     *         |--JAVADOC_INLINE_TAG_END[3x21] : [}]
     * }
     * </pre>
     */
    public static final int JAVADOC_INLINE_TAG_END = JavadocParser.JAVADOC_INLINE_TAG_END;

    /**
     * '@code' literal in {&#64;code} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag can have such child nodes:
     * <ul>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #WS}</li>
     * <li>{@link #TEXT}</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;code Comparable&lt;E&gt;}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[3x0] : [{&#64;code Comparable&lt;E&gt;}]
     *         |--JAVADOC_INLINE_TAG_START[3x0] : [{]
     *         |--CODE_LITERAL[3x1] : [@code]
     *         |--WS[3x6] : [ ]
     *         |--TEXT[3x7] : [Comparable&lt;E&gt;]
     *         |--JAVADOC_INLINE_TAG_END[3x21] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDFHHBB">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int CODE_LITERAL = JavadocParser.CODE_LITERAL;

    /**
     * '@docRoot' literal in {&#64;docRoot} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag does not have any arguments and can have such child nodes:
     * <ul>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #WS}</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;docRoot}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code  |--JAVADOC_INLINE_TAG[1x0] : [{&#64;docRoot \n}]
     *            |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *            |--DOC_ROOT_LITERAL[1x1] : [@docRoot]
     *            |--JAVADOC_INLINE_TAG_END[2x0] : [}]
     * }
     * </pre>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;docRoot
     *}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code  |--JAVADOC_INLINE_TAG[1x0] : [{&#64;docRoot \n}]
     *            |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *            |--DOC_ROOT_LITERAL[1x1] : [@docRoot]
     *            |--WS[1x9] : [ ]
     *            |--NEWLINE[1x10] : [\n]
     *            |--JAVADOC_INLINE_TAG_END[2x0] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDBACBF">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int DOC_ROOT_LITERAL = JavadocParser.DOC_ROOT_LITERAL;

    /**
     * '@link' literal in {&#64;link} Javadoc inline tag.
     * <p>
     * Such Javadoc inline tag can have one argument - {@link #REFERENCE}
     * </p>
     * <p><b>Example:</b></p>
     * <pre>{@code {&#64;link org.apache.utils.Lists.Comparator#compare(Object)}}</pre>
     * <p><b>Tree:</b></p>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[1x0] :
     *               [{&#64;link org.apache.utils.Lists.Comparator#compare(Object)}]
     *        |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *        |--LINK_LITERAL[1x1] : [@link]
     *        |--WS[1x6] : [ ]
     *        |--REFERENCE[1x7] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[1x7] : [org.apache.utils]
     *            |--DOT[1x23] : [.]
     *            |--CLASS[1x24] : [Lists]
     *            |--DOT[1x29] : [.]
     *            |--CLASS[1x30] : [Comparator]
     *            |--HASH[1x40] : [#]
     *            |--MEMBER[1x41] : [compare]
     *            |--PARAMETERS[1x48] : [(Object)]
     *                |--LEFT_BRACE[1x48] : [(]
     *                |--ARGUMENT[1x49] : [Object]
     *                |--RIGHT_BRACE[1x55] : [)]
     *        |--JAVADOC_INLINE_TAG_END[1x56] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDDIECH">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LINK_LITERAL = JavadocParser.LINK_LITERAL;

    /**
     * '@inheritDoc' literal in {&#64;inheritDoc} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag does not have any arguments and can have such child nodes:
     * <ul>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #WS}</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;inheritDoc}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code  |--JAVADOC_INLINE_TAG[1x0] : [{&#64;inheritDoc}]
     *            |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *            |--INHERIT_DOC_LITERAL[1x1] : [@inheritDoc]
     *            |--JAVADOC_INLINE_TAG_END[1x12] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDGJCHC">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int INHERIT_DOC_LITERAL = JavadocParser.INHERIT_DOC_LITERAL;

    /**
     * '@linkplain' literal in {&#64;linkplain} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag can have one argument - {@link #REFERENCE}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;linkplain org.apache.utils.Lists.Comparator#compare(Object) compare}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[1x0] :
     *               [{&#64;linkplain org.apache.utils.Lists.Comparator#compare(Object) compare}]
     *        |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *        |--LINKPLAIN_LITERAL[1x1] : [@linkplain]
     *        |--WS[1x11] : [ ]
     *        |--REFERENCE[1x12] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[1x12] : [org.apache.utils]
     *            |--DOT[1x28] : [.]
     *            |--CLASS[1x29] : [Lists]
     *            |--DOT[1x34] : [.]
     *            |--CLASS[1x35] : [Comparator]
     *            |--HASH[1x45] : [#]
     *            |--MEMBER[1x46] : [compare]
     *            |--PARAMETERS[1x53] : [(Object)]
     *                |--LEFT_BRACE[1x53] : [(]
     *                |--ARGUMENT[1x54] : [Object]
     *                |--RIGHT_BRACE[1x60] : [)]
     *        |--DESCRIPTION[1x61] : [ compare]
     *            |--TEXT[1x61] : [ compare]
     *        |--JAVADOC_INLINE_TAG_END[1x69] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDGBICD">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LINKPLAIN_LITERAL = JavadocParser.LINKPLAIN_LITERAL;

    /**
     * '@literal' literal in {&#64;literal} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag can have such child nodes:
     * <ul>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #WS}</li>
     * <li>{@link #TEXT}</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;literal #compare(Object)}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[1x0] : [{&#64;literal #compare(Object)}]
     *        |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *        |--LITERAL_LITERAL[1x1] : [@literal]
     *        |--WS[1x9] : [ ]
     *        |--TEXT[1x10] : [#compare(Object)]
     *        |--JAVADOC_INLINE_TAG_END[1x27] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDCFJDG">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int LITERAL_LITERAL = JavadocParser.LITERAL_LITERAL;

    /**
     * '@value' literal in {&#64;value} Javadoc inline tag.
     *
     * <p>Such Javadoc inline tag has one argument {@link #REFERENCE}
     * and can have such child nodes:
     * <ul>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #WS}</li>
     * </ul>
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code {&#64;value Integer#MAX_VALUE}}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_INLINE_TAG[1x0] : [{&#64;value Integer#MAX_VALUE}]
     *        |--JAVADOC_INLINE_TAG_START[1x0] : [{]
     *        |--VALUE_LITERAL[1x1] : [@value]
     *        |--WS[1x7] : [ ]
     *        |--REFERENCE[1x8] : [Integer#MAX_VALUE]
     *            |--CLASS[1x8] : [Integer]
     *            |--HASH[1x15] : [#]
     *            |--MEMBER[1x16] : [MAX_VALUE]
     *        |--JAVADOC_INLINE_TAG_END[1x25] : [}]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDDCDHH">
     * Oracle Docs</a>
     * @see #JAVADOC_INLINE_TAG
     */
    public static final int VALUE_LITERAL = JavadocParser.VALUE_LITERAL;

    /**
     * Parameter of the Javadoc tags listed below.
     * <ul>
     * <li>{@link #SEE_LITERAL @see}</li>
     * <li>{@link #LINK_LITERAL &#123;&#64;link&#125;}</li>
     * <li>{@link #LINKPLAIN_LITERAL &#123;&#64;linkplain&#125;}</li>
     * <li>{@link #VALUE_LITERAL &#123;&#64;value&#125;}</li>
     * </ul>
     */
    public static final int REFERENCE = JavadocParser.RULE_reference + RULE_TYPES_OFFSET;

    /**
     * Package definition in {@link #REFERENCE}.
     * Package definition is lowercase part of REFERENCE and before a hash character (#).
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *        |--SEE_LITERAL[3x0] : [@see]
     *        |--WS[3x4] : [ ]
     *        |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[3x5] : [org.apache.utils]
     *            |--DOT[3x21] : [.]
     *            |--CLASS[3x22] : [Lists]
     *            |--DOT[3x27] : [.]
     *            |--CLASS[3x28] : [Comparator]
     *            |--HASH[3x38] : [#]
     *            |--MEMBER[3x39] : [compare]
     *            |--PARAMETERS[3x46] : [(Object)]
     *                |--LEFT_BRACE[3x46] : [(]
     *                |--ARGUMENT[3x47] : [Object]
     *                |--RIGHT_BRACE[3x53] : [)]
     * }
     * </pre>
     */
    public static final int PACKAGE = JavadocParser.PACKAGE;

    /**
     * Class definition in {@link #REFERENCE}.
     * Class definition is part of REFERENCE, that is started by capital letter
     * and before a hash character (#).
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *        |--SEE_LITERAL[3x0] : [@see]
     *        |--WS[3x4] : [ ]
     *        |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[3x5] : [org.apache.utils]
     *            |--DOT[3x21] : [.]
     *            |--CLASS[3x22] : [Lists]
     *            |--DOT[3x27] : [.]
     *            |--CLASS[3x28] : [Comparator]
     *            |--HASH[3x38] : [#]
     *            |--MEMBER[3x39] : [compare]
     *            |--PARAMETERS[3x46] : [(Object)]
     *                |--LEFT_BRACE[3x46] : [(]
     *                |--ARGUMENT[3x47] : [Object]
     *                |--RIGHT_BRACE[3x53] : [)]
     * }
     * </pre>
     */
    public static final int CLASS = JavadocParser.CLASS;

    /**
     * Dot separator in {@link #REFERENCE}.
     * Dot separator is used between {@link #PACKAGE} and {@link #CLASS}; between {@link #CLASS}
     * and {@link #CLASS}
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *        |--SEE_LITERAL[3x0] : [@see]
     *        |--WS[3x4] : [ ]
     *        |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[3x5] : [org.apache.utils]
     *            |--DOT[3x21] : [.]
     *            |--CLASS[3x22] : [Lists]
     *            |--DOT[3x27] : [.]
     *            |--CLASS[3x28] : [Comparator]
     *            |--HASH[3x38] : [#]
     *            |--MEMBER[3x39] : [compare]
     *            |--PARAMETERS[3x46] : [(Object)]
     *                |--LEFT_BRACE[3x46] : [(]
     *                |--ARGUMENT[3x47] : [Object]
     *                |--RIGHT_BRACE[3x53] : [)]
     * }
     * </pre>
     */
    public static final int DOT = JavadocParser.DOT;

    /**
     * Hash character in {@link #REFERENCE}.
     * Hash character is used before specifying a class member.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *        |--SEE_LITERAL[3x0] : [@see]
     *        |--WS[3x4] : [ ]
     *        |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[3x5] : [org.apache.utils]
     *            |--DOT[3x21] : [.]
     *            |--CLASS[3x22] : [Lists]
     *            |--DOT[3x27] : [.]
     *            |--CLASS[3x28] : [Comparator]
     *            |--HASH[3x38] : [#]
     *            |--MEMBER[3x39] : [compare]
     *            |--PARAMETERS[3x46] : [(Object)]
     *                |--LEFT_BRACE[3x46] : [(]
     *                |--ARGUMENT[3x47] : [Object]
     *                |--RIGHT_BRACE[3x53] : [)]
     * }
     * </pre>
     */
    public static final int HASH = JavadocParser.HASH;

    /**
     * A class member in {@link #REFERENCE}.
     * Class member is specified after {@link #HASH} symbol.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see org.apache.utils.Lists.Comparator#compare(Object)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@see org.apache.utils.Lists.Comparator#compare(Object)]
     *        |--SEE_LITERAL[3x0] : [@see]
     *        |--WS[3x4] : [ ]
     *        |--REFERENCE[3x5] : [org.apache.utils.Lists.Comparator#compare(Object)]
     *            |--PACKAGE[3x5] : [org.apache.utils]
     *            |--DOT[3x21] : [.]
     *            |--CLASS[3x22] : [Lists]
     *            |--DOT[3x27] : [.]
     *            |--CLASS[3x28] : [Comparator]
     *            |--HASH[3x38] : [#]
     *            |--MEMBER[3x39] : [compare]
     *            |--PARAMETERS[3x46] : [(Object)]
     *                |--LEFT_BRACE[3x46] : [(]
     *                |--ARGUMENT[3x47] : [Object]
     *                |--RIGHT_BRACE[3x53] : [)]
     * }
     * </pre>
     */
    public static final int MEMBER = JavadocParser.MEMBER;

    /**
     * Parameters part in {@link #REFERENCE}.
     * It is used to specify parameters for {@link #MEMBER method}.
     * Always contains {@link #LEFT_BRACE} as first child and {@link #RIGHT_BRACE} as last child.
     * Each parameter is represented by {@link #ARGUMENT} node.
     * Arguments in braces are separated by {@link #COMMA} (and optional {@link #WS}).
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see #method(Processor, String)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see #method(Processor, String)]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--REFERENCE[1x5] : [#method(Processor, String)]
     *            |--HASH[1x5] : [#]
     *            |--MEMBER[1x6] : [method]
     *            |--PARAMETERS[1x12] : [(Processor, String)]
     *                |--LEFT_BRACE[1x12] : [(]
     *                |--ARGUMENT[1x13] : [Processor]
     *                |--COMMA[1x22] : [,]
     *                |--WS[1x23] : [ ]
     *                |--ARGUMENT[1x24] : [String]
     *                |--RIGHT_BRACE[1x30] : [)]
     * }
     * </pre>
     */
    public static final int PARAMETERS = JavadocParser.RULE_parameters + RULE_TYPES_OFFSET;

    /**
     * Left brace in {@link #PARAMETERS} part of {@link #REFERENCE}.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see #method(Processor, String)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see #method(Processor, String)]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--REFERENCE[1x5] : [#method(Processor, String)]
     *            |--HASH[1x5] : [#]
     *            |--MEMBER[1x6] : [method]
     *            |--PARAMETERS[1x12] : [(Processor, String)]
     *                |--LEFT_BRACE[1x12] : [(]
     *                |--ARGUMENT[1x13] : [Processor]
     *                |--COMMA[1x22] : [,]
     *                |--WS[1x23] : [ ]
     *                |--ARGUMENT[1x24] : [String]
     *                |--RIGHT_BRACE[1x30] : [)]
     * }
     * </pre>
     */
    public static final int LEFT_BRACE = JavadocParser.LEFT_BRACE;

    /**
     * Right brace in {@link #PARAMETERS} part of {@link #REFERENCE}.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see #method(Processor, String)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see #method(Processor, String)]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--REFERENCE[1x5] : [#method(Processor, String)]
     *            |--HASH[1x5] : [#]
     *            |--MEMBER[1x6] : [method]
     *            |--PARAMETERS[1x12] : [(Processor, String)]
     *                |--LEFT_BRACE[1x12] : [(]
     *                |--ARGUMENT[1x13] : [Processor]
     *                |--COMMA[1x22] : [,]
     *                |--WS[1x23] : [ ]
     *                |--ARGUMENT[1x24] : [String]
     *                |--RIGHT_BRACE[1x30] : [)]
     * }
     * </pre>
     */
    public static final int RIGHT_BRACE = JavadocParser.RIGHT_BRACE;

    /**
     * Argument definition in {@link #PARAMETERS} part of {@link #REFERENCE}.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see #method(Processor, String)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see #method(Processor, String)]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--REFERENCE[1x5] : [#method(Processor, String)]
     *            |--HASH[1x5] : [#]
     *            |--MEMBER[1x6] : [method]
     *            |--PARAMETERS[1x12] : [(Processor, String)]
     *                |--LEFT_BRACE[1x12] : [(]
     *                |--ARGUMENT[1x13] : [Processor]
     *                |--COMMA[1x22] : [,]
     *                |--WS[1x23] : [ ]
     *                |--ARGUMENT[1x24] : [String]
     *                |--RIGHT_BRACE[1x30] : [)]
     * }
     * </pre>
     */
    public static final int ARGUMENT = JavadocParser.ARGUMENT;

    /**
     * Comma separator between parameters in {@link #PARAMETERS} part of {@link #REFERENCE}.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see #method(Processor, String)}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see #method(Processor, String)]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--REFERENCE[1x5] : [#method(Processor, String)]
     *            |--HASH[1x5] : [#]
     *            |--MEMBER[1x6] : [method]
     *            |--PARAMETERS[1x12] : [(Processor, String)]
     *                |--LEFT_BRACE[1x12] : [(]
     *                |--ARGUMENT[1x13] : [Processor]
     *                |--COMMA[1x22] : [,]
     *                |--WS[1x23] : [ ]
     *                |--ARGUMENT[1x24] : [String]
     *                |--RIGHT_BRACE[1x30] : [)]
     * }
     * </pre>
     *
     * @see #PARAMETERS
     * @see #REFERENCE
     * @see #ARGUMENT
     */
    public static final int COMMA = JavadocParser.COMMA;

    /**
     * Quoted text.
     * One of possible @see tag arguments.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @see "Spring Framework"}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@see "Spring Framework"]
     *        |--SEE_LITERAL[1x0] : [@see]
     *        |--WS[1x4] : [ ]
     *        |--STRING[1x5] : ["Spring Framework"]
     * }
     * </pre>
     *
     * @see #SEE_LITERAL
     */
    public static final int STRING = JavadocParser.STRING;

    /**
     * Description node, that contains:
     * <ul>
     * <li>{@link #TEXT}</li>
     * <li>{@link #WS}</li>
     * <li>{@link #NEWLINE}</li>
     * <li>{@link #HTML_ELEMENT}</li>
     * </ul>
     *
     * <p>It is argument for many Javadoc tags and inline tags.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @throws IOException if &lt;b&gt;connection&lt;/b&gt; problems occur}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] :
     *               [@throws IOException if &lt;b&gt;connection&lt;/b&gt; problems occur]
     *        |--THROWS_LITERAL[1x0] : [@throws]
     *        |--WS[1x7] : [ ]
     *        |--CLASS_NAME[1x8] : [IOException]
     *        |--WS[1x19] : [ ]
     *        |--DESCRIPTION[1x20] : [if &lt;b&gt;connection&lt;/b&gt; problems occur]
     *            |--TEXT[1x20] : [if ]
     *            |--HTML_ELEMENT[1x23] : [&lt;b&gt;connection&lt;/b&gt;]
     *                |--HTML_TAG[1x23] : [&lt;b&gt;connection&lt;/b&gt;]
     *                    |--HTML_ELEMENT_OPEN[1x23] : [&lt;b&gt;]
     *                        |--OPEN[1x23] : [&lt;]
     *                        |--HTML_TAG_NAME[1x24] : [b]
     *                        |--CLOSE[1x25] : [&gt;]
     *                    |--TEXT[1x26] : [connection]
     *                    |--HTML_ELEMENT_CLOSE[1x36] : [&lt;/b&gt;]
     *                        |--OPEN[1x36] : [&lt;]
     *                        |--SLASH[1x37] : [/]
     *                        |--HTML_TAG_NAME[1x38] : [b]
     *                        |--CLOSE[1x39] : [&gt;]
     *            |--TEXT[1x40] : [ problems occur]
     * }
     * </pre>
     */
    public static final int DESCRIPTION = JavadocParser.RULE_description + RULE_TYPES_OFFSET;

    /**
     * Exception class name. First argument in {@link #THROWS_LITERAL @throws} and
     * {@link #EXCEPTION_LITERAL @exception} Javadoc tags.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @throws IOException connection problems}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@throws IOException connection problems]
     *        |--THROWS_LITERAL[1x0] : [@throws]
     *        |--WS[1x7] : [ ]
     *        |--CLASS_NAME[1x8] : [IOException]
     *        |--WS[1x19] : [ ]
     *        |--DESCRIPTION[1x20] : [connection problems]
     *            |--TEXT[1x20] : [connection problems]
     * }
     * </pre>
     *
     * @see #EXCEPTION_LITERAL
     * @see #THROWS_LITERAL
     */
    public static final int CLASS_NAME = JavadocParser.CLASS_NAME;

    /**
     * First argument in {@link #PARAM_LITERAL @param} Javadoc tag.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @param T The bar.}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[4x3] : [@param T The bar.]
     *        |--PARAM_LITERAL[4x3] : [@param]
     *        |--WS[4x9] : [ ]
     *        |--PARAMETER_NAME[4x10] : [T]
     *        |--WS[4x11] : [ ]
     *        |--DESCRIPTION[4x12] : [The bar.]
     *            |--TEXT[4x12] : [The bar.]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHJECF">
     * Oracle Docs</a>
     * @see #PARAM_LITERAL
     */
    public static final int PARAMETER_NAME = JavadocParser.PARAMETER_NAME;

    /**
     * 'exclude' literal.
     * One of three possible {@link #SERIAL_LITERAL @serial} tag arguments.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serial exclude}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@serial exclude]
     *        |--SERIAL_LITERAL[1x0] : [@serial]
     *        |--WS[1x7] : [ ]
     *        |--LITERAL_EXCLUDE[1x8] : [exclude]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #SERIAL_LITERAL
     */
    public static final int LITERAL_EXCLUDE = JavadocParser.LITERAL_EXCLUDE;

    /**
     * 'include' literal.
     * One of three possible {@link #SERIAL_LITERAL @serial} tag arguments.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serial include}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[1x0] : [@serial include]
     *        |--SERIAL_LITERAL[1x0] : [@serial]
     *        |--WS[1x7] : [ ]
     *        |--LITERAL_INCLUDE[1x8] : [include]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #SERIAL_LITERAL
     */
    public static final int LITERAL_INCLUDE = JavadocParser.LITERAL_INCLUDE;

    /**
     * Field name. First argument of {@link #SERIAL_FIELD_LITERAL @serialField} Javadoc tag.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serialField counter Integer objects counter}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@serialField counter Integer objects counter]
     *        |--SERIAL_FIELD_LITERAL[3x0] : [@serialField]
     *        |--WS[3x12] : [ ]
     *        |--FIELD_NAME[3x13] : [counter]
     *        |--WS[3x20] : [ ]
     *        |--FIELD_TYPE[3x21] : [Integer]
     *        |--WS[3x28] : [ ]
     *        |--DESCRIPTION[3x29] : [objects counter]
     *            |--TEXT[3x29] : [objects counter]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #SERIAL_FIELD_LITERAL
     */
    public static final int FIELD_NAME = JavadocParser.FIELD_NAME;

    /**
     * Field type. Second argument of {@link #SERIAL_FIELD_LITERAL @serialField} Javadoc tag.
     *
     * <p>
     * <b>Example:</b>
     * <pre>{@code @serialField counter Integer objects counter}</pre>
     * <b>Tree:</b>
     * <pre>
     * {@code |--JAVADOC_TAG[3x0] : [@serialField counter Integer objects counter]
     *        |--SERIAL_FIELD_LITERAL[3x0] : [@serialField]
     *        |--WS[3x12] : [ ]
     *        |--FIELD_NAME[3x13] : [counter]
     *        |--WS[3x20] : [ ]
     *        |--FIELD_TYPE[3x21] : [Integer]
     *        |--WS[3x28] : [ ]
     *        |--DESCRIPTION[3x29] : [objects counter]
     *            |--TEXT[3x29] : [objects counter]
     * }
     * </pre>
     *
     * @see
     * <a href="http://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHDECF">
     * Oracle Docs</a>
     * @see #SERIAL_FIELD_LITERAL
     */
    public static final int FIELD_TYPE = JavadocParser.FIELD_TYPE;

    //--------------------------------------------------------------------------------------------//
    //------------------        HTML TAGS          -----------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    /**
     * Parent node for all html tags.
     */
    public static final int HTML_ELEMENT = JavadocParser.RULE_htmlElement
            + RULE_TYPES_OFFSET;

    /**
     * Open html tag: &lt;XXX&gt;.
     */
    public static final int HTML_ELEMENT_OPEN = JavadocParser.RULE_htmlElementOpen
            + RULE_TYPES_OFFSET
            + RULE_TYPES_OFFSET;

    /**
     * Close html tag: &lt;XXX&gt;.
     */
    public static final int HTML_ELEMENT_CLOSE = JavadocParser.RULE_htmlElementClose
            + RULE_TYPES_OFFSET;

    /**
     * Non-special HTML tag.
     */
    public static final int HTML_TAG = JavadocParser.RULE_htmlTag + RULE_TYPES_OFFSET;

    /**
     * Identifier inside HTML tag: tag name or attribute name.
     */
    public static final int HTML_TAG_NAME = JavadocParser.HTML_TAG_NAME;

    /**
     * Html tag attribute. Parent node for: {@code HTML_TAG_IDENT, EQUALS, ATTR_VALUE}.
     */
    public static final int ATTRIBUTE = JavadocParser.RULE_attribute
            + RULE_TYPES_OFFSET
            + RULE_TYPES_OFFSET;

    // HTML tag components

    /**
     * Open html tag component: {@code '<'}.
     */
    public static final int OPEN = JavadocParser.OPEN;

    /**
     * Slash html tag component: {@code '/'}.
     */
    public static final int SLASH = JavadocParser.SLASH;

    /**
     * Close html tag component: {@code '>'}.
     */
    public static final int CLOSE = JavadocParser.CLOSE;

    /**
     * Slash close html tag component: {@code '/>'}.
     */
    public static final int SLASH_CLOSE = JavadocParser.SLASH_CLOSE;

    /**
     * Equals html tag component: {@code '='}.
     */
    public static final int EQUALS = JavadocParser.EQUALS;

    /**
     * Attribute value html tag component.
     */
    public static final int ATTR_VALUE = JavadocParser.ATTR_VALUE;

    /////////////////////// HTML TAGS WITH OPTIONAL CLOSE TAG /////////////////////////////////////
    /** Paragraph html tag: {@code <p></p>}. */
    public static final int PARAGRAPH = JavadocParser.RULE_paragraph + RULE_TYPES_OFFSET;
    /** Open paragraph tag. */
    public static final int P_TAG_OPEN = JavadocParser.RULE_pTagOpen + RULE_TYPES_OFFSET;
    /** Close paragraph tag. */
    public static final int P_TAG_CLOSE = JavadocParser.RULE_pTagClose + RULE_TYPES_OFFSET;
    /** Paragraph tag name. */
    public static final int P_HTML_TAG_NAME = JavadocParser.P_HTML_TAG_NAME;

    /** List item html tag: {@code <li></li>}. */
    public static final int LI = JavadocParser.RULE_li + RULE_TYPES_OFFSET;
    /** Open list item tag. */
    public static final int LI_TAG_OPEN = JavadocParser.RULE_liTagOpen + RULE_TYPES_OFFSET;
    /** Close list item tag. */
    public static final int LI_TAG_CLOSE = JavadocParser.RULE_liTagClose + RULE_TYPES_OFFSET;
    /** List item tag name. */
    public static final int LI_HTML_TAG_NAME = JavadocParser.LI_HTML_TAG_NAME;

    /** Table row html tag: {@code <tr></tr>}. */
    public static final int TR = JavadocParser.RULE_tr + RULE_TYPES_OFFSET;
    /** Open table row tag. */
    public static final int TR_TAG_OPEN = JavadocParser.RULE_trTagOpen + RULE_TYPES_OFFSET;
    /** Close table row tag. */
    public static final int TR_TAG_CLOSE = JavadocParser.RULE_trTagClose + RULE_TYPES_OFFSET;
    /** Table row tag name. */
    public static final int TR_HTML_TAG_NAME = JavadocParser.TR_HTML_TAG_NAME;

    /** Table cell html tag: {@code <td></td>}. */
    public static final int TD = JavadocParser.RULE_td + RULE_TYPES_OFFSET;
    /** Open table cell tag. */
    public static final int TD_TAG_OPEN = JavadocParser.RULE_tdTagOpen + RULE_TYPES_OFFSET;
    /** Close table cell tag. */
    public static final int TD_TAG_CLOSE = JavadocParser.RULE_tdTagClose + RULE_TYPES_OFFSET;
    /** Table cell tag name. */
    public static final int TD_HTML_TAG_NAME = JavadocParser.TD_HTML_TAG_NAME;

    /** Table header cell html tag: {@code <th></th>}. */
    public static final int TH = JavadocParser.RULE_th + RULE_TYPES_OFFSET;
    /** Open table header cell tag. */
    public static final int TH_TAG_OPEN = JavadocParser.RULE_thTagOpen + RULE_TYPES_OFFSET;
    /** Close table header cell tag. */
    public static final int TH_TAG_CLOSE = JavadocParser.RULE_thTagClose + RULE_TYPES_OFFSET;
    /** Table header cell tag name. */
    public static final int TH_HTML_TAG_NAME = JavadocParser.TH_HTML_TAG_NAME;

    /** Body html tag. */
    public static final int BODY = JavadocParser.RULE_body + RULE_TYPES_OFFSET;
    /** Open body tag. */
    public static final int BODY_TAG_OPEN = JavadocParser.RULE_bodyTagOpen + RULE_TYPES_OFFSET;
    /** Close body tag. */
    public static final int BODY_TAG_CLOSE = JavadocParser.RULE_bodyTagClose + RULE_TYPES_OFFSET;
    /** Body tag name. */
    public static final int BODY_HTML_TAG_NAME = JavadocParser.BODY_HTML_TAG_NAME;

    /** Colgroup html tag. */
    public static final int COLGROUP = JavadocParser.RULE_colgroup + RULE_TYPES_OFFSET;
    /** Open colgroup tag. */
    public static final int COLGROUP_TAG_OPEN = JavadocParser.RULE_colgroupTagOpen
            + RULE_TYPES_OFFSET;
    /** Close colgroup tag. */
    public static final int COLGROUP_TAG_CLOSE = JavadocParser.RULE_colgroupTagClose
            + RULE_TYPES_OFFSET;
    /** Colgroup tag name. */
    public static final int COLGROUP_HTML_TAG_NAME = JavadocParser.COLGROUP_HTML_TAG_NAME;

    /** Description of a term html tag: {@code <dd></dd>}. */
    public static final int DD = JavadocParser.RULE_dd + RULE_TYPES_OFFSET;
    /** Open description of a term tag. */
    public static final int DD_TAG_OPEN = JavadocParser.RULE_ddTagOpen + RULE_TYPES_OFFSET;
    /** Close description of a term tag. */
    public static final int DD_TAG_CLOSE = JavadocParser.RULE_ddTagClose + RULE_TYPES_OFFSET;
    /** Description of a term tag name. */
    public static final int DD_HTML_TAG_NAME = JavadocParser.DD_HTML_TAG_NAME;

    /** Description term html tag: {@code <dt></dt>}. */
    public static final int DT = JavadocParser.RULE_dt + RULE_TYPES_OFFSET;
    /** Open description term tag. */
    public static final int DT_TAG_OPEN = JavadocParser.RULE_dtTagOpen + RULE_TYPES_OFFSET;
    /** Close description term tag. */
    public static final int DT_TAG_CLOSE = JavadocParser.RULE_dtTagClose + RULE_TYPES_OFFSET;
    /** Description term tag name. */
    public static final int DT_HTML_TAG_NAME = JavadocParser.DT_HTML_TAG_NAME;

    /** Head html tag. */
    public static final int HEAD = JavadocParser.RULE_head + RULE_TYPES_OFFSET;
    /** Open head tag. */
    public static final int HEAD_TAG_OPEN = JavadocParser.RULE_headTagOpen + RULE_TYPES_OFFSET;
    /** Close head tag. */
    public static final int HEAD_TAG_CLOSE = JavadocParser.RULE_headTagClose + RULE_TYPES_OFFSET;
    /** Head tag name. */
    public static final int HEAD_HTML_TAG_NAME = JavadocParser.HEAD_HTML_TAG_NAME;

    /** Html html tag. */
    public static final int HTML = JavadocParser.RULE_html + RULE_TYPES_OFFSET;
    /** Open html tag. */
    public static final int HTML_TAG_OPEN = JavadocParser.RULE_htmlTagOpen + RULE_TYPES_OFFSET;
    /** Close html tag. */
    public static final int HTML_TAG_CLOSE = JavadocParser.RULE_htmlTagClose + RULE_TYPES_OFFSET;
    /** Html tag name. */
    public static final int HTML_HTML_TAG_NAME = JavadocParser.HTML_HTML_TAG_NAME;

    /** Option html tag. */
    public static final int OPTION = JavadocParser.RULE_option + RULE_TYPES_OFFSET;
    /** Open option tag. */
    public static final int OPTION_TAG_OPEN = JavadocParser.RULE_optionTagOpen + RULE_TYPES_OFFSET;
    /** Close option tag. */
    public static final int OPTION_TAG_CLOSE = JavadocParser.RULE_optionTagClose
            + RULE_TYPES_OFFSET;
    /** Option tag name. */
    public static final int OPTION_HTML_TAG_NAME = JavadocParser.OPTION_HTML_TAG_NAME;

    /** Table body html tag. */
    public static final int TBODY = JavadocParser.RULE_tbody + RULE_TYPES_OFFSET;
    /** Open table body tag. */
    public static final int TBODY_TAG_OPEN = JavadocParser.RULE_tbodyTagOpen + RULE_TYPES_OFFSET;
    /** Close table body tag. */
    public static final int TBODY_TAG_CLOSE = JavadocParser.RULE_tbodyTagClose + RULE_TYPES_OFFSET;
    /** Table body tag name. */
    public static final int TBODY_HTML_TAG_NAME = JavadocParser.TBODY_HTML_TAG_NAME;

    /** Table foot html tag. */
    public static final int TFOOT = JavadocParser.RULE_tfoot + RULE_TYPES_OFFSET;
    /** Open table foot tag. */
    public static final int TFOOT_TAG_OPEN = JavadocParser.RULE_tfootTagOpen + RULE_TYPES_OFFSET;
    /** Close table foot tag. */
    public static final int TFOOT_TAG_CLOSE = JavadocParser.RULE_tfootTagClose + RULE_TYPES_OFFSET;
    /** Table foot tag name. */
    public static final int TFOOT_HTML_TAG_NAME = JavadocParser.TFOOT_HTML_TAG_NAME;

    /** Table head html tag. */
    public static final int THEAD = JavadocParser.RULE_thead + RULE_TYPES_OFFSET;
    /** Open table head tag. */
    public static final int THEAD_TAG_OPEN = JavadocParser.RULE_theadTagOpen + RULE_TYPES_OFFSET;
    /** Close table head tag. */
    public static final int THEAD_TAG_CLOSE = JavadocParser.RULE_theadTagClose + RULE_TYPES_OFFSET;
    /** Table head tag name. */
    public static final int THEAD_HTML_TAG_NAME = JavadocParser.THEAD_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////// SINGLETON HTML TAGS  //////////////////////////////////////////////////
    /**
     * Parent node for all singleton html tags.
     */
    public static final int SINGLETON_ELEMENT = JavadocParser.RULE_singletonElement
            + RULE_TYPES_OFFSET;

    /**
     * Non-special singleton html tag.
     */
    public static final int SINGLETON_TAG = JavadocParser.RULE_singletonTag
            + RULE_TYPES_OFFSET;

    /** Area html tag. */
    public static final int AREA_TAG = JavadocParser.RULE_areaTag + RULE_TYPES_OFFSET;
    /** Area tag name. */
    public static final int AREA_HTML_TAG_NAME = JavadocParser.AREA_HTML_TAG_NAME;

    /** Base html tag. */
    public static final int BASE_TAG = JavadocParser.RULE_baseTag + RULE_TYPES_OFFSET;
    /** Base tag name. */
    public static final int BASE_HTML_TAG_NAME = JavadocParser.BASE_HTML_TAG_NAME;

    /** Basefront html tag. */
    public static final int BASEFRONT_TAG = JavadocParser.RULE_basefrontTag + RULE_TYPES_OFFSET;
    /** Basefront tag name. */
    public static final int BASEFRONT_HTML_TAG_NAME = JavadocParser.BASEFRONT_HTML_TAG_NAME;

    /** Br html tag. */
    public static final int BR_TAG = JavadocParser.RULE_brTag + RULE_TYPES_OFFSET;
    /** Br tag name. */
    public static final int BR_HTML_TAG_NAME = JavadocParser.BR_HTML_TAG_NAME;

    /** Col html tag. */
    public static final int COL_TAG = JavadocParser.RULE_colTag + RULE_TYPES_OFFSET;
    /** Col tag name. */
    public static final int COL_HTML_TAG_NAME = JavadocParser.COL_HTML_TAG_NAME;

    /** Frame html tag. */
    public static final int FRAME_TAG = JavadocParser.RULE_frameTag + RULE_TYPES_OFFSET;
    /** Frame tag name. */
    public static final int FRAME_HTML_TAG_NAME = JavadocParser.FRAME_HTML_TAG_NAME;

    /** Hr html tag. */
    public static final int HR_TAG = JavadocParser.RULE_hrTag + RULE_TYPES_OFFSET;
    /** Hr tag name. */
    public static final int HR_HTML_TAG_NAME = JavadocParser.HR_HTML_TAG_NAME;

    /** Img html tag. */
    public static final int IMG_TAG = JavadocParser.RULE_imgTag + RULE_TYPES_OFFSET;
    /** Img tag name. */
    public static final int IMG_HTML_TAG_NAME = JavadocParser.IMG_HTML_TAG_NAME;

    /** Input html tag. */
    public static final int INPUT_TAG = JavadocParser.RULE_inputTag + RULE_TYPES_OFFSET;
    /** Input tag name. */
    public static final int INPUT_HTML_TAG_NAME = JavadocParser.INPUT_HTML_TAG_NAME;

    /** Isindex html tag. */
    public static final int ISINDEX_TAG = JavadocParser.RULE_isindexTag + RULE_TYPES_OFFSET;
    /** Isindex tag name. */
    public static final int ISINDEX_HTML_TAG_NAME = JavadocParser.ISINDEX_HTML_TAG_NAME;

    /** Link html tag. */
    public static final int LINK_TAG = JavadocParser.RULE_linkTag + RULE_TYPES_OFFSET;
    /** Link tag name. */
    public static final int LINK_HTML_TAG_NAME = JavadocParser.LINK_HTML_TAG_NAME;

    /** Meta html tag. */
    public static final int META_TAG = JavadocParser.RULE_metaTag + RULE_TYPES_OFFSET;
    /** Meta tag name. */
    public static final int META_HTML_TAG_NAME = JavadocParser.META_HTML_TAG_NAME;

    /** Param html tag. */
    public static final int PARAM_TAG = JavadocParser.RULE_paramTag + RULE_TYPES_OFFSET;
    /** Param tag name. */
    public static final int PARAM_HTML_TAG_NAME = JavadocParser.PARAM_HTML_TAG_NAME;
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /** Html comment: {@code <!-- -->}. */
    public static final int HTML_COMMENT = JavadocParser.RULE_htmlComment
            + RULE_TYPES_OFFSET
            + RULE_TYPES_OFFSET;

    /**
     * HTML comment start symbol '&lt;!--'.
     */
    public static final int HTML_COMMENT_START = JavadocParser.HTML_COMMENT_START;

    /**
     * HTML comment end symbol '--&gt;'.
     */
    public static final int HTML_COMMENT_END = JavadocParser.HTML_COMMENT_END;

    /**
     * &lt;![CDATA[...]]&gt; block.
     */
    public static final int CDATA = JavadocParser.CDATA;

    //--------------------------------------------------------------------------------------------//
    //------------------        OTHER          ---------------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    /** Leading asterisk. */
    public static final int LEADING_ASTERISK = JavadocParser.LEADING_ASTERISK;

    /**
     * Newline symbol - '\n'.
     */
    public static final int NEWLINE = JavadocParser.NEWLINE;

    /**
     * Any other symbol.
     */
    public static final int CHAR = JavadocParser.CHAR;

    /**
     * Whitespace or tab ('\t') symbol.
     */
    public static final int WS = JavadocParser.WS;

    /**
     * CHAR and WS sequence.
     */
    public static final int TEXT = JavadocParser.RULE_text + RULE_TYPES_OFFSET;

    /**
     * End Of File symbol.
     */
    public static final int EOF = Recognizer.EOF;

    /** Empty private constructor of the current class. */
    private JavadocTokenTypes() {
    }
}
