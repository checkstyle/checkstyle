////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
 * @author o_sukhodolsky
 */
public class MemberDefHandler extends ExpressionHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAST           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public MemberDefHandler(IndentationCheck aIndentCheck,
        DetailAST aAST, ExpressionHandler aParent)
    {
        super(aIndentCheck, "member def", aAST, aParent);
    }

    /**
     * Check the indentation of the method name.
     */
    private void checkIdent()
    {
        final DetailAST ident = getMainAst().findFirstToken(TokenTypes.IDENT);
        final int columnNo = expandedTabsColumnNo(ident);
        if (startsLine(ident) && !getLevel().accept(columnNo)) {
            logError(ident, "", columnNo);
        }
    }

    /**
     * Check the indentation of the method type.
     */
    private void checkType()
    {
        final DetailAST type = getMainAst().findFirstToken(TokenTypes.TYPE);
        final DetailAST ident = ExpressionHandler.getFirstToken(type);
        final int columnNo = expandedTabsColumnNo(ident);
        if (startsLine(ident) && !getLevel().accept(columnNo)) {
            logError(ident, "type", columnNo);
        }
    }

    @Override
    public void checkIndentation()
    {
        checkModifiers();
        checkType();
        checkIdent();
    }

    @Override
    public IndentLevel suggestedChildLevel(ExpressionHandler aChild)
    {
        return getLevel();
    }
}
