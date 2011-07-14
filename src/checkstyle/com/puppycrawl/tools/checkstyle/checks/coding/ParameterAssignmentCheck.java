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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.Set;

/**
 * <p>
 * Disallow assignment of parameters.
 * </p>
 * <p>
 * Rationale:
 * Parameter assignment is often considered poor
 * programming practice. Forcing developers to declare
 * parameters as final is often onerous. Having a check
 * ensure that parameters are never assigned would give
 * the best of both worlds.
 * </p>
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class ParameterAssignmentCheck extends Check
{
    /** Stack of methods' parameters. */
    private final FastStack<Set<String>> mParameterNamesStack =
        FastStack.newInstance();
    /** Current set of perameters. */
    private Set<String> mParameterNames;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.INC,
            TokenTypes.POST_INC,
            TokenTypes.DEC,
            TokenTypes.POST_DEC,
        };
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        // clear data
        mParameterNamesStack.clear();
        mParameterNames = null;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
            visitMethodDef(aAST);
            break;
        case TokenTypes.ASSIGN:
        case TokenTypes.PLUS_ASSIGN:
        case TokenTypes.MINUS_ASSIGN:
        case TokenTypes.STAR_ASSIGN:
        case TokenTypes.DIV_ASSIGN:
        case TokenTypes.MOD_ASSIGN:
        case TokenTypes.SR_ASSIGN:
        case TokenTypes.BSR_ASSIGN:
        case TokenTypes.SL_ASSIGN:
        case TokenTypes.BAND_ASSIGN:
        case TokenTypes.BXOR_ASSIGN:
        case TokenTypes.BOR_ASSIGN:
            visitAssign(aAST);
            break;
        case TokenTypes.INC:
        case TokenTypes.POST_INC:
        case TokenTypes.DEC:
        case TokenTypes.POST_DEC:
            visitIncDec(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
            leaveMethodDef();
            break;
        case TokenTypes.ASSIGN:
        case TokenTypes.PLUS_ASSIGN:
        case TokenTypes.MINUS_ASSIGN:
        case TokenTypes.STAR_ASSIGN:
        case TokenTypes.DIV_ASSIGN:
        case TokenTypes.MOD_ASSIGN:
        case TokenTypes.SR_ASSIGN:
        case TokenTypes.BSR_ASSIGN:
        case TokenTypes.SL_ASSIGN:
        case TokenTypes.BAND_ASSIGN:
        case TokenTypes.BXOR_ASSIGN:
        case TokenTypes.BOR_ASSIGN:
        case TokenTypes.INC:
        case TokenTypes.POST_INC:
        case TokenTypes.DEC:
        case TokenTypes.POST_DEC:
            // Do nothing
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Ckecks if this is assignments of parameter.
     * @param aAST assignment to check.
     */
    private void visitAssign(DetailAST aAST)
    {
        checkIdent(aAST);
    }

    /**
     * Checks if this is increment/decrement of parameter.
     * @param aAST dec/inc to check.
     */
    private void visitIncDec(DetailAST aAST)
    {
        checkIdent(aAST);
    }

    /**
     * Check if ident is parameter.
     * @param aAST ident to check.
     */
    private void checkIdent(DetailAST aAST)
    {
        if ((mParameterNames != null) && !mParameterNames.isEmpty()) {
            final DetailAST identAST = aAST.getFirstChild();

            if ((identAST != null)
                && (identAST.getType() == TokenTypes.IDENT)
                && mParameterNames.contains(identAST.getText()))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "parameter.assignment", identAST.getText());
            }
        }
    }

    /**
     * Creates new set of parameters and store old one in stack.
     * @param aAST a method to process.
     */
    private void visitMethodDef(DetailAST aAST)
    {
        mParameterNamesStack.push(mParameterNames);
        mParameterNames = Sets.newHashSet();

        visitMethodParameters(aAST.findFirstToken(TokenTypes.PARAMETERS));
    }

    /** Restores old set of parameters. */
    private void leaveMethodDef()
    {
        mParameterNames = mParameterNamesStack.pop();
    }

    /**
     * Creates new parameter set for given method.
     * @param aAST a method for process.
     */
    private void visitMethodParameters(DetailAST aAST)
    {
        DetailAST parameterDefAST =
            aAST.findFirstToken(TokenTypes.PARAMETER_DEF);

        for (; parameterDefAST != null;
             parameterDefAST = parameterDefAST.getNextSibling())
        {
            if (parameterDefAST.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST param =
                    parameterDefAST.findFirstToken(TokenTypes.IDENT);
                mParameterNames.add(param.getText());
            }
        }
    }
}
