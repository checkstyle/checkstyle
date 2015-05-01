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
    private final Integer lowerBound;

    /** upper bound of the range */
    private final Integer upperBound;

    /**
     * Constructs a <code>IntRangeFilter</code> with a
     * lower bound and an upper bound for the range.
     * @param lowerBound the lower bound of the range.
     * @param upperBound the upper bound of the range.
     */
    public IntRangeFilter(int lowerBound, int upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(int intValue)
    {
        return lowerBound.compareTo(intValue) <= 0
            && upperBound.compareTo(intValue) >= 0;
    }

    @Override
    public int hashCode()
    {
        return HASH_MULT * lowerBound.intValue() + upperBound.intValue();
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof IntRangeFilter) {
            final IntRangeFilter other = (IntRangeFilter) object;
            return this.lowerBound.equals(other.lowerBound)
                && this.upperBound.equals(other.upperBound);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "IntRangeFilter[" + lowerBound + "," + upperBound + "]";
    }

}
