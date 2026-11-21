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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Objects;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for redundant null checks with the instanceof operator.
 * </div>
 *
 * <p>
 * The instanceof operator inherently returns false when the left operand is null,
 * making explicit null checks redundant in boolean expressions with instanceof.
 * </p>
 *
 * @since 10.25.0
 */
@StatelessCheck
public class UnnecessaryNullCheckWithInstanceOfCheck extends AbstractCheck {

    /**
     * The error message key for reporting unnecessary null checks.
     */
    public static final String MSG_UNNECESSARY_NULLCHECK = "unnecessary.nullcheck.with.instanceof";

    /**
     * Creates a new {@code UnnecessaryNullCheckWithInstanceOfCheck} instance.
     */
    public UnnecessaryNullCheckWithInstanceOfCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_INSTANCEOF};
    }

    @Override
    public void visitToken(DetailAST instanceofNode) {
        findUnnecessaryNullCheck(instanceofNode)
                .ifPresent(violationNode -> log(violationNode, MSG_UNNECESSARY_NULLCHECK));
    }

    /**
     * Checks for an unnecessary null check within a logical AND expression or nested if statements.
     *
     * @param instanceOfNode the AST node representing the instanceof expression
     * @return the identifier if the check is redundant, otherwise empty
     */
    private static Optional<DetailAST> findUnnecessaryNullCheck(DetailAST instanceOfNode) {
        final DetailAST topLevelExpr = findTopLevelLogicalExpression(instanceOfNode);

        Optional<DetailAST> result = Optional.empty();
        if (topLevelExpr.getType() == TokenTypes.LAND) {
            result = findRedundantNullCheck(topLevelExpr, instanceOfNode)
                    .map(DetailAST::getFirstChild);
        }
        if (result.isEmpty()) {
            final DetailAST instanceOfIdent = instanceOfNode.findFirstToken(TokenTypes.IDENT);
            if (instanceOfIdent != null) {
                result = findRedundantNullCheckInOuterIf(instanceOfNode, instanceOfIdent);
                if (result.isEmpty()) {
                    result = findRedundantNullCheckInInnerIf(instanceOfNode, instanceOfIdent);
                }
            }
        }
        return result;
    }

    /**
     * Checks for a redundant null check in an outer if statement enclosing an inner if statement
     * with an instanceof check.
     *
     * @param instanceOfNode the AST node representing the instanceof expression
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the AST node representing the redundant null check, or empty if not found
     */
    private static Optional<DetailAST> findRedundantNullCheckInOuterIf(DetailAST instanceOfNode,
        DetailAST instanceOfIdent) {

        Optional<DetailAST> result = Optional.empty();
        final Optional<DetailAST> innerIfOpt = getEnclosingIfStatement(instanceOfNode);

        if (innerIfOpt.isPresent()) {
            final DetailAST innerIf = innerIfOpt.orElseThrow();
            final DetailAST outerIf = getSingleEnclosingOuterIf(innerIf);

            if (hasNoElse(outerIf) && hasNoElse(innerIf)
                    && isSimpleInstanceOf(innerIf, instanceOfNode)) {
                final DetailAST notEqual = getSimpleNotEqualNode(outerIf);
                if (notEqual != null && isNullCheckRedundant(instanceOfIdent, notEqual)) {
                    result = Optional.of(notEqual.getFirstChild());
                }
            }
        }
        return result;
    }

    /**
     * Checks for a redundant null check in an inner if statement enclosed by an outer if statement
     * with an instanceof check.
     *
     * @param instanceOfNode the AST node representing the instanceof expression
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the AST node representing the redundant null check, or empty if not found
     */
    private static Optional<DetailAST> findRedundantNullCheckInInnerIf(DetailAST instanceOfNode,
        DetailAST instanceOfIdent) {

        Optional<DetailAST> result = Optional.empty();
        final Optional<DetailAST> outerIfOpt = getEnclosingIfStatement(instanceOfNode);

        if (outerIfOpt.isPresent()) {
            final DetailAST outerIf = outerIfOpt.orElseThrow();
            final DetailAST innerIf = getSingleInnerIf(outerIf);

            if (hasNoElse(outerIf) && hasNoElse(innerIf)
                    && isSimpleInstanceOf(outerIf, instanceOfNode)) {
                final DetailAST notEqual = getSimpleNotEqualNode(innerIf);
                if (notEqual != null && isNullCheckRedundant(instanceOfIdent, notEqual)) {
                    result = Optional.of(notEqual.getFirstChild());
                }
            }
        }
        return result;
    }

    /**
     * Finds the if statement whose condition directly encloses the given node.
     *
     * @param node the node to search from
     * @return the enclosing if statement, or empty if node is not in an if condition
     */
    private static Optional<DetailAST> getEnclosingIfStatement(DetailAST node) {
        DetailAST current = node;
        DetailAST result = null;
        while (current.getParent() != null) {
            if (current.getParent().getType() == TokenTypes.LITERAL_IF) {
                final DetailAST cond = current.getParent().findFirstToken(TokenTypes.EXPR);
                if (Objects.equals(current, cond)) {
                    result = current.getParent();
                }
                break;
            }
            current = current.getParent();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Checks if the given if statement has no else branch.
     *
     * @param ifStatement the if statement to check
     * @return true if ifStatement is non-null and has no else branch
     */
    private static boolean hasNoElse(DetailAST ifStatement) {
        return ifStatement != null
                && ifStatement.findFirstToken(TokenTypes.LITERAL_ELSE) == null;
    }

    /**
     * Finds the enclosing outer if statement if the given inner if is its sole then-branch
     * statement.
     *
     * @param innerIf the inner if statement
     * @return the outer if statement, or null if not solely enclosed by an if statement
     */
    private static DetailAST getSingleEnclosingOuterIf(DetailAST innerIf) {
        final DetailAST parent = innerIf.getParent();
        DetailAST outerIf = null;
        if (parent.getType() == TokenTypes.SLIST) {
            if (isOnlyStatementInBlock(innerIf, parent)) {
                outerIf = parent.getParent();
            }
        }
        else if (parent.getType() == TokenTypes.LITERAL_IF) {
            outerIf = parent;
        }
        if (outerIf != null && outerIf.getType() != TokenTypes.LITERAL_IF) {
            outerIf = null;
        }
        return outerIf;
    }

    /**
     * Finds the single if statement directly contained in the then-branch of an outer if statement.
     *
     * @param ifStatement the outer if statement
     * @return the single inner if statement, or null if none
     */
    private static DetailAST getSingleInnerIf(DetailAST ifStatement) {
        DetailAST innerIf = null;
        final DetailAST slist = ifStatement.findFirstToken(TokenTypes.SLIST);
        if (slist == null) {
            final DetailAST candidate = getStatementAfterRparen(ifStatement);
            if (candidate != null && candidate.getType() == TokenTypes.LITERAL_IF) {
                innerIf = candidate;
            }
        }
        else {
            final DetailAST firstStatement = slist.getFirstChild();
            if (firstStatement != null
                    && firstStatement.getType() == TokenTypes.LITERAL_IF
                    && isOnlyStatementInBlock(firstStatement, slist)) {
                innerIf = firstStatement;
            }
        }
        return innerIf;
    }

    /**
     * Gets the statement following the right parenthesis in an if statement without braces.
     *
     * @param ifStatement the if statement
     * @return the statement node, or null if not present
     */
    private static DetailAST getStatementAfterRparen(DetailAST ifStatement) {
        final DetailAST rparen = ifStatement.findFirstToken(TokenTypes.RPAREN);
        DetailAST statement = null;
        if (rparen != null) {
            statement = rparen.getNextSibling();
        }
        return statement;
    }

    /**
     * Extracts the simple condition expression node from an if statement condition.
     *
     * @param ifNode the if statement node
     * @return the unwrapped condition expression node, or null if condition is complex
     */
    private static DetailAST getSimpleCondition(DetailAST ifNode) {
        final DetailAST cond = ifNode.findFirstToken(TokenTypes.EXPR);
        DetailAST result = null;
        if (cond != null && cond.findFirstToken(TokenTypes.LAND) == null
                && cond.findFirstToken(TokenTypes.LOR) == null) {
            DetailAST exprChild = cond.getFirstChild();
            while (exprChild != null && exprChild.getType() == TokenTypes.LPAREN) {
                exprChild = exprChild.getNextSibling();
            }
            result = exprChild;
        }
        return result;
    }

    /**
     * Checks if the condition of an if statement is a simple instanceof check matching the node.
     *
     * @param ifNode the if statement node
     * @param instanceOfNode the expected instanceof node
     * @return true if the condition is a simple instanceof expression matching instanceOfNode
     */
    private static boolean isSimpleInstanceOf(DetailAST ifNode, DetailAST instanceOfNode) {
        return Objects.equals(getSimpleCondition(ifNode), instanceOfNode);
    }

    /**
     * Extracts a simple not equal operator node from the condition of an if statement.
     *
     * @param ifNode the if statement node
     * @return the not equal node, or null if the condition is not a simple not equal check
     */
    private static DetailAST getSimpleNotEqualNode(DetailAST ifNode) {
        final DetailAST cond = getSimpleCondition(ifNode);
        DetailAST notEqual = null;
        if (cond != null && cond.getType() == TokenTypes.NOT_EQUAL) {
            notEqual = cond;
        }
        return notEqual;
    }

    /**
     * Checks whether the given statement is the only statement inside a block (SLIST).
     *
     * @param statement the statement to check
     * @param slist the block node
     * @return true if statement is the only statement in slist
     */
    private static boolean isOnlyStatementInBlock(DetailAST statement, DetailAST slist) {
        boolean onlyStatement = true;
        DetailAST child = slist.getFirstChild();
        while (child != null) {
            if (child.getType() != TokenTypes.RCURLY && !Objects.equals(child, statement)) {
                onlyStatement = false;
                break;
            }
            child = child.getNextSibling();
        }
        return onlyStatement;
    }

    /**
     * Traverses up through LAND and LOR operators to find the top-level logical expression.
     *
     * @param node the starting node
     * @return the top-level logical expression node
     */
    private static DetailAST findTopLevelLogicalExpression(DetailAST node) {
        DetailAST currentParent = node;
        while (currentParent.getParent().getType() == TokenTypes.LAND
                || currentParent.getParent().getType() == TokenTypes.LOR) {
            currentParent = currentParent.getParent();
        }
        return currentParent;
    }

    /**
     * Finds a redundant null check in a logical AND expression combined with an instanceof check.
     *
     * @param logicalAndNode the root node of the logical AND expression
     * @param instanceOfNode the instanceof expression node
     * @return the AST node representing the redundant null check, or null if not found
     */
    private static Optional<DetailAST> findRedundantNullCheck(DetailAST logicalAndNode,
        DetailAST instanceOfNode) {

        Optional<DetailAST> nullCheckNode = Optional.empty();
        final DetailAST instanceOfIdent = instanceOfNode.findFirstToken(TokenTypes.IDENT);

        if (instanceOfIdent != null
                && !containsVariableDereference(logicalAndNode, instanceOfIdent)) {

            nullCheckNode = searchForNullCheck(logicalAndNode, instanceOfNode, instanceOfIdent);
        }
        return nullCheckNode;
    }

    /**
     * Searches for a redundant null check in the children of a logical AND node.
     *
     * @param logicalAndNode the LAND node to search
     * @param instanceOfNode the instanceof expression node
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the null check node if found, null otherwise
     */
    private static Optional<DetailAST> searchForNullCheck(DetailAST logicalAndNode,
        DetailAST instanceOfNode, DetailAST instanceOfIdent) {

        Optional<DetailAST> nullCheckNode = Optional.empty();

        final Optional<DetailAST> instanceOfSubtree =
                findDirectChildContaining(logicalAndNode, instanceOfNode);

        final DetailAST instanceOfSubtreeNode =
                instanceOfSubtree.orElse(null);

        final boolean instanceOfInLor =
                instanceOfSubtreeNode != null
                        && instanceOfSubtreeNode.getType() == TokenTypes.LOR;

        DetailAST currentChild = logicalAndNode.getFirstChild();
        while (currentChild != null) {
            if (instanceOfInLor && currentChild.equals(instanceOfSubtreeNode)) {
                break;
            }

            if (nullCheckNode.isEmpty()) {
                nullCheckNode = checkChildForNullCheck(
                        currentChild, instanceOfNode, instanceOfIdent);
            }

            currentChild = currentChild.getNextSibling();
        }

        return nullCheckNode;
    }

    /**
     * Checks a child node for a redundant null check.
     *
     * @param currentChild the child node to check
     * @param instanceOfNode the instanceof expression node
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the null check node if found, otherwise empty
     */
    private static Optional<DetailAST> checkChildForNullCheck(DetailAST currentChild,
        DetailAST instanceOfNode, DetailAST instanceOfIdent) {

        Optional<DetailAST> result = Optional.empty();

        if (isNotEqual(currentChild)
                && isNullCheckRedundant(instanceOfIdent, currentChild)) {
            result = Optional.of(currentChild);
        }
        else if (currentChild.getType() == TokenTypes.LAND) {
            result = findRedundantNullCheck(currentChild, instanceOfNode);
        }

        return result;
    }

    /**
     * Finds the direct child of parent that contains the target node.
     *
     * @param parent the parent node
     * @param target the target node to find
     * @return the direct child containing target, or null if not found
     */
    private static Optional<DetailAST> findDirectChildContaining(DetailAST parent,
        DetailAST target) {

        DetailAST result = null;
        DetailAST child = parent.getFirstChild();
        while (child != null) {
            if (isAncestorOf(child, target)) {
                result = child;
                break;
            }
            child = child.getNextSibling();
        }
        return Optional.ofNullable(result);
    }

    /**
     * Checks if the given node is an ancestor of (or the same as) the target node.
     *
     * @param node the potential ancestor node
     * @param target the target node to check
     * @return true if node is an ancestor of target, false otherwise
     */
    private static boolean isAncestorOf(DetailAST node, DetailAST target) {
        boolean found = false;
        DetailAST current = target;
        while (current != null) {
            if (current.equals(node)) {
                found = true;
                break;
            }
            current = current.getParent();
        }
        return found;
    }

    /**
     * Checks if the given AST node contains a field access or method call on the
     * variable of the instanceof operand, evaluated before that operand.
     *
     * @param node the AST node to check
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return true if the variable is dereferenced before the instanceof, false otherwise
     */
    private static boolean containsVariableDereference(DetailAST node,
        DetailAST instanceOfIdent) {

        boolean found = false;
        DetailAST child = node.getFirstChild();

        while (child != null && !found) {
            found = node.getType() == TokenTypes.DOT
                    && instanceOfIdent.getText().equals(child.getText())
                    && child.getNextSibling() != null
                    && isBefore(child, instanceOfIdent)
                || containsVariableDereference(child, instanceOfIdent);
            child = child.getNextSibling();
        }
        return found;
    }

    /**
     * Checks if the first node appears earlier in the source than the second node.
     *
     * @param first the node expected to appear first
     * @param second the node expected to appear second
     * @return true if first is located before second, false otherwise
     */
    private static boolean isBefore(DetailAST first, DetailAST second) {
        int result = Integer.compare(first.getLineNo(), second.getLineNo());
        if (result == 0) {
            result = Integer.compare(first.getColumnNo(), second.getColumnNo());
        }
        return result == -1;
    }

    /**
     * Checks if the given AST node represents a {@code !=} (not equal) operator.
     *
     * @param node the AST node to check
     * @return {@code true} if the node is a not equal operator, otherwise {@code false}
     */
    private static boolean isNotEqual(DetailAST node) {
        return node.getType() == TokenTypes.NOT_EQUAL;
    }

    /**
     * Checks if the given AST node is a null literal.
     *
     * @param node AST node to check
     * @return true if the node is a null literal, false otherwise
     */
    private static boolean isNullLiteral(DetailAST node) {
        return node.getType() == TokenTypes.LITERAL_NULL;
    }

    /**
     * Determines if the null check is redundant with the instanceof check.
     *
     * @param instanceOfIdent the identifier from the instanceof check
     * @param nullCheckNode the node representing the null check
     * @return true if the null check is unnecessary, false otherwise
     */
    private static boolean isNullCheckRedundant(DetailAST instanceOfIdent,
        final DetailAST nullCheckNode) {

        final DetailAST nullCheckIdent = nullCheckNode.findFirstToken(TokenTypes.IDENT);
        return nullCheckIdent != null
                && (isNullLiteral(nullCheckNode.getFirstChild().getNextSibling())
                    || isNullLiteral(nullCheckNode.getFirstChild()))
                && instanceOfIdent.getText().equals(nullCheckIdent.getText());
    }

}
