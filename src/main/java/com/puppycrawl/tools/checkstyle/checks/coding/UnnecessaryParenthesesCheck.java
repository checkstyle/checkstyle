////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks if unnecessary parentheses are used in a statement or expression.
 * The check will flag the following with warnings:
 * </p>
 * <pre>
 *     return (x);          // parens around identifier
 *     return (x + 1);      // parens around return value
 *     int x = (y / 2 + 1); // parens around assignment rhs
 *     for (int i = (0); i &lt; 10; i++) {  // parens around literal
 *     t -= (z + 1);        // parens around assignment rhs</pre>
 * <p>
 * The check is not "type aware", that is to say, it can't tell if parentheses
 * are unnecessary based on the types in an expression.  It also doesn't know
 * about operator precedence and associativity; therefore it won't catch
 * something like
 * </p>
 * <pre>
 *     int x = (a + b) + c;</pre>
 * <p>
 * In the above case, given that <em>a</em>, <em>b</em>, and <em>c</em> are
 * all {@code int} variables, the parentheses around {@code a + b}
 * are not needed.
 * </p>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
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
 * LAMBDA</a>.
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
 *   int square = (a * b); //violation
 *   return (square); //violation
 * }
 * int sumOfSquares = 0;
 * for(int i=(0); i&lt;10; i++){ //violation
 *   int x = (i + 1); //violation
 *   sumOfSquares += (square(x * x)); //violation
 * }
 * double num = (10.0); //violation
 * List&lt;String&gt; list = Arrays.asList(&quot;a1&quot;, &quot;b1&quot;, &quot;c1&quot;);
 * myList.stream()
 *   .filter((s) -&gt; s.startsWith(&quot;c&quot;)) //violation
 *   .forEach(System.out::println);
 * </pre>
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
            else if (surrounded && isInTokenList(type, LITERALS)) {
                parentToSkip = ast.getParent();
                if (type == TokenTypes.STRING_LITERAL) {
                    log(ast, MSG_STRING,
                        chopString(ast.getText()));
                }
                else {
                    log(ast, MSG_LITERAL, ast.getText());
                }
            }
            // The rhs of an assignment surrounded by parentheses.
            else if (isInTokenList(type, ASSIGNMENTS)) {
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
            // An expression is surrounded by parentheses.
            if (type == TokenTypes.EXPR) {
                // If 'parentToSkip' == 'ast', then we've already logged a
                // warning about an immediate child node in visitToken, so we don't
                // need to log another one here.

                if (parentToSkip != ast && isExprSurrounded(ast)) {
                    if (assignDepth >= 1) {
                        log(ast, MSG_ASSIGN);
                    }
                    else if (ast.getParent().getType() == TokenTypes.LITERAL_RETURN) {
                        log(ast, MSG_RETURN);
                    }
                    else {
                        log(ast, MSG_EXPR);
                    }
                }

                parentToSkip = null;
            }
            else if (isInTokenList(type, ASSIGNMENTS)) {
                assignDepth--;
            }
        }
    }

    /**
     * Tests if the given {@code DetailAST} is surrounded by parentheses.
     * In short, does {@code ast} have a previous sibling whose type is
     * {@code TokenTypes.LPAREN} and a next sibling whose type is {@code
     * TokenTypes.RPAREN}.
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
     * @param ast a {@code DetailAST} whose type is
     *        {@code TokenTypes.EXPR}.
     * @return {@code true} if the expression is surrounded by
     *         parentheses.
     */
    private static boolean isExprSurrounded(DetailAST ast) {
        return ast.getFirstChild().getType() == TokenTypes.LPAREN;
    }

    /**
     * Tests if the given lambda node has a single parameter, no defined type, and is surrounded
     * by parentheses.
     * @param ast a {@code DetailAST} whose type is
     *        {@code TokenTypes.LAMBDA}.
     * @return {@code true} if the lambda has a single parameter, no defined type, and is
     *         surrounded by parentheses.
     */
    private static boolean isLambdaSingleParameterSurrounded(DetailAST ast) {
        final DetailAST firstChild = ast.getFirstChild();
        boolean result = false;
        if (firstChild.getType() == TokenTypes.LPAREN) {
            final DetailAST parameters = firstChild.getNextSibling();
            if (parameters.getChildCount(TokenTypes.PARAMETER_DEF) == 1
                    && !parameters.getFirstChild().findFirstToken(TokenTypes.TYPE).hasChildren()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Check if the given token type can be found in an array of token types.
     * @param type the token type.
     * @param tokens an array of token types to search.
     * @return {@code true} if {@code type} was found in {@code
     *         tokens}.
     */
    private static boolean isInTokenList(int type, int... tokens) {
        // NOTE: Given the small size of the two arrays searched, I'm not sure
        //       it's worth bothering with doing a binary search or using a
        //       HashMap to do the searches.

        boolean found = false;
        for (int i = 0; i < tokens.length && !found; i++) {
            found = tokens[i] == type;
        }
        return found;
    }

    /**
     * Returns the specified string chopped to {@code MAX_QUOTED_LENGTH}
     * plus an ellipsis (...) if the length of the string exceeds {@code
     * MAX_QUOTED_LENGTH}.
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
