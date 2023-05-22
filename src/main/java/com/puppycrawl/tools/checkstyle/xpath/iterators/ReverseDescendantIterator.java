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

package com.puppycrawl.tools.checkstyle.xpath.iterators;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;

/**
 * Recursive-free implementation of the descendant axis iterator. Difference between this iterator
 * and {@link DescendantIterator} in traversal order of the child nodes. In some cases it is useful
 * to iterate from last child backwards to the first one, for example in {@link PrecedingIterator}.
 */
public class ReverseDescendantIterator implements AxisIterator {
    /**
     * Queue for sibling nodes.
     */
    private final Queue<NodeInfo> queue = new LinkedList<>();
    /**
     * Stack for child nodes, to represent them in reverse order.
     */
    private final Deque<NodeInfo> stack = new LinkedList<>();

    /**
     * Create an iterator over the "descendant" axis in reverse order.
     *
     * @param start the initial context node.
     */
    public ReverseDescendantIterator(NodeInfo start) {
        pushToStack(start.iterateAxis(AxisInfo.CHILD));
    }

    /**
     * Pushes all children to the stack.
     *
     * @param iterateAxis {@link AxisInfo#CHILD} axis iterator.
     */
    private void pushToStack(AxisIterator iterateAxis) {
        NodeInfo nodeInfo = iterateAxis.next();
        while (nodeInfo != null) {
            stack.addLast(nodeInfo);
            nodeInfo = iterateAxis.next();
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
            if (stack.isEmpty()) {
                if (queue.isEmpty()) {
                    break;
                }
                pushToStack(queue.poll().iterateAxis(AxisInfo.CHILD));
            }
            else {
                result = stack.removeLast();
            }
        } while (result == null);

        if (result != null) {
            queue.add(result);
        }
        return result;
    }
}
