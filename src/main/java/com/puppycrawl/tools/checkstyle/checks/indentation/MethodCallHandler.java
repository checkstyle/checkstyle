////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
        super(indentCheck,
            ast.getType() == TokenTypes.METHOD_CALL
                ? "method call" : "ctor call",
            ast,
            parent);
    }

    @Override
    protected IndentLevel getLevelImpl() {
        // if inside a method call's params, this could be part of
        // an expression, so get the previous line's start
        if (getParent() instanceof MethodCallHandler) {
            final MethodCallHandler container =
                    (MethodCallHandler) getParent();
            if (container != null) {
                if (areOnSameLine(container.getMainAst(), getMainAst())) {
                    return container.getLevel();
                }

                // we should increase indentation only if this is the first
                // chained method call which was moved to the next line
                if (isChainedMethodCallWrapped()) {
                    return container.getLevel();
                }
                else {
                    return new IndentLevel(container.getLevel(), getBasicOffset());
                }
            }

            // if we get here, we are the child of the left hand side (name
            //  side) of a method call with no "containing" call, use
            //  the first non-method call parent

            AbstractExpressionHandler p = getParent();
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
     * if this is the first chained method call which was moved to the next line
     * @return true if chained class are wrapped
     */
    private boolean isChainedMethodCallWrapped() {
        boolean result = false;
        final DetailAST main = getMainAst();
        final DetailAST dot = main.getFirstChild();
        final DetailAST target = dot.getFirstChild();

        if (dot.getType() == TokenTypes.DOT
            && target.getType() == TokenTypes.METHOD_CALL) {
            final DetailAST dot1 = target.getFirstChild();
            final DetailAST target1 = dot1.getFirstChild();

            if (dot1.getType() == TokenTypes.DOT
                && target1.getType() == TokenTypes.METHOD_CALL) {
                result = true;
            }
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
        while (astNode != null && astNode.getType() == TokenTypes.DOT) {
            astNode = astNode.getFirstChild();
        }

        if (astNode == null) {
            astNode = ast;
        }

        return astNode;
    }

    @Override
    public IndentLevel suggestedChildLevel(AbstractExpressionHandler child) {
        // for whatever reason a method that crosses lines, like asList
        // here:
        //            System.out.println("methods are: " + Arrays.asList(
        //                new String[] {"method"}).toString());
        // will not have the right line num, so just get the child name

        final DetailAST first = getMainAst().getFirstChild();
        int indentLevel = getLineStart(first);
        if (!areOnSameLine(child.getMainAst().getFirstChild(),
                           getMainAst().getFirstChild())) {
            indentLevel += getBasicOffset();
        }
        return new IndentLevel(indentLevel);
    }

    @Override
    public void checkIndentation() {
        final DetailAST exprNode = getMainAst().getParent();
        if (exprNode.getParent().getType() != TokenTypes.LCURLY
            && exprNode.getParent().getType() != TokenTypes.SLIST) {
            return;
        }
        final DetailAST methodName = getMainAst().getFirstChild();
        checkExpressionSubtree(methodName, getLevel(), false, false);

        final DetailAST lparen = getMainAst();
        final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        checkLParen(lparen);

        if (rparen.getLineNo() == lparen.getLineNo()) {
            return;
        }

        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.ELIST),
            new IndentLevel(getLevel(), getBasicOffset()),
            false, true);

        checkRParen(lparen, rparen);
        final LineWrappingHandler lineWrap =
            new LineWrappingHandler(getIndentCheck(), getMainAst(),
                    getMethodCallLastNode(getMainAst()));
        lineWrap.checkIndentation();
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
     * method calls are chained returns right paren for last call.
     */
    private static DetailAST getMethodCallLastNode(DetailAST firstNode) {
        DetailAST lastNode;
        if (firstNode.getNextSibling() == null) {
            lastNode = firstNode.getLastChild();
        }
        else {
            lastNode = firstNode.getNextSibling();
        }
        return lastNode;
    }
}
