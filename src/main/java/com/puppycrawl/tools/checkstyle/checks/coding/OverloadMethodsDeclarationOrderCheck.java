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
 * @since 5.8
 */
@StatelessCheck
public class OverloadMethodsDeclarationOrderCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "overload.methods.declaration";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ORDER = "overload.methods.declaration.order";

    /**
     * Control whether to order overloaded methods by increasing parameter count.
     */
    private boolean orderByIncreasingParameterCount;

    /**
     * Creates a new {@code OverloadMethodsDeclarationOrderCheck} instance.
     */
    public OverloadMethodsDeclarationOrderCheck() {
        // no code by default
    }

    /**
     * Setter to control whether to enforce order by increasing parameter count (arity) or not.
     *
     * @param orderByIncreasingParameterCount true if order by increasing parameter
     *               count is required.
     * @since 13.7.0
     */
    public void setOrderByIncreasingParameterCount(boolean orderByIncreasingParameterCount) {
        this.orderByIncreasingParameterCount = orderByIncreasingParameterCount;
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
            TokenTypes.OBJBLOCK,
            TokenTypes.COMPACT_COMPILATION_UNIT,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int[] objectBlockParentTypes = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.RECORD_DEF,
        };

        if (ast.getType() == TokenTypes.COMPACT_COMPILATION_UNIT
            || TokenUtil.isOfType(ast.getParent().getType(), objectBlockParentTypes)) {
            checkOverloadMethodsGrouping(ast);
        }
    }

    /**
     * Checks that if overload methods are grouped together they should not be
     * separated from each other.
     *
     * @param objectBlock
     *        is a class, interface, enum object block, or a compact
     *        compilation unit for a JEP 512 compact source file.
     */
    private void checkOverloadMethodsGrouping(DetailAST objectBlock) {
        final int allowedDistance = 1;
        DetailAST currentToken = objectBlock.getFirstChild();
        final Map<String, Integer> methodIndexMap = new HashMap<>();
        final Map<String, Integer> methodLineNumberMap = new HashMap<>();

        final Map<String, Integer> methodParameterCountMap = new HashMap<>();
        final Map<String, Boolean> methodIsOrderedMap = new HashMap<>();

        int currentIndex = 0;
        while (currentToken != null) {
            if (currentToken.getType() == TokenTypes.METHOD_DEF) {
                currentIndex++;
                final String methodName =
                        currentToken.findFirstToken(TokenTypes.IDENT).getText();
                final Integer previousIndex = methodIndexMap.get(methodName);

                if (previousIndex != null) {
                    final DetailAST previousSibling = currentToken.getPreviousSibling();
                    final boolean isMethod = previousSibling.getType() == TokenTypes.METHOD_DEF;

                    if (!isMethod || currentIndex - previousIndex > allowedDistance) {
                        final int previousLineWithOverloadMethod =
                                methodLineNumberMap.get(methodName);
                        log(currentToken, MSG_KEY,
                                previousLineWithOverloadMethod);
                    }

                    if (orderByIncreasingParameterCount) {
                        checkMethodOrdering(currentToken, methodName,
                                methodIsOrderedMap, methodParameterCountMap);
                    }
                }
                methodIsOrderedMap.putIfAbsent(methodName, Boolean.TRUE);
                methodIndexMap.put(methodName, currentIndex);
                methodLineNumberMap.put(methodName, currentToken.getLineNo());
                methodParameterCountMap.put(methodName, getParameterCount(currentToken));
            }
            currentToken = currentToken.getNextSibling();
        }
    }

    /**
     * Checks that overloaded methods are ordered with increasing parameter count
     * or not.
     *
     * @param currentMethod ast of the method.
     * @param methodName method name.
     * @param methodIsOrderedMap
     *        is a map of method names to booleans indicating whether the method is ordered.
     * @param methodParameterCountMap
     *        is a map of method names to integers indicating the parameter count of the previous
     *        similar method.
     */
    private void checkMethodOrdering(DetailAST currentMethod, String methodName,
        Map<String, Boolean> methodIsOrderedMap, Map<String, Integer> methodParameterCountMap) {

        final int currentParamCount = getParameterCount(currentMethod);
        final boolean isOrdered = methodIsOrderedMap.get(methodName)
                            && currentParamCount >= methodParameterCountMap.get(methodName);
        if (!isOrdered) {
            methodIsOrderedMap.put(methodName, Boolean.FALSE);
            log(currentMethod, MSG_ORDER);
        }
    }

    /**
     * Get the parameter count of a method.
     *
     * @param method the method AST
     * @return the parameter count of the method
     */
    private static int getParameterCount(DetailAST method) {
        final DetailAST params = method.findFirstToken(TokenTypes.PARAMETERS);
        return params.getChildCount(TokenTypes.PARAMETER_DEF);
    }

}
