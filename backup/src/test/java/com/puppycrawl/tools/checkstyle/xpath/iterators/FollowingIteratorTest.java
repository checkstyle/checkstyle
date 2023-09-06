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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.XpathIteratorUtil.findNode;

import org.junit.jupiter.api.Test;

import net.sf.saxon.om.NodeInfo;

public class FollowingIteratorTest {

    @Test
    public void testFollowingSibling() {
        final NodeInfo startNode = findNode("ANNOTATIONS");

        try (FollowingIterator iterator = new FollowingIterator(startNode)) {
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("CLASS_DEF"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("OBJBLOCK"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("LCURLY"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("METHOD_DEF"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("RCURLY"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("PARAMETERS"));
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(findNode("SLIST"));
            assertWithMessage("Node should be null")
                    .that(iterator.next())
                    .isNull();
            assertWithMessage("Node should be null")
                    .that(iterator.next())
                    .isNull();
        }
    }

    @Test
    public void testNoSibling() {
        final NodeInfo startNode = findNode("CLASS_DEF");

        try (FollowingIterator iterator = new FollowingIterator(startNode)) {
            assertWithMessage("Node should be null")
                    .that(iterator.next())
                    .isNull();
        }
    }
}
