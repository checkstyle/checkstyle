////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.GenericTreeInfo;
import net.sf.saxon.om.NodeInfo;

public class AbstractNodeTest {

    @Test
    public void testCompareTo() {
        final MockNode node1 = new MockNode(10, 1);
        final MockNode node2 = new MockNode(8, 1);
        assertWithMessage("Invalid comparison result")
                .that(node1.compareOrder(node2))
                .isEqualTo(2);
        assertWithMessage("Invalid comparison result")
                .that(node2.compareOrder(node1))
                .isEqualTo(-2);
    }

    @Test
    public void testCompareToSameLines() {
        final MockNode node1 = new MockNode(10, 12);
        final MockNode node2 = new MockNode(10, 1);

        assertWithMessage("Invalid comparison result")
                .that(node1.compareOrder(node2))
                .isEqualTo(11);
        assertWithMessage("Invalid comparison result")
                .that(node2.compareOrder(node1))
                .isEqualTo(-11);
    }

    @Test
    public void testCompareToSameLinesAndColumns() {
        final MockNode node1 = new MockNode(10, 12, "abc");
        final MockNode node2 = new MockNode(10, 12, "def");

        assertWithMessage("Invalid comparison result")
                .that(node1.compareOrder(node2))
                .isEqualTo(-3);
        assertWithMessage("Invalid comparison result")
                .that(node2.compareOrder(node1))
                .isEqualTo(3);
    }

    @Test
    public void testCompareToIdentical() {
        final MockNode node1 = new MockNode(10, 12, "abc");
        final MockNode node2 = new MockNode(10, 12, "abc");

        assertWithMessage("Invalid comparison result")
                .that(node1.compareOrder(node2))
                .isEqualTo(0);
        assertWithMessage("Invalid comparison result")
                .that(node2.compareOrder(node1))
                .isEqualTo(0);
    }

    /* default */ private static class MockNode extends AbstractNode {

        private final int lineNumber;
        private final int columnNumber;
        private final String localPart;

        /* default */ MockNode(int lineNumber, int columnNumber) {
            this(lineNumber, columnNumber, "");
        }

        /* default */ MockNode(int lineNumber, int columnNumber, String localPart) {
            super(new GenericTreeInfo(Configuration.newConfiguration()));
            this.lineNumber = lineNumber;
            this.columnNumber = columnNumber;
            this.localPart = localPart;
        }

        @Override
        public int getLineNumber() {
            return lineNumber;
        }

        @Override
        public int getColumnNumber() {
            return columnNumber;
        }

        @Override
        public String getLocalPart() {
            return localPart;
        }

        @Override
        public int getTokenType() {
            throw createUnsupportedOperationException();
        }

        @Override
        public DetailAST getUnderlyingNode() {
            throw createUnsupportedOperationException();
        }

        @Override
        public int getNodeKind() {
            throw createUnsupportedOperationException();
        }

        @Override
        public String getStringValue() {
            throw createUnsupportedOperationException();
        }

        @Override
        public NodeInfo getParent() {
            throw createUnsupportedOperationException();
        }

        @Override
        public String getAttributeValue(String s, String s1) {
            throw createUnsupportedOperationException();
        }

        @Override
        public NodeInfo getRoot() {
            throw createUnsupportedOperationException();
        }

        private static UnsupportedOperationException createUnsupportedOperationException() {
            return new UnsupportedOperationException("Operation is not supported");
        }
    }
}
