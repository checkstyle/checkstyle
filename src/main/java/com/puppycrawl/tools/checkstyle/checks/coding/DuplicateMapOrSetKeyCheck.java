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
import java.util.Objects;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for duplicate key or element arguments in Java factory methods like
 * {@code Map.of}, {@code Set.of}, and {@code Map.ofEntries}.
 * </div>
 *
 * @since 10.22.0
 */
@StatelessCheck
public class DuplicateMapOrSetKeyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_KEY = "duplicate.map.or.set.key";

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
            TokenTypes.METHOD_CALL,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST dot = ast.findFirstToken(TokenTypes.DOT);
        if (dot != null) {
            final DetailAST target = dot.getFirstChild();
            final DetailAST methodName = dot.getLastChild();

            if (target != null && methodName != null) {
                final String targetName = target.getText();
                final String name = methodName.getText();

                if ("Map".equals(targetName) && "of".equals(name)) {
                    processMapOfCall(ast);
                }
                else if ("Set".equals(targetName) && "of".equals(name)) {
                    processSetOfCall(ast);
                }
                else if ("Map".equals(targetName) && "ofEntries".equals(name)) {
                    processMapOfEntriesCall(ast);
                }
            }
        }
    }

    /**
     * Processes Map.of(...) method call for duplicate keys (even indexed arguments: 0, 2, 4...).
     *
     * @param methodCall METHOD_CALL AST node
     */
    private void processMapOfCall(DetailAST methodCall) {
        final List<DetailAST> args = getArgumentNodes(methodCall);
        final List<DetailAST> keys = new ArrayList<>();

        for (int i = 0; i < args.size(); i += 2) {
            final DetailAST keyArg = args.get(i);
            checkAndAddKey(keyArg, keys);
        }
    }

    /**
     * Processes Set.of(...) method call for duplicate elements (all arguments).
     *
     * @param methodCall METHOD_CALL AST node
     */
    private void processSetOfCall(DetailAST methodCall) {
        final List<DetailAST> args = getArgumentNodes(methodCall);
        final List<DetailAST> keys = new ArrayList<>();

        for (DetailAST arg : args) {
            checkAndAddKey(arg, keys);
        }
    }

    /**
     * Processes Map.ofEntries(...) method call for duplicate keys in nested Map.entry calls.
     *
     * @param methodCall METHOD_CALL AST node
     */
    private void processMapOfEntriesCall(DetailAST methodCall) {
        final List<DetailAST> args = getArgumentNodes(methodCall);
        final List<DetailAST> keys = new ArrayList<>();

        for (DetailAST arg : args) {
            final DetailAST entryCall = getMethodCallFromArg(arg);
            if (entryCall != null && isMapEntryCall(entryCall)) {
                final List<DetailAST> entryArgs = getArgumentNodes(entryCall);
                if (!entryArgs.isEmpty()) {
                    checkAndAddKey(entryArgs.get(0), keys);
                }
            }
        }
    }

    /**
     * Checks if a method call node represents Map.entry(...).
     *
     * @param methodCall METHOD_CALL AST node
     * @return true if method call is Map.entry
     */
    private static boolean isMapEntryCall(DetailAST methodCall) {
        boolean result = false;
        final DetailAST dot = methodCall.findFirstToken(TokenTypes.DOT);
        if (dot != null) {
            final DetailAST target = dot.getFirstChild();
            final DetailAST methodName = dot.getLastChild();
            if (target != null && methodName != null) {
                result = "Map".equals(target.getText()) && "entry".equals(methodName.getText());
            }
        }
        return result;
    }

    /**
     * Extracts argument EXPR children from ELIST of a METHOD_CALL.
     *
     * @param methodCall METHOD_CALL AST node
     * @return list of argument EXPR nodes
     */
    private static List<DetailAST> getArgumentNodes(DetailAST methodCall) {
        final List<DetailAST> argumentNodes = new ArrayList<>();
        final DetailAST elist = methodCall.findFirstToken(TokenTypes.ELIST);
        if (elist != null) {
            for (DetailAST child = elist.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.EXPR && child.getFirstChild() != null) {
                    argumentNodes.add(child.getFirstChild());
                }
            }
        }
        return argumentNodes;
    }

    /**
     * Extracts nested METHOD_CALL node from an argument node if present.
     *
     * @param arg argument AST node
     * @return METHOD_CALL AST node or null
     */
    private static DetailAST getMethodCallFromArg(DetailAST arg) {
        DetailAST result = null;
        if (arg.getType() == TokenTypes.METHOD_CALL) {
            result = arg;
        }
        else if (arg.getParent() != null && arg.getParent().getType() == TokenTypes.EXPR) {
            result = arg.getParent().findFirstToken(TokenTypes.METHOD_CALL);
        }
        return result;
    }

    /**
     * Checks if key AST is structurally equal to any previously recorded key.
     * If so, logs a violation; otherwise adds it to the list.
     *
     * @param key key/element AST node
     * @param keys list of previously recorded key AST nodes
     */
    private void checkAndAddKey(DetailAST key, List<DetailAST> keys) {
        boolean isDuplicate = false;
        int duplicateLineNumber = 0;

        for (DetailAST prevKey : keys) {
            if (isAstEqual(key, prevKey)) {
                isDuplicate = true;
                duplicateLineNumber = prevKey.getLineNo();
                break;
            }
        }

        if (isDuplicate) {
            log(key, MSG_KEY, duplicateLineNumber);
        }
        else {
            keys.add(key);
        }
    }

    /**
     * Recursively compares two DetailAST subtrees structurally.
     *
     * @param node1 first AST node
     * @param node2 second AST node
     * @return true if subtrees are structurally identical
     */
    private static boolean isAstEqual(DetailAST node1, DetailAST node2) {
        if (node1 == node2) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        if (node1.getType() != node2.getType()) {
            return false;
        }
        if (!Objects.equals(node1.getText(), node2.getText())) {
            return false;
        }

        return isAstEqual(node1.getFirstChild(), node2.getFirstChild())
                && isAstEqual(node1.getNextSibling(), node2.getNextSibling());
    }
}
