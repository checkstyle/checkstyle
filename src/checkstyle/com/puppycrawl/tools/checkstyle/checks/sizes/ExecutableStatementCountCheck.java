////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
    private int mMax;

    /** Stack of method contexts. */
    private final FastStack<Context> mContextStack = FastStack.newInstance();

    /** Current method context. */
    private Context mContext;

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

    /**
     * Gets the maximum threshold.
     * @return the maximum threshold.
     */
    public int getMax()
    {
        return mMax;
    }

    /**
     * Sets the maximum threshold.
     * @param aMax the maximum threshold.
     */
    public void setMax(int aMax)
    {
        mMax = aMax;
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mContext = null;
        mContextStack.clear();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            visitMemberDef(aAST);
            break;
        case TokenTypes.SLIST:
            visitSlist(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            leaveMemberDef(aAST);
            break;
        case TokenTypes.SLIST:
            // Do nothing
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Process the start of the member definition.
     * @param aAST the token representing the member definition.
     */
    private void visitMemberDef(DetailAST aAST)
    {
        mContextStack.push(mContext);
        mContext = new Context(aAST);
    }

    /**
     * Process the end of a member definition.
     *
     * @param aAST the token representing the member definition.
     */
    private void leaveMemberDef(DetailAST aAST)
    {
        final int count = mContext.getCount();
        if (count > getMax()) {
            log(aAST.getLineNo(), aAST.getColumnNo(),
                    "executableStatementCount", count, getMax());
        }
        mContext = mContextStack.pop();
    }

    /**
     * Process the end of a statement list.
     *
     * @param aAST the token representing the statement list.
     */
    private void visitSlist(DetailAST aAST)
    {
        if (mContext != null) {
            // find member AST for the statement list
            final DetailAST contextAST = mContext.getAST();
            DetailAST parent = aAST.getParent();
            while (parent != null) {
                final int type = parent.getType();
                if ((type == TokenTypes.CTOR_DEF)
                    || (type == TokenTypes.METHOD_DEF)
                    || (type == TokenTypes.INSTANCE_INIT)
                    || (type == TokenTypes.STATIC_INIT))
                {
                    if (parent == contextAST) {
                        mContext.addCount(aAST.getChildCount() / 2);
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
        private final DetailAST mAST;

        /** Counter for context elements. */
        private int mCount;

        /**
         * Creates new member context.
         * @param aAST member AST node.
         */
        public Context(DetailAST aAST)
        {
            mAST = aAST;
            mCount = 0;
        }

        /**
         * Increase count.
         * @param aCount the count increment.
         */
        public void addCount(int aCount)
        {
            mCount += aCount;
        }

        /**
         * Gets the member AST node.
         * @return the member AST node.
         */
        public DetailAST getAST()
        {
            return mAST;
        }

        /**
         * Gets the count.
         * @return the count.
         */
        public int getCount()
        {
            return mCount;
        }
    }
}
