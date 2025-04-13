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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Finds nested blocks (blocks that are used freely in the code).
 * </div>
 *
 * <p>
 * Rationale: Nested blocks are often leftovers from the
 * debugging process, they confuse the reader.
 * </p>
 *
 * <p>
 * For example, this check finds the obsolete braces in
 * </p>
 * <pre>
 * public void guessTheOutput()
 * {
 *   int whichIsWhich = 0;
 *   {
 *     whichIsWhich = 2;
 *   }
 *   System.out.println("value = " + whichIsWhich);
 * }
 * </pre>
 *
 * <p>
 * and debugging / refactoring leftovers such as
 * </p>
 * <pre>
 * // if (conditionThatIsNotUsedAnyLonger)
 * {
 *   System.out.println("unconditional");
 * }
 * </pre>
 *
 * <p>
 * A case in a switch statement does not implicitly form a block.
 * Thus, to be able to introduce local variables that have case scope
 * it is necessary to open a nested block. This is supported, set
 * the allowInSwitchCase property to true and include all statements
 * of the case in the block.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowInSwitchCase} - Allow nested blocks if they are the
 * only child of a switch case.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
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
 * {@code block.nested}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class AvoidNestedBlocksCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_BLOCK_NESTED = "block.nested";

    /**
     * Allow nested blocks if they are the only child of a switch case.
     */
    private boolean allowInSwitchCase;

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
        return new int[] {TokenTypes.SLIST};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        if (parent.getType() == TokenTypes.SLIST
                && (!allowInSwitchCase || hasSiblings(ast))) {
            log(ast, MSG_KEY_BLOCK_NESTED);
        }
    }

    /**
     * Checks whether the AST node has any siblings or not.
     *
     * @param ast node to examine
     * @return {@code true} if the node has one or more siblings
     */
    private static boolean hasSiblings(DetailAST ast) {
        return ast.getPreviousSibling() != null || ast.getNextSibling() != null;
    }

    /**
     * Setter to allow nested blocks if they are the only child of a switch case.
     *
     * @param allowInSwitchCase whether nested blocks are allowed
     *                 if they are the only child of a switch case.
     * @since 3.2
     */
    public void setAllowInSwitchCase(boolean allowInSwitchCase) {
        this.allowInSwitchCase = allowInSwitchCase;
    }

}
