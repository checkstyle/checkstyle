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

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;


/**
 * Checks for overly complicated boolean return statements.
 *
 * Idea shamelessly stolen from the equivalent PMD rule (pmd.sourceforge.net).
 */
public class SimplifyBooleanReturnCheck extends Check
{
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.LITERAL_if};
    }

    public void visitToken(DetailAST aAST)
    {
        // paranoia - what an untrusting sole :-)
        if (aAST.getType() != JavaTokenTypes.LITERAL_if) {
            throw new IllegalArgumentException("not an if");
        }

        // don't bother if this is not if then else
        if (aAST.getChildCount() != 3) {
            return;
        }

        AST condition = aAST.getFirstChild();
        AST thenStatement = condition.getNextSibling();
        AST elseStatement = thenStatement.getNextSibling();

        if (returnsOnlyBooleanLiteral(thenStatement)
                && returnsOnlyBooleanLiteral(elseStatement))
        {
            log(aAST.getLineNo(), "Remove conditional logic.");
        }
    }

    private boolean returnsOnlyBooleanLiteral(AST aAST)
    {
        if (isBooleanLiteralReturnStatement(aAST)) {
            return true;
        }

        AST firstStmnt = aAST.getFirstChild();
        return isBooleanLiteralReturnStatement(firstStmnt);
    }

    private boolean isBooleanLiteralReturnStatement(AST aAST)
    {
        if (aAST.getType() != JavaTokenTypes.LITERAL_return)
            return false;

        AST expr = aAST.getFirstChild();
        AST value = expr.getFirstChild();

        int valueType = value.getType();
        return ( valueType == JavaTokenTypes.LITERAL_true
                || valueType == JavaTokenTypes.LITERAL_false );
    }
}
