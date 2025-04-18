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
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
 * @since 10.23.1
 */
@FileStatefulCheck
public class UnusedLocalMethodCheck extends AbstractCheck {

    /** Message key for unused local method violations. */
    public static final String MSG_UNUSED_LOCAL_METHOD = "unused.local.method";

    /** Map of private method names to their AST nodes. */
    private final Map<String, DetailAST> methods = new HashMap<>();

    /** Set of method names that are called in the code. */
    private final Set<String> calls = new HashSet<>();

    /**
     * Returns the default token types this check processes.
     *
     * @return array of default token types
     */
    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    /**
     * Returns the acceptable token types this check processes.
     *
     * @return array of acceptable token types
     */
    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.METHOD_REF,
            TokenTypes.METHOD_CALL,
        };
    }

    /**
     * Returns the required token types this check processes.
     *
     * @return array of required token types
     */
    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    /**
     * Initializes the check at the start of tree processing.
     *
     * @param root the root AST node of the file
     */
    @Override
    public void beginTree(DetailAST root) {
        methods.clear();
        calls.clear();
    }

    /**
     * Processes each token during tree traversal.
     *
     * @param ast the AST node being visited
     */
    @Override
    public void visitToken(DetailAST ast) {
        processMethod(ast.getType(), ast.getFirstChild());
    }

    /**
     * Routes method-related AST nodes to appropriate processing methods.
     *
     * @param type the token type of the AST node
     * @param ast the AST node to process
     * @throws IllegalArgumentException if ast is null
     * @see #processMethodDef(DetailAST, DetailAST)
     * @see #processMethodCall(DetailAST)
     * @see #processMethodRef(DetailAST)
     */
    private void processMethod(int type, DetailAST ast) {
        if (type == TokenTypes.METHOD_DEF) {
            processMethodDef(ast, ast.getParent());
        }
        else if (type == TokenTypes.METHOD_CALL) {
            processMethodCall(ast);
        }
        else {
            processMethodRef(ast);
        }
    }

    /**
     * Processes a method definition node.
     *
     * <p>Records private methods in the methods map for later verification.</p>
     *
     * @param modifiersAST the MODIFIERS node of the method
     * @param methodDefAST the METHOD_DEF node
     */
    private void processMethodDef(DetailAST modifiersAST, DetailAST methodDefAST) {
        if (modifiersAST.findFirstToken(TokenTypes.LITERAL_PRIVATE) != null) {
            methods.put(methodDefAST.findFirstToken(TokenTypes.IDENT).getText(), methodDefAST);
        }
    }

    /**
     * Processes a method call node.
     *
     * <p>Records called method names in the calls set.</p>
     *
     * @param methodCallAST the METHOD_CALL node
     */
    private void processMethodCall(DetailAST methodCallAST) {
        if (methodCallAST.getFirstChild() == null) {
            calls.add(methodCallAST.getText());
        }
        else {
            calls.add(methodCallAST.getFirstChild().getNextSibling().getText());
        }
    }

    /**
     * Processes a method reference node.
     *
     * <p>Records referenced method names in the calls set.</p>
     *
     * @param methodRefAST the METHOD_REF node
     */
    private void processMethodRef(DetailAST methodRefAST) {
        calls.add(methodRefAST.getNextSibling().getText());
    }

    /**
     * Verifies unused methods after complete tree processing.
     *
     * <p>Logs violations for private methods that were declared but not called.</p>
     *
     * @param ast the root AST node of the file
     */
    @Override
    public void finishTree(DetailAST ast) {
        methods.keySet().stream()
                .filter(methodName -> !calls.contains(methodName))
                .forEach(methodName -> {
                    log(methods.get(methodName), MSG_UNUSED_LOCAL_METHOD, methodName);
                });
    }
}
