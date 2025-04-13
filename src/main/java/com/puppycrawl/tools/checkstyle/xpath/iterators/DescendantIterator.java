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

import java.util.LinkedList;
import java.util.Queue;

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.SingleNodeIterator;

/**
 * Recursive-free implementation of the descendant axis iterator.
 */
public class DescendantIterator implements AxisIterator {

    /**
     * Enum defines starting node for iterator.
     */
    public enum StartWith {
        /** Start with current node. */
        CURRENT_NODE,
        /** Omit current node and start with child nodes. */
        CHILDREN,
    }

    /**
     * Queue for sibling nodes.
     */
    private final Queue<NodeInfo> queue = new LinkedList<>();
    /**
     * Descendant axis iterator.
     */
    private AxisIterator descendantEnum;

    /**
     * Create an iterator over the "descendant" axis.
     *
     * @param start the initial context node.
     * @param startWith mode of the iterator, see {@link StartWith}.
     */
    public DescendantIterator(NodeInfo start, StartWith startWith) {
        if (startWith == StartWith.CURRENT_NODE) {
            descendantEnum = SingleNodeIterator.makeIterator(start);
        }
        else if (startWith == StartWith.CHILDREN) {
            descendantEnum = start.iterateAxis(AxisInfo.CHILD);
        }
    }

    /**
     * Get the next item in the sequence.
     *
     * @return the next Item. If there are no more nodes, return null.
     */
    @Override
    public NodeInfo next() {
        NodeInfo result = null;
        do {
            if (descendantEnum == null) {
                if (queue.isEmpty()) {
                    break;
                }
                descendantEnum = queue.poll().iterateAxis(AxisInfo.CHILD);
            }
            else {
                result = descendantEnum.next();
                if (result == null) {
                    descendantEnum = null;
                }
            }
        } while (result == null);

        if (result != null) {
            queue.add(result);
        }
        return result;
    }
}
