///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Objects;
import java.util.stream.Stream;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks for long methods and constructors.
 * </p>
 * <p>
 * Rationale: If a method becomes very long it is hard to understand.
 * Therefore, long methods should usually be refactored into several
 * individual methods that focus on a specific task.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of lines allowed.
 * Type is {@code int}.
 * Default value is {@code 150}.
 * </li>
 * <li>
 * Property {@code countEmpty} - Control whether to count empty lines and comments.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   public MyClass() {  // constructor (line 1)
 *        /&#42; line 2
 *            ...
 *           line 150 &#42;/
 *   } // line 151, violation, as it is over 150
 *
 *   public void firstExample() { // line 1
 *
 *       // line 3
 *       System.out.println("line 4");
 *       /&#42; line 5
 *          line 6 &#42;/
 *   } // line 7, OK, as it is less than 150
 *
 *   public void secondExample() { // line 1
 *       // line 2
 *       System.out.println("line 3");
 *
 *       /&#42; line 5
 *           ...
 *          line 150 &#42;/
 *   } // line 151, violation, as it is over 150
 * }
 * </pre>
 * <p>
 * To configure the check so that it accepts methods with at most 4 lines:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *   &lt;property name="max" value="4"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyTest {
 *   public MyTest()  {            // constructor (line 1)
 *       int var1 = 2;             // line 2
 *       int var2 = 4;             // line 3
 *       int sum = var1 + var2;  // line 4
 *   } // line 5, OK, constructor is not mentioned in the tokens
 *
 *   public void firstMethod() { // line 1
 *       // comment (line 2)
 *       System.out.println("line 3");
 *   } // line 4, OK, as it allows at most 4 lines
 *
 *   public void secondMethod() { // line 1
 *       int index = 0;   // line 2
 *       if (index &#60; 5) { // line 3
 *           index++;     // line 4
 *       }                // line 5
 *   } // line 6, violation, as it is over 4 lines
 *
 *   public void thirdMethod() { // line 1
 *
 *       // comment (line 3)
 *       System.out.println("line 4");
 *   } // line 5, violation, as it is over 4 lines
 * }
 * </pre>
 * <p>
 * To configure the check so that it accepts methods with at most 4 lines,
 * not counting empty lines and comments:
 * </p>
 * <pre>
 * &lt;module name="MethodLength"&gt;
 *   &lt;property name="tokens" value="METHOD_DEF"/&gt;
 *   &lt;property name="max" value="4"/&gt;
 *   &lt;property name="countEmpty" value="false"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyTest {
 *   public MyTest()  {          // constructor (line 1)
 *       int var1 = 2;           // line 2
 *       int var2 = 4;           // line 3
 *       int sum = var1 + var2;  // line 4
 *   } // line 5, OK, constructor is not mentioned in the tokens
 *
 *   public void firstMethod() { // line 1
 *       // comment - not counted as line
 *       System.out.println("line 2");
 *   } // line 3, OK, as it allows at most 4 lines
 *
 *   public void secondMethod() { // line 1
 *       int index = 0;   // line 2
 *       if (index &#60; 5) { // line 3
 *           index++;     // line 4
 *       }                // line 5
 *   } // line 6, violation, as it is over 4 lines
 *
 *   public void thirdMethod() { // line 1
 *
 *       // comment - not counted as line
 *       System.out.println("line 2");
 *   } // line 3, OK, as it allows at most 4 lines
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code maxLen.method}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class MethodLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.method";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX_LINES = 150;

    /** Control whether to count empty lines and comments. */
    private boolean countEmpty = true;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX_LINES;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST openingBrace = ast.findFirstToken(TokenTypes.SLIST);
        if (openingBrace != null) {
            final int length;
            if (countEmpty) {
                final DetailAST closingBrace = openingBrace.findFirstToken(TokenTypes.RCURLY);
                length = getLengthOfBlock(openingBrace, closingBrace);
            }
            else {
                length = countUsedLines(openingBrace);
            }
            if (length > max) {
                final String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();
                log(ast, MSG_KEY, length, max, methodName);
            }
        }
    }

    /**
     * Returns length of code.
     *
     * @param openingBrace block opening brace
     * @param closingBrace block closing brace
     * @return number of lines with code for current block
     */
    private static int getLengthOfBlock(DetailAST openingBrace, DetailAST closingBrace) {
        final int startLineNo = openingBrace.getLineNo();
        final int endLineNo = closingBrace.getLineNo();
        return endLineNo - startLineNo + 1;
    }

    /**
     * Count number of used code lines without comments.
     *
     * @param ast start ast
     * @return number of used lines of code
     */
    private static int countUsedLines(DetailAST ast) {
        final Deque<DetailAST> nodes = new ArrayDeque<>();
        nodes.add(ast);
        final BitSet usedLines = new BitSet();
        final int startLineNo = ast.getLineNo();
        while (!nodes.isEmpty()) {
            final DetailAST node = nodes.removeFirst();
            final int lineIndex = node.getLineNo() - startLineNo;
            // text block requires special treatment,
            // since it is the only non-comment token that can span more than one line
            if (node.getType() == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) {
                final int endLineIndex = node.getLastChild().getLineNo() - startLineNo;
                usedLines.set(lineIndex, endLineIndex + 1);
            }
            else {
                usedLines.set(lineIndex);
                Stream.iterate(
                    node.getLastChild(), Objects::nonNull, DetailAST::getPreviousSibling
                ).forEach(nodes::addFirst);
            }
        }
        return usedLines.cardinality();
    }

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of a method.
     */
    public void setMax(int length) {
        max = length;
    }

    /**
     * Setter to control whether to count empty lines and comments.
     *
     * @param countEmpty whether to count empty and comments.
     */
    public void setCountEmpty(boolean countEmpty) {
        this.countEmpty = countEmpty;
    }

}
