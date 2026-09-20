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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for duplicate condition expressions in {@code if ... else if} chains.
 * </div>
 *
 * @since 10.22.0
 */
@StatelessCheck
public class DuplicateConditionIfChainCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_KEY = "duplicate.condition.if.chain";

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
        return new int[] {
            TokenTypes.LITERAL_IF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent().getType() != TokenTypes.LITERAL_ELSE) {
            final List<DetailAST> conditionNodes = new ArrayList<>();

            DetailAST currentIf = ast;
            while (currentIf != null) {
                final DetailAST exprNode = currentIf.findFirstToken(TokenTypes.EXPR);
                if (exprNode != null && exprNode.getFirstChild() != null) {
                    checkAndAddCondition(exprNode.getFirstChild(), conditionNodes);
                }

                final DetailAST elseNode = currentIf.findFirstToken(TokenTypes.LITERAL_ELSE);
                if (elseNode != null) {
                    currentIf = elseNode.findFirstToken(TokenTypes.LITERAL_IF);
                }
                else {
                    currentIf = null;
                }
            }
        }
    }

    /**
     * Checks if the condition AST is structurally equal to any previously recorded condition
     * in the chain. If so, logs a violation; otherwise adds it to the list.
     *
     * @param condition current condition EXPR node
     * @param conditionNodes list of previously processed condition EXPR nodes
     */
    private void checkAndAddCondition(DetailAST condition, List<DetailAST> conditionNodes) {
        boolean isDuplicate = false;
        int duplicateLineNumber = 0;

        for (DetailAST prevCondition : conditionNodes) {
            if (isAstEqual(condition, prevCondition)) {
                isDuplicate = true;
                duplicateLineNumber = prevCondition.getLineNo();
                break;
            }
        }

        if (isDuplicate) {
            log(condition, MSG_KEY, duplicateLineNumber);
        }
        else {
            conditionNodes.add(condition);
        }
    }

    /**
     * Recursively compares two DetailAST subtrees structurally.
     *
     * @param node1 first AST node
     * @param node2 second AST node
     * @return true if subtrees are structurally identical
     */
    private static boolean isAstEqual(DetailAST node1, DetailAST node2) {
        if (node1 == node2) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        if (node1.getType() != node2.getType()) {
            return false;
        }
        if (!Objects.equals(node1.getText(), node2.getText())) {
            return false;
        }

        return isAstEqual(node1.getFirstChild(), node2.getFirstChild())
                && isAstEqual(node1.getNextSibling(), node2.getNextSibling());
    }
}
