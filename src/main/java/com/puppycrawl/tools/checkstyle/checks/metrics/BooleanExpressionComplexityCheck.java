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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Restricts the number of boolean operators ({@code &&}, {@code ||},
 * {@code &}, {@code |} and {@code ^}) in an expression.
 * </div>
 *
 * <p>
 * Rationale: Too many conditions leads to code that is difficult to read
 * and hence debug and maintain.
 * </p>
 *
 * <p>
 * Note that the operators {@code &} and {@code |} are not only integer bitwise
 * operators, they are also the
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-15.html#jls-15.22.2">
 * non-shortcut versions</a> of the boolean operators {@code &&} and {@code ||}.
 * </p>
 *
 * <p>
 * Note that {@code &}, {@code |} and {@code ^} are not checked if they are part
 * of constructor or method call because they can be applied to non-boolean
 * variables and Checkstyle does not know types of methods from different classes.
 * </p>
 *
 * @since 3.4
 */
@FileStatefulCheck
public final class BooleanExpressionComplexityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "booleanExpressionComplexity";

    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 3;

    /** Stack of contexts. */
    private final Deque<Context> contextStack = new ArrayDeque<>();
    /** Specify the maximum number of boolean operations allowed in one expression. */
    private int max;
    /**
     * Control whether a flat, uniform chain of the same boolean operator counts
     * as a single unit of complexity instead of one unit per operator.
     */
    private boolean treatUniformExpressionsAsOne = true;

    /** Current context. */
    private Context context = new Context(false);

    /** Creates new instance of the check. */
    public BooleanExpressionComplexityCheck() {
        max = DEFAULT_MAX;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
            TokenTypes.BAND,
            TokenTypes.LOR,
            TokenTypes.BOR,
            TokenTypes.BXOR,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
            TokenTypes.BAND,
            TokenTypes.LOR,
            TokenTypes.BOR,
            TokenTypes.BXOR,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    /**
     * Setter to specify the maximum number of boolean operations allowed in one expression.
     *
     * @param max new maximum allowed complexity.
     * @since 3.4
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Setter to control whether a flat, uniform chain of the same boolean operator
     * counts as a single unit of complexity instead of one unit per operator.
     *
     * @param treatUniformExpressionsAsOne whether to treat
     *     uniform operator chains as one.
     * @since 14.2.0
     */
    public void setTreatUniformExpressionsAsOne(boolean treatUniformExpressionsAsOne) {
        this.treatUniformExpressionsAsOne = treatUniformExpressionsAsOne;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF,
                 TokenTypes.METHOD_DEF,
                 TokenTypes.COMPACT_CTOR_DEF -> visitMethodDef(ast);

            case TokenTypes.EXPR -> visitExpr();

            case TokenTypes.LAND,
                 TokenTypes.LOR,
                 TokenTypes.BAND,
                 TokenTypes.BOR,
                 TokenTypes.BXOR -> visitBooleanOperator(ast);

            default -> throw new IllegalArgumentException("Unknown type: " + ast);
        }
    }

    /**
     * Visits a boolean operator node and adds its complexity to the current context.
     *
     * @param ast the boolean operator node.
     */
    private void visitBooleanOperator(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        if (!isBooleanOperatorType(parent.getType())) {
            context.addComplexity(complexityOf(ast));
        }
    }

    /**
     * Computes the total complexity contribution of the subtree rooted at a boolean operator node.
     * {@code treatUniformExpressionsAsOne} is enabled.
     *
     * @param ast a boolean operator node.
     * @return the complexity contribution of this node and all its descendants.
     */
    private int complexityOf(DetailAST ast) {
        final int result;
        if (isCountable(ast)) {
            final boolean uniformChain =
                    treatUniformExpressionsAsOne && isUniformChain(ast);
            if (uniformChain) {
                result = 1;
            }
            else {
                result = 1 + childComplexity(ast);
            }
        }
        else {
            result = childComplexity(ast);
        }
        return result;

    }

    /**
     * Sums the complexity contribution of both operands of a binary boolean operator node.
     *
     * @param ast a boolean operator node.
     * @return the summed complexity of both operands.
     */
    private int childComplexity(DetailAST ast) {
        return operandComplexity(leftOperand(ast)) + operandComplexity(rightOperand(ast));
    }

    /**
     * Returns the complexity contribution of a single operand of a boolean operator node.
     *
     * @param operand a possibly-null operand of a boolean-operator node.
     * @return the operand's complexity contribution, or 0 if not applicable.
     */
    private int operandComplexity(DetailAST operand) {
        int result = 0;
        if (operand != null && isBooleanOperatorType(operand.getType())) {
            result = complexityOf(operand);

        }
        return result;
    }

    /**
     * Returns the real left operand of a binary boolean-operator node.
     * Also skipping any leading parentheses.
     *
     * @param ast a binary boolean-operator node.
     * @return the left operand, skipping any wrapping parentheses, or null if none.
     */
    private static DetailAST leftOperand(DetailAST ast) {
        return skipParens(ast.getFirstChild());
    }

    /**
     * Skips over any leading {@code LPAREN} sibling tokens, returning the first
     * non-parenthesis node. Checkstyle's AST preserves redundant parentheses as
     * literal {@code LPAREN}/{@code RPAREN} sibling tokens, so an operand or a
     * sub-expression wrapped in parentheses is not directly the node itself.
     * Shared by {@link #leftOperand} (for boolean-operator operands) and
     * {@link #leafKey} (for an {@code instanceof} node's tested expression),
     * since both need the same skipping behavior.
     *
     * @param ast the node to start from, possibly itself an {@code LPAREN}.
     * @return the first non-parenthesis node, or null if none.
     */
    private static DetailAST skipParens(DetailAST ast) {
        DetailAST child = ast;
        while (child != null && child.getType() == TokenTypes.LPAREN) {
            child = child.getNextSibling();
        }
        return child;
    }

    /**
     * Returns the real right operand of a binary boolean-operator node, skipping
     * the closing parenthesis of a parenthesized left operand and any opening
     * parenthesis of a parenthesized right operand. Returns {@code null} if there
     * is no left operand to begin with, or no sibling follows it.
     *
     * @param ast a binary boolean-operator node.
     * @return the right operand, skipping any wrapping parentheses, or null if none.
     */
    private static DetailAST rightOperand(DetailAST ast) {
        final DetailAST left = leftOperand(ast);
        DetailAST sibling;
        if (left == null) {
            sibling = null;
        }
        else {
            sibling = left.getNextSibling();
        }
        while (sibling != null
                && (sibling.getType() == TokenTypes.RPAREN
                || sibling.getType() == TokenTypes.LPAREN)) {
            sibling = sibling.getNextSibling();
        }
        return sibling;
    }

    /**
     * Checks whether a token type is among the tokens.
     *
     * @param type a token type.
     * @return true if this check is configured to count that token type.
     */
    private boolean isConfiguredToken(int type) {
        boolean result = false;
        for (int token : resolveConfiguredTokens()) {
            if (token == type) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Resolves the tokens this check instance is actually configured to listen for.
     *
     * @return the resolved token types.
     */
    private int[] resolveConfiguredTokens() {
        final Set<String> tokenNames = getTokenNames();
        final int[] result;
        if (tokenNames.isEmpty()) {
            result = getDefaultTokens();
        }
        else {
            result = new int[tokenNames.size()];
            int index = 0;
            for (String name : tokenNames) {
                result[index] = TokenUtil.getTokenId(name);
                index++;
            }
        }
        return result;
    }

    /**
     * Checks whether a boolean operator node should be counted at all.
     *
     * @param ast a boolean operator node.
     * @return true if the node is eligible to be counted.
     */
    private boolean isCountable(DetailAST ast) {
        final boolean result;
        if (isConfiguredToken(ast.getType())) {
            switch (ast.getType()) {
                case TokenTypes.BOR -> result = !isPipeOperator(ast) && !isPassedInParameter(ast);
                case TokenTypes.BAND, TokenTypes.BXOR -> result = !isPassedInParameter(ast);
                default -> result = true;
            }
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Determines whether the maximal flat chain of.
     * the same operator type starting at the given node is "uniform".
     *
     * @param ast the head of a candidate chain.
     * @return true if the whole chain is uniform.
     */
    private static boolean isUniformChain(DetailAST ast) {
        final List<DetailAST> leaves = new ArrayList<>();
        collectChainLeaves(ast, ast.getType(), leaves);
        return haveSameShape(leaves);
    }

    /**
     * Walks down a chain of same-type operator nodes via the left operand only,
     * collecting every operand that is not itself part of the chain as a leaf.
     *
     * @param ast current node in the walk.
     * @param chainType the operator token type identifying the chain.
     * @param leaves accumulator for the chain's leaf operands.
     */
    private static void collectChainLeaves(DetailAST ast, int chainType,
                                           List<DetailAST> leaves) {
        final DetailAST left = leftOperand(ast);
        final DetailAST right = rightOperand(ast);
        if (left.getType() == chainType) {
            collectChainLeaves(left, chainType, leaves);
        }
        else {
            leaves.add(left);
        }
        leaves.add(right);
    }

    /**
     * Checks whether every leaf operand in a chain shares the same shape.
     *
     * @param leaves the chain's leaf operands.
     * @return true if all leaves share the same shape.
     */
    private static boolean haveSameShape(Iterable<DetailAST> leaves) {
        String commonKey = null;
        boolean uniform = true;
        for (DetailAST leaf : leaves) {
            final String key = leafKey(leaf);
            if (key == null) {
                uniform = false;
            }
            if (commonKey == null) {
                commonKey = key;
            }
            else if (!commonKey.equals(key)) {
                uniform = false;
            }
        }
        return uniform;
    }

    /**
     * Computes a shape key for a single leaf operand of a boolean chain.
     *
     * @param leaf a chain leaf operand.
     * @return a shape key, or null if the leaf cannot participate in a uniform chain.
     */
    private static String leafKey(DetailAST leaf) {
        final String key;
        if (isBooleanOperatorType(leaf.getType())) {
            key = null;
        }
        else if (isRelationalType(leaf.getType())) {
            final String canonical = canonicalText(leftOperand(leaf));
            if (canonical == null) {
                key = null;
            }
            else {
                key = "REL:" + canonical;
            }
        }
        else if (leaf.getType() == TokenTypes.METHOD_CALL) {
            final String canonical = canonicalText(leaf);
            if (canonical == null) {
                key = null;
            }
            else {
                key = "CALL:" + canonical;
            }
        }
        else if (leaf.getType() == TokenTypes.LITERAL_INSTANCEOF) {
            final String canonical = canonicalText(skipParens(leaf.getFirstChild()));
            key = "INSTANCEOF:" + canonical;
        }
        else {
            key = "BARE";
        }
        return key;
    }

    /**
     * Reconstructs a canonical textual form of an identifier chain or a
     * (possibly qualified) method call, for comparing left-hand sides of
     * relational expressions, or method call targets, structurally rather than
     * lexically.
     *
     * @param ast the expression to canonicalize.
     * @return canonical text, or null if the shape is not recognized.
     */
    private static String canonicalText(DetailAST ast) {
        final String result;
        if (ast.getType() == TokenTypes.IDENT) {
            result = ast.getText();
        }
        else if (ast.getType() == TokenTypes.DOT) {
            final String leftText = canonicalText(leftOperand(ast));
            if (leftText == null) {
                result = null;
            }
            else {
                final DetailAST right = rightOperand(ast);
                result = leftText + "." + right.getText();
            }
        }
        else if (ast.getType() == TokenTypes.METHOD_CALL) {
            final DetailAST target = ast.getFirstChild();
            final String targetText = canonicalText(target);

            if (targetText == null) {
                result = null;
            }
            else {
                result = targetText + "()";
            }
        }
        else {
            result = null;
        }

        return result;
    }

    /**
     * Checks if a token type is one of the boolean operators this check counts.
     *
     * @param type a token type.
     * @return true if the type is a qualifying boolean operator.
     */
    private static boolean isBooleanOperatorType(int type) {
        return type == TokenTypes.LAND
                || type == TokenTypes.LOR
                || type == TokenTypes.BAND
                || type == TokenTypes.BOR
                || type == TokenTypes.BXOR;
    }

    /**
     * Checks if a token type is a relational or equality operator.
     *
     * @param type a token type.
     * @return true if the type is relational or equality.
     */
    private static boolean isRelationalType(int type) {
        return type == TokenTypes.EQUAL
                || type == TokenTypes.NOT_EQUAL
                || type == TokenTypes.LT
                || type == TokenTypes.GT
                || type == TokenTypes.LE
                || type == TokenTypes.GE;
    }

    /**
     * Checks if logical operator is part of constructor or method call.
     *
     * @param logicalOperator logical operator
     * @return true if logical operator is part of constructor or method call
     */
    private static boolean isPassedInParameter(DetailAST logicalOperator) {
        return logicalOperator.getParent().getParent().getType() == TokenTypes.ELIST;
    }

    /**
     * Checks if {@link TokenTypes#BOR binary OR} is applied to exceptions
     * in
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20">
     * multi-catch</a> (pipe-syntax).
     *
     * @param binaryOr {@code TokenTypes#BOR binary or}
     * @return true if binary or is applied to exceptions in multi-catch.
     */
    private static boolean isPipeOperator(DetailAST binaryOr) {
        return binaryOr.getParent().getType() == TokenTypes.TYPE;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF,
                 TokenTypes.METHOD_DEF,
                 TokenTypes.COMPACT_CTOR_DEF -> leaveMethodDef();

            case TokenTypes.EXPR -> leaveExpr(ast);

            default -> {
                // Do nothing
            }
        }
    }

    /**
     * Creates new context for a given method.
     *
     * @param ast a method we start to check.
     */
    private void visitMethodDef(DetailAST ast) {
        contextStack.push(context);
        final boolean check = !CheckUtil.isEqualsMethod(ast);
        context = new Context(check);
    }

    /** Removes old context. */
    private void leaveMethodDef() {
        context = contextStack.pop();
    }

    /** Creates and pushes new context. */
    private void visitExpr() {
        contextStack.push(context);
        context = new Context(context.isChecking());
    }

    /**
     * Restores previous context.
     *
     * @param ast expression we leave.
     */
    private void leaveExpr(DetailAST ast) {
        context.checkCount(ast);
        context = contextStack.pop();
    }

    /**
     * Represents context (method/expression) in which we check complexity.
     *
     */
    private final class Context {

        /**
         * Should we perform check in current context or not.
         * Usually false if we are inside equals() method.
         */
        private final boolean checking;
        /** Count of boolean operators. */
        private int count;

        /**
         * Creates new instance.
         *
         * @param checking should we check in current context or not.
         */
        private Context(boolean checking) {
            this.checking = checking;
        }

        /**
         * Getter for checking property.
         *
         * @return should we check in current context or not.
         */
        /* package */ boolean isChecking() {
            return checking;
        }

        /**
         * Adds a precomputed complexity contribution to this context's count.
         *
         * @param complexity the contribution to add.
         */
        /* package */ void addComplexity(int complexity) {
            count += complexity;
        }

        /**
         * Checks if we violate maximum allowed complexity.
         *
         * @param ast a node we check now.
         */
        /* package */ void checkCount(DetailAST ast) {
            if (checking && count > max) {
                final DetailAST parentAST = ast.getParent();

                log(parentAST, MSG_KEY, count, max);
            }
        }

    }

}
