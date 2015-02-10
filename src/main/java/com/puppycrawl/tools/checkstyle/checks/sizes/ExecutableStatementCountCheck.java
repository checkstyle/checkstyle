////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Restricts the number of executable statements to a specified limit
 * (default = 30).
 * @author Simon Harris
 */
public final class ExecutableStatementCountCheck
    extends Check
{
    /** default threshold */
    private static final int DEFAULT_MAX = 30;

    /** threshold to report error for */
    private int max;

    /** Stack of method contexts. */
    private final FastStack<Context> contextStack = FastStack.newInstance();

    /** Current method context. */
    private Context context;

    /** Constructs a <code>ExecutableStatementCountCheck</code>. */
    public ExecutableStatementCountCheck()
    {
        setMax(DEFAULT_MAX);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {TokenTypes.SLIST};
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.SLIST,
        };
    }

    /**
     * Gets the maximum threshold.
     * @return the maximum threshold.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Sets the maximum threshold.
     * @param max the maximum threshold.
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
        context = null;
        contextStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                visitMemberDef(ast);
                break;
            case TokenTypes.SLIST:
                visitSlist(ast);
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast)
    {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                leaveMemberDef(ast);
                break;
            case TokenTypes.SLIST:
                // Do nothing
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Process the start of the member definition.
     * @param ast the token representing the member definition.
     */
    private void visitMemberDef(DetailAST ast)
    {
        contextStack.push(context);
        context = new Context(ast);
    }

    /**
     * Process the end of a member definition.
     *
     * @param ast the token representing the member definition.
     */
    private void leaveMemberDef(DetailAST ast)
    {
        final int count = context.getCount();
        if (count > getMax()) {
            log(ast.getLineNo(), ast.getColumnNo(),
                    "executableStatementCount", count, getMax());
        }
        context = contextStack.pop();
    }

    /**
     * Process the end of a statement list.
     *
     * @param ast the token representing the statement list.
     */
    private void visitSlist(DetailAST ast)
    {
        if (context != null) {
            // find member AST for the statement list
            final DetailAST contextAST = context.getAST();
            DetailAST parent = ast.getParent();
            while (parent != null) {
                final int type = parent.getType();
                if ((type == TokenTypes.CTOR_DEF)
                    || (type == TokenTypes.METHOD_DEF)
                    || (type == TokenTypes.INSTANCE_INIT)
                    || (type == TokenTypes.STATIC_INIT))
                {
                    if (parent == contextAST) {
                        context.addCount(ast.getChildCount() / 2);
                    }
                    break;
                }
                parent = parent.getParent();
            }
        }
    }

    /**
     * Class to encapsulate counting information about one member.
     * @author Simon Harris
     */
    private static class Context
    {
        /** Member AST node. */
        private final DetailAST ast;

        /** Counter for context elements. */
        private int count;

        /**
         * Creates new member context.
         * @param ast member AST node.
         */
        public Context(DetailAST ast)
        {
            this.ast = ast;
            count = 0;
        }

        /**
         * Increase count.
         * @param count the count increment.
         */
        public void addCount(int count)
        {
            this.count += count;
        }

        /**
         * Gets the member AST node.
         * @return the member AST node.
         */
        public DetailAST getAST()
        {
            return ast;
        }

        /**
         * Gets the count.
         * @return the count.
         */
        public int getCount()
        {
            return count;
        }
    }
}
