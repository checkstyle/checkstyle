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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;


/**
 * Checks for overly complicated boolean return statements.
 *
 * Idea shamelessly stolen from the equivalent PMD rule (pmd.sourceforge.net).
 *
 * @author Lars K?hne
 */
public class SimplifyBooleanReturnCheck
    extends Check
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_IF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // paranoia - what an untrusting sole :-)
        if (aAST.getType() != TokenTypes.LITERAL_IF) {
            throw new IllegalArgumentException("not an if");
        }

        // LITERAL_IF has the following four or five children:
        // '('
        // condition
        // ')'
        // thenstatement
        // [ LITERAL_ELSE (with the elseStatement as a child) ]

        // don't bother if this is not if then else
        if (aAST.getChildCount() != 5) {
            return;
        }

        // skip '(' and ')'
        // TODO: Introduce helpers in DetailAST
        AST condition = aAST.getFirstChild().getNextSibling();
        AST thenStatement = condition.getNextSibling().getNextSibling();
        AST elseStatement = thenStatement.getNextSibling().getFirstChild();

        if (returnsOnlyBooleanLiteral(thenStatement)
            && returnsOnlyBooleanLiteral(elseStatement))
        {
            // TODO: i18n
            log(aAST.getLineNo(),
                aAST.getColumnNo(),
                "Remove conditional logic.");
        }
    }

    /**
     * Returns if an AST is a return statment with a boolean literal
     * or a compound statement that contains only such a return statement.
     *
     * Returns <code>true</code> iff aAST represents
     * <br>
     * <pre>
     * return true/false;
     * <pre>
     * or
     * <br>
     * <pre>
     * {
     *   return true/false;
     * }
     * <pre>
     *
     * @param aAST the sytax tree to check
     * @return if aAST is a return statment with a boolean literal.
     */
    private boolean returnsOnlyBooleanLiteral(AST aAST)
    {
        if (isBooleanLiteralReturnStatement(aAST)) {
            return true;
        }

        AST firstStmnt = aAST.getFirstChild();
        return isBooleanLiteralReturnStatement(firstStmnt);
    }

    /**
     * Returns if an AST is a return statment with a boolean literal.
     *
     * Returns <code>true</code> iff aAST represents
     * <br>
     * <pre>
     * return true/false;
     * <pre>
     *
     * @param aAST the sytax tree to check
     * @return if aAST is a return statment with a boolean literal.
     */
    private boolean isBooleanLiteralReturnStatement(AST aAST)
    {
        if (aAST == null || aAST.getType() != TokenTypes.LITERAL_RETURN) {
            return false;
        }

        final AST expr = aAST.getFirstChild();

        if (expr == null) {
            return false;
        }

        final AST value = expr.getFirstChild();

        final int valueType = value.getType();
        return ((valueType == TokenTypes.LITERAL_TRUE)
                || (valueType == TokenTypes.LITERAL_FALSE));
    }
}
