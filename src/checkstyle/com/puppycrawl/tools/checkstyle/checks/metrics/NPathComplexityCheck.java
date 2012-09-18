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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.math.BigInteger;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks the npath complexity against a specified limit (default = 200).
 * The npath metric computes the number of possible execution paths
 * through a function. Similar to the cyclomatic complexity but also
 * takes into account the nesting of conditional statements and
 * multi-part boolean expressions.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 * TODO: For every or: _value += (_orCount * (nestedValue - 1));
 * TODO: For every and: ???
 */
public final class NPathComplexityCheck extends AbstractComplexityCheck
{
    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 200;

    /** Creates new instance of the check. */
    public NPathComplexityCheck()
    {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
        };
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.LITERAL_WHILE:
        case TokenTypes.LITERAL_DO:
        case TokenTypes.LITERAL_FOR:
        case TokenTypes.LITERAL_IF:
        case TokenTypes.QUESTION:
        case TokenTypes.LITERAL_TRY:
        case TokenTypes.LITERAL_SWITCH:
            visitMultiplyingConditional();
            break;
        case TokenTypes.LITERAL_ELSE:
        case TokenTypes.LITERAL_CATCH:
        case TokenTypes.LITERAL_CASE:
            visitAddingConditional();
            break;
        default:
            super.visitToken(aAST);
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.LITERAL_WHILE:
        case TokenTypes.LITERAL_DO:
        case TokenTypes.LITERAL_FOR:
        case TokenTypes.LITERAL_IF:
        case TokenTypes.QUESTION:
        case TokenTypes.LITERAL_TRY:
        case TokenTypes.LITERAL_SWITCH:
            leaveMultiplyingConditional();
            break;
        case TokenTypes.LITERAL_ELSE:
        case TokenTypes.LITERAL_CATCH:
        case TokenTypes.LITERAL_CASE:
            leaveAddingConditional();
            break;
        default:
            super.leaveToken(aAST);
        }
    }

    @Override
    protected String getMessageID()
    {
        return "npathComplexity";
    }

    /** Visits else, catch or case. */
    private void visitAddingConditional()
    {
        pushValue();
    }

    /** Leaves else, catch or case. */
    private void leaveAddingConditional()
    {
        setCurrentValue(
                getCurrentValue().subtract(BigInteger.ONE).add(popValue()));
    }

    /** Visits while, do, for, if, try, ? (in ?::) or switch. */
    private void visitMultiplyingConditional()
    {
        pushValue();
    }

    /** Leaves while, do, for, if, try, ? (in ?::) or switch. */
    private void leaveMultiplyingConditional()
    {
        setCurrentValue(
                getCurrentValue().add(BigInteger.ONE).multiply(popValue()));
    }
}
