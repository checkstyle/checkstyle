///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for method calls.
 *
 */
public class MethodCallHandler extends AbstractExpressionHandler {

    /**
     * The instance of {@code IndentationCheck} used by this class.
     */
    private final IndentationCheck indentCheck;

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
        this.indentCheck = indentCheck;
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final IndentLevel indentLevel;
        // if inside a method call's params, this could be part of
        // an expression, so get the previous line's start
        if (getParent() instanceof MethodCallHandler) {
            final MethodCallHandler container =
                    (MethodCallHandler) getParent();
            if (TokenUtil.areOnSameLine(container.getMainAst(), getMainAst())
                    || isChainedMethodCallWrapped()
                    || areMethodsChained(container.getMainAst(), getMainAst())) {
                indentLevel = container.getIndent();
            }
            // we should increase indentation only if this is the first
            // chained method call which was moved to the next line
            else {
                indentLevel = new IndentLevel(container.getIndent(),
                    getIndentCheck().getLineWrappingIndentation());
            }
        }
        else if (getMainAst().getFirstChild().getType() == TokenTypes.LITERAL_NEW) {
            indentLevel = super.getIndentImpl();
        }
        else {
            // if our expression isn't first on the line, just use the start
            // of the line
            final DetailAstSet astSet = new DetailAstSet(indentCheck);
            findSubtreeAst(astSet, getMainAst().getFirstChild(), true);
            final int firstCol = expandedTabsColumnNo(astSet.firstLine());
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
     * Checks if ast2 is a chained method call that starts on the same level as ast1 ends.
     * In other words, if the right paren of ast1 is on the same level as the lparen of ast2:
     * {@code
     *     value.methodOne(
     *         argument1
     *     ).methodTwo(
     *         argument2
     *     );
     * }
     *
     * @param ast1 Ast1
     * @param ast2 Ast2
     * @return True if ast2 begins on the same level that ast1 ends
     */
    private static boolean areMethodsChained(DetailAST ast1, DetailAST ast2) {
        final DetailAST rparen = ast1.findFirstToken(TokenTypes.RPAREN);
        return TokenUtil.areOnSameLine(rparen, ast2);
    }

    /**
     * If this is the first chained method call which was moved to the next line.
     *
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

    /**
     * Returns method or constructor name. For {@code foo(arg)} it is `foo`, for
     *     {@code foo.bar(arg)} it is `bar` for {@code super(arg)} it is 'super'.
     *
     * @return TokenTypes.IDENT node for a method call, TokenTypes.SUPER_CTOR_CALL otherwise.
     */
    private DetailAST getMethodIdentAst() {
        DetailAST ast = getMainAst();
        if (ast.getType() != TokenTypes.SUPER_CTOR_CALL) {
            ast = ast.getFirstChild();
            if (ast.getType() == TokenTypes.DOT) {
                ast = ast.getLastChild();
            }
        }
        return ast;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        // for whatever reason a method that crosses lines, like asList
        // here:
        //            System.out.println("methods are: " + Arrays.asList(
        //                new String[] {"method"}).toString());
        // will not have the right line num, so just get the child name

        final DetailAST ident = getMethodIdentAst();
        final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        IndentLevel suggestedLevel = new IndentLevel(getLineStart(ident));
        if (!TokenUtil.areOnSameLine(child.getMainAst().getFirstChild(), ident)) {
            suggestedLevel = new IndentLevel(suggestedLevel,
                    getBasicOffset(),
                    getIndentCheck().getLineWrappingIndentation());
        }

        // If the right parenthesis is at the start of a line;
        // include line wrapping in suggested indent level.
        if (getLineStart(rparen) == rparen.getColumnNo()) {
            suggestedLevel = IndentLevel.addAcceptable(suggestedLevel, new IndentLevel(
                    getParent().getSuggestedChildIndent(this),
                    getIndentCheck().getLineWrappingIndentation()
            ));
        }

        return suggestedLevel;
    }

    @Override
    public void checkIndentation() {
        DetailAST lparen = null;
    
        if (getMainAst().getType() == TokenTypes.METHOD_CALL) {
            final DetailAST exprNode = getMainAst().getParent();
    
            if (exprNode.getParent().getType() == TokenTypes.SLIST) {
                // Traverse method call children, skipping array access (INDEX_OP)
                for (DetailAST child = getMainAst().getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getType() == TokenTypes.INDEX_OP) {
                        continue;
                    }
    
                    checkExpressionSubtree(child, getIndent(), false, false);
                }
            }
    
            lparen = getMainAst(); // This should be outside the for-loop, after it's done
        } else {
            // TokenTypes.CTOR_CALL | TokenTypes.SUPER_CTOR_CALL
            lparen = getMainAst().getFirstChild();
        }
    
        if (lparen != null) {
            final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
            checkLeftParen(lparen);
    
            if (!TokenUtil.areOnSameLine(rparen, lparen)) {
                checkExpressionSubtree(
                    getMainAst().findFirstToken(TokenTypes.ELIST),
                    new IndentLevel(getIndent(), getBasicOffset()),
                    false, true);
    
                checkRightParen(lparen, rparen);
                checkWrappingIndentation(getMainAst(), getCallLastNode(getMainAst()));
            }
        }
    }
    
    
    @Override
    protected boolean shouldIncreaseIndent() {
        return false;
    }

    /**
     * Returns method or constructor call right paren.
     *
     * @param firstNode
     *          call ast(TokenTypes.METHOD_CALL|TokenTypes.CTOR_CALL|TokenTypes.SUPER_CTOR_CALL)
     * @return ast node containing right paren for specified method or constructor call. If
     *     method calls are chained returns right paren for last call.
     */
    private static DetailAST getCallLastNode(DetailAST firstNode) {
        return firstNode.getLastChild();
    }

}
