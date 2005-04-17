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

package com.puppycrawl.tools.checkstyle.checks.coding;

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
 * <p>
 * Checks that classes that override equals() also override hashCode().
 * </p>
 * <p>
 * Rationale: The contract of equals() and hashCode() requires that
 * equal objects have the same hashCode. Hence, whenever you override
 * equals() you must override hashCode() to ensure that your class can
 * be used in collections that are hash based.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EqualsHashCode"/&gt;
 * </pre>
 * @author lkuehne
 */
public class EqualsHashCodeCheck
        extends Check
{
    // implementation note: we have to use the following members to
    // keep track of definitions in different inner classes

    /** maps OBJ_BLOCK to the method definition of equals() */
    private final Map mObjBlockEquals = new HashMap();

    /** the set of OBJ_BLOCKs that contain a definition of hashCode() */
    private final Set mObjBlockWithHashCode = new HashSet();


    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    /** @see Check */
    public void beginTree(DetailAST aRootAST)
    {
        mObjBlockEquals.clear();
        mObjBlockWithHashCode.clear();
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST modifiers = (DetailAST) aAST.getFirstChild();
        final AST type = aAST.findFirstToken(TokenTypes.TYPE);
        final AST methodName = aAST.findFirstToken(TokenTypes.IDENT);
        final DetailAST parameters = aAST.findFirstToken(TokenTypes.PARAMETERS);

        if (type.getFirstChild().getType() == TokenTypes.LITERAL_BOOLEAN
                && "equals".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getChildCount() == 1
                && isObjectParam(parameters.getFirstChild())
            )
        {
            mObjBlockEquals.put(aAST.getParent(), aAST);
        }
        else if (type.getFirstChild().getType() == TokenTypes.LITERAL_INT
                && "hashCode".equals(methodName.getText())
                && modifiers.branchContains(TokenTypes.LITERAL_PUBLIC)
                && parameters.getFirstChild() == null) // no params
        {
            mObjBlockWithHashCode.add(aAST.getParent());
        }
    }

    /**
     * Determines if an AST is a formal param of type Object (or subclass).
     * @param aFirstChild the AST to check
     * @return true iff aFirstChild is a parameter of an Object type.
     */
    private boolean isObjectParam(AST aFirstChild)
    {
        final AST modifiers = aFirstChild.getFirstChild();
        final AST type = modifiers.getNextSibling();
        switch (type.getFirstChild().getType()) {
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

    /**
     * @see Check
     */
    public void finishTree(DetailAST aRootAST)
    {
        final Set equalsDefs = mObjBlockEquals.keySet();
        for (Iterator it = equalsDefs.iterator(); it.hasNext();) {
            final Object objBlock = it.next();
            if (!mObjBlockWithHashCode.contains(objBlock)) {
                final DetailAST equalsAST =
                    (DetailAST) mObjBlockEquals.get(objBlock);
                log(equalsAST.getLineNo(), equalsAST.getColumnNo(),
                        "equals.noHashCode");
            }
        }

        mObjBlockEquals.clear();
        mObjBlockWithHashCode.clear();
    }
}
