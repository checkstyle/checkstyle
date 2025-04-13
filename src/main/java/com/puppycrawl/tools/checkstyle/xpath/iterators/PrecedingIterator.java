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

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;

/**
 * Recursive-free implementation of the preceding axis iterator.
 */
public class PrecedingIterator implements AxisIterator {
    /**
     * Ancestor axis iterator.
     */
    private final AxisIterator ancestorEnum;
    /**
     * Preceding-sibling axis iterator.
     */
    private AxisIterator previousSiblingEnum;
    /**
     * Descendant axis iterator.
     */
    private AxisIterator descendantEnum;

    /**
     * Create an iterator over the "preceding" axis.
     *
     * @param start the initial context node.
     */
    public PrecedingIterator(NodeInfo start) {
        ancestorEnum = start.iterateAxis(AxisInfo.ANCESTOR);
        previousSiblingEnum = start.iterateAxis(AxisInfo.PRECEDING_SIBLING);
    }

    /**
     * Get the next item in the sequence.
     *
     * @return the next Item. If there are no more nodes, return null.
     */
    @Override
    public NodeInfo next() {
        NodeInfo result = null;

        while (result == null) {
            if (descendantEnum != null) {
                result = descendantEnum.next();
            }

            if (result == null && previousSiblingEnum != null) {
                result = previousSiblingEnum.next();
                if (result == null) {
                    previousSiblingEnum = null;
                }
                else {
                    descendantEnum = new ReverseDescendantIterator(result);
                }
            }

            if (result == null) {
                result = ancestorEnum.next();
                if (result == null) {
                    break;
                }
                previousSiblingEnum = result.iterateAxis(AxisInfo.PRECEDING_SIBLING);
            }
        }
        return result;
    }
}
