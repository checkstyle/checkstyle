////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

/**
 * Abstract class that represents options.
 *
 * @author Oliver Burn
 * @author Rick Giles

 */
public abstract class AbstractOption
    implements Serializable
{

    /** the string representation of the option **/
    private final String mStrRep;

    /**
     * Creates a new <code>AbstractOption</code> instance.
     * @param aStrRep the string representation
     */
    protected AbstractOption(String aStrRep)
    {
        mStrRep = aStrRep.trim().toLowerCase();
        final Map strToOpt = getStrToOpt();
        strToOpt.put(mStrRep, this);
    }

    /**
     * Returns the map from string representations to options.
     * @return <code>Map</code> from strings to options.
     */
    protected abstract Map getStrToOpt();

    /**
     * Returns the option specified by a string representation. If no
     * option exists then null is returned.
     * @param aStrRep the String representation to parse
     * @return the <code>AbstractOption</code> value represented by
     *         aStrRep, or null if none exists.
     */
    public AbstractOption decode(String aStrRep)
    {
        final Map strToOpt = getStrToOpt();
        return (AbstractOption) strToOpt.get(aStrRep.trim().toLowerCase());
    }

    /**
     * Returns the string representation of this AbstractOption.
     * @see java.lang.Object
     **/
    public String toString()
    {
        return mStrRep;
    }

    /**
     * Ensures that we don't get multiple instances of one AbstractOption
     * during deserialization. See Section 3.6 of the Java Object
     * Serialization Specification for details.
     *
     * @return the serialization replacement object
     * @throws ObjectStreamException if a deserialization error occurs
     */
    protected Object readResolve()
        throws ObjectStreamException
    {
        return decode(mStrRep);
    }
}
