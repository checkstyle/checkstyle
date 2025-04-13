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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Restricts the number of executable statements to a specified limit.
 * </div>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum threshold allowed.
 * Type is {@code int}.
 * Default value is {@code 30}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">
 * INSTANCE_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">
 * STATIC_INIT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>.
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
 * {@code executableStatementCount}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class ExecutableStatementCountCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "executableStatementCount";

    /** Default threshold. */
    private static final int DEFAULT_MAX = 30;

    /** Stack of method contexts. */
    private final Deque<Context> contextStack = new ArrayDeque<>();

    /** Specify the maximum threshold allowed. */
    private int max;

    /** Current method context. */
    private Context context;

    /** Constructs a {@code ExecutableStatementCountCheck}. */
    public ExecutableStatementCountCheck() {
        max = DEFAULT_MAX;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.SLIST};
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LAMBDA,
        };
    }

    /**
     * Setter to specify the maximum threshold allowed.
     *
     * @param max the maximum threshold.
     * @since 3.2
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        context = new Context(null);
        contextStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isContainerNode(ast)) {
            visitContainerNode(ast);
        }
        else if (TokenUtil.isOfType(ast, TokenTypes.SLIST)) {
            visitSlist(ast);
        }
        else {
            throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (isContainerNode(ast)) {
            leaveContainerNode(ast);
        }
        else if (!TokenUtil.isOfType(ast, TokenTypes.SLIST)) {
            throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Process the start of the container node.
     *
     * @param ast the token representing the container node.
     */
    private void visitContainerNode(DetailAST ast) {
        contextStack.push(context);
        context = new Context(ast);
    }

    /**
     * Process the end of a container node.
     *
     * @param ast the token representing the container node.
     */
    private void leaveContainerNode(DetailAST ast) {
        final int count = context.getCount();
        if (count > max) {
            log(ast, MSG_KEY, count, max);
        }
        context = contextStack.pop();
    }

    /**
     * Process the end of a statement list.
     *
     * @param ast the token representing the statement list.
     */
    private void visitSlist(DetailAST ast) {
        final DetailAST contextAST = context.getAST();
        DetailAST parent = ast;
        while (parent != null && !isContainerNode(parent)) {
            parent = parent.getParent();
        }
        if (parent == contextAST) {
            context.addCount(ast.getChildCount() / 2);
        }
    }

    /**
     * Check if the node is of type ctor (compact or canonical),
     * instance/ static initializer, method definition or lambda.
     *
     * @param node AST node we are checking
     * @return true if node is of the given types
     */
    private static boolean isContainerNode(DetailAST node) {
        return TokenUtil.isOfType(node, TokenTypes.METHOD_DEF,
            TokenTypes.LAMBDA, TokenTypes.CTOR_DEF, TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT, TokenTypes.COMPACT_CTOR_DEF);
    }

    /**
     * Class to encapsulate counting information about one member.
     */
    private static final class Context {

        /** Member AST node. */
        private final DetailAST ast;

        /** Counter for context elements. */
        private int count;

        /**
         * Creates new member context.
         *
         * @param ast member AST node.
         */
        private Context(DetailAST ast) {
            this.ast = ast;
        }

        /**
         * Increase count.
         *
         * @param addition the count increment.
         */
        public void addCount(int addition) {
            count += addition;
        }

        /**
         * Gets the member AST node.
         *
         * @return the member AST node.
         */
        public DetailAST getAST() {
            return ast;
        }

        /**
         * Gets the count.
         *
         * @return the count.
         */
        public int getCount() {
            return count;
        }

    }

}
