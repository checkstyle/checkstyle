////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that classes that override equals() also override hashCode().
 *
 * <p>
 * Rationale: The contract of equals() and hashCode() requires that
 * equal objects have the same hashCode. Hence, whenever you override
 * equals() you must override hashCode() to ensure that your class can
 * be used in collections that are hash based.
 * </p>
 * @author lkuehne
 */
public class EqualsHashCodeCheck
        extends Check
{
    // implementation note: we have to use the following members to
    // keep track of definitions in different inner classes

    /** maps OBJ_BLOCK to the method definition of equals() */
    private final Map objBlockEquals = new HashMap();

    /** maps OBJ_BLOCK to the method definition of equals() */
    private final Set objBlockWithHashCode = new HashSet();


    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    public void beginTree()
    {
        objBlockEquals.clear();
        objBlockWithHashCode.clear();
    }

    public void visitToken(DetailAST aAST)
    {
        // paranoia
        if (aAST.getType() != TokenTypes.METHOD_DEF) {
            return;
        }

        DetailAST modifiers = (DetailAST) aAST.getFirstChild();

        AST type = modifiers.getNextSibling();
        AST methodName = type.getNextSibling();
        DetailAST parameters = (DetailAST) methodName.getNextSibling();

        if (type.getFirstChild().getType() == TokenTypes.LITERAL_BOOLEAN
                && "equals".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getChildCount() == 1
                && isObjectParam(parameters.getFirstChild())
                )
        {
            objBlockEquals.put(aAST.getParent(), aAST);
        }
        else if (type.getFirstChild().getType() == TokenTypes.LITERAL_INT
                && "hashCode".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getFirstChild() == null) // no params
        {
            objBlockWithHashCode.add(aAST.getParent());
        }
    }

    private boolean isObjectParam(AST aFirstChild)
    {
        if (aFirstChild.getType() != TokenTypes.PARAMETER_DEF) {
            return false;
        }

        final AST modifiers = aFirstChild.getFirstChild();
        AST type = modifiers.getNextSibling();
        switch (type.getFirstChild().getType())
        {
            case TokenTypes.LITERAL_BOOLEAN:
            case TokenTypes.LITERAL_BYTE:
            case TokenTypes.LITERAL_CHAR:
            case TokenTypes.LITERAL_DOUBLE:
            case TokenTypes.LITERAL_FLOAT:
            case TokenTypes.LITERAL_INT:
            case TokenTypes.LITERAL_LONG:
            case TokenTypes.LITERAL_SHORT:
                return false;
            default:
                return true;
        }
    }

    public void finishTree()
    {
        Set equalsDefs = objBlockEquals.keySet();
        for (Iterator it = equalsDefs.iterator(); it.hasNext();) {
            Object objBlock = it.next();
            if (!objBlockWithHashCode.contains(objBlock)) {
                DetailAST equalsAST = (DetailAST) objBlockEquals.get(objBlock);
                log(equalsAST.getLineNo(), equalsAST.getColumnNo(),
                        "equals.noHashCode");
            }
        }

        objBlockEquals.clear();
        objBlockWithHashCode.clear();
    }
}
