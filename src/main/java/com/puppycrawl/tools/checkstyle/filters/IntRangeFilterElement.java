///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

/**
 * This filter element is immutable and accepts an Integer in a range.
 *
 * @param lowerBound the lower bound of the range
 * @param upperBound the upper bound of the range
 */
record IntRangeFilterElement(int lowerBound, int upperBound) implements IntFilterElement {

    /**
     * Constructs a new {@code IntRangeFilterElement} for the given range.
     *
     * @param lowerBound the lower bound of the range
     * @param upperBound the upper bound of the range
     */
    /* package */ IntRangeFilterElement {
        // Canonical constructor; no extra validation needed
    }

    @Override
    public boolean accept(int intValue) {
        return lowerBound <= intValue && intValue <= upperBound;
    }

    @Override
    public String toString() {
        return "IntRangeFilterElement[" + lowerBound + ", " + upperBound + "]";
    }

}
