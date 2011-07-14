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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;


/**
 * <p>
 * Checks for overly complicated boolean return statements.
 * Idea shamelessly stolen from the equivalent PMD rule (pmd.sourceforge.net).
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="SimplifyBooleanReturn"/&gt;
 * </pre>
 * @author Lars Kühne
 */
public class SimplifyBooleanReturnCheck
    extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_IF};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        // LITERAL_IF has the following four or five children:
        // '('
        // condition
        // ')'
        // thenStatement
        // [ LITERAL_ELSE (with the elseStatement as a child) ]

        // don't bother if this is not if then else
        final AST elseLiteral =
            aAST.findFirstToken(TokenTypes.LITERAL_ELSE);
        if (elseLiteral == null) {
            return;
        }
        final AST elseStatement = elseLiteral.getFirstChild();

        // skip '(' and ')'
        // TODO: Introduce helpers in DetailAST
        final AST condition = aAST.getFirstChild().getNextSibling();
        final AST thenStatement = condition.getNextSibling().getNextSibling();

        if (returnsOnlyBooleanLiteral(thenStatement)
            && returnsOnlyBooleanLiteral(elseStatement))
        {
            log(aAST.getLineNo(), aAST.getColumnNo(), "simplify.boolreturn");
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
    private static boolean returnsOnlyBooleanLiteral(AST aAST)
    {
        if (isBooleanLiteralReturnStatement(aAST)) {
            return true;
        }

        final AST firstStmnt = aAST.getFirstChild();
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
    private static boolean isBooleanLiteralReturnStatement(AST aAST)
    {
        if ((aAST == null) || (aAST.getType() != TokenTypes.LITERAL_RETURN)) {
            return false;
        }

        final AST expr = aAST.getFirstChild();

        if ((expr == null) || (expr.getType() == TokenTypes.SEMI)) {
            return false;
        }

        final AST value = expr.getFirstChild();
        return isBooleanLiteralType(value.getType());
    }

    /**
     * Checks if a token type is a literal true or false.
     * @param aTokenType the TokenType
     * @return true iff aTokenType is LITERAL_TRUE or LITERAL_FALSE
     */
    private static boolean isBooleanLiteralType(final int aTokenType)
    {
        final boolean isTrue = (aTokenType == TokenTypes.LITERAL_TRUE);
        final boolean isFalse = (aTokenType == TokenTypes.LITERAL_FALSE);
        return isTrue || isFalse;
    }
}
