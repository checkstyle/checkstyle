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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Checks for assignments in subexpressions, such as in
 * <code>String s = Integer.toString(i = 2);</code>.
 * </p>
 * <p>
 * Rationale: With the exception of <code>for</code> iterators, all assignments
 * should occur in their own toplevel statement to increase readability.
 * With inner assignments like the above it is difficult to see all places
 * where a variable is set.
 * </p>
 * <p>
 * By default the check will check the following assignment operators:
 *  {@link TokenTypes#ASSIGN ASSIGN},
 *  {@link TokenTypes#BAND_ASSIGN BAND_ASSIGN},
 *  {@link TokenTypes#BOR_ASSIGN BOR_ASSIGN},
 *  {@link TokenTypes#BSR_ASSIGN BSR_ASSIGN},
 *  {@link TokenTypes#BXOR_ASSIGN BXOR_ASSIGN},
 *  {@link TokenTypes#DIV_ASSIGN DIV_ASSIGN},
 *  {@link TokenTypes#MINUS_ASSIGN MINUS_ASSIGN},
 *  {@link TokenTypes#MOD_ASSIGN MOD_ASSIGN},
 *  {@link TokenTypes#PLUS_ASSIGN PLUS_ASSIGN},
 *  {@link TokenTypes#SL_ASSIGN SL_ASSIGN},
 *  {@link TokenTypes#SR_ASSIGN SR_ASSIGN},
 *  {@link TokenTypes#STAR_ASSIGN STAR_ASSIGN}.
 * </p>
 * <p> An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="InnerAssignment"/&gt;
 * </pre>
 *
 * <p> An example of how to configure the check for only <code>=</code>,
 * <code>+=</code>, and <code>-=</code> operators is:
 * </p>
 * <pre>
 * &lt;module name="InnerAssignment"&gt;
 *    &lt;tokens&gt;ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN&lt;/tokens&gt;
 * &lt;/module&gt;
 * </pre>

 * @author lkuehne
 */
public class InnerAssignmentCheck
        extends Check
{
    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      //"-="
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.BAND_ASSIGN,       // "&="
        };
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST parent1 = aAST.getParent();
        final DetailAST parent2 = parent1.getParent();
        final DetailAST parent3 = parent2.getParent();

        final boolean assigment = isAssignment(parent1);
        final boolean expr = parent1.getType() == TokenTypes.EXPR;
        final boolean exprList =
                expr && parent2.getType() == TokenTypes.ELIST;
        final boolean methodCall =
                exprList && parent3.getType() == TokenTypes.METHOD_CALL;
        final boolean ctorCall =
                exprList && parent3.getType() == TokenTypes.LITERAL_NEW;

        if (assigment || methodCall || ctorCall) {
            log(aAST.getLineNo(), aAST.getColumnNo(), "assignment.inner.avoid");
        }
    }

    /**
     * Checks if an AST is an assignment operator.
     * @param aAST the AST to check
     * @return true iff aAST is an assignment operator.
     */
    private boolean isAssignment(DetailAST aAST)
    {
        // TODO: make actual tokens available to Check and loop over actual
        // tokens here?
        final int[] tokens = getDefaultTokens();

        final int astType = aAST.getType();

        for (int i = 0; i < tokens.length; i++) {
            final int tokenType = tokens[i];
            if (astType == tokenType) {
                return true;
            }
        }
        return false;
    }


}
