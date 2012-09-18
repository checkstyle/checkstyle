////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check that method/constructor/catch/foreach parameters are final.
 * The user can set the token set to METHOD_DEF, CONSTRUCTOR_DEF,
 * LITERAL_CATCH, FOR_EACH_CLAUSE or any combination of these token
 * types, to control the scope of this check.
 * Default scope is both METHOD_DEF and CONSTRUCTOR_DEF.
 *
 * @author lkuehne
 * @author o_sukhodolsky
 * @author Michael Studman
 */
public class FinalParametersCheck extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.FOR_EACH_CLAUSE,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        // don't flag interfaces
        final DetailAST container = aAST.getParent().getParent();
        if (container.getType() == TokenTypes.INTERFACE_DEF) {
            return;
        }

        if (aAST.getType() == TokenTypes.LITERAL_CATCH) {
            visitCatch(aAST);
        }
        else if (aAST.getType() == TokenTypes.FOR_EACH_CLAUSE) {
            visitForEachClause(aAST);
        }
        else {
            visitMethod(aAST);
        }
    }

    /**
     * Checks parameters of the method or ctor.
     * @param aMethod method or ctor to check.
     */
    private void visitMethod(final DetailAST aMethod)
    {
        // exit on fast lane if there is nothing to check here
        if (!aMethod.branchContains(TokenTypes.PARAMETER_DEF)) {
            return;
        }

        // ignore abstract method
        final DetailAST modifiers =
            aMethod.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiers.branchContains(TokenTypes.ABSTRACT)) {
            return;
        }

        // we can now be sure that there is at least one parameter
        final DetailAST parameters =
            aMethod.findFirstToken(TokenTypes.PARAMETERS);
        DetailAST child = parameters.getFirstChild();
        while (child != null) {
            // childs are PARAMETER_DEF and COMMA
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                checkParam(child);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Checks parameter of the catch block.
     * @param aCatch catch block to check.
     */
    private void visitCatch(final DetailAST aCatch)
    {
        checkParam(aCatch.findFirstToken(TokenTypes.PARAMETER_DEF));
    }

    /**
     * Checks parameter of the for each clause.
     * @param aForEachClause for each clause to check.
     */
    private void visitForEachClause(final DetailAST aForEachClause)
    {
        checkParam(aForEachClause.findFirstToken(TokenTypes.VARIABLE_DEF));
    }

    /**
     * Checks if the given parameter is final.
     * @param aParam parameter to check.
     */
    private void checkParam(final DetailAST aParam)
    {
        if (!aParam.branchContains(TokenTypes.FINAL)) {
            final DetailAST paramName = aParam.findFirstToken(TokenTypes.IDENT);
            final DetailAST firstNode = CheckUtils.getFirstNode(aParam);
            log(firstNode.getLineNo(), firstNode.getColumnNo(),
                "final.parameter", paramName.getText());
        }
    }
}
