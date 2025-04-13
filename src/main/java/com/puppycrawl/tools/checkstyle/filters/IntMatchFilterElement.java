////
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
///

package com.puppycrawl.tools.checkstyle.filters;

/**
 * This filter element is immutable and accepts a matching Integer.
 */
class IntMatchFilterElement implements IntFilterElement {

    /** The matching Integer. */
    private final int matchValue;

    /**
     * Constructs a MatchFilter for an int.
     *
     * @param matchValue the matching int.
     */
    /* package */ IntMatchFilterElement(int matchValue) {
        this.matchValue = matchValue;
    }

    @Override
    public boolean accept(int intValue) {
        return matchValue == intValue;
    }

    @Override
    public String toString() {
        return "IntMatchFilterElement[" + matchValue + "]";
    }

    @Override
    public final int hashCode() {
        return Integer.valueOf(matchValue).hashCode();
    }

    @Override
    public final boolean equals(Object object) {
        if (object instanceof IntMatchFilterElement) {
            final IntMatchFilterElement other = (IntMatchFilterElement) object;
            return matchValue == other.matchValue;
        }
        return false;
    }

}
