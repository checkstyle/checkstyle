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
    private boolean treatUniformSimpleSequentialExpressionsAsOne;

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
            TokenTypes.BAND,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
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
     * @since 13.11.0
     */
    public void setTreatUniformSimpleSequentialExpressionsAsOne(
            boolean treatUniformExpressionsAsOne) {
        treatUniformSimpleSequentialExpressionsAsOne =
                treatUniformExpressionsAsOne;
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
     * Visits a boolean operator node. Only nodes whose parent is not itself a
     * qualifying boolean operator are handled here; such a node is the root of
     * a connected subtree of boolean operators, and its entire complexity
     * contribution (including all descendants) is computed in one pass so
     * descendants are not counted twice when the tree walker visits them later.
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
     * Computes the total complexity contribution of the subtree rooted at a
     * boolean operator node, collapsing uniform chains to 1 when
     * {@code treatUniformSimpleSequentialExpressionsAsOne} is enabled.
     *
     * @param ast a boolean operator node.
     * @return the complexity contribution of this node and all its descendants.
     */
    private int complexityOf(DetailAST ast) {
        final int result;
        if (isCountable(ast)) {
            final boolean uniformChain =
                    treatUniformSimpleSequentialExpressionsAsOne && isUniformChain(ast);
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
     * Sums the complexity contribution of both operands of a binary boolean
     * operator node via {@link #operandComplexity}, recursing into operands
     * that are themselves qualifying boolean operator nodes.
     *
     * @param ast a boolean operator node.
     * @return the summed complexity of both operands.
     */
    private int childComplexity(DetailAST ast) {
        return operandComplexity(leftOperand(ast)) + operandComplexity(rightOperand(ast));
    }

    /**
     * Returns the complexity contribution of a single operand of a boolean
     * operator node: 0 if the operand is null (a genuine, real case for
     * multi-catch pipe nodes -- see the class-level note on {@link #leftOperand})
     * or not itself a qualifying boolean-operator node, otherwise its full
     * recursive complexity via {@link #complexityOf}. The null check and the
     * type check are deliberately kept as separate, nested conditions rather
     * than fused into a single {@code &&} expression: PIT's block-coverage
     * matching can fail to attribute a test to a fused condition's mutants when
     * that test only ever exercises the short-circuited (null) branch, which is
     * exactly the multi-catch scenario this method must handle correctly.
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
     * Returns the real left operand of a binary boolean-operator node, skipping
     * any leading {@code LPAREN} tokens. Checkstyle's AST preserves redundant
     * parentheses as literal {@code LPAREN}/{@code RPAREN} sibling tokens
     * wherever they appear in the source, so an operand wrapped in parentheses
     * is not directly {@code ast.getFirstChild()}. A multi-catch pipe node (e.g.
     * {@code catch (A | B e)}) is a confirmed real case with no operand children
     * at all -- Checkstyle does not attach the individual exception types as
     * children of the {@code BOR} node the way it does for a genuine binary
     * boolean expression -- so this method returns {@code null} rather than
     * throwing when no such operand exists.
     *
     * @param ast a binary boolean-operator node.
     * @return the left operand, skipping any wrapping parentheses, or null if none.
     */
    private static DetailAST leftOperand(DetailAST ast) {
        DetailAST child = ast.getFirstChild();
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
     * Checks whether a token type is among the tokens this check instance is
     * actually configured to listen for. This is distinct from
     * {@link #isBooleanOperatorType(int)}, which recognizes all five
     * boolean-operator token types regardless of configuration; this method
     * reflects only what the user set via the {@code tokens} property, falling
     * back to {@link #getDefaultTokens()} if unset. It is used to decide whether
     * a given operator node's own occurrence should contribute to the complexity
     * count, as opposed to merely being walked through on the way to a
     * configured descendant.
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
     * Resolves the tokens this check instance is actually configured to listen
     * for, from {@link #getTokenNames()}, falling back to {@link #getDefaultTokens()}
     * when no explicit {@code tokens} property was set. This mirrors how
     * {@code TreeWalker} itself resolves a check's effective tokens, and is
     * recomputed on each call rather than cached, since the set is small and
     * this method is only invoked once per boolean-operator node visited.
     *
     * @return the resolved token types.
     */
    private int[] resolveConfiguredTokens() {
        final Set<String> tokenNames = getTokenNames();
        final int[] result;
        if (tokenNames.isEmpty()) {
            // No explicit "tokens" property set, so TreeWalker falls back
            // to getDefaultTokens() for this check; mirror that here.
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
     * Checks whether a boolean operator node should be counted at all, applying
     * the existing exclusions for multi-catch pipe syntax and bitwise operators
     * passed as method/constructor arguments, and requiring that the node's own
     * type is one this check instance is actually configured to count. Nodes
     * that fail this check are not themselves counted, but {@link #complexityOf}
     * still recurses into their children via {@link #childComplexity}, so a
     * configured operator nested beneath an Unconfigured one (e.g. a configured
     * {@code ||} inside an Unconfigured {@code &} subtree) is still found and
     * counted.
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
     * Determines whether the maximal flat chain of the same operator type
     * starting at the given node is "uniform": every operand in the chain has
     * the same shape (either all bare boolean operands, or all relational or
     * equality comparisons sharing a structurally identical left-hand side).
     * A chain is broken by explicit parenthesized regrouping and by any operand
     * that is itself a different kind of composite boolean expression -- both
     * of those cases are caught by {@link #haveSameShape}, since it rejects any
     * leaf that is itself a boolean-operator node regardless of its specific
     * type, so no separate type-equality check is needed here.
     *
     * @param ast the head of a candidate chain.
     * @return true if the whole chain is uniform.
     */
    private static boolean isUniformChain(DetailAST ast) {
        final List<DetailAST> leaves = new ArrayList<>();
        final boolean flat = collectChainLeaves(ast, ast.getType(), leaves);
        return flat && haveSameShape(leaves);
    }

    /**
     * Walks down a chain of same-type operator nodes via the left operand only,
     * collecting every operand that is not itself part of the chain as a leaf.
     * Stops (returning {@code false}) as soon as a node is missing its right
     * operand, which cannot happen for any well-formed boolean expression parsed
     * from real Java source, but can occur for excluded/malformed node shapes.
     * A right operand that happens to be the same operator type as the chain
     * (an explicit parenthesized regrouping, e.g. {@code a && (b && c)}) is
     * still collected as an ordinary leaf here -- {@link #haveSameShape} rejects
     * it immediately afterward since it is itself a boolean-operator node, so no
     * separate check for that case is needed in this method.
     *
     * @param ast current node in the walk.
     * @param chainType the operator token type identifying the chain.
     * @param leaves accumulator for the chain's leaf operands.
     * @return true if every node walked had a real right operand.
     */
    private static boolean collectChainLeaves(DetailAST ast, int chainType,
                                              List<DetailAST> leaves) {
        final DetailAST right = rightOperand(ast);
        boolean flat = right != null;
        if (flat) {
            final DetailAST left = leftOperand(ast);
            leaves.add(right);
            if (left.getType() == chainType) {
                flat = collectChainLeaves(left, chainType, leaves);
            }
            else {
                leaves.add(left);
            }
        }
        return flat;
    }

    /**
     * Checks whether every leaf operand in a chain shares the same shape: all
     * bare boolean operands, or all comparisons with a structurally identical
     * left-hand side. Also rejects (via {@link #leafKey} returning null) any
     * leaf that is itself a boolean-operator node, which is what makes an
     * explicit parenthesized regrouping or a composite sub-expression correctly
     * non-uniform without needing a separate structural check elsewhere.
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
     * Bare operands (no top-level relational/equality operator) all share the
     * key {@code "BARE"} regardless of their identifier, matching e.g.
     * {@code a && b && c}. A relational or equality comparison is keyed by the
     * canonical text of its left-hand side, so {@code type == X} only matches
     * other comparisons against the same {@code type}. Any leaf that is itself
     * a composite boolean-operator expression, or whose left-hand side cannot
     * be canonicalized, returns {@code null} so it can never match another leaf.
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
            final DetailAST ast = leftOperand(leaf);
            final String canonical;
            if (ast == null) {
                canonical = null;
            }
            else {
                canonical = canonicalText(ast);
            }
            if (canonical == null) {
                key = null;
            }
            else {
                key = "REL:" + canonical;
            }
        }
        else {
            key = "BARE";
        }
        return key;
    }

    /**
     * Reconstructs a canonical textual form of an identifier chain or a
     * (possibly qualified) method call, for comparing left-hand sides of
     * relational expressions structurally rather than lexically.
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
            final DetailAST left = leftOperand(ast);
            final DetailAST right = rightOperand(ast);
            final String leftText;

            if (left == null) {
                leftText = null;
            }
            else {
                leftText = canonicalText(left);
            }
            if (leftText == null || right == null) {
                result = null;
            }
            else {
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
