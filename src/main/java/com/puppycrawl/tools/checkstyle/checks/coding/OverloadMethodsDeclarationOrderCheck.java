////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that overload methods are grouped together. Example:
 * </p>
 * <pre>
 * {@code
 * public void foo(int i) {}
 * public void foo(String s) {}
 * public void notFoo() {} // Have to be after foo(int i, String s)
 * public void foo(int i, String s) {}
 * }
 * </pre>
 * <p>
 * An example of how to configure the check is:
 * </p>
 *
 * <pre>
 * &lt;module name="OverloadMethodsDeclarationOrder"/&gt;
 * </pre>
 * @author maxvetrenko
 */
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int parentType = ast.getParent().getType();
        if (parentType == TokenTypes.CLASS_DEF
                || parentType == TokenTypes.ENUM_DEF
                || parentType == TokenTypes.INTERFACE_DEF
                || parentType == TokenTypes.LITERAL_NEW) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
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
                if (methodIndexMap.containsKey(methodName)) {
                    final int previousIndex = methodIndexMap.get(methodName);
                    if (currentIndex - previousIndex > allowedDistance) {
                        final int previousLineWithOverloadMethod =
                                methodLineNumberMap.get(methodName);
                        log(currentToken.getLineNo(), MSG_KEY,
                                previousLineWithOverloadMethod);
                    }
                }
                methodIndexMap.put(methodName, currentIndex);
                methodLineNumberMap.put(methodName, currentToken.getLineNo());
            }
            currentToken = currentToken.getNextSibling();
        }
    }
}
