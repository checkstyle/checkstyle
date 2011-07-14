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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
/**
 * Check for ensuring that for loop control variables are not modified
 * inside the for block.
 *
 * @author Daniel Grenner
 */
public final class ModifiedControlVariableCheck extends Check
{
    /** Current set of parameters. */
    private FastStack<String> mCurrentVariables = FastStack.newInstance();
    /** Stack of block parameters. */
    private final FastStack<FastStack<String>> mVariableStack =
        FastStack.newInstance();

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.FOR_ITERATOR,
            TokenTypes.FOR_EACH_CLAUSE,
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
        mCurrentVariables.clear();
        mVariableStack.clear();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.OBJBLOCK:
            enterBlock();
            break;
        case TokenTypes.LITERAL_FOR:
        case TokenTypes.FOR_ITERATOR:
        case TokenTypes.FOR_EACH_CLAUSE:
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
            checkIdent(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }


    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.FOR_ITERATOR:
            leaveForIter(aAST.getParent());
            break;
        case TokenTypes.FOR_EACH_CLAUSE:
            leaveForEach(aAST);
            break;
        case TokenTypes.LITERAL_FOR:
            leaveForDef(aAST);
            break;
        case TokenTypes.OBJBLOCK:
            exitBlock();
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
     * Enters an inner class, which requires a new variable set.
     */
    private void enterBlock()
    {
        mVariableStack.push(mCurrentVariables);
        mCurrentVariables = FastStack.newInstance();

    }
    /**
     * Leave an inner class, so restore variable set.
     */
    private void exitBlock()
    {
        mCurrentVariables = mVariableStack.pop();
    }

    /**
     * Check if ident is parameter.
     * @param aAST ident to check.
     */
    private void checkIdent(DetailAST aAST)
    {
        if ((mCurrentVariables != null) && !mCurrentVariables.isEmpty()) {
            final DetailAST identAST = aAST.getFirstChild();

            if ((identAST != null)
                && (identAST.getType() == TokenTypes.IDENT)
                && mCurrentVariables.contains(identAST.getText()))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "modified.control.variable", identAST.getText());
            }
        }
    }

    /**
     * Push current variables to the stack.
     * @param aAST a for definition.
     */
    private void leaveForIter(DetailAST aAST)
    {
        final DetailAST forInitAST = aAST.findFirstToken(TokenTypes.FOR_INIT);
        DetailAST parameterDefAST =
            forInitAST.findFirstToken(TokenTypes.VARIABLE_DEF);

        for (; parameterDefAST != null;
             parameterDefAST = parameterDefAST.getNextSibling())
        {
            if (parameterDefAST.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST param =
                    parameterDefAST.findFirstToken(TokenTypes.IDENT);
                mCurrentVariables.push(param.getText());
            }
        }
    }

    /**
     * Push current variables to the stack.
     * @param aForEach a for-each clause
     */
    private void leaveForEach(DetailAST aForEach)
    {
        final DetailAST paramDef =
            aForEach.findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST paramName = paramDef.findFirstToken(TokenTypes.IDENT);
        mCurrentVariables.push(paramName.getText());
    }

    /**
     * Pops the variables from the stack.
     * @param aAST a for definition.
     */
    private void leaveForDef(DetailAST aAST)
    {
        final DetailAST forInitAST = aAST.findFirstToken(TokenTypes.FOR_INIT);
        if (forInitAST != null) {
            DetailAST parameterDefAST =
                forInitAST.findFirstToken(TokenTypes.VARIABLE_DEF);

            for (; parameterDefAST != null;
                 parameterDefAST = parameterDefAST.getNextSibling())
            {
                if (parameterDefAST.getType() == TokenTypes.VARIABLE_DEF) {
                    mCurrentVariables.pop();
                }
            }
        }
        else {
            // this is for-each loop, just pop veriables
            mCurrentVariables.pop();
        }
    }
}
