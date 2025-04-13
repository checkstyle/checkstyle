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

import java.util.Optional;

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
        final DetailAST modifiersNode = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersNode.hasChildren()) {
            checkModifiers();
        }
        else {
            checkType();
        }
        final DetailAST firstNode = getMainAst();
        final DetailAST lastNode = getArrayInitNode(firstNode)
            .orElseGet(() -> getVarDefStatementSemicolon(firstNode));

        if (lastNode != null) {
            checkWrappingIndentation(firstNode, lastNode);
        }
    }

    /**
     * Finds the array init node.
     *
     * @param firstNode Node to begin searching
     * @return array init node
     */
    private static Optional<DetailAST> getArrayInitNode(DetailAST firstNode) {
        return Optional.ofNullable(firstNode.findFirstToken(TokenTypes.ASSIGN))
            .map(assign -> {
                return Optional.ofNullable(assign.findFirstToken(TokenTypes.EXPR))
                    .map(expr -> expr.findFirstToken(TokenTypes.LITERAL_NEW))
                    .orElse(assign);
            })
            .map(node -> node.findFirstToken(TokenTypes.ARRAY_INIT));
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getIndent();
    }

    @Override
    protected void checkModifiers() {
        final DetailAST modifier = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (isOnStartOfLine(modifier)
            && !getIndent().isAcceptable(expandedTabsColumnNo(modifier))) {
            logError(modifier, "modifier", expandedTabsColumnNo(modifier));
        }
    }

    /**
     * Check the indentation of the method type.
     */
    private void checkType() {
        final DetailAST type = getMainAst().findFirstToken(TokenTypes.TYPE);
        final DetailAST ident = AbstractExpressionHandler.getFirstToken(type);
        final int columnNo = expandedTabsColumnNo(ident);
        if (isOnStartOfLine(ident) && !getIndent().isAcceptable(columnNo)) {
            logError(ident, "type", columnNo);
        }
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
