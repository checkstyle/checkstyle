///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.SequenceIterator;

/**
 * Recursive-free implementation of the following axis iterator.
 */
public class FollowingIterator implements SequenceIterator {

    /**
     * Ancestor axis iterator.
     */
    private final SequenceIterator ancestorEnum;
    /**
     * Following-sibling axis iterator.
     */
    private SequenceIterator siblingEnum;
    /**
     * Descendant axis iterator.
     */
    private SequenceIterator descendantEnum;

    /**
     * Create an iterator over the "following" axis.
     *
     * @param start the initial context node.
     */
    public FollowingIterator(NodeInfo start) {
        ancestorEnum = start.iterateAncestorAxis(null);
        siblingEnum = start.iterateFollowingSiblingAxis(null);
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
            if (descendantEnum != null) {
                result = (NodeInfo) descendantEnum.next();
            }

            if (result == null) {
                result = (NodeInfo) siblingEnum.next();
                if (result != null) {
                    descendantEnum = result.iterateDescendantAxis(null);
                }
            }

            if (result == null) {
                final NodeInfo parent = (NodeInfo) ancestorEnum.next();
                if (parent == null) {
                    break;
                }
                siblingEnum = parent.iterateFollowingSiblingAxis(null);
            }
        } while (result == null);
        return result;
    }

}
