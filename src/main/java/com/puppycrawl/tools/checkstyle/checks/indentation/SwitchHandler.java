///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import org.antlr.v4.runtime.Token;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for switch statements.
 *
 */
public class SwitchHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public SwitchHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "switch", ast, parent);
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getListChild() {
        // all children should be taken care of by case handler (plus
        // there is no parent of just the cases, if checking is needed
        // here in the future, an additional way beyond checkChildren()
        // will have to be devised to get children)
        return null;
    }

    @Override
    protected DetailAST getNonListChild() {
        return null;
    }

    @Override
    protected IndentLevel getIndentImpl() {
        IndentLevel indentLevel = super.getIndentImpl();
        DetailAST ast = getMainAst();
        loop: while (ast != null) {
            switch (ast.getType()) {
                case TokenTypes.METHOD_CALL:
                    indentLevel = getIndentOfMethod(ast);
                    break loop;
                case TokenTypes.VARIABLE_DEF:
                    if(getIndentLevelVariable(ast) != null) {
                        indentLevel = getIndentLevelVariable(ast);
                    }
                    break loop;
                case TokenTypes.LITERAL_FOR:
                    indentLevel = getIndentLevelForLoop(ast);
                    break loop;
                case TokenTypes.LITERAL_WHILE:
                    indentLevel = getIndentLevelWhileLoop(ast);
                    break loop;
                case TokenTypes.LITERAL_IF:
                    indentLevel = getIndentLevelForIf(ast);
                    break loop;
                case TokenTypes.LITERAL_YIELD:
                    indentLevel = getIndentForYield(ast);
                    break loop;
                case TokenTypes.LITERAL_DO:
                    indentLevel = getIndentLevelVariabledoWhileLoop(ast);
                    break loop;
            }
            ast = ast.getParent();
        }

        return indentLevel;
    }

    private IndentLevel getIndentOfMethod(DetailAST ast) {
        IndentLevel indentLevel = super.getIndentImpl();
        if (getMainAst().getPreviousSibling() != null) {
            indentLevel = new IndentLevel(getMainAst().getPreviousSibling().getColumnNo());
        }
        DetailAST currentAst = ast;
        while (currentAst != null) {
            if (currentAst.getType() == TokenTypes.LITERAL_FOR) {
                indentLevel = getIndentLevelForLoop(currentAst);
                break;
            }
            if (currentAst.getType() == TokenTypes.VARIABLE_DEF) {
                indentLevel = getIndentLevelVariable(currentAst);
                break;
            }
            currentAst = currentAst.getParent();
        }
        return indentLevel;
    }

    private IndentLevel getIndentLevelVariable(DetailAST ast) {
        DetailAST childAst = ast;
        IndentLevel indentLevel = null;
        if (ast.getParent().getParent().getType() == TokenTypes.LITERAL_FOR) {
            indentLevel = getIndentLevelForLoop(ast.getParent().getParent());
        }
        while (childAst != null) {
            if (childAst.getType() == TokenTypes.METHOD_CALL) {
                indentLevel = new IndentLevel(ast.getColumnNo());
                break;
            }
            childAst = childAst.getLastChild();
        }
        return indentLevel;
    }

    private IndentLevel getIndentLevelForIf(DetailAST ast) {
        return new IndentLevel(ast.getColumnNo());
    }

    private IndentLevel getIndentLevelForLoop(DetailAST ast) {
        DetailAST ast1 = ast;
        DetailAST switchAst = getMainAst();
        IndentLevel indentLevel = new IndentLevel(ast.getColumnNo());
        if (ast.getLineNo() != getMainAst().getLineNo()) {
            while (switchAst.getType() != TokenTypes.LITERAL_FOR) {
                if (switchAst.getType() == TokenTypes.FOR_CONDITION
                        || switchAst.getType() == TokenTypes.FOR_INIT
                        || switchAst.getType() == TokenTypes.FOR_ITERATOR) {
                    indentLevel = new IndentLevel(getMainAst().getColumnNo());
                    break;
                }
                switchAst = switchAst.getParent();
            }
        }
        while (ast1 != null) {
            if (ast1.getType() == TokenTypes.METHOD_CALL) {
                indentLevel = new IndentLevel(ast1.getFirstChild().getColumnNo());
                break;
            }
            ast1 = ast1.getLastChild();
            if (ast1 != null && ast1.getType() == TokenTypes.SLIST) {
                ast1 = ast1.getFirstChild();
            }
        }
        return indentLevel;
    }

    private IndentLevel getIndentLevelWhileLoop(DetailAST ast) {
        IndentLevel indentLevel = new IndentLevel(ast.getColumnNo());
        if (ast.getLineNo() != getMainAst().getLineNo()) {
            indentLevel = new IndentLevel(getMainAst().getColumnNo());
        }
        return indentLevel;
    }

    private IndentLevel getIndentLevelVariabledoWhileLoop(DetailAST ast) {
        DetailAST Rcurly = ast.findFirstToken(TokenTypes.SLIST).findFirstToken(TokenTypes.RCURLY);
        DetailAST doWhile = ast.findFirstToken(TokenTypes.DO_WHILE);
        IndentLevel indentLevel;
        if (Rcurly.getLineNo() == doWhile.getLineNo() && doWhile.getLineNo() == getMainAst().getLineNo())  {
            indentLevel = new IndentLevel(Rcurly.getColumnNo());
        }
        else if (Rcurly.getLineNo() != doWhile.getLineNo() && doWhile.getLineNo() == getMainAst().getLineNo()) {
            indentLevel = new IndentLevel(doWhile.getColumnNo());

        }
        else {
            indentLevel = new IndentLevel(getMainAst().getColumnNo());
        }
        return indentLevel;
    }

    private IndentLevel getIndentForYield(DetailAST ast) {
        final IndentLevel indentLevel;
        if (ast.getLineNo() == getMainAst().getLineNo()) {
            indentLevel = new IndentLevel(ast.getColumnNo());
        }
        else {
            indentLevel = new IndentLevel(getMainAst().getColumnNo());
        }
        return indentLevel;
    }

    /**
     * Check the indentation of the switch expression.
     */
    private void checkSwitchExpr() {
        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.LPAREN).getNextSibling(),
            getIndent(),
            false,
            false);
    }

    @Override
    public void checkIndentation() {
        checkSwitchExpr();
        super.checkIndentation();
    }

}
