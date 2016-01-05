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
 * Handler for operator new.
 * @author o_sukhodolsky
 * @author Ilja Dubinin
 */
public class NewHandler extends AbstractExpressionHandler {
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public NewHandler(IndentationCheck indentCheck,
                      DetailAST ast,
                      AbstractExpressionHandler parent) {
        super(indentCheck, "operator new", ast, parent);
    }

    @Override
    public void checkIndentation() {
        final DetailAST type = getMainAst().getFirstChild();
        if (type != null) {
            checkExpressionSubtree(type, getIndent(), false, false);
        }

        final DetailAST lparen = getMainAst().findFirstToken(TokenTypes.LPAREN);
        checkLParen(lparen);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        // if our expression isn't first on the line, just use the start
        // of the line
        if (getLineStart(getMainAst()) != getMainAst().getColumnNo()) {
            return new IndentLevel(getLineStart(getMainAst()));
        }
        return super.getIndentImpl();
    }

    @Override
    protected boolean shouldIncreaseIndent() {
        return false;
    }
}
