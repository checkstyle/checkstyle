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
package com.puppycrawl.tools.checkstyle;

import java.io.Serializable;
import java.io.ObjectStreamException;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents the options for a  block.
 *
 * @author <a href="mailto:oliver@puppycrawl.com">Oliver Burn</a>
 */
public final class BlockOption implements Serializable
{
    /** maps from a string representation to an option **/
    private static final Map STR_TO_OPT = new HashMap();

    /** represents ignoring  blocks **/
    public static final BlockOption IGNORE = new BlockOption("ignore");
    /** represents requiring some text in the  block **/
    public static final BlockOption TEXT = new BlockOption("text");
    /** represents requiring a statement in the  block **/
    public static final BlockOption STMT = new BlockOption("stmt");

    /** the string representation of the option **/
    private final String mStrRep;

    /**
     * Creates a new <code>BlockOption</code> instance.
     * @param aStrRep the string representation
     */
    private BlockOption(String aStrRep)
    {
        mStrRep = aStrRep.trim().toLowerCase();
        STR_TO_OPT.put(mStrRep, this);
    }

    /**
     * Returns the BlockOption specified by a string representation. If no
     * option exists then null is returned.
     * @param aStrRep the String representation to parse
     * @return the <code>BlockOption</code> value represented by aStrRep,
     *         or null if none exists.
     */
    public static BlockOption decode(String aStrRep)
    {
        return (BlockOption) STR_TO_OPT.get(aStrRep.trim().toLowerCase());
    }

    /**
     * Ensures that we don't get multiple instances of one BlockOption
     * during deserialization. See Section 3.6 of the Java Object
     * Serialization Specification for details.
     *
     * @return the serialization replacement object
     * @throws ObjectStreamException if a deserialization error occurs
     */
    private Object readResolve() throws ObjectStreamException
    {
        return decode(mStrRep);
    }
}
