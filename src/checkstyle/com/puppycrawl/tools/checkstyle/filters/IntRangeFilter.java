////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Filter;

/**
 * This filter accepts an Integer in a range and is neutral
 * on other Objects.
 * @author Rick Giles
 */
public class IntRangeFilter
    implements Filter
{
    /** hash function multiplicand */
    private static final int HASH_MULT = 29;

    /** lower bound of the range */
    private Integer mLowerBound;

    /** upper bound of the range */
    private Integer mUpperBound;

    /**
     * Constructs a <code>IntRangeFilter</code> with a
     * lower bound and an upper bound for the range.
     * @param aLowerBound the lower bound of the range.
     * @param aUpperBound the upper bound of the range.
     */
    public IntRangeFilter(int aLowerBound, int aUpperBound)
    {
        mLowerBound = new Integer(aLowerBound);
        mUpperBound = new Integer(aUpperBound);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Filter#decide */
    public int decide(Object aObject)
    {
        if ((mLowerBound.compareTo(aObject) <= 0)
            && (mUpperBound.compareTo(aObject) >= 0))
        {
            return Filter.ACCEPT;
        }
        else {
            return Filter.NEUTRAL;
        }
    }

    /** @see java.lang.Object#hashCode() */
    public int hashCode()
    {
        return HASH_MULT * mLowerBound.intValue() + mUpperBound.intValue();
    }

    /** @see java.lang.Object#equals(java.lang.Object) */
    public boolean equals(Object aObject)
    {
        if (aObject instanceof IntRangeFilter) {
            final IntRangeFilter other = (IntRangeFilter) aObject;
            return (this.mLowerBound.equals(other.mLowerBound)
                && this.mUpperBound.equals(other.mUpperBound));
        }
        else {
            return false;
        }
    }
    /** @see java.lang.Object#toString() */
    public String toString()
    {
        return "IntRangeFilter[" + mLowerBound + "," + mUpperBound + "]";
    }

}
