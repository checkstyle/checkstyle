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
package com.puppycrawl.tools.checkstyle.filters;

/**
 * This filter accepts a matching Integer.
 * @author Rick Giles
 */
class IntMatchFilter implements IntFilter
{
    /** the matching Integer */
    private Integer mMatchValue;

    /**
     * Constructs a MatchFilter for an int.
     * @param aMatchValue the matching int.
     */
    public IntMatchFilter(int aMatchValue)
    {
        mMatchValue = new Integer(aMatchValue);
    }

    /** @see com.puppycrawl.tools.checkstyle.filters.IntFilter */
    public boolean accept(Integer aInt)
    {
        return mMatchValue.equals(aInt);
    }

    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return "IntMatchFilter[" + mMatchValue + "]";
    }

    /** @see java.lang.Object#hashCode() */
    public int hashCode()
    {
        return mMatchValue.hashCode();
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    public boolean equals(Object aObject)
    {
        if (aObject instanceof IntMatchFilter) {
            final IntMatchFilter other = (IntMatchFilter) aObject;
            return (this.mMatchValue).equals(other.mMatchValue);
        }
        return false;
    }
}
