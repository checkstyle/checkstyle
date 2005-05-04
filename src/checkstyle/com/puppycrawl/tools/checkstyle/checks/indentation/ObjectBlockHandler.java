////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
 * Handler for inner classes.
 *
 * @author jrichard
 */
public class ObjectBlockHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ObjectBlockHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "object def", aAst, aParent);
    }

    /**
     * There is no top level expression for this handler.
     *
     * @return null
     */
    protected DetailAST getToplevelAST()
    {
        return null;
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.LCURLY);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Get the child element representing the list of statements.
     *
     * @return the statement list child
     */
    protected DetailAST getListChild()
    {
        return getMainAst();
    }

    /**
     * Compute the indentation amount for this handler.
     *
     * @return the expected indentation amount
     */
    public IndentLevel getLevelImpl()
    {
        DetailAST parentAST = getMainAst().getParent();
        IndentLevel indent = getParent().getLevel();
        if (parentAST.getType() == TokenTypes.LITERAL_NEW) {
            indent.addAcceptedIndent(super.getLevelImpl());
        }
        else if (parentAST.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
            indent = super.getLevelImpl();
        }
        return indent;
    }

    /**
     * Check the indentation of the expression we are handling.
     */
    public void checkIndentation()
    {
        // if we have a class or interface as a parent, don't do anything,
        // as this is checked by class def; so
        // only do this if we have a new for a parent (anonymous inner
        // class)
        DetailAST parentAST = getMainAst().getParent();
        if (parentAST.getType() != TokenTypes.LITERAL_NEW) {
            return;
        }

        super.checkIndentation();
    }
}
