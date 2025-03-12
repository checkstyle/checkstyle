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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that a local method is declared but not used, except for overloaded ones.
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
 * {@code unused.local.method}
 * </li>
 * </ul>
 *
 * @since 9.3
 */
@FileStatefulCheck
public class UnusedLocalMethodCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_UNUSED_LOCAL_METHOD = "unused.local.method";

    /**
     * Identified methods.
     */
    private final Map<String, DetailAST> methods = new HashMap<>();

    /**
     * Overall calls.
     */
    private final Set<String> calls = new HashSet<>();

    @Override
    public int[] getDefaultTokens() {
        return new int[]{
            TokenTypes.METHOD_DEF,
            TokenTypes.METHOD_REF,
            TokenTypes.METHOD_CALL,
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
        methods.clear();
        calls.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF
            && Objects.nonNull(ast.getFirstChild().getFirstChild())
            && ast.getFirstChild().getFirstChild().getText().equals(Scope.PRIVATE.getName())) {
            methods.put(ast.findFirstToken(TokenTypes.IDENT).getText(), ast);
        }
        else if (ast.getType() == TokenTypes.METHOD_CALL) {
            if (Objects.nonNull(ast.getFirstChild().getFirstChild())) {
                calls.add(ast.getFirstChild().getFirstChild().getNextSibling().getText());
            }
            else {
                calls.add(ast.getFirstChild().getText());
            }
        }
        else {
            calls.add(ast.getFirstChild().getNextSibling().getText());
        }
    }

    @Override
    public void finishTree(DetailAST ast) {
        for (Map.Entry<String, DetailAST> method : methods.entrySet()) {
            if (!calls.contains(method.getKey())) {
                log(method.getValue(), MSG_UNUSED_LOCAL_METHOD, method.getKey());
            }
        }
    }
}
