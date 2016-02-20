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
 * Handler for class definitions.
 *
 * @author jrichard
 */
public class ClassDefHandler extends BlockParentHandler {
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public ClassDefHandler(IndentationCheck indentCheck,
                           DetailAST ast,
                           AbstractExpressionHandler parent) {
        super(indentCheck, getHandlerName(ast), ast, parent);
    }

    @Override
    protected DetailAST getLCurly() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRCurly() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getTopLevelAst() {
        return null;
        // note: ident checked by hand in check indentation;
    }

    @Override
    protected DetailAST getListChild() {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK);
    }

    @Override
    public void checkIndentation() {
        final DetailAST modifiers = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.getChildCount() == 0) {
            final DetailAST ident = getMainAst().findFirstToken(TokenTypes.IDENT);
            final int lineStart = getLineStart(ident);
            if (!getIndent().isAcceptable(lineStart)) {
                logError(ident, "ident", lineStart);
            }

        }
        else {
            checkModifiers();
        }

        checkWrappingIndentation(getMainAst(), getMainAst().getLastChild());
        super.checkIndentation();
    }

    @Override
    protected int[] getCheckedChildren() {
        return new int[] {
            TokenTypes.EXPR,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_CONTINUE,
        };
    }

    /**
     * Creates a handler name for this class according to ast type.
     *
     * @param ast the abstract syntax tree.
     * @return handler name for this class.
     */
    private static String getHandlerName(DetailAST ast) {
        final String name;

        if (ast.getType() == TokenTypes.CLASS_DEF) {
            name = "class def";
        }
        else if (ast.getType() == TokenTypes.ENUM_DEF) {
            name = "enum def";
        }
        else {
            name = "interface def";
        }
        return name;
    }
}
