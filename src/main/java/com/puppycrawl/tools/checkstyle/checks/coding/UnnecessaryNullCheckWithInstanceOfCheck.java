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
        DetailAST currentParent = instanceOfNode;

        while (currentParent.getParent().getType() == TokenTypes.LAND) {
            currentParent = currentParent.getParent();
        }
        return findRedundantNullCheck(currentParent, instanceOfNode)
            .map(DetailAST::getFirstChild);
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

        DetailAST nullCheckNode = null;
        final DetailAST instanceOfIdent = instanceOfNode.findFirstToken(TokenTypes.IDENT);

        if (instanceOfIdent != null
            && !containsVariableDereference(logicalAndNode, instanceOfIdent.getText())) {

            DetailAST currentChild = logicalAndNode.getFirstChild();
            while (currentChild != null) {
                if (isNotEqual(currentChild)
                        && isNullCheckRedundant(instanceOfIdent, currentChild)) {
                    nullCheckNode = currentChild;
                }
                else if (nullCheckNode == null && currentChild.getType() == TokenTypes.LAND) {
                    nullCheckNode = findRedundantNullCheck(currentChild, instanceOfNode)
                            .orElse(null);
                }
                currentChild = currentChild.getNextSibling();
            }
        }
        return Optional.ofNullable(nullCheckNode);
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
            || node.getType() == TokenTypes.METHOD_CALL || node.getType() == TokenTypes.LAND) {

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
