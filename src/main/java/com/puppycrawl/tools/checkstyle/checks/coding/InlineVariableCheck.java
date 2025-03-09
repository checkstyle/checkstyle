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

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Local variable should not be declared and then immediately returned or thrown.
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
 * {@code inline.variable}
 * </li>
 * </ul>
 *
 * @since 9.3
 */
@FileStatefulCheck
public class InlineVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_INLINE_VARIABLE = "inline.variable";

    /**
     * Identified variable.
     */
    private final List<DetailAST> variables = new ArrayList<>();

    /**
     * Identified variable.
     */
    private final List<DetailAST> usages = new ArrayList<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.VARIABLE_DEF,
        };
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
    public void beginTree(DetailAST root) {
        variables.clear();
        usages.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.VARIABLE_DEF) {
            variables.add(ast);
        }
        if (ast.getType() == TokenTypes.LITERAL_THROW) {
            usages.add(ast);
        }
        if (ast.getType() == TokenTypes.LITERAL_RETURN) {
            usages.add(ast);
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        for (DetailAST variable : variables) {
            final String name =
                variable.getFirstChild().getNextSibling().getNextSibling().getText();
            usages.stream()
                .filter(usage -> {
                    return usage.getLineNo() - 1 == variable.getLineNo()
                        && name.equals(usageName(usage));
                })
                .forEach(detailAST -> log(variable, MSG_INLINE_VARIABLE, name));
        }
    }

    /**
     * Usage name.
     *
     * @param usage checked for either LITERAL_RETURN or LITERAL_THROW.
     * @return referenced name
     */
    private static String usageName(DetailAST usage) {
        final String name;
        if (usage.getType() == TokenTypes.LITERAL_RETURN) {
            name = usage.getFirstChild().getFirstChild().getText();
        }
        else {
            name = usage.getFirstChild()
                .getFirstChild()
                .getFirstChild()
                .getNextSibling()
                .getNextSibling()
                .getFirstChild()
                .getFirstChild()
                .getText();
        }
        return name;
    }
}
