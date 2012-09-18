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
package com.puppycrawl.tools.checkstyle.filters;

/**
 * This filter accepts a matching Integer.
 * @author Rick Giles
 */
class IntMatchFilter implements IntFilter
{
    /** the matching Integer */
    private final int mMatchValue;

    /**
     * Constructs a MatchFilter for an int.
     * @param aMatchValue the matching int.
     */
    public IntMatchFilter(int aMatchValue)
    {
        mMatchValue = aMatchValue;
    }

    /** {@inheritDoc} */
    public boolean accept(int aInt)
    {
        return mMatchValue == aInt;
    }

    @Override
    public String toString()
    {
        return "IntMatchFilter[" + mMatchValue + "]";
    }

    @Override
    public int hashCode()
    {
        return Integer.valueOf(mMatchValue).hashCode();
    }

    @Override
    public boolean equals(Object aObject)
    {
        if (aObject instanceof IntMatchFilter) {
            final IntMatchFilter other = (IntMatchFilter) aObject;
            return this.mMatchValue == other.mMatchValue;
        }
        return false;
    }
}
