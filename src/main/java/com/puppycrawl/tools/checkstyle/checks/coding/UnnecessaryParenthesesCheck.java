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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks if unnecessary parentheses are used in a statement or expression.
 * The check will flag the following with warnings:
 * </div>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * return (x);          // parens around identifier
 * return (x + 1);      // parens around return value
 * int x = (y / 2 + 1); // parens around assignment rhs
 * for (int i = (0); i &lt; 10; i++) {  // parens around literal
 * t -= (z + 1);                     // parens around assignment rhs
 * boolean a = (x &gt; 7 &amp;&amp; y &gt; 5)      // parens around expression
 *             || z &lt; 9;
 * boolean b = (~a) &gt; -27            // parens around ~a
 *             &amp;&amp; (a-- &lt; 30);        // parens around expression
 * </code></pre></div>
 *
 * <p>
 * Notes:
 * The check is not "type aware", that is to say, it can't tell if parentheses
 * are unnecessary based on the types in an expression. The check is partially aware about
 * operator precedence but unaware about operator associativity.
 * It won't catch cases such as:
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * int x = (a + b) + c; // 1st Case
 * boolean p = true; // 2nd Case
 * int q = 4;
 * int r = 3;
 * if (p == (q &lt;= r)) {}
 * </code></pre></div>
 *
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
 *
 * <p>
 * The partial support for operator precedence includes cases of the following type:
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
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
 * </code></pre></div>
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

    /** Token types for conditional operators. */
    private static final int[] CONDITIONAL_OPERATOR = {
        TokenTypes.LOR,
        TokenTypes.LAND,
    };

    /** Token types for relation operator. */
    private static final int[] RELATIONAL_OPERATOR = {
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

    /** Types of tokens with higher priority than unary operators. */
    private static final int[] ARRAY_AND_FIELD_ACCESS = {
        TokenTypes.INDEX_OP,
        TokenTypes.DOT,
        TokenTypes.LITERAL_NEW,
    };

    /** Token types for bitwise binary operator. */
    private static final int[] BITWISE_BINARY_OPERATORS = {
        TokenTypes.BXOR,
        TokenTypes.BOR,
        TokenTypes.BAND,
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
            TokenTypes.INDEX_OP,
            TokenTypes.DOT,
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
            TokenTypes.BXOR,
            TokenTypes.BOR,
            TokenTypes.BAND,
            TokenTypes.QUESTION,
            TokenTypes.INDEX_OP,
            TokenTypes.DOT,
            TokenTypes.LITERAL_NEW,
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
        final DetailAST parent = ast.getParent();

        if (isLambdaSingleParameterSurrounded(ast)) {
            log(ast, MSG_LAMBDA);
        }
        else if (ast.getType() == TokenTypes.QUESTION) {
            getParenthesesChildrenAroundQuestion(ast)
                .forEach(unnecessaryChild -> log(unnecessaryChild, MSG_EXPR));
        }
        else if (parent.getType() != TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
            final int type = ast.getType();
            final boolean surrounded = isSurrounded(getSelfOrParentMethodCall(ast));
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
        DetailAST selfOrParentMethodCall = getSelfOrParentMethodCall(ast);
        if (type != TokenTypes.ASSIGN
            || parent.getType() != TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR) {
            if (type == TokenTypes.EXPR) {
                checkExpression(ast);
            }
            else if (TokenUtil.isOfType(type, ASSIGNMENTS)) {
                assignDepth--;
            }
            else if (isSurrounded(selfOrParentMethodCall) && unnecessaryParenAroundOperators(ast)) {
                log(selfOrParentMethodCall.getPreviousSibling(), MSG_EXPR);
            }
        }
    }

    private DetailAST getSelfOrParentMethodCall(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.METHOD_CALL
            ? ast.getParent() : ast;
    }

    /**
     * Tests if the given {@code DetailAST} is surrounded by parentheses.
     *
     * @param ast the {@code DetailAST} to check if it is surrounded by
     *        parentheses.
     * @return {@code true} if {@code ast} is surrounded by
     *         parentheses.
     */
    private static boolean isSurrounded(DetailAST ast) {
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
    }

    /**
     * Checks if conditional, relational, bitwise binary operator, unary and postfix operators
     * in expressions are surrounded by unnecessary parentheses.
     *
     * @param ast the {@code DetailAST} to check if it is surrounded by
     *        unnecessary parentheses.
     * @return {@code true} if the expression is surrounded by
     *         unnecessary parentheses.
     */
    private static boolean unnecessaryParenAroundOperators(DetailAST ast) {
        final int type = ast.getType();
        final boolean isConditionalOrRelational = TokenUtil.isOfType(type, CONDITIONAL_OPERATOR)
                        || TokenUtil.isOfType(type, RELATIONAL_OPERATOR);
        final boolean isBitwise = TokenUtil.isOfType(type, BITWISE_BINARY_OPERATORS);
        final boolean hasUnnecessaryParentheses;
        if (isConditionalOrRelational) {
            hasUnnecessaryParentheses = checkConditionalOrRelationalOperator(ast);
        }
        else if (isBitwise) {
            hasUnnecessaryParentheses = checkBitwiseBinaryOperator(ast);
        }
        else if (TokenUtil.isOfType(type, ARRAY_AND_FIELD_ACCESS)) {
            hasUnnecessaryParentheses = isNotFirstArgOfTernary(ast);
        }
        else {
            hasUnnecessaryParentheses = TokenUtil.isOfType(type, UNARY_AND_POSTFIX)
                    && isBitWiseBinaryOrConditionalOrRelationalOperator(ast.getParent().getType());
        }
        return hasUnnecessaryParentheses;
    }

    /**
     * Check that an expression is not the first argument of a conditional ternary operator.
     *
     * @param ast expression
     * @return whether the expression is not the first argument of a ternary operator
     */
    private static boolean isNotFirstArgOfTernary(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        return parent.getParent().getType() != TokenTypes.QUESTION
                || !parent.equals(parent.getParent().getFirstChild().getNextSibling());
    }

    /**
     * Check if conditional or relational operator has unnecessary parentheses.
     *
     * @param ast to check if surrounded by unnecessary parentheses
     * @return true if unnecessary parenthesis
     */
    private static boolean checkConditionalOrRelationalOperator(DetailAST ast) {
        final int type = ast.getType();
        final int parentType = ast.getParent().getType();
        final boolean isParentEqualityOperator =
                TokenUtil.isOfType(parentType, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL);
        final boolean result;
        if (type == TokenTypes.LOR) {
            result = !TokenUtil.isOfType(parentType, TokenTypes.LAND)
                    && !TokenUtil.isOfType(parentType, BITWISE_BINARY_OPERATORS);
        }
        else if (type == TokenTypes.LAND) {
            result = !TokenUtil.isOfType(parentType, BITWISE_BINARY_OPERATORS);
        }
        else {
            result = true;
        }
        return result && !isParentEqualityOperator
                && isBitWiseBinaryOrConditionalOrRelationalOperator(parentType);
    }

    /**
     * Check if bitwise binary operator has unnecessary parentheses.
     *
     * @param ast to check if surrounded by unnecessary parentheses
     * @return true if unnecessary parenthesis
     */
    private static boolean checkBitwiseBinaryOperator(DetailAST ast) {
        final int type = ast.getType();
        final int parentType = ast.getParent().getType();
        final boolean result;
        if (type == TokenTypes.BOR) {
            result = !TokenUtil.isOfType(parentType, TokenTypes.BAND, TokenTypes.BXOR)
                    && !TokenUtil.isOfType(parentType, RELATIONAL_OPERATOR);
        }
        else if (type == TokenTypes.BXOR) {
            result = !TokenUtil.isOfType(parentType, TokenTypes.BAND)
                    && !TokenUtil.isOfType(parentType, RELATIONAL_OPERATOR);
        }
        // we deal with bitwise AND here.
        else {
            result = !TokenUtil.isOfType(parentType, RELATIONAL_OPERATOR);
        }
        return result && isBitWiseBinaryOrConditionalOrRelationalOperator(parentType);
    }

    /**
     * Check if token type is bitwise binary or conditional or relational operator.
     *
     * @param type Token type to check
     * @return true if it is bitwise binary or conditional operator
     */
    private static boolean isBitWiseBinaryOrConditionalOrRelationalOperator(int type) {
        return TokenUtil.isOfType(type, CONDITIONAL_OPERATOR)
                || TokenUtil.isOfType(type, RELATIONAL_OPERATOR)
                || TokenUtil.isOfType(type, BITWISE_BINARY_OPERATORS);
    }

    /**
     * Tests if the given node has a single parameter, no defined type, and is surrounded
     * by parentheses. This condition can only be true for lambdas.
     *
     * @param ast a {@code DetailAST} node
     * @return {@code true} if the lambda has a single parameter, no defined type, and is
     *         surrounded by parentheses.
     */
    private static boolean isLambdaSingleParameterSurrounded(DetailAST ast) {
        final DetailAST firstChild = ast.getFirstChild();
        boolean result = false;
        if (TokenUtil.isOfType(firstChild, TokenTypes.LPAREN)) {
            final DetailAST parameters = firstChild.getNextSibling();
            if (parameters.getChildCount(TokenTypes.PARAMETER_DEF) == 1
                    && !parameters.getFirstChild().findFirstToken(TokenTypes.TYPE).hasChildren()) {
                result = true;
            }
        }
        return result;
    }

    /**
     *  Returns the direct LPAREN tokens children to a given QUESTION token which
     *  contain an expression not a literal variable.
     *
     *  @param questionToken {@code DetailAST} question token to be checked
     *  @return the direct children to the given question token which their types are LPAREN
     *          tokens and not contain a literal inside the parentheses
     */
    private static List<DetailAST> getParenthesesChildrenAroundQuestion(DetailAST questionToken) {
        final List<DetailAST> surroundedChildren = new ArrayList<>();
        DetailAST directChild = questionToken.getFirstChild();
        while (directChild != null) {
            if (directChild.getType() == TokenTypes.LPAREN
                    && !TokenUtil.isOfType(directChild.getNextSibling(), LITERALS)) {
                surroundedChildren.add(directChild);
            }
            directChild = directChild.getNextSibling();
        }
        return Collections.unmodifiableList(surroundedChildren);
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
