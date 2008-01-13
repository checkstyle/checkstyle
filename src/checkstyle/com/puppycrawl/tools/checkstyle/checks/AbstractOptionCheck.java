////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Abstract class for checks with options.
 * @author Rick Giles
 */
public abstract class AbstractOptionCheck
    extends Check
{
    /** the policy to enforce */
    private AbstractOption mOption;

    /**
     * Creates a new <code>AbstractOptionCheck</code> instance.
     * @param aDefault the default option.
     */
    public AbstractOptionCheck(AbstractOption aDefault)
    {
        mOption = aDefault;
    }

    /**
     * Set the option to enforce.
     * @param aOption string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String aOption)
        throws ConversionException
    {
        mOption = mOption.decode(aOption);
        if (mOption == null) {
            throw new ConversionException("unable to parse " + aOption);
        }
    }

    /**
     * @return the <code>AbstractOption</code> set
     */
    public AbstractOption getAbstractOption()
    {
        // WARNING!! Do not rename this method to getOption(). It breaks
        // BeanUtils, which will silently not call setOption. Very annoying!
        return mOption;
    }
}
