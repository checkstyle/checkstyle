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
 * This filter accepts a matching Integer.
 * @author Rick Giles
 */
class IntMatchFilter implements IntFilter {
    /** the matching Integer */
    private final int matchValue;

    /**
     * Constructs a MatchFilter for an int.
     * @param matchValue the matching int.
     */
    public IntMatchFilter(int matchValue) {
        this.matchValue = matchValue;
    }

    @Override
    public boolean accept(int intValue) {
        return matchValue == intValue;
    }

    @Override
    public String toString() {
        return "IntMatchFilter[" + matchValue + "]";
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(matchValue).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof IntMatchFilter) {
            final IntMatchFilter other = (IntMatchFilter) object;
            return matchValue == other.matchValue;
        }
        return false;
    }
}
