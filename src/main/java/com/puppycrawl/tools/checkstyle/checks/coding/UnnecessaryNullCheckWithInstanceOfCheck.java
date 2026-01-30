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
     * Checks for an unnecessary null check within a logical AND expression.
     *
     * @param instanceOfNode the AST node representing the instanceof expression
     * @return the identifier if the check is redundant, otherwise {@code null}
     */
    private static Optional<DetailAST> findUnnecessaryNullCheck(DetailAST instanceOfNode) {
        final DetailAST topLevelExpr = findTopLevelLogicalExpression(instanceOfNode);

        Optional<DetailAST> result = Optional.empty();
        if (topLevelExpr.getType() == TokenTypes.LAND) {
            result = findRedundantNullCheck(topLevelExpr, instanceOfNode)
                    .map(DetailAST::getFirstChild);
        }
        return result;
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
                && !containsVariableDereference(logicalAndNode, instanceOfIdent.getText())) {

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

        DetailAST nullCheckNode = null;

        final Optional<DetailAST> instanceOfSubtree =
                findDirectChildContaining(logicalAndNode, instanceOfNode);

        final DetailAST instanceOfSubtreeNode = instanceOfSubtree.orElse(null);

        final boolean instanceOfInLor =
                instanceOfSubtreeNode != null
                        && instanceOfSubtreeNode.getType() == TokenTypes.LOR;

        DetailAST currentChild = logicalAndNode.getFirstChild();
        while (currentChild != null) {
            if (instanceOfInLor && currentChild.equals(instanceOfSubtreeNode)) {
                break;
            }

            nullCheckNode = checkChildForNullCheck(
                    currentChild, instanceOfNode, instanceOfIdent, nullCheckNode);

            currentChild = currentChild.getNextSibling();
        }

        return Optional.ofNullable(nullCheckNode);
    }

    /**
     * Checks a child node for a redundant null check.
     *
     * @param currentChild the child node to check
     * @param instanceOfNode the instanceof expression node
     * @param instanceOfIdent the identifier from the instanceof expression
     * @param currentNullCheck the current found null check (may be null)
     * @return the null check node if found, otherwise the current null check
     */
    private static DetailAST checkChildForNullCheck(DetailAST currentChild,
        DetailAST instanceOfNode, DetailAST instanceOfIdent, DetailAST currentNullCheck) {

        DetailAST result = currentNullCheck;

        if (isNotEqual(currentChild)
                && isNullCheckRedundant(instanceOfIdent, currentChild)) {
            result = currentChild;
        }
        else if (result == null && currentChild.getType() == TokenTypes.LAND) {
            result = findRedundantNullCheck(currentChild, instanceOfNode).orElse(null);
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
        while (child != null && result == null) {
            if (isAncestorOf(child, target)) {
                result = child;
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
        while (current != null && !found) {
            if (current.equals(node)) {
                found = true;
            }
            current = current.getParent();
        }
        return found;
    }

    /**
     * Checks if the given AST node contains a method call or field access
     * on the specified variable.
     *
     * @param node the AST node to check
     * @param variableName the name of the variable
     * @return true if the variable is dereferenced, false otherwise
     */
    private static boolean containsVariableDereference(DetailAST node, String variableName) {

        boolean found = false;

        if (node.getType() == TokenTypes.DOT
            || node.getType() == TokenTypes.METHOD_CALL
            || node.getType() == TokenTypes.LAND
            || node.getType() == TokenTypes.LOR) {

            DetailAST firstChild = node.getFirstChild();

            while (firstChild != null) {
                if (variableName.equals(firstChild.getText())
                        && firstChild.getNextSibling().getType() != TokenTypes.ELIST
                            || containsVariableDereference(firstChild, variableName)) {
                    found = true;
                    break;
                }
                firstChild = firstChild.getNextSibling();
            }
        }
        return found;
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
