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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that a condition is not repeated where it can never matter: an {@code else if}
 * condition identical to an earlier condition in the same {@code if-else-if} chain.
 * Such a branch is unreachable.
 * Conditions are compared by AST structure, so formatting and comments are ignored.
 * </div>
 *
 * @since 14.4.0
 */
@StatelessCheck
public class DuplicateElseIfConditionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "duplicate.else.if.condition";

    /**
     * Creates a new {@code DuplicateElseIfConditionCheck} instance.
     */
    public DuplicateElseIfConditionCheck() {
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
        return new int[] {
            TokenTypes.LITERAL_IF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> seenConditions = new ArrayList<>();
        if (ast.getParent().getType() != TokenTypes.LITERAL_ELSE) {
            DetailAST current = ast;
            while (current != null) {
                final DetailAST condition = current.findFirstToken(TokenTypes.EXPR);
                final DetailAST firstOccurrence =
                        findEquivalentCondition(seenConditions, condition);
                if (firstOccurrence == null) {
                    seenConditions.add(condition);
                }
                else {
                    log(condition, MSG_KEY, firstOccurrence.getLineNo());
                }
                current = getNextIfInChain(current);
            }
        }
    }

    /**
     * Finds the first earlier condition that is structurally equal to the given one.
     *
     * @param seenConditions conditions already visited
     * @param condition the condition node to look for
     * @return the matching earlier node, or null if there is none
     */
    private static DetailAST findEquivalentCondition(List<DetailAST> seenConditions,
                                                     DetailAST condition) {
        DetailAST result = null;
        for (DetailAST seen : seenConditions) {
            if (isStructurallyEqual(seen.getFirstChild(), condition.getFirstChild())) {
                result = seen;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the next LITERAL_IF of the chain, i.e. the one directly after "else".
     *
     * @param ifNode the current LITERAL_IF node
     * @return the next LITERAL_IF in the chain, or null if the chain ends here
     */
    private static DetailAST getNextIfInChain(DetailAST ifNode) {
        DetailAST next = null;
        final DetailAST elseNode = ifNode.findFirstToken(TokenTypes.LITERAL_ELSE);
        if (elseNode != null && elseNode.getFirstChild().getType() == TokenTypes.LITERAL_IF) {
            next = elseNode.getFirstChild();
        }
        return next;
    }

    /**
     * Compares two AST subtrees (including the siblings of the given nodes)
     * by token type, token text and shape.
     *
     * @param left first node
     * @param right second node
     * @return true if both subtrees have identical structure
     */
    private static boolean isStructurallyEqual(DetailAST left, DetailAST right) {
        boolean equal = true;
        DetailAST currentLeft = left;
        DetailAST currentRight = right;
        while (equal && currentLeft != null && currentRight != null) {
            equal = currentLeft.getType() == currentRight.getType()
                && currentLeft.getText().equals(currentRight.getText())
                && isStructurallyEqual(currentLeft.getFirstChild(), currentRight.getFirstChild());
            currentLeft = currentLeft.getNextSibling();
            currentRight = currentRight.getNextSibling();
        }
        return equal && currentLeft == null && currentRight == null;
    }

}
