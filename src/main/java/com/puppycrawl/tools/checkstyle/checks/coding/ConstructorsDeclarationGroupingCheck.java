///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that all constructors are grouped together.
 * If there is any code between the constructors ( expect comments )
 * then this check will give an error.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code constructors.declaration.grouping}
 * </li>
 * </ul>
 *
 * @since 10.16.0
 */

@FileStatefulCheck
public class ConstructorsDeclarationGroupingCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "constructors.declaration.grouping";

    /**
     * Specifies different Object Blocks scope.
     */
    private final Map<DetailAST, Integer> allObjBlocks = new HashMap<>();

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
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public void beginTree(DetailAST rootAst) {
        allObjBlocks.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST currObjBlock = ast.getParent();
        final Integer previousCtorLineNo = allObjBlocks.get(currObjBlock);

        if (previousCtorLineNo != null) {
            final DetailAST previousSibling = ast.getPreviousSibling();
            final int siblingType = previousSibling.getType();
            final boolean isCtor = siblingType == TokenTypes.CTOR_DEF;
            final boolean isCompactCtor = siblingType == TokenTypes.COMPACT_CTOR_DEF;

            if (!isCtor && !isCompactCtor) {
                log(ast, MSG_KEY, previousCtorLineNo);
            }

            allObjBlocks.put(currObjBlock, ast.getLineNo());
        }
        else {
            allObjBlocks.put(currObjBlock, ast.getLineNo());
        }
    }
}
