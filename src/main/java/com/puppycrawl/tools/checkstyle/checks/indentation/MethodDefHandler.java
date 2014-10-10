////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
 * Handler for method definitions.
 *
 * @author jrichard
 * @author Maikel Steneker
 */
public class MethodDefHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public MethodDefHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, (aAst.getType() == TokenTypes.CTOR_DEF)
            ? "ctor def" : "method def", aAst, aParent);
    }

    @Override
    protected DetailAST getToplevelAST()
    {
        // we check this stuff ourselves below
        return null;
    }

    @Override
    protected void checkModifiers()
    {
        final DetailAST modifier = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (startsLine(modifier)
            && !getLevel().accept(expandedTabsColumnNo(modifier)))
        {
            logError(modifier, "modifier", expandedTabsColumnNo(modifier));
        }
    }

    @Override
    public void checkIndentation()
    {
        checkModifiers();

        final LineWrappingHandler lineWrap =
            new LineWrappingHandler(getIndentCheck(), getMainAst()) {
                @Override
                public DetailAST findLastNode(DetailAST aFirstNode)
                {
                    return aFirstNode.getLastChild().getPreviousSibling();
                }
            };
        lineWrap.checkIndentation();
        if (getLCurly() == null) {
            // asbtract method def -- no body
            return;
        }
        super.checkIndentation();
    }
}
