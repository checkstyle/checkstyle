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

package com.puppycrawl.tools.checkstyle.xpath.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;

/**
 * Iterates list in backward direction.
 */
public class ReverseListIterator implements AxisIterator {
    /**
     * List of nodes.
     */
    private final List<? extends NodeInfo> items;
    /**
     * Current index.
     */
    private int index;

    /**
     * Constructor for {@code ReverseListIterator} class.
     *
     * @param items the collection of nodes.
     */
    public ReverseListIterator(Collection<? extends NodeInfo> items) {
        if (items == null) {
            this.items = null;
            index = -1;
        }
        else {
            this.items = new ArrayList<>(items);
            index = items.size() - 1;
        }
    }

    /**
     * Get the next item in the sequence.
     *
     * @return the next Item. If there are no more nodes, return null.
     */
    @Override
    public NodeInfo next() {
        final NodeInfo result;
        if (index == -1) {
            result = null;
        }
        else {
            result = items.get(index);
            index--;
        }
        return result;
    }
}
