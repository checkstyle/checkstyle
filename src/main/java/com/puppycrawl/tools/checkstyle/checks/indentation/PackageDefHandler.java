////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
 * Handler for package definitions.
 *
 */
public class PackageDefHandler extends AbstractExpressionHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public PackageDefHandler(IndentationCheck indentCheck,
            DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "package def", ast, parent);
    }

    @Override
    public void checkIndentation() {
        final int columnNo = expandedTabsColumnNo(getMainAst());

        if (!getIndent().isAcceptable(columnNo) && isOnStartOfLine(getMainAst())) {
            logError(getMainAst(), "", columnNo);
        }

        final DetailAST semi = getMainAst().findFirstToken(TokenTypes.SEMI);

        checkWrappingIndentation(getMainAst(), semi);
    }

}
