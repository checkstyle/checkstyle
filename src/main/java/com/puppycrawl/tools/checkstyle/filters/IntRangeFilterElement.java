///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.util.Objects;

/**
 * This filter element is immutable and accepts an Integer in a range.
 */
class IntRangeFilterElement implements IntFilterElement {

    /** Lower bound of the range. */
    private final Integer lowerBound;

    /** Upper bound of the range. */
    private final Integer upperBound;

    /**
     * Constructs a {@code IntRangeFilterElement} with a
     * lower bound and an upper bound for the range.
     *
     * @param lowerBound the lower bound of the range.
     * @param upperBound the upper bound of the range.
     */
    /* package */ IntRangeFilterElement(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean accept(int intValue) {
        return lowerBound.compareTo(intValue) <= 0
            && upperBound.compareTo(intValue) >= 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final IntRangeFilterElement intRangeFilter = (IntRangeFilterElement) other;
        return Objects.equals(lowerBound, intRangeFilter.lowerBound)
                && Objects.equals(upperBound, intRangeFilter.upperBound);
    }

}
