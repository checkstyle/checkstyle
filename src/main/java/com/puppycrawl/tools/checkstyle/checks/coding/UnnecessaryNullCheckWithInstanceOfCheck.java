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
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 *
 * <ul>
 *     <li>
 *         {@code unnecessary.nullcheck.with.instanceof}
 *     </li>
 * </ul>
 *
 * @since 10.22.0
 */

@StatelessCheck
public class UnnecessaryNullCheckWithInstanceOfCheck extends AbstractCheck {

    /**
     * The error message key for reporting unnecessary null checks.
     */
    public static final String MSG_UNNECESSARY_NULLCHECK = "unnecessary.nullcheck.with.instanceof";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {TokenTypes.NOT_EQUAL};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST nullComparisonNode) {
        if (isNullLiteral(nullComparisonNode.getFirstChild().getNextSibling())
            || isNullLiteral(nullComparisonNode.getFirstChild())) {
            final DetailAST violationNode = checkForUnnecessaryNullCheck(nullComparisonNode);
            if (violationNode != null) {
                log(violationNode, MSG_UNNECESSARY_NULLCHECK);
            }
        }
    }

    /**
     * Checks for an unnecessary null check within a logical AND expression.
     *
     * @param nullComparisonNode the AST node representing the null comparison
     * @return the first child of the null comparison node if an unnecessary null check is found
     */
    private static DetailAST checkForUnnecessaryNullCheck(DetailAST nullComparisonNode) {
        DetailAST currentParent = nullComparisonNode.getParent();
        DetailAST result = null;

        while (currentParent.getType() == TokenTypes.LAND) {
            if (findInstanceOfCheckInLogicalAnd(currentParent, nullComparisonNode)) {
                result = nullComparisonNode.getFirstChild();
                break;
            }
            else {
                currentParent = currentParent.getParent();
            }

        }
        return result;
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
     * Checks if the given AST node represents an instanceof check.
     *
     * @param node AST node to check
     * @return true if the node is an instanceof check, false otherwise
     */
    private static boolean isInstanceofCheck(DetailAST node) {
        return node.getType() == TokenTypes.LITERAL_INSTANCEOF;
    }

    /**
     * Determines if the null check is redundant with the instanceof check.
     *
     * @param nullCheckNode null comparison node
     * @param instanceofCheckNode instanceof check node
     * @return true if null check is unnecessary, false otherwise
     */
    private static boolean isNullCheckRedundant(DetailAST nullCheckNode,
        final DetailAST instanceofCheckNode) {
        final DetailAST nullCheckIdent = nullCheckNode.findFirstToken(TokenTypes.IDENT);
        final DetailAST instanceofIdent = instanceofCheckNode.findFirstToken(TokenTypes.IDENT);
        return nullCheckIdent != null && instanceofIdent != null && nullCheckIdent.getText()
            .equals(instanceofIdent.getText());
    }

    /**
     * Finds an instanceof check within a logical AND expression.
     *
     * @param logicalAndNode the logical AND AST node
     * @param nullCheckNode the null check node to compare against
     * @return true, if the instanceof check node is found
     */
    private static boolean findInstanceOfCheckInLogicalAnd(DetailAST logicalAndNode,
        DetailAST nullCheckNode) {
        DetailAST currentChild = logicalAndNode.getFirstChild();
        boolean found = false;
        while (currentChild != null && !found) {
            if (isInstanceofCheck(currentChild) && isNullCheckRedundant(nullCheckNode,
                currentChild)) {
                found = true;
            }
            else if (currentChild.getType() == TokenTypes.LAND) {
                found = findInstanceOfCheckInLogicalAnd(currentChild,
                    nullCheckNode);
            }
            currentChild = currentChild.getNextSibling();
        }
        return found;
    }
}
