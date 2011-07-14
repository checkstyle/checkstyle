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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Implements Bloch, Effective Java, Item 17 -
 * Use Interfaces only to define types.
 *
 * <p>
 * An interface should describe a <em>type</em>, it is therefore
 * inappropriate to define an interface that does not contain any methods
 * but only constants.
 * </p>
 *
 * <p>
 * The check can be configured to also disallow marker interfaces like
 * <code>java.io.Serializable</code>, that do not contain methods or
 * constants at all.
 * </p>
 *
 * @author lkuehne
 */
public final class InterfaceIsTypeCheck
        extends Check
{
    /** flag to control whether marker interfaces are allowed. */
    private boolean mAllowMarkerInterfaces = true;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.INTERFACE_DEF};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST objBlock =
                aAST.findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST methodDef =
                objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST variableDef =
                objBlock.findFirstToken(TokenTypes.VARIABLE_DEF);
        final boolean methodRequired =
                !mAllowMarkerInterfaces || (variableDef != null);

        if ((methodDef == null) && methodRequired) {
            log(aAST.getLineNo(), "interface.type");
        }

    }

    /**
     * Controls whether marker interfaces like Serializable are allowed.
     * @param aFlag whether to allow marker interfaces or not
     */
    public void setAllowMarkerInterfaces(boolean aFlag)
    {
        mAllowMarkerInterfaces = aFlag;
    }
}
