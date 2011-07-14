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
package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Abstract class for checking if a class/method type parameter's name
 * conforms to a format specified by the format property.
 * </p>
 *
 * <p>This class extends {@link AbstractNameCheck}</p>
 *
 * @author Travis Schneeberger
 * @version 1.0
 */
public abstract class AbstractTypeParameterNameCheck
    extends AbstractNameCheck
{
    /** the location of the type parameter **/
    private int mLocation;

    /**
     * Creates a new <code>AbstractTypeParameterNameCheck</code> instance.
     * @param aFormat format to check with
     */
    public AbstractTypeParameterNameCheck(String aFormat)
    {
        super(aFormat);
    }

    @Override
    public final int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.TYPE_PARAMETER,
        };
    }

    @Override
    public final void init()
    {
        this.mLocation = getLocation();

        assert (this.mLocation == TokenTypes.CLASS_DEF)
            || (this.mLocation == TokenTypes.METHOD_DEF);
    }

    @Override
    protected final boolean mustCheckName(DetailAST aAST)
    {
        DetailAST location =
            aAST.getParent().getParent();

        if (location.getType() == TokenTypes.MODIFIERS) {
            location = location.getParent();
        }

        return location.getType() == this.mLocation;
    }

    /**
     * This method must be overriden to specify the
     * location of the type parameter to check.
     *
     * @return <code> TokenTypes.CLASS_DEF </code>
     * or <code> TokenTypes.METHOD_DEF </code>
     */
    protected abstract int getLocation();
}
