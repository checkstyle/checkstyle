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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Locale;
import java.util.Map;
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

    /** Precedence level for method reference operator. */
    private static final int PRECEDENCE_METHOD_REF = 1;

    /** Precedence level for multiplicative operators (*, /, %). */
    private static final int PRECEDENCE_MULTIPLICATIVE = 2;

    /** Precedence level for additive operators (+, -). */
    private static final int PRECEDENCE_ADDITIVE = 3;

    /** Precedence level for shift operators (<<, >>, >>>). */
    private static final int PRECEDENCE_SHIFT = 4;

    /** Precedence level for relational operators (<, >, <=, >=, instanceof). */
    private static final int PRECEDENCE_RELATIONAL = 5;

    /** Precedence level for equality operators (==, !=). */
    private static final int PRECEDENCE_EQUALITY = 6;

    /** Precedence level for bitwise AND (&). */
    private static final int PRECEDENCE_BITWISE_AND = 7;

    /** Precedence level for bitwise XOR (^). */
    private static final int PRECEDENCE_BITWISE_XOR = 8;

    /** Precedence level for bitwise OR (|). */
    private static final int PRECEDENCE_BITWISE_OR = 9;

    /** Precedence level for logical AND (&&). */
    private static final int PRECEDENCE_LOGICAL_AND = 10;

    /** Precedence level for logical OR (||). */
    private static final int PRECEDENCE_LOGICAL_OR = 11;

    /** Precedence level for ternary/conditional operators (?, :). */
    private static final int PRECEDENCE_TERNARY = 12;

    /** Precedence level for assignment operators (=, +=, -=, etc.). */
    private static final int PRECEDENCE_ASSIGNMENT = 13;

    /** Precedence level for lambda operator (->). */
    private static final int PRECEDENCE_LAMBDA = 14;

    /** Maps operator token types to their relative precedence levels. */
    private static final Map<Integer, Integer> PRECEDENCE_MAP = Map.ofEntries(
        Map.entry(TokenTypes.METHOD_REF, PRECEDENCE_METHOD_REF),
        Map.entry(TokenTypes.STAR, PRECEDENCE_MULTIPLICATIVE),
        Map.entry(TokenTypes.DIV, PRECEDENCE_MULTIPLICATIVE),
        Map.entry(TokenTypes.MOD, PRECEDENCE_MULTIPLICATIVE),
        Map.entry(TokenTypes.PLUS, PRECEDENCE_ADDITIVE),
        Map.entry(TokenTypes.MINUS, PRECEDENCE_ADDITIVE),
        Map.entry(TokenTypes.SL, PRECEDENCE_SHIFT),
        Map.entry(TokenTypes.SR, PRECEDENCE_SHIFT),
        Map.entry(TokenTypes.BSR, PRECEDENCE_SHIFT),
        Map.entry(TokenTypes.LT, PRECEDENCE_RELATIONAL),
        Map.entry(TokenTypes.GT, PRECEDENCE_RELATIONAL),
        Map.entry(TokenTypes.LE, PRECEDENCE_RELATIONAL),
        Map.entry(TokenTypes.GE, PRECEDENCE_RELATIONAL),
        Map.entry(TokenTypes.LITERAL_INSTANCEOF, PRECEDENCE_RELATIONAL),
        Map.entry(TokenTypes.EQUAL, PRECEDENCE_EQUALITY),
        Map.entry(TokenTypes.NOT_EQUAL, PRECEDENCE_EQUALITY),
        Map.entry(TokenTypes.BAND, PRECEDENCE_BITWISE_AND),
        Map.entry(TokenTypes.BXOR, PRECEDENCE_BITWISE_XOR),
        Map.entry(TokenTypes.BOR, PRECEDENCE_BITWISE_OR),
        Map.entry(TokenTypes.LAND, PRECEDENCE_LOGICAL_AND),
        Map.entry(TokenTypes.LOR, PRECEDENCE_LOGICAL_OR),
        Map.entry(TokenTypes.QUESTION, PRECEDENCE_TERNARY),
        Map.entry(TokenTypes.COLON, PRECEDENCE_TERNARY),
        Map.entry(TokenTypes.ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.PLUS_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.MINUS_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.STAR_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.DIV_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.MOD_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.SR_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.BSR_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.SL_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.BAND_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.BXOR_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.BOR_ASSIGN, PRECEDENCE_ASSIGNMENT),
        Map.entry(TokenTypes.LAMBDA, PRECEDENCE_LAMBDA)
    );

    /** Specify policy on how to wrap lines. */
    private WrapOption option = WrapOption.NL;

    /**
     * Control whether to enforce the higher-level wrap preference from the OpenJDK
     * Java Style Guide.
     */
    private boolean higherLevelWrap;

    /**
     * Creates a new {@code OperatorWrapCheck} instance.
     */
    public OperatorWrapCheck() {
        // no code by default
    }

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

    /**
     * Setter to control whether to enforce the higher-level wrap preference.
     *
     * @param higherLevelWrap whether to enforce higher-level wrapping
     * @since 14.2.0
     */
    public void setHigherLevelWrap(boolean higherLevelWrap) {
        this.higherLevelWrap = higherLevelWrap;
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
            TokenTypes.LAMBDA,            // "->"
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
            else if (higherLevelWrap) {
                checkHigherLevelWrapViolation(ast);
            }
        }
    }

    /**
     * Checks if the operator is wrapped at a lower syntactical level
     * when a higher-level operator was available on the same line.
     *
     * @param ast the operator node
     */
    private void checkHigherLevelWrapViolation(DetailAST ast) {
        final DetailAST leftNode = getLeftNode(ast);
        final DetailAST rightNode = getRightNode(ast);
        final boolean isWrapped = !TokenUtil.areOnSameLine(ast, leftNode)
                || !TokenUtil.areOnSameLine(ast, rightNode);

        if (isWrapped) {
            checkHigherLevelWrapAncestor(ast, leftNode, rightNode);
        }
    }

    /**
     * Checks if a higher-level ancestor was available for wrapping.
     *
     * @param ast the operator node
     * @param leftNode the left node of the operator
     * @param rightNode the right node of the operator
     */
    private void checkHigherLevelWrapAncestor(DetailAST ast, DetailAST leftNode,
                                              DetailAST rightNode) {
        final int opPrecedence = getPrecedence(ast.getType());

        DetailAST current = ast.getParent();
        while (current != null) {
            final int type = current.getType();

            if (isAcceptableToken(type)
                    && !isAssignment(type)
                    && getPrecedence(type) != opPrecedence
                    && isValidHigherLevelWrap(current, ast, leftNode, rightNode)) {
                final String msg;
                if (option == WrapOption.NL) {
                    msg = MSG_LINE_NEW;
                }
                else {
                    msg = MSG_LINE_PREVIOUS;
                }
                log(current, msg, current.getText());
                break;
            }
            else if (!isTransparentContainer(type)) {
                break;
            }
            current = current.getParent();
        }
    }

    /**
     * Checks if an ancestor node was a valid higher-level candidate for wrapping.
     *
     * @param ancestor the ancestor node
     * @param opNode the original operator node
     * @param leftNode the left node of the operator
     * @param rightNode the right node of the operator
     * @return true if the ancestor was a valid higher-level wrap candidate
     */
    private static boolean isValidHigherLevelWrap(DetailAST ancestor, DetailAST opNode,
                                                  DetailAST leftNode, DetailAST rightNode) {
        final DetailAST parentLeft = getLeftNode(ancestor);
        final DetailAST parentRight = getRightNode(ancestor);
        boolean result = false;
        final boolean parentWrapped = !TokenUtil.areOnSameLine(ancestor, parentLeft)
                || !TokenUtil.areOnSameLine(ancestor, parentRight);

        if (!parentWrapped) {
            final int parentLine = ancestor.getLineNo();
            if (parentLine == opNode.getLineNo() || parentLine == leftLine(leftNode)
                    || parentLine == rightNode.getLineNo()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Helper to get the line number of left node.
     *
     * @param leftNode the left node
     * @return the line number
     */
    private static int leftLine(DetailAST leftNode) {
        return leftNode.getLineNo();
    }

    /**
     * Checks if the token type is an acceptable token for this check.
     *
     * @param type the token type
     * @return true if it is an acceptable token
     */
    private boolean isAcceptableToken(int type) {
        boolean found = false;
        for (int tokenType : getAcceptableTokens()) {
            if (tokenType == type) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Checks if the token type is an assignment operator.
     * Assignment operators are not considered for higher-level wrapping.
     *
     * @param type the token type
     * @return true if it is an assignment operator
     */
    private static boolean isAssignment(int type) {
        return TokenUtil.isOfType(type,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.BOR_ASSIGN);
    }

    /**
     * Checks if a token type is a transparent container node.
     *
     * @param type the token type
     * @return true if transparent
     */
    private static boolean isTransparentContainer(int type) {
        return type == TokenTypes.EXPR;
    }

    /**
     * Gets the precedence level of a given operator token.
     * Lower number means higher precedence (tighter binding).
     *
     * @param type the token type
     * @return the precedence level, or -1 if not an operator
     */
    private static int getPrecedence(int type) {
        final Integer precedence = PRECEDENCE_MAP.get(type);
        final int result;
        if (precedence == null) {
            result = -1;
        }
        else {
            result = precedence;
        }
        return result;
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
        else {
            result = adjustParens(node.getFirstChild(), DetailAST::getNextSibling);
        }
        while (result.getLastChild() != null) {
            result = result.getLastChild();
        }
        return result;
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
