////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Detect the double-checked locking idiom, a technique that tries to avoid
 * synchronization overhead but is incorrect because of subtle artifacts of
 * the java memory model.
 *
 * See <a href=
 * "http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html"
 * >The &quot;Double-Checked Locking is Broken&quot; Declaration</a> for a
 * more in depth explanation.
 *
 * @author Lars K&uuml;hne
 */
public class DoubleCheckedLockingCheck extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.LITERAL_IF};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST synchronizedAST =
            getLowestParent(aAST, TokenTypes.LITERAL_SYNCHRONIZED);
        if (synchronizedAST == null) {
            return;
        }

        final DetailAST ifAST =
            getLowestParent(synchronizedAST, TokenTypes.LITERAL_IF);
        if (ifAST == null) {
            return;
        }

        if (getIfCondition(aAST).equalsTree(getIfCondition(ifAST))) {
            log(aAST.getLineNo(), aAST.getColumnNo(),
                "doublechecked.locking.avoid");
        }
    }

    /**
     * returns the condition of an if statement.
     * @param aIfAST the LITERAL_IF AST
     * @return the AST that represents the condition of the if statement
     */
    private AST getIfCondition(DetailAST aIfAST)
    {
        return aIfAST.getFirstChild().getNextSibling();
    }

    /**
     * searches towards the root of the AST for a specific AST type.
     * @param aAST the starting node for searching (inclusive)
     * @param aTokenType the token type to search for
     * @return the first token of type aTokenTye or null if no such token exists
     */
    private DetailAST getLowestParent(DetailAST aAST, int aTokenType)
    {
        DetailAST synchronizedParent = aAST;
        while ((synchronizedParent != null)
               && (synchronizedParent.getType() != aTokenType))
        {
            synchronizedParent = synchronizedParent.getParent();
        }
        return synchronizedParent;
    }
}
