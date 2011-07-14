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
package com.puppycrawl.tools.checkstyle.checks;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Abstract class for checks with a parameter named <tt>option</tt>, where the
 * option is identified by a {@link Enum}. The logic to convert from a string
 * representation to the {@link Enum} is to {@link String#trim()} the string
 * and convert using {@link String#toUpperCase()} and then look up using
 * {@link Enum#valueOf}.
 * @param <T> the type of the option.
 * @author Oliver Burn
 * @author Rick Giles
 */
public abstract class AbstractOptionCheck<T extends Enum<T>>
    extends Check
{
    /** Since I cannot get this by going <tt>T.class</tt>. */
    private final Class<T> mOptionClass;
    /** the policy to enforce */
    private T mOption;

    /**
     * Creates a new <code>AbstractOptionCheck</code> instance.
     * @param aDefault the default option.
     * @param aOptionClass the class for the option. Required due to a quirk
     *        in the Java language.
     */
    public AbstractOptionCheck(T aDefault, Class<T> aOptionClass)
    {
        mOption = aDefault;
        mOptionClass = aOptionClass;
    }

    /**
     * Set the option to enforce.
     * @param aOption string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String aOption) throws ConversionException
    {
        try {
            mOption = Enum.valueOf(mOptionClass, aOption.trim().toUpperCase());
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + aOption, iae);
        }
    }

    /**
     * @return the <code>AbstractOption</code> set
     */
    public T getAbstractOption()
    {
        // WARNING!! Do not rename this method to getOption(). It breaks
        // BeanUtils, which will silently not call setOption. Very annoying!
        return mOption;
    }
}
