////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
public class MethodCallHandler extends AbstractExpressionHandler {
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public MethodCallHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "method call", ast, parent);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final IndentLevel indentLevel;
        // if inside a method call's params, this could be part of
        // an expression, so get the previous line's start
        if (getParent() instanceof MethodCallHandler) {
            final MethodCallHandler container =
                    (MethodCallHandler) getParent();
            if (areOnSameLine(container.getMainAst(), getMainAst())
                    || isChainedMethodCallWrapped()) {
                indentLevel = container.getIndent();
            }
            // we should increase indentation only if this is the first
            // chained method call which was moved to the next line
            else {
                indentLevel = new IndentLevel(container.getIndent(), getBasicOffset());
            }
        }
        else {
            // if our expression isn't first on the line, just use the start
            // of the line
            final LineSet lines = new LineSet();
            findSubtreeLines(lines, getMainAst().getFirstChild(), true);
            final int firstCol = lines.firstLineCol();
            final int lineStart = getLineStart(getFirstAst(getMainAst()));
            if (lineStart == firstCol) {
                indentLevel = super.getIndentImpl();
            }
            else {
                indentLevel = new IndentLevel(lineStart);
            }
        }
        return indentLevel;
    }

    /**
     * If this is the first chained method call which was moved to the next line.
     * @return true if chained class are wrapped
     */
    private boolean isChainedMethodCallWrapped() {
        boolean result = false;
        final DetailAST main = getMainAst();
        final DetailAST dot = main.getFirstChild();
        final DetailAST target = dot.getFirstChild();

        final DetailAST dot1 = target.getFirstChild();
        final DetailAST target1 = dot1.getFirstChild();

        if (dot1.getType() == TokenTypes.DOT
            && target1.getType() == TokenTypes.METHOD_CALL) {
            result = true;
        }
        return result;
    }

    /**
     * Get the first AST of the specified method call.
     *
     * @param ast
     *            the method call
     *
     * @return the first AST of the specified method call
     */
    private static DetailAST getFirstAst(DetailAST ast) {
        // walk down the first child part of the dots that make up a method
        // call name

        DetailAST astNode = ast.getFirstChild();
        while (astNode.getType() == TokenTypes.DOT) {
            astNode = astNode.getFirstChild();
        }
        return astNode;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        // for whatever reason a method that crosses lines, like asList
        // here:
        //            System.out.println("methods are: " + Arrays.asList(
        //                new String[] {"method"}).toString());
        // will not have the right line num, so just get the child name

        final DetailAST first = getMainAst().getFirstChild();
        IndentLevel suggestedLevel = new IndentLevel(getLineStart(first));
        if (!areOnSameLine(child.getMainAst().getFirstChild(),
                           getMainAst().getFirstChild())) {
            suggestedLevel = new IndentLevel(suggestedLevel,
                    getBasicOffset(),
                    getIndentCheck().getLineWrappingIndentation());
        }

        // If the right parenthesis is at the start of a line;
        // include line wrapping in suggested indent level.
        final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        if (getLineStart(rparen) == rparen.getColumnNo()) {
            suggestedLevel.addAcceptedIndent(new IndentLevel(
                    getParent().getSuggestedChildIndent(this),
                    getIndentCheck().getLineWrappingIndentation()
            ));
        }

        return suggestedLevel;
    }

    @Override
    public void checkIndentation() {
        final DetailAST exprNode = getMainAst().getParent();
        if (exprNode.getParent().getType() == TokenTypes.SLIST) {
            final DetailAST methodName = getMainAst().getFirstChild();
            checkExpressionSubtree(methodName, getIndent(), false, false);

            final DetailAST lparen = getMainAst();
            final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
            checkLParen(lparen);

            if (rparen.getLineNo() != lparen.getLineNo()) {
                checkExpressionSubtree(
                    getMainAst().findFirstToken(TokenTypes.ELIST),
                    new IndentLevel(getIndent(), getBasicOffset()),
                    false, true);

                checkRParen(lparen, rparen);
                checkWrappingIndentation(getMainAst(), getMethodCallLastNode(getMainAst()));
            }
        }
    }

    @Override
    protected boolean shouldIncreaseIndent() {
        return false;
    }

    /**
     * Returns method call right paren.
     * @param firstNode
     *          method call ast(TokenTypes.METHOD_CALL)
     * @return ast node containing right paren for specified method call. If
     *     method calls are chained returns right paren for last call.
     */
    private static DetailAST getMethodCallLastNode(DetailAST firstNode) {
        return firstNode.getLastChild();
    }
}
