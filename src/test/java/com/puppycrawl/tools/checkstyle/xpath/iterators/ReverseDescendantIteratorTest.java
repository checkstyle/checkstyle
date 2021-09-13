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

package com.puppycrawl.tools.checkstyle.xpath.iterators;

import static com.puppycrawl.tools.checkstyle.internal.utils.XpathIteratorUtil.findNode;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import net.sf.saxon.om.NodeInfo;

public class ReverseDescendantIteratorTest {

    @Test
    public void testCorrectOrder() {
        final NodeInfo startNode = findNode("CLASS_DEF");

        try (ReverseDescendantIterator iterator = new ReverseDescendantIterator(startNode)) {
            assertThat("Invalid node", iterator.next(), equalTo(findNode("OBJBLOCK")));
            assertThat("Invalid node", iterator.next(), equalTo(findNode("RCURLY")));
            assertThat("Invalid node", iterator.next(), equalTo(findNode("METHOD_DEF")));
            assertThat("Invalid node", iterator.next(), equalTo(findNode("LCURLY")));
            assertThat("Invalid node", iterator.next(), equalTo(findNode("SLIST")));
            assertThat("Invalid node", iterator.next(), equalTo(findNode("PARAMETERS")));
            assertThat("Node should be null", iterator.next(), nullValue());
        }
    }
}
