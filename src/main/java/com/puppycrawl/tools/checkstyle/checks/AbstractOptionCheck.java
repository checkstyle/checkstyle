////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Abstract class for checks with a parameter named <tt>option</tt>, where the
 * option is identified by a {@link Enum}. The logic to convert from a string
 * representation to the {@link Enum} is to {@link String#trim()} the string
 * and convert using {@link String#toUpperCase()} and then look up using
 * {@link Enum#valueOf}.
 * @author Oliver Burn
 * @author Rick Giles
 * @param <T> the type of the option.
 */
public abstract class AbstractOptionCheck<T extends Enum<T>>
    extends Check {

    /** Semicolon literal. */
    protected static final String SEMICOLON = ";";

    /** Since I cannot get this by going <tt>T.class</tt>. */
    private final Class<T> optionClass;
    /** The policy to enforce. */
    private T abstractOption;

    /**
     * Creates a new {@code AbstractOptionCheck} instance.
     * @param literalDefault the default option.
     * @param optionClass the class for the option. Required due to a quirk
     *        in the Java language.
     */
    protected AbstractOptionCheck(T literalDefault, Class<T> optionClass) {
        abstractOption = literalDefault;
        this.optionClass = optionClass;
    }

    /**
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            abstractOption =
                    Enum.valueOf(optionClass, optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + abstractOption, iae);
        }
    }

    /**
     * Gets AbstractOption set.
     * @return the {@code AbstractOption} set
     */
    public T getAbstractOption() {
        // WARNING!! Do not rename this method to getOption(). It breaks
        // BeanUtils, which will silently not call setOption. Very annoying!
        return abstractOption;
    }
}
