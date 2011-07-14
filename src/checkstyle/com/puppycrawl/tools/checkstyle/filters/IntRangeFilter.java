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
package com.puppycrawl.tools.checkstyle.filters;

/**
 * This filter accepts an Integer in a range.
 * @author Rick Giles
 */
class IntRangeFilter implements IntFilter
{
    /** hash function multiplicand */
    private static final int HASH_MULT = 29;

    /** lower bound of the range */
    private final Integer mLowerBound;

    /** upper bound of the range */
    private final Integer mUpperBound;

    /**
     * Constructs a <code>IntRangeFilter</code> with a
     * lower bound and an upper bound for the range.
     * @param aLowerBound the lower bound of the range.
     * @param aUpperBound the upper bound of the range.
     */
    public IntRangeFilter(int aLowerBound, int aUpperBound)
    {
        mLowerBound = aLowerBound;
        mUpperBound = aUpperBound;
    }

    /** {@inheritDoc} */
    public boolean accept(int aInt)
    {
        return ((mLowerBound.compareTo(aInt) <= 0)
            && (mUpperBound.compareTo(aInt) >= 0));
    }

    @Override
    public int hashCode()
    {
        return HASH_MULT * mLowerBound.intValue() + mUpperBound.intValue();
    }

    @Override
    public boolean equals(Object aObject)
    {
        if (aObject instanceof IntRangeFilter) {
            final IntRangeFilter other = (IntRangeFilter) aObject;
            return (this.mLowerBound.equals(other.mLowerBound)
                && this.mUpperBound.equals(other.mUpperBound));
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "IntRangeFilter[" + mLowerBound + "," + mUpperBound + "]";
    }

}
