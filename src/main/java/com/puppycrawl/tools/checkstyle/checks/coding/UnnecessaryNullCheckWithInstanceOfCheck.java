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
     * Checks for an unnecessary null check within a logical expression.
     *
     * @param instanceOfNode the AST node representing the instanceof expression
     * @return the identifier if the check is redundant, otherwise {@code null}
     */
    private static Optional<DetailAST> findUnnecessaryNullCheck(DetailAST instanceOfNode) {
        Optional<DetailAST> result = Optional.empty();
        final DetailAST ident = instanceOfNode.findFirstToken(TokenTypes.IDENT);
        if (ident != null) {
            final DetailAST topLevelExpr = findTopLevelLogicalExpression(instanceOfNode, ident);
            result = searchForNullCheck(topLevelExpr, ident).map(DetailAST::getFirstChild);
        }
        return result;
    }

    /**
     * Traverses logical operators while every OR branch requires the same non-null value.
     *
     * @param node the starting node
     * @param ident the identifier from the instanceof expression
     * @return the top-level logical expression node
     */
    private static DetailAST findTopLevelLogicalExpression(DetailAST node, DetailAST ident) {
        DetailAST currentParent = node;
        while (requiresNonNullValue(currentParent.getParent(), ident)) {
            currentParent = currentParent.getParent();
        }
        return currentParent;
    }

    /**
     * Checks whether an expression establishes a non-null value before dereferencing it.
     *
     * @param node the logical expression to inspect
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return true if evaluating the expression safely establishes a non-null value
     */
    private static boolean requiresNonNullValue(DetailAST node, DetailAST instanceOfIdent) {
        final int type = node.getType();
        boolean result = false;
        if (type == TokenTypes.LITERAL_INSTANCEOF) {
            final DetailAST ident = node.findFirstToken(TokenTypes.IDENT);
            result = ident != null && instanceOfIdent.getText().equals(ident.getText());
        }
        else if (type == TokenTypes.LAND) {
            result = requiresNonNullConjunction(node, instanceOfIdent);
        }
        else if (type == TokenTypes.LOR) {
            result = true;
            for (DetailAST child = node.getFirstChild(); child != null && result;
                    child = child.getNextSibling()) {
                if (child.getType() != TokenTypes.LPAREN
                        && child.getType() != TokenTypes.RPAREN) {
                    result = requiresNonNullValue(child, instanceOfIdent);
                }
            }
        }
        return result;
    }

    /**
     * Checks that a conjunction establishes a non-null value before any dereference.
     *
     * @param node the conjunction to inspect
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return true if an operand safely establishes a non-null value
     */
    private static boolean requiresNonNullConjunction(DetailAST node, DetailAST instanceOfIdent) {
        boolean result = false;
        for (DetailAST child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            result = requiresNonNullValue(child, instanceOfIdent);
            if (result || containsVariableDereference(child, instanceOfIdent)) {
                break;
            }
        }
        return result;
    }

    /**
     * Searches for a redundant null check in the children of a logical expression.
     *
     * @param logicalNode the logical expression to search
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the null check node if found, null otherwise
     */
    private static Optional<DetailAST> searchForNullCheck(DetailAST logicalNode,
        DetailAST instanceOfIdent) {

        Optional<DetailAST> nullCheckNode = Optional.empty();

        DetailAST currentChild = logicalNode.getFirstChild();
        while (currentChild != null) {
            if (nullCheckNode.isEmpty()) {
                nullCheckNode = checkChildForNullCheck(
                        currentChild, instanceOfIdent);
            }

            currentChild = currentChild.getNextSibling();
        }

        return nullCheckNode;
    }

    /**
     * Checks a child node for a redundant null check.
     *
     * @param currentChild the child node to check
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return the null check node if found, otherwise empty
     */
    private static Optional<DetailAST> checkChildForNullCheck(DetailAST currentChild,
        DetailAST instanceOfIdent) {

        Optional<DetailAST> result = Optional.empty();

        if (isNotEqual(currentChild)
                && isNullCheckRedundant(instanceOfIdent, currentChild)) {
            result = Optional.of(currentChild);
        }
        else if (currentChild.getType() == TokenTypes.LAND) {
            result = searchForNullCheck(currentChild, instanceOfIdent);
        }

        return result;
    }

    /**
     * Checks if the given AST node contains a field access or method call on the
     * variable of the instanceof operand.
     *
     * @param node the AST node to check
     * @param instanceOfIdent the identifier from the instanceof expression
     * @return true if the variable is dereferenced in the expression
     */
    private static boolean containsVariableDereference(DetailAST node,
        DetailAST instanceOfIdent) {

        boolean found = false;
        DetailAST child = node.getFirstChild();

        while (child != null && !found) {
            found = node.getType() == TokenTypes.DOT
                    && instanceOfIdent.getText().equals(child.getText())
                    && child.getNextSibling() != null
                || containsVariableDereference(child, instanceOfIdent);
            child = child.getNextSibling();
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
