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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * Restricts nested boolean operators (&amp;&amp;, ||, &amp;, | and ^) to
 * a specified depth (default = 3).
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public final class BooleanExpressionComplexityCheck extends Check
{
    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 3;

    /** Stack of contexts. */
    private final FastStack<Context> mContextStack = FastStack.newInstance();
    /** Maximum allowed complexity. */
    private int mMax;
    /** Current context. */
    private Context mContext;

    /** Creates new instance of the check. */
    public BooleanExpressionComplexityCheck()
    {
        setMax(DEFAULT_MAX);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
            TokenTypes.LAND,
            TokenTypes.BAND,
            TokenTypes.LOR,
            TokenTypes.BOR,
            TokenTypes.BXOR,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.EXPR,
        };
    }

    /**
     * Getter for maximum allowed complexity.
     * @return value of maximum allowed complexity.
     */
    public int getMax()
    {
        return mMax;
    }

    /**
     * Setter for maximum allowed complexity.
     * @param aMax new maximum allowed complexity.
     */
    public void setMax(int aMax)
    {
        mMax = aMax;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
            visitMethodDef(aAST);
            break;
        case TokenTypes.EXPR:
            visitExpr();
            break;
        case TokenTypes.LAND:
        case TokenTypes.BAND:
        case TokenTypes.LOR:
        case TokenTypes.BOR:
        case TokenTypes.BXOR:
            mContext.visitBooleanOperator();
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
            leaveMethodDef();
            break;
        case TokenTypes.EXPR:
            leaveExpr(aAST);
            break;
        default:
            // Do nothing
        }
    }

    /**
     * Creates new context for a given method.
     * @param aAST a method we start to check.
     */
    private void visitMethodDef(DetailAST aAST)
    {
        mContextStack.push(mContext);
        mContext = new Context(!CheckUtils.isEqualsMethod(aAST));
    }

    /** Removes old context. */
    private void leaveMethodDef()
    {
        mContext = mContextStack.pop();
    }

    /** Creates and pushes new context. */
    private void visitExpr()
    {
        mContextStack.push(mContext);
        mContext = new Context((mContext == null) || mContext.isChecking());
    }

    /**
     * Restores previous context.
     * @param aAST expression we leave.
     */
    private void leaveExpr(DetailAST aAST)
    {
        mContext.checkCount(aAST);
        mContext = mContextStack.pop();
    }

    /**
     * Represents context (method/expression) in which we check complexity.
     *
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     * @author o_sukhodolsky
     */
    private class Context
    {
        /**
         * Should we perform check in current context or not.
         * Usually false if we are inside equals() method.
         */
        private final boolean mChecking;
        /** Count of boolean operators. */
        private int mCount;

        /**
         * Creates new instance.
         * @param aChecking should we check in current context or not.
         */
        public Context(boolean aChecking)
        {
            mChecking = aChecking;
            mCount = 0;
        }

        /**
         * Getter for checking property.
         * @return should we check in current context or not.
         */
        public boolean isChecking()
        {
            return mChecking;
        }

        /** Increases operator counter. */
        public void visitBooleanOperator()
        {
            ++mCount;
        }

        /**
         * Checks if we violates maximum allowed complexity.
         * @param aAST a node we check now.
         */
        public void checkCount(DetailAST aAST)
        {
            if (mChecking && (mCount > getMax())) {
                final DetailAST parentAST = aAST.getParent();

                log(parentAST.getLineNo(), parentAST.getColumnNo(),
                    "booleanExpressionComplexity", mCount, getMax());
            }
        }
    }
}
