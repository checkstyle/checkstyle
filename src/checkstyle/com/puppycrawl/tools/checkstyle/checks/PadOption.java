////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import java.io.Serializable;
import java.io.ObjectStreamException;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents the options for whitespace around parenthesis.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 * @version 1
 */
public final class PadOption
    implements Serializable
{
    /** maps from a string representation to an option **/
    private static final Map STR_TO_OPT = new HashMap();

    /** represents no spacing **/
    public static final PadOption NOSPACE = new PadOption("nospace");
    /** represents ignoring the spacing **/
    public static final PadOption IGNORE = new PadOption("ignore");
    /** represents mandatory spacing **/
    public static final PadOption SPACE = new PadOption("space");

    /** the string representation of the option **/
    private final String mStrRep;

    /**
     * Creates a new <code>PadOption</code> instance.
     * @param aStrRep the string representation
     */
    private PadOption(String aStrRep)
    {
        mStrRep = aStrRep.trim().toLowerCase();
        STR_TO_OPT.put(mStrRep, this);
    }

    /**
     * Returns the option specified by a string representation. If no
     * option exists then null is returned.
     * @param aStrRep the String representation to parse
     * @return the <code>PadOption</code> value represented by aStrRep, or
     *         null if none exists.
     */
    public static PadOption decode(String aStrRep)
    {
        return (PadOption) STR_TO_OPT.get(aStrRep.trim().toLowerCase());
    }

    /** @see java.lang.Object **/
    public String toString()
    {
        return mStrRep;
    }

    /**
     * Ensures that we don't get multiple instances of one PadOption
     * during deserialization. See Section 3.6 of the Java Object
     * Serialization Specification for details.
     *
     * @return the serialization replacement object
     * @throws ObjectStreamException if a deserialization error occurs
     */
    private Object readResolve()
        throws ObjectStreamException
    {
        return decode(mStrRep);
    }
}
