////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.meta;


/** Simple POJO class for a pair. */
public final class Pair<L, R> {

    /** The left object. */
    private final L left;

    /** The right object. */
    private final R right;

    /**
     * Constructor for a pair
     *
     * @param left left
     * @param right right
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the left object.
     *
     * @return left
     */
    public L getLeft() {
        return left;
    }

    /**
     * Get the right object.
     *
     * @return right
     */
    public R getRight() {
        return right;
    }
}
