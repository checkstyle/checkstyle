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
 * Checks for unnecessary null checks when used in conjunction with the instanceof operator.
 *
 * <p>This check identifies and flags redundant null checks that are implicitly handled
 * by the instanceof operator, helping to simplify and clean up conditional logic.</p>
 *
 * <p>Examples of patterns detected:</p>
 * <pre>
 * // Unnecessary null check - will be flagged
 * if (obj != null && obj instanceof String) {
 *     // do something
 * }
 *
 * // Preferred pattern
 * if (obj instanceof String) {
 *     // do something
 * }
 * </pre>
 */
@StatelessCheck
public class UnnecessaryNullCheckWithInstanceOfCheck extends AbstractCheck {

    /**
     * The error message key for reporting unnecessary null checks.
     */
    public static final String MSG_UNNECESSARY_NULLCHECK = "unnecessary.nullcheck.with.instanceof";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.NOT_EQUAL};
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
            checkForUnnecessaryNullCheck(nullComparisonNode);
        }
    }

    /**
     * Checks for unnecessary null checks in conditional expressions.
     *
     * @param nullComparisonNode the not null comparison AST node to check
     */
    private void checkForUnnecessaryNullCheck(DetailAST nullComparisonNode) {
        DetailAST currentParent = nullComparisonNode.getParent();

        while (currentParent != null) {
            if (currentParent.getType() == TokenTypes.LAND
                && findInstanceOfCheckInLogicalAnd(currentParent, nullComparisonNode) != null) {
                log(nullComparisonNode.getFirstChild().getLineNo(),
                    nullComparisonNode.getFirstChild().getColumnNo(),
                    MSG_UNNECESSARY_NULLCHECK);
                break;
            }
            else if (currentParent.getType() == TokenTypes.EXPR || currentParent.getType() == TokenTypes.LAND) {
                currentParent = currentParent.getParent();
            }
            else {
                break;
            }
        }
    }

    /**
     * Checks if the given AST node represents a null comparison.
     *
     * @param node AST node to check
     * @return true if the node is a null comparison, false otherwise
     */
    private static boolean isNullComparisonCheck(DetailAST node) {
        return node.getType() == TokenTypes.NOT_EQUAL
                && isNullLiteral(node.getFirstChild().getNextSibling())
                || node.getType() == TokenTypes.EQUAL
                && isNullLiteral(node.getFirstChild().getNextSibling());
    }

    /**
     * Checks if the given AST node is a null literal.
     *
     * @param node AST node to check
     * @return true if the node is a null literal, false otherwise
     */
    private static boolean isNullLiteral(DetailAST node) {
        return node != null && node.getType() == TokenTypes.LITERAL_NULL;
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
    private static boolean isNullCheckRedundant(DetailAST nullCheckNode, DetailAST instanceofCheckNode) {
        String nullCheckVariableName = nullCheckNode.findFirstToken(TokenTypes.IDENT).getText();
        String instanceofVariableName = instanceofCheckNode.findFirstToken(TokenTypes.IDENT).getText();
        return isPatternMatchingInstanceof(instanceofCheckNode)
               && nullCheckVariableName.equals(instanceofVariableName);
    }

    /**
     * Checks if the instanceof check is a pattern matching instanceof.
     *
     * @param instanceofCheckNode instanceof check node
     * @return true if it appears to be a pattern matching instanceof, false otherwise
     */
    private static boolean isPatternMatchingInstanceof(DetailAST instanceofCheckNode) {
        DetailAST typeNode = instanceofCheckNode.getFirstChild().getNextSibling();

        return typeNode != null && typeNode.getType() == TokenTypes.TYPE;
    }

    /**
     * Finds an instanceof check within a logical AND expression.
     *
     * @param logicalAndNode the logical AND AST node
     * @param nullCheckNode the null check node to compare against
     * @return the instanceof check node if found, null otherwise
     */
    private static DetailAST findInstanceOfCheckInLogicalAnd(DetailAST logicalAndNode, DetailAST nullCheckNode) {
        DetailAST currentChild = logicalAndNode.getFirstChild();
        DetailAST instanceofCheckResult = null;

        while (currentChild != null) {
            if (isInstanceofCheck(currentChild) && isNullCheckRedundant(nullCheckNode, currentChild)) {
                instanceofCheckResult = currentChild;
                break;
            }
            if (currentChild.getType() == TokenTypes.EXPR) {
                currentChild = currentChild.getFirstChild();
            }
            else if (currentChild.getType() == TokenTypes.LAND) {
                instanceofCheckResult = findInstanceOfCheckInLogicalAnd(currentChild, nullCheckNode);
                break;
            }
            else {
                currentChild = currentChild.getNextSibling();
            }
        }
        return instanceofCheckResult;
    }
}
