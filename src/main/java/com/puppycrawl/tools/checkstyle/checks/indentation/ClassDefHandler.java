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
 * Handler for class definitions.
 *
 * @author jrichard
 */
public class ClassDefHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ClassDefHandler(IndentationCheck aIndentCheck,
                           DetailAST aAst,
                           ExpressionHandler aParent)
    {
        super(aIndentCheck,
              (aAst.getType() == TokenTypes.CLASS_DEF)
              ? "class def" : ((aAst.getType() == TokenTypes.ENUM_DEF)
                  ? "enum def" : "interface def"),
              aAst, aParent);
    }

    @Override
    protected DetailAST getLCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK)
            .findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getToplevelAST()
    {
        return null;
        // note: ident checked by hand in check indentation;
    }

    @Override
    protected DetailAST getListChild()
    {
        return getMainAst().findFirstToken(TokenTypes.OBJBLOCK);
    }

    @Override
    public void checkIndentation()
    {
        final DetailAST modifiers = getMainAst().findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.getChildCount() == 0) {
            final DetailAST ident = getMainAst().findFirstToken(TokenTypes.IDENT);
            final int lineStart = getLineStart(ident);
            if (!getLevel().accept(lineStart)) {
                logError(ident, "ident", lineStart);
            }

        }
        else {
            checkModifiers();
        }

        final LineWrappingHandler lineWrap =
            new LineWrappingHandler(getIndentCheck(), getMainAst()) {
                @Override
                public DetailAST findLastNode(DetailAST aFirstNode)
                {
                    return aFirstNode.getLastChild();
                }
            };
        lineWrap.checkIndentation();
        super.checkIndentation();
    }

    @Override
    protected int[] getCheckedChildren()
    {
        return new int[] {
            TokenTypes.EXPR,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_CONTINUE,
        };
    }

}
