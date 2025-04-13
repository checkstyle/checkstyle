////
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;
import java.util.function.UnaryOperator;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the policy on how to wrap lines on
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html">
 * operators</a>.
 * </div>
 *
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-15.html#jls-15.20.2">
 * Java Language Specification</a> for more information about {@code instanceof} operator.
 * </p>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to wrap lines.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.WrapOption}.
 * Default value is {@code nl}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COLON">
 * COLON</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EQUAL">
 * EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#NOT_EQUAL">
 * NOT_EQUAL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DIV">
 * DIV</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#PLUS">
 * PLUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MINUS">
 * MINUS</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STAR">
 * STAR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#MOD">
 * MOD</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SR">
 * SR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BSR">
 * BSR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GE">
 * GE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#GT">
 * GT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SL">
 * SL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LE">
 * LE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LT">
 * LT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BXOR">
 * BXOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BOR">
 * BOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LOR">
 * LOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#BAND">
 * BAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAND">
 * LAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#TYPE_EXTENSION_AND">
 * TYPE_EXTENSION_AND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_INSTANCEOF">
 * LITERAL_INSTANCEOF</a>.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code line.new}
 * </li>
 * <li>
 * {@code line.previous}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class OperatorWrapCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_NEW = "line.new";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_LINE_PREVIOUS = "line.previous";

    /** Specify policy on how to wrap lines. */
    private WrapOption option = WrapOption.NL;

    /**
     * Setter to specify policy on how to wrap lines.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 3.0
     */
    public void setOption(String optionStr) {
        option = WrapOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              // '+' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.LITERAL_INSTANCEOF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              // '+' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPE_EXTENSION_AND,
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      // "-="
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.BAND_ASSIGN,       // "&="
            TokenTypes.METHOD_REF,        // "::"
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isTargetNode(ast)) {
            if (option == WrapOption.NL && isNewLineModeViolation(ast)) {
                log(ast, MSG_LINE_NEW, ast.getText());
            }
            else if (option == WrapOption.EOL && isEndOfLineModeViolation(ast)) {
                log(ast, MSG_LINE_PREVIOUS, ast.getText());
            }
        }
    }

    /**
     * Filters some false tokens that this check should ignore.
     *
     * @param node the node to check
     * @return {@code true} for all nodes this check should validate
     */
    private static boolean isTargetNode(DetailAST node) {
        final boolean result;
        if (node.getType() == TokenTypes.COLON) {
            result = !isColonFromLabel(node);
        }
        else if (node.getType() == TokenTypes.STAR) {
            // Unlike the import statement, the multiply operator always has children
            result = node.hasChildren();
        }
        else {
            result = true;
        }
        return result;
    }

    /**
     * Checks whether operator violates {@link WrapOption#NL} mode.
     *
     * @param ast the DetailAst of an operator
     * @return {@code true} if mode does not match
     */
    private static boolean isNewLineModeViolation(DetailAST ast) {
        return TokenUtil.areOnSameLine(ast, getLeftNode(ast))
            && !TokenUtil.areOnSameLine(ast, getRightNode(ast));
    }

    /**
     * Checks whether operator violates {@link WrapOption#EOL} mode.
     *
     * @param ast the DetailAst of an operator
     * @return {@code true} if mode does not match
     */
    private static boolean isEndOfLineModeViolation(DetailAST ast) {
        return !TokenUtil.areOnSameLine(ast, getLeftNode(ast));
    }

    /**
     * Checks if a node is {@link TokenTypes#COLON} from a label, switch case of default.
     *
     * @param node the node to check
     * @return {@code true} if node matches
     */
    private static boolean isColonFromLabel(DetailAST node) {
        return TokenUtil.isOfType(node.getParent(), TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks if a node is {@link TokenTypes#ASSIGN} to a variable or resource.
     *
     * @param node the node to check
     * @return {@code true} if node matches
     */
    private static boolean isAssignToVariable(DetailAST node) {
        return TokenUtil.isOfType(node.getParent(), TokenTypes.VARIABLE_DEF, TokenTypes.RESOURCE);
    }

    /**
     * Returns the left neighbour of a binary operator. This is the rightmost
     * grandchild of the left child or sibling. For the assign operator the return value is
     * the variable name.
     *
     * @param node the binary operator
     * @return nearest node from left
     */
    private static DetailAST getLeftNode(DetailAST node) {
        DetailAST result;
        if (node.getFirstChild() == null || isAssignToVariable(node)) {
            result = node.getPreviousSibling();
        }
        else if (isInPatternDefinition(node)) {
            result = node.getFirstChild();
        }
        else {
            result = adjustParens(node.getFirstChild(), DetailAST::getNextSibling);
        }
        while (result.getLastChild() != null) {
            result = result.getLastChild();
        }
        return result;
    }

    /**
     * Ascends AST to determine if given node is part of a pattern
     * definition.
     *
     * @param node the node to check
     * @return true if node is in pattern definition
     */
    private static boolean isInPatternDefinition(DetailAST node) {
        DetailAST parent = node;
        final int[] tokensToStopOn = {
            // token we are looking for
            TokenTypes.PATTERN_DEF,
            // tokens that mean we can stop looking
            TokenTypes.EXPR,
            TokenTypes.RESOURCE,
            TokenTypes.COMPILATION_UNIT,
        };

        do {
            parent = parent.getParent();
        } while (!TokenUtil.isOfType(parent, tokensToStopOn));
        return parent.getType() == TokenTypes.PATTERN_DEF;
    }

    /**
     * Returns the right neighbour of a binary operator. This is the leftmost
     * grandchild of the right child or sibling. For the ternary operator this
     * is the node between {@code ?} and {@code :} .
     *
     * @param node the binary operator
     * @return nearest node from right
     */
    private static DetailAST getRightNode(DetailAST node) {
        DetailAST result;
        if (node.getLastChild() == null) {
            result = node.getNextSibling();
        }
        else {
            final DetailAST rightNode;
            if (node.getType() == TokenTypes.QUESTION) {
                rightNode = node.findFirstToken(TokenTypes.COLON).getPreviousSibling();
            }
            else {
                rightNode = node.getLastChild();
            }
            result = adjustParens(rightNode, DetailAST::getPreviousSibling);
        }

        if (!TokenUtil.isOfType(result, TokenTypes.ARRAY_INIT, TokenTypes.ANNOTATION_ARRAY_INIT)) {
            while (result.getFirstChild() != null) {
                result = result.getFirstChild();
            }
        }
        return result;
    }

    /**
     * Finds matching parentheses among siblings. If the given node is not
     * {@link TokenTypes#LPAREN} nor {@link TokenTypes#RPAREN}, the method adjusts nothing.
     * This method is for handling case like {@code
     *   (condition && (condition
     *     || condition2 || condition3) && condition4
     *     && condition3)
     * }
     *
     * @param node the node to adjust
     * @param step the node transformer, should be {@link DetailAST#getPreviousSibling}
     *             or {@link DetailAST#getNextSibling}
     * @return adjusted node
     */
    private static DetailAST adjustParens(DetailAST node, UnaryOperator<DetailAST> step) {
        DetailAST result = node;
        int accumulator = 0;
        while (true) {
            if (result.getType() == TokenTypes.LPAREN) {
                accumulator--;
            }
            else if (result.getType() == TokenTypes.RPAREN) {
                accumulator++;
            }
            if (accumulator == 0) {
                break;
            }
            result = step.apply(result);
        }
        return result;
    }

}
