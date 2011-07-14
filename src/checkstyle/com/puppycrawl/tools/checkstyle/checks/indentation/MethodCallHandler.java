////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for method calls.
 *
 * @author jrichard
 */
public class MethodCallHandler extends ExpressionHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAST           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public MethodCallHandler(IndentationCheck aIndentCheck,
        DetailAST aAST, ExpressionHandler aParent)
    {
        super(aIndentCheck,
            aAST.getType() == TokenTypes.METHOD_CALL
                ? "method call" : "ctor call",
            aAST,
            aParent);
    }

    @Override
    protected IndentLevel getLevelImpl()
    {
        // if inside a method call's params, this could be part of
        // an expression, so get the previous line's start
        if (getParent() instanceof MethodCallHandler) {
            final MethodCallHandler container =
                ((MethodCallHandler) getParent());
            if (container != null) {
                if (areOnSameLine(container.getMainAst(), getMainAst())) {
                    return container.getLevel();
                }

                // we should increase indentation only if this is the first
                // chained method call which was moved to the next line
                final DetailAST main = getMainAst();
                final DetailAST dot = main.getFirstChild();
                final DetailAST target = dot.getFirstChild();

                if ((dot.getType() == TokenTypes.DOT)
                    && (target.getType() == TokenTypes.METHOD_CALL))
                {
                    final DetailAST dot1 = target.getFirstChild();
                    final DetailAST target1 = dot1.getFirstChild();

                    if ((dot1.getType() == TokenTypes.DOT)
                        && (target1.getType() == TokenTypes.METHOD_CALL))
                    {
                        return container.getLevel();
                    }
                }
                return new IndentLevel(container.getLevel(), getBasicOffset());
            }

            // if we get here, we are the child of the left hand side (name
            //  side) of a method call with no "containing" call, use
            //  the first non-method call parent

            ExpressionHandler p = getParent();
            while (p instanceof MethodCallHandler) {
                p = p.getParent();
            }
            return p.suggestedChildLevel(this);
        }

        // if our expression isn't first on the line, just use the start
        // of the line
        final LineSet lines = new LineSet();
        findSubtreeLines(lines, getMainAst().getFirstChild(), true);
        final int firstCol = lines.firstLineCol();
        final int lineStart = getLineStart(getFirstAst(getMainAst()));
        if (lineStart != firstCol) {
            return new IndentLevel(lineStart);
        }
        return super.getLevelImpl();
    }

    /**
     * Get the first AST of the specified method call.
     *
     * @param aAst
     *            the method call
     *
     * @return the first AST of the specified method call
     */
    private DetailAST getFirstAst(DetailAST aAst)
    {
        // walk down the first child part of the dots that make up a method
        // call name

        DetailAST ast = aAst.getFirstChild();
        while ((ast != null) && (ast.getType() == TokenTypes.DOT)) {
            ast = ast.getFirstChild();
        }

        if (ast == null) {
            ast = aAst;
        }

        return ast;
    }

    @Override
    public IndentLevel suggestedChildLevel(ExpressionHandler aChild)
    {
        // for whatever reason a method that crosses lines, like asList
        // here:
        //            System.out.println("methods are: " + Arrays.asList(
        //                new String[] {"method"}).toString());
        // will not have the right line num, so just get the child name

        final DetailAST first = getMainAst().getFirstChild();
        int indentLevel = getLineStart(first);
        if (!areOnSameLine(aChild.getMainAst().getFirstChild(),
                           getMainAst().getFirstChild()))
        {
            indentLevel += getBasicOffset();
        }
        return new IndentLevel(indentLevel);
    }

    @Override
    public void checkIndentation()
    {
        final DetailAST methodName = getMainAst().getFirstChild();
        checkExpressionSubtree(methodName, getLevel(), false, false);

        final DetailAST lparen = getMainAst();
        final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        checkLParen(lparen);

        if (rparen.getLineNo() == lparen.getLineNo()) {
            return;
        }

        // if this method name is on the same line as a containing
        // method, don't indent, this allows expressions like:
        //    method("my str" + method2(
        //        "my str2"));
        // as well as
        //    method("my str" +
        //        method2(
        //            "my str2"));
        //

        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.ELIST),
            new IndentLevel(getLevel(), getBasicOffset()),
            false, true);

        checkRParen(lparen, rparen);
    }

    @Override
    protected boolean shouldIncreaseIndent()
    {
        return false;
    }
}
