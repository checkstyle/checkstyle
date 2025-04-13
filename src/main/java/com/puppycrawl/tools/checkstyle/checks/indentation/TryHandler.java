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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for try blocks.
 *
 */
public class TryHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public TryHandler(IndentationCheck indentCheck,
                      DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "try", ast, parent);
    }

    /**
     * Method to find left parenthesis of try with resources.
     *
     * @return DetailAst    left parenthesis of try with resources
     */
    private DetailAST getTryResLparen() {
        return getMainAst().getFirstChild().getFirstChild();
    }

    /**
     * Method to find right parenthesis of try with resources.
     *
     * @return DetailAst    right parenthesis of try with resources
     */
    private DetailAST getTryResRparen() {
        return getMainAst().getFirstChild().getLastChild();
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        final IndentLevel result;
        if (child instanceof CatchHandler
            || child instanceof FinallyHandler
            || child instanceof NewHandler
            && isTryBlocksResourceSpecification(child)) {
            result = getIndent();
        }
        else {
            result = super.getSuggestedChildIndent(child);
        }
        return result;
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();
        if (getMainAst().getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
            checkTryResParen(getTryResLparen(), "lparen");
            checkTryResParen(getTryResRparen(), "rparen");
            checkTryResources(getMainAst().getFirstChild());
        }
    }

    /**
     * Method to check the indentation of left paren or right paren.
     * This method itself checks whether either of these are on start of line. This method
     * takes care of line wrapping strict condition as well.
     *
     * @param parenAst      lparen or rparen ast to check
     * @param subType       name to be used in log message
     */
    private void checkTryResParen(final DetailAST parenAst,
                                  final String subType) {
        if (isOnStartOfLine(parenAst)) {
            final IndentLevel expectedIdent = new IndentLevel(getIndent(), 0,
                getIndentCheck().getLineWrappingIndentation());

            checkChildIndentation(parenAst, subType, expectedIdent);
        }
    }

    /**
     * Method to check indentation of try resources children.
     * It takes into account forceStrictCondition value when logging violations.
     * Example of usage would include checking for try parenthesis and try resources.
     *
     * @param ast           AST to check.
     * @param subType       String representing child type.
     * @param expectedIdent Expected indent level.
     */
    private void checkChildIndentation(DetailAST ast, String subType, IndentLevel expectedIdent) {
        if (getIndentCheck().isForceStrictCondition()) {
            if (!expectedIdent.isAcceptable(expandedTabsColumnNo(ast))) {
                logError(ast, subType, expandedTabsColumnNo(ast), expectedIdent);
            }
        }
        else {
            if (expandedTabsColumnNo(ast) < expectedIdent.getFirstIndentLevel()) {
                logError(ast, subType, expandedTabsColumnNo(ast), expectedIdent);
            }
        }
    }

    /**
     * Checks indentation of resources parameters in try resources.
     *
     * @param resourcesSpecAst   Resource specification ast
     */
    private void checkTryResources(final DetailAST resourcesSpecAst) {
        final DetailAST resourcesAst = resourcesSpecAst.findFirstToken(TokenTypes.RESOURCES);
        final int indentation = getIndent().getFirstIndentLevel()
            + getIndentCheck().getLineWrappingIndentation();
        final IndentLevel expectedResourceIndent = new IndentLevel(indentation);

        final String subType = "resource";

        DetailAST resourceAst = resourcesAst.getFirstChild();
        while (resourceAst != null) {
            if (resourceAst.getType() == TokenTypes.RESOURCE) {
                final DetailAST nextSibling;
                if (resourceAst.getNextSibling() == null) {
                    nextSibling = getTryResRparen();
                }
                else {
                    nextSibling = resourceAst.getNextSibling();
                }
                if (isOnStartOfLine(resourceAst)) {
                    checkChildIndentation(resourceAst, subType, expectedResourceIndent);
                    checkWrappingIndentation(
                        resourceAst,
                        nextSibling,
                        getIndentCheck().getLineWrappingIndentation(),
                        expectedResourceIndent.getFirstIndentLevel(),
                        true);
                }
                else {
                    checkWrappingIndentation(resourceAst, nextSibling);
                }
            }
            resourceAst = resourceAst.getNextSibling();
        }
    }

    /**
     * Check if the expression is resource of
     * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20.3">
     * try block</a>.
     *
     * @param expression The expression to check
     * @return if the expression provided is try block's resource specification.
     */
    private static boolean isTryBlocksResourceSpecification(AbstractExpressionHandler expression) {
        boolean isResourceSpecificationExpression = false;

        DetailAST ast = expression.getMainAst();

        while (ast.getType() != TokenTypes.LITERAL_TRY) {
            if (ast.getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                isResourceSpecificationExpression = true;
                break;
            }

            ast = ast.getParent();
        }

        return isResourceSpecificationExpression;
    }

}
