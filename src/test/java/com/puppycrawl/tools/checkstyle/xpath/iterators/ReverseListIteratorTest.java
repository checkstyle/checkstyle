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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;

public class ReverseListIteratorTest {

    @Test
    public void testCorrectOrder() {
        final List<AbstractNode> nodes = Arrays.asList(new TestNode(), new TestNode(),
                new TestNode());

        try (ReverseListIterator iterator = new ReverseListIterator(nodes)) {
            for (int i = nodes.size() - 1; i >= 0; i--) {
                assertThat("Invalid node", iterator.next(), equalTo(nodes.get(i)));
            }
            assertThat("Node should be null", iterator.next(), nullValue());
        }
    }

    @Test
    public void testNullList() {
        try (ReverseListIterator iterator = new ReverseListIterator(null)) {
            assertThat("Node should be null", iterator.next(), nullValue());
        }
    }

    private static class TestNode extends AbstractNode {

        /* package */ TestNode() {
            super(null);
        }

        protected TestNode(TreeInfo treeInfo) {
            super(treeInfo);
        }

        @Override
        public int getTokenType() {
            return 0;
        }

        @Override
        public DetailAST getUnderlyingNode() {
            return null;
        }

        @Override
        public int getDepth() {
            return 0;
        }

        @Override
        protected List<AbstractNode> createChildren() {
            return new ArrayList<>();
        }

        @Override
        public int getNodeKind() {
            return 0;
        }

        @Override
        public int compareOrder(NodeInfo other) {
            return 0;
        }

        @Override
        public String getLocalPart() {
            return null;
        }

        @Override
        public NodeInfo getParent() {
            return null;
        }

        @Override
        public String getAttributeValue(String uri, String local) {
            return null;
        }

        @Override
        public NodeInfo getRoot() {
            return null;
        }

        @Override
        public boolean hasChildNodes() {
            return false;
        }
    }
}
