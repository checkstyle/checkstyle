////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
 * Handler for member definitions.
 *
 */
public class MemberDefHandler extends AbstractExpressionHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public MemberDefHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "member def", ast, parent);
    }

    @Override
    public void checkIndentation() {
        final String subtype;
        if (getMainAst().findFirstToken(TokenTypes.MODIFIERS).hasChildren()) {
            subtype = "modifier";
        }
        else {
            subtype = "type";
        }
        final DetailAST firstNode = getFirstNode();
        final DetailAST lastNode = getVarDefStatementSemicolon(getMainAst());

        final int firstNodeColumnNo = expandedTabsColumnNo(firstNode);
        final boolean indentationAcceptable = getIndent().isAcceptable(firstNodeColumnNo);
        final int expectedFirstNodeIndent;
        if (indentationAcceptable) {
            expectedFirstNodeIndent = firstNodeColumnNo;
        }
        else {
            expectedFirstNodeIndent = getIndent().getFirstIndentLevel();
        }
        if (!indentationAcceptable && getLineStart(firstNode) == firstNodeColumnNo) {
            logError(firstNode, subtype, firstNodeColumnNo);
        }
        if (lastNode != null && !isArrayDeclaration(getMainAst())) {
            checkWrappingIndentation(getMainAst(), lastNode,
                    getIndentCheck().getLineWrappingIndentation(),
                    expectedFirstNodeIndent,
                    true);
        }
    }

    /**
     * Searches in given sub-tree (including given node) for the token
     * which represents first symbol for this sub-tree in file.
     *
     * @return a token which occurs first in the file.
     */
    private DetailAST getFirstNode() {
        DetailAST type = getMainAst().findFirstToken(TokenTypes.TYPE);
        final DetailAST result;
        if (type.getFirstChild().getType() == TokenTypes.DOT) {
            type = type.getFirstChild();
            while (type.getFirstChild().getType() == TokenTypes.DOT) {
                type = type.getFirstChild();
            }
            result = type.getFirstChild();
        }
        else {
            result = getMainAst();
        }
        return result;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getIndent();
    }

    /**
     * Checks if variable_def node is array declaration.
     *
     * @param variableDef current variable_def.
     * @return true if variable_def node is array declaration.
     */
    private static boolean isArrayDeclaration(DetailAST variableDef) {
        return variableDef.findFirstToken(TokenTypes.TYPE)
            .findFirstToken(TokenTypes.ARRAY_DECLARATOR) != null;
    }

    /**
     * Returns semicolon for variable definition statement.
     *
     * @param variableDef
     *          ast node of type TokenTypes.VARIABLE_DEF
     * @return ast node of type TokenTypes.SEMI
     */
    private static DetailAST getVarDefStatementSemicolon(DetailAST variableDef) {
        DetailAST lastNode = variableDef.getLastChild();
        if (lastNode.getType() != TokenTypes.SEMI) {
            lastNode = variableDef.getNextSibling();
        }
        return lastNode;
    }

}
