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
    private final FastStack<Context> contextStack = FastStack.newInstance();
    /** Maximum allowed complexity. */
    private int max;
    /** Current context. */
    private Context context;

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

    @Override
    public int[] getAcceptableTokens()
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

    /**
     * Getter for maximum allowed complexity.
     * @return value of maximum allowed complexity.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Setter for maximum allowed complexity.
     * @param max new maximum allowed complexity.
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                visitMethodDef(ast);
                break;
            case TokenTypes.EXPR:
                visitExpr();
                break;
            case TokenTypes.LAND:
            case TokenTypes.BAND:
            case TokenTypes.LOR:
            case TokenTypes.BOR:
            case TokenTypes.BXOR:
                context.visitBooleanOperator();
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
                leaveMethodDef();
                break;
            case TokenTypes.EXPR:
                leaveExpr(ast);
                break;
            default:
                // Do nothing
        }
    }

    /**
     * Creates new context for a given method.
     * @param ast a method we start to check.
     */
    private void visitMethodDef(DetailAST ast)
    {
        contextStack.push(context);
        context = new Context(!CheckUtils.isEqualsMethod(ast));
    }

    /** Removes old context. */
    private void leaveMethodDef()
    {
        context = contextStack.pop();
    }

    /** Creates and pushes new context. */
    private void visitExpr()
    {
        contextStack.push(context);
        context = new Context((context == null) || context.isChecking());
    }

    /**
     * Restores previous context.
     * @param ast expression we leave.
     */
    private void leaveExpr(DetailAST ast)
    {
        context.checkCount(ast);
        context = contextStack.pop();
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
        private final boolean checking;
        /** Count of boolean operators. */
        private int count;

        /**
         * Creates new instance.
         * @param checking should we check in current context or not.
         */
        public Context(boolean checking)
        {
            this.checking = checking;
            count = 0;
        }

        /**
         * Getter for checking property.
         * @return should we check in current context or not.
         */
        public boolean isChecking()
        {
            return checking;
        }

        /** Increases operator counter. */
        public void visitBooleanOperator()
        {
            ++count;
        }

        /**
         * Checks if we violates maximum allowed complexity.
         * @param ast a node we check now.
         */
        public void checkCount(DetailAST ast)
        {
            if (checking && (count > getMax())) {
                final DetailAST parentAST = ast.getParent();

                log(parentAST.getLineNo(), parentAST.getColumnNo(),
                    "booleanExpressionComplexity", count, getMax());
            }
        }
    }
}
