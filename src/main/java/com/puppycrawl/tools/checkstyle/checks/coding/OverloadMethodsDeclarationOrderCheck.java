////
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

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that overloaded methods are grouped together. Overloaded methods have the same
 * name but different signatures where the signature can differ by the number of
 * input parameters or type of input parameters or both.
 * </div>
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
 * {@code overload.methods.declaration}
 * </li>
 * </ul>
 *
 * @since 5.8
 */
@StatelessCheck
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

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
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();

        final int[] tokenTypes = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.RECORD_DEF,
        };

        if (TokenUtil.isOfType(parentType, tokenTypes)) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock
     *        is a class, interface or enum object block.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        DetailAST currentToken = objectBlock.getFirstChild();
        final Map<String, Integer> methodIndexMap = new HashMap<>();
        final Map<String, Integer> methodLineNumberMap = new HashMap<>();
        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final String methodName =
                    currentToken.findFirstToken(TokenTypes.IDENT).getText();
                final Integer previousIndex = methodIndexMap.get(methodName);
                final DetailAST previousSibling = currentToken.getPreviousSibling();
                final boolean isMethod = previousSibling.getType() == TokenTypes.METHOD_DEF;

                if (previousIndex != null
                    && (!isMethod || currentIndex - previousIndex > allowedDistance)) {
                    final int previousLineWithOverloadMethod =
                        methodLineNumberMap.get(methodName);
                    log(currentToken, MSG_KEY,
                        previousLineWithOverloadMethod);
                }
                methodIndexMap.put(methodName, currentIndex);
                methodLineNumberMap.put(methodName, currentToken.getLineNo());
            }
            currentToken = currentToken.getNextSibling();
        }
    }

}
