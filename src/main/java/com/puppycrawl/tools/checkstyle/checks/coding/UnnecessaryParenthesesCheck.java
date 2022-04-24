////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks if unnecessary parentheses are used in a statement or expression.
 * The check will flag the following with warnings:
 * </p>
 * <pre>
 * return (x);          // parens around identifier
 * return (x + 1);      // parens around return value
 * int x = (y / 2 + 1); // parens around assignment rhs
 * for (int i = (0); i &lt; 10; i++) {  // parens around literal
 * t -= (z + 1);                     // parens around assignment rhs
 * boolean a = (x &gt; 7 &amp;&amp; y &gt; 5)      // parens around expression
 *             || z &lt; 9;
 * boolean b = (~a) &gt; -27            // parens around ~a
 *             &amp;&amp; (a-- &lt; 30);        // parens around expression
 * </pre>
 * <p>
 * The check is not "type aware", that is to say, it can't tell if parentheses
 * are unnecessary based on the types in an expression. The check is partially aware about
 * operator precedence but unaware about operator associativity.
 * It won't catch cases such as:
 * </p>
 * <pre>
 * int x = (a + b) + c; // 1st Case
 * boolean p = true; // 2nd Case
 * int q = 4;
 * int r = 3;
 * if (p == (q &lt;= r)) {}</pre>
 * <p>
 * In the first case, given that <em>a</em>, <em>b</em>, and <em>c</em> are
 * all {@code int} variables, the parentheses around {@code a + b}
 * are not needed.
 * In the second case, parentheses are required as <em>q</em>, <em>r</em> are
 * of type {@code int} and <em>p</em> is of type {@code boolean}
 * and removing parentheses will give a compile-time error. Even if <em>q</em>
 * and <em>r</em> were {@code boolean} still there will be no violation
 * raised as check is not "type aware".
 * </p>
 * <p>
 * The partial support for operator precedence includes cases of the following type:
 * </p>
 * <pre>
 * boolean a = true, b = true;
 * boolean c = false, d = false;
 * if ((a &amp;&amp; b) || c) { // violation, unnecessary paren
 * }
 * if (a &amp;&amp; (b || c)) { // ok
 * }
 * if ((a == b) &amp;&amp; c) { // violation, unnecessary paren
 * }
 * String e = &quot;e&quot;;
 * if ((e instanceof String) &amp;&amp; a || b) { // violation, unnecessary paren
 * }
 * int f = 0;
 * int g = 0;
 * if (!(f &gt;= g) // ok
 *         &amp;&amp; (g &gt; f)) { // violation, unnecessary paren
 * }
 * if ((++f) &gt; g &amp;&amp; a) { // violation, unnecessary paren
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EXPR">
 * EXPR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#IDENT">
 * IDENT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_DOUBLE">
 * NUM_DOUBLE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_FLOAT">
 * NUM_FLOAT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_INT">
 * NUM_INT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NUM_LONG">
 * NUM_LONG</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STRING_LITERAL">
 * STRING_LITERAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NULL">
 * LITERAL_NULL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FALSE">
 * LITERAL_FALSE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRUE">
 * LITERAL_TRUE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ASSIGN">
 * ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND_ASSIGN">
 * BAND_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR_ASSIGN">
 * BOR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR_ASSIGN">
 * BSR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR_ASSIGN">
 * BXOR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV_ASSIGN">
 * DIV_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS_ASSIGN">
 * MINUS_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD_ASSIGN">
 * MOD_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS_ASSIGN">
 * PLUS_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL_ASSIGN">
 * SL_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR_ASSIGN">
 * SR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR_ASSIGN">
 * STAR_ASSIGN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#TEXT_BLOCK_LITERAL_BEGIN">
 * TEXT_BLOCK_LITERAL_BEGIN</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAND">
 * LAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LOR">
 * LOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_INSTANCEOF">
 * LITERAL_INSTANCEOF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GT">
 * GT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LT">
 * LT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GE">
 * GE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LE">
 * LE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EQUAL">
 * EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NOT_EQUAL">
 * NOT_EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#UNARY_MINUS">
 * UNARY_MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#UNARY_PLUS">
 * UNARY_PLUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INC">
 * INC</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DEC">
 * DEC</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LNOT">
 * LNOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BNOT">
 * BNOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#POST_INC">
 * POST_INC</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#POST_DEC">
 * POST_DEC</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;UnnecessaryParentheses&quot;/&gt;
 * </pre>
 * <p>
 * Which results in the following violations:
 * </p>
 * <pre>
 * public int square(int a, int b){
 *   int square = (a * b); // violation
 *   return (square);      // violation
 * }
 * int sumOfSquares = 0;
 * for(int i=(0); i&lt;10; i++){          // violation
 *   int x = (i + 1);                  // violation
 *   sumOfSquares += (square(x * x));  // violation
 * }
 * double num = (10.0); //violation
 * List&lt;String&gt; list = Arrays.asList(&quot;a1&quot;, &quot;b1&quot;, &quot;c1&quot;);
 * myList.stream()
 *   .filter((s) -&gt; s.startsWith(&quot;c&quot;)) // violation
 *   .forEach(System.out::println);
 * int a = 10, b = 12, c = 15;
 * boolean x = true, y = false, z= true;
 * if ((a &gt;= 0 &amp;&amp; b &lt;= 9)            // violation, unnecessary parenthesis
 *          || (c &gt;= 5 &amp;&amp; b &lt;= 5)    // violation, unnecessary parenthesis
 *          || (c &gt;= 3 &amp;&amp; a &lt;= 7)) { // violation, unnecessary parenthesis
 *     return;
 * }
 * if ((-a) != -27 // violation, unnecessary parenthesis
 *          &amp;&amp; b &gt; 5) {
 *     return;
 * }
 * if (x==(a &lt;= 15)) { // ok
 *     return;
 * }
 * if (x==(y == z)) { // ok
 *     return;
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
 * {@code unnecessary.paren.assign}
 * </li>
 * <li>
 * {@code unnecessary.paren.expr}
 * </li>
 * <li>
 * {@code unnecessary.paren.ident}
 * </li>
 * <li>
 * {@code unnecessary.paren.lambda}
 * </li>
 * <li>
 * {@code unnecessary.paren.literal}
 * </li>
 * <li>
 * {@code unnecessary.paren.return}
 * </li>
 * <li>
 * {@code unnecessary.paren.string}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
@FileStatefulCheck
public class UnnecessaryParenthesesCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_IDENT = "unnecessary.paren.ident";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ASSIGN = "unnecessary.paren.assign";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_EXPR = "unnecessary.paren.expr";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LITERAL = "unnecessary.paren.literal";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_STRING = "unnecessary.paren.string";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_RETURN = "unnecessary.paren.return";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LAMBDA = "unnecessary.paren.lambda";

    /**
     * Compiled pattern used to match newline control characters, for replacement.
     */
    private static final Pattern NEWLINE = Pattern.compile("\\R");

    /**
     * String used to amend TEXT_BLOCK_CONTENT so that it matches STRING_LITERAL.
     */
    private static final String QUOTE = "\"";

    /** The maximum string length before we chop the string. */
    private static final int MAX_QUOTED_LENGTH = 25;

    /** Token types for literals. */
    private static final int[] LITERALS = {
        TokenTypes.NUM_DOUBLE,
        TokenTypes.NUM_FLOAT,
        TokenTypes.NUM_INT,
        TokenTypes.NUM_LONG,
        TokenTypes.STRING_LITERAL,
        TokenTypes.LITERAL_NULL,
        TokenTypes.LITERAL_FALSE,
        TokenTypes.LITERAL_TRUE,
        TokenTypes.TEXT_BLOCK_LITERAL_BEGIN,
    };

    /** Token types for assignment operations. */
    private static final int[] ASSIGNMENTS = {
        TokenTypes.ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BOR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.BXOR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MINUS_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.SL_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.STAR_ASSIGN,
    };

    /** Token types for conditional and relational operators. */
    private static final int[] CONDITIONALS_AND_RELATIONAL = {
        TokenTypes.LOR,
        TokenTypes.LAND,
        TokenTypes.LITERAL_INSTANCEOF,
        TokenTypes.GT,
        TokenTypes.LT,
        TokenTypes.GE,
        TokenTypes.LE,
        TokenTypes.EQUAL,
        TokenTypes.NOT_EQUAL,
    };

    /** Token types for unary and postfix operators. */
    private static final int[] UNARY_AND_POSTFIX = {
        TokenTypes.UNARY_MINUS,
        TokenTypes.UNARY_PLUS,
        TokenTypes.INC,
        TokenTypes.DEC,
        TokenTypes.LNOT,
        TokenTypes.BNOT,
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
    };

    /**
     * Used to test if logging a warning in a parent node may be skipped
     * because a warning was already logged on an immediate child node.
     */
    private DetailAST parentToSkip;
    /** Depth of nested assignments.  Normally this will be 0 or 1. */
    private int assignDepth;

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.EXPR,
            TokenTypes.IDENT,
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_NULL,
            TokenTypes.LITERAL_FALSE,
            TokenTypes.LITERAL_TRUE,
            TokenTypes.ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LAMBDA,
            TokenTypes.TEXT_BLOCK_LITERAL_BEGIN,
            TokenTypes.LAND,
            TokenTypes.LOR,
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.GT,
            TokenTypes.LT,
            TokenTypes.GE,
            TokenTypes.LE,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.LNOT,
            TokenTypes.BNOT,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.EXPR,
            TokenTypes.IDENT,
            TokenTypes.NUM_DOUBLE,
            TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_INT,
            TokenTypes.NUM_LONG,
            TokenTypes.STRING_LITERAL,
            TokenTypes.LITERAL_NULL,
            TokenTypes.LITERAL_FALSE,
            TokenTypes.LITERAL_TRUE,
            TokenTypes.ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.LAMBDA,
            TokenTypes.TEXT_BLOCK_LITERAL_BEGIN,
            TokenTypes.LAND,
            TokenTypes.LOR,
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.GT,
            TokenTypes.LT,
            TokenTypes.GE,
            TokenTypes.LE,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.LNOT,
            TokenTypes.BNOT,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        // Check can work with any of acceptable tokens
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    // -@cs[CyclomaticComplexity] All logs should be in visit token.
    @Override
    public void visitToken(DetailAST ast) {
        final int type = ast.getType();
        final DetailAST parent = ast.getParent();

        if (type == TokenTypes.LAMBDA && isLambdaSingleParameterSurrounded(ast)) {
            log(ast, MSG_LAMBDA, ast.getText());
        }
        else if (type != TokenTypes.ASSIGN
            || parent.getType() != TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
            final boolean surrounded = isSurrounded(ast);
            // An identifier surrounded by parentheses.
            if (surrounded && type == TokenTypes.IDENT) {
                parentToSkip = ast.getParent();
                log(ast, MSG_IDENT, ast.getText());
            }
            // A literal (numeric or string) surrounded by parentheses.
            else if (surrounded && TokenUtil.isOfType(type, LITERALS)) {
                parentToSkip = ast.getParent();
                if (type == TokenTypes.STRING_LITERAL) {
                    log(ast, MSG_STRING,
                        chopString(ast.getText()));
                }
                else if (type == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) {
                    // Strip newline control characters to keep message as single-line, add
                    // quotes to make string consistent with STRING_LITERAL
                    final String logString = QUOTE
                        + NEWLINE.matcher(
                            ast.getFirstChild().getText()).replaceAll("\\\\n")
                        + QUOTE;
                    log(ast, MSG_STRING, chopString(logString));
                }
                else {
                    log(ast, MSG_LITERAL, ast.getText());
                }
            }
            // The rhs of an assignment surrounded by parentheses.
            else if (TokenUtil.isOfType(type, ASSIGNMENTS)) {
                assignDepth++;
                final DetailAST last = ast.getLastChild();
                if (last.getType() == TokenTypes.RPAREN) {
                    log(ast, MSG_ASSIGN);
                }
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        final int type = ast.getType();
        final DetailAST parent = ast.getParent();

        // shouldn't process assign in annotation pairs
        if (type != TokenTypes.ASSIGN
            || parent.getType() != TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
            if (type == TokenTypes.EXPR) {
                checkExpression(ast);
            }
            else if (TokenUtil.isOfType(type, ASSIGNMENTS)) {
                assignDepth--;
            }
            else if (isSurrounded(ast) && unnecessaryParenAroundOperators(ast)) {
                log(ast.getPreviousSibling(), MSG_EXPR);
            }
        }
    }

    /**
     * Tests if the given {@code DetailAST} is surrounded by parentheses.
     * In short, does {@code ast} have a previous sibling whose type is
     * {@code TokenTypes.LPAREN} and a next sibling whose type is {@code
     * TokenTypes.RPAREN}.
     *
     * @param ast the {@code DetailAST} to check if it is surrounded by
     *        parentheses.
     * @return {@code true} if {@code ast} is surrounded by
     *         parentheses.
     */
    private static boolean isSurrounded(DetailAST ast) {
        // if previous sibling is left parenthesis,
        // next sibling can't be other than right parenthesis
        final DetailAST prev = ast.getPreviousSibling();
        return prev != null && prev.getType() == TokenTypes.LPAREN;
    }

    /**
     * Tests if the given expression node is surrounded by parentheses.
     *
     * @param ast a {@code DetailAST} whose type is
     *        {@code TokenTypes.EXPR}.
     * @return {@code true} if the expression is surrounded by
     *         parentheses.
     */
    private static boolean isExprSurrounded(DetailAST ast) {
        return ast.getFirstChild().getType() == TokenTypes.LPAREN;
    }

    /**
     * Checks whether an expression is surrounded by parentheses.
     *
     * @param ast the {@code DetailAST} to check if it is surrounded by
     *        parentheses.
     */
    private void checkExpression(DetailAST ast) {
        // If 'parentToSkip' == 'ast', then we've already logged a
        // warning about an immediate child node in visitToken, so we don't
        // need to log another one here.
        if (parentToSkip != ast && isExprSurrounded(ast)) {
            if (ast.getParent().getType() == TokenTypes.LITERAL_RETURN) {
                log(ast, MSG_RETURN);
            }
            else if (assignDepth >= 1) {
                log(ast, MSG_ASSIGN);
            }
            else {
                log(ast, MSG_EXPR);
            }
        }

        parentToSkip = null;
    }

    /**
     * Checks if conditional, relational, unary and postfix operators
     * in expressions are surrounded by unnecessary parentheses.
     *
     * @param ast the {@code DetailAST} to check if it is surrounded by
     *        unnecessary parentheses.
     * @return {@code true} if the expression is surrounded by
     *         unnecessary parentheses.
     */
    private static boolean unnecessaryParenAroundOperators(DetailAST ast) {
        final int type = ast.getType();
        final int parentType = ast.getParent().getType();
        final boolean isConditional = TokenUtil.isOfType(type, CONDITIONALS_AND_RELATIONAL);
        boolean result = TokenUtil.isOfType(parentType, CONDITIONALS_AND_RELATIONAL);
        if (isConditional) {
            if (type == TokenTypes.LOR) {
                result = result && !TokenUtil.isOfType(parentType, TokenTypes.LAND);
            }
            result = result && !TokenUtil.isOfType(parentType, TokenTypes.EQUAL,
                TokenTypes.NOT_EQUAL);
        }
        else {
            result = result && TokenUtil.isOfType(type, UNARY_AND_POSTFIX);
        }
        return result;
    }

    /**
     * Tests if the given lambda node has a single parameter, no defined type, and is surrounded
     * by parentheses.
     *
     * @param ast a {@code DetailAST} whose type is
     *        {@code TokenTypes.LAMBDA}.
     * @return {@code true} if the lambda has a single parameter, no defined type, and is
     *         surrounded by parentheses.
     */
    private static boolean isLambdaSingleParameterSurrounded(DetailAST ast) {
        final DetailAST firstChild = ast.getFirstChild();
        boolean result = false;
        if (firstChild != null && firstChild.getType() == TokenTypes.LPAREN) {
            final DetailAST parameters = firstChild.getNextSibling();
            if (parameters.getChildCount(TokenTypes.PARAMETER_DEF) == 1
                    && !parameters.getFirstChild().findFirstToken(TokenTypes.TYPE).hasChildren()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns the specified string chopped to {@code MAX_QUOTED_LENGTH}
     * plus an ellipsis (...) if the length of the string exceeds {@code
     * MAX_QUOTED_LENGTH}.
     *
     * @param value the string to potentially chop.
     * @return the chopped string if {@code string} is longer than
     *         {@code MAX_QUOTED_LENGTH}; otherwise {@code string}.
     */
    private static String chopString(String value) {
        String result = value;
        if (value.length() > MAX_QUOTED_LENGTH) {
            result = value.substring(0, MAX_QUOTED_LENGTH) + "...\"";
        }
        return result;
    }

}
