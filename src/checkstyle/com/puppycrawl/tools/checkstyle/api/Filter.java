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
package com.puppycrawl.tools.checkstyle.api;

/**
 * Users should extend this class to implement customized
 * Object filtering.
 * @author Rick Giles
 */
public interface Filter

{
    /** The object is acceptable to this Filter */
    int ACCEPT = -1;

    /** The object is rejected by this filter. */
    int DENY = 0;

    /** This filter is neutral with respect to the object. */
    int NEUTRAL = 1;

    /**
     * Determines the filtering of an Object.
     * If the decision is <code>DENY</code>, then the Object is rejected.
     * If the decision is <code>NEUTRAL</code>, then the filter is neutral
     * with respect to the Object.
     * If the decision is <code>ACCEPT</code> then the object will be accepted.
     * @param aObject the object to decide on.
     * @return the decision of the filter.
     */
    int decide(Object aObject);
}
