///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that each variable declaration is in its own statement
 * and on its own line.
 * </div>
 *
 * <p>
 * Rationale: <a
 * href="https://checkstyle.org/styleguides/sun-code-conventions-19990420/CodeConventions.doc5.html#a2992">
 * the Java code conventions chapter 6.1</a> recommends that
 * declarations should be one per line/statement.
 * </p>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code multiple.variable.declarations}
 * </li>
 * <li>
 * {@code multiple.variable.declarations.comma}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
@StatelessCheck
public class MultipleVariableDeclarationsCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MULTIPLE = "multiple.variable.declarations";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MULTIPLE_COMMA = "multiple.variable.declarations.comma";

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST nextNode = ast.getNextSibling();

        if (nextNode != null) {
            final boolean isCommaSeparated = nextNode.getType() == TokenTypes.COMMA;

            if (isCommaSeparated
                || nextNode.getType() == TokenTypes.SEMI) {
                nextNode = nextNode.getNextSibling();
            }

            if (nextNode != null
                    && nextNode.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST firstNode = CheckUtil.getFirstNode(ast);
                if (isCommaSeparated) {
                    // Check if the multiple variable declarations are in a
                    // for loop initializer. If they are, then no warning
                    // should be displayed. Declaring multiple variables in
                    // a for loop initializer is a good way to minimize
                    // variable scope. Refer Feature Request Id - 2895985
                    // for more details
                    if (ast.getParent().getType() != TokenTypes.FOR_INIT) {
                        log(firstNode, MSG_MULTIPLE_COMMA);
                    }
                }
                else {
                    final DetailAST lastNode = getLastNode(ast);
                    final DetailAST firstNextNode = CheckUtil.getFirstNode(nextNode);

                    if (TokenUtil.areOnSameLine(firstNextNode, lastNode)) {
                        log(firstNode, MSG_MULTIPLE);
                    }
                }
            }
        }
    }

    /**
     * Finds sub-node for given node maximum (line, column) pair.
     *
     * @param node the root of tree for search.
     * @return sub-node with maximum (line, column) pair.
     */
    private static DetailAST getLastNode(final DetailAST node) {
        DetailAST currentNode = node;
        final DetailAST child = node.getLastChild();
        if (child != null) {
            currentNode = getLastNode(child);
        }

        return currentNode;
    }

}
