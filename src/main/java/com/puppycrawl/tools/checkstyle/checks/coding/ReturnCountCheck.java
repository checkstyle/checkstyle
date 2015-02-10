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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractFormatCheck;

/**
 * <p>
 * Restricts return statements to a specified count (default = 2).
 * Ignores specified methods (<code>equals()</code> by default).
 * </p>
 * <p>
 * Rationale: Too many return points can be indication that code is
 * attempting to do too much or may be difficult to understand.
 * </p>
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * TODO: Test for inside a static block
 */
public final class ReturnCountCheck extends AbstractFormatCheck
{
    /** Default allowed number of return statements. */
    private static final int DEFAULT_MAX = 2;

    /** Stack of method contexts. */
    private final FastStack<Context> contextStack = FastStack.newInstance();
    /** Maximum allowed number of return stmts. */
    private int max;
    /** Current method context. */
    private Context context;

    /** Creates new instance of the checks. */
    public ReturnCountCheck()
    {
        super("^equals$");
        setMax(DEFAULT_MAX);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_RETURN,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return new int[]{
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.LITERAL_RETURN,
        };
    }

    /**
     * Getter for max property.
     * @return maximum allowed number of return statements.
     */
    public int getMax()
    {
        return max;
    }

    /**
     * Setter for max property.
     * @param max maximum allowed number of return statements.
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
                visitMethodDef(ast);
                break;
            case TokenTypes.LITERAL_RETURN:
                context.visitLiteralReturn();
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
                leaveMethodDef(ast);
                break;
            case TokenTypes.LITERAL_RETURN:
                // Do nothing
                break;
            default:
                throw new IllegalStateException(ast.toString());
        }
    }

    /**
     * Creates new method context and places old one on the stack.
     * @param ast method definition for check.
     */
    private void visitMethodDef(DetailAST ast)
    {
        contextStack.push(context);
        final DetailAST methodNameAST = ast.findFirstToken(TokenTypes.IDENT);
        context =
            new Context(!getRegexp().matcher(methodNameAST.getText()).find());
    }

    /**
     * Checks number of return statements and restore
     * previous method context.
     * @param ast method def node.
     */
    private void leaveMethodDef(DetailAST ast)
    {
        context.checkCount(ast);
        context = contextStack.pop();
    }

    /**
     * Class to encapsulate information about one method.
     * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
     */
    private class Context
    {
        /** Whether we should check this method or not. */
        private final boolean checking;
        /** Counter for return statements. */
        private int count;

        /**
         * Creates new method context.
         * @param checking should we check this method or not.
         */
        public Context(boolean checking)
        {
            this.checking = checking;
            count = 0;
        }

        /** Increase number of return statements. */
        public void visitLiteralReturn()
        {
            ++count;
        }

        /**
         * Checks if number of return statements in method more
         * than allowed.
         * @param ast method def associated with this context.
         */
        public void checkCount(DetailAST ast)
        {
            if (checking && (count > getMax())) {
                log(ast.getLineNo(), ast.getColumnNo(), "return.count",
                    count, getMax());
            }
        }
    }
}
