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
 * This class checks line wrapping for method call statements.
 * @author pirat9600q
 *
 */
public class MethodCallLineWrapHandler extends LineWrappingHandler
{
    /**
     * Creates new instance of MethodCallLineWrapHandler.
     * @param instance instance of IndentationCheck.
     * @param firstNode first node of method call statement.
     * @param lastNode last node of method call statement.
     */
    public MethodCallLineWrapHandler(IndentationCheck instance,
        DetailAST firstNode, DetailAST lastNode)
    {
        super(instance, firstNode, lastNode);
    }

    @Override
    protected int getCurrentIndentation()
    {
        DetailAST curNode = getFirstNode();
        while (curNode.getType() != TokenTypes.IDENT) {
            curNode = curNode.getFirstChild();
        }
        return curNode.getColumnNo() + getIndentLevel();
    }
}
