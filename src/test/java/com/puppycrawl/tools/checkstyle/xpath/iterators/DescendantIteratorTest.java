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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.XpathIteratorUtil.findNode;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import net.sf.saxon.om.NodeInfo;

public class DescendantIteratorTest {

    @Test
    public void testIncludeSelf() {
        final NodeInfo startNode = findNode("CLASS_DEF");

        try (DescendantIterator iterator = new DescendantIterator(startNode,
                DescendantIterator.StartWith.CURRENT_NODE)) {
            assertWithMessage("Invalid node")
                    .that(iterator.next())
                    .isEqualTo(startNode);
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
    public void testWithoutSelf() {
        final NodeInfo startNode = findNode("CLASS_DEF");

        try (DescendantIterator iterator = new DescendantIterator(startNode,
                DescendantIterator.StartWith.CHILDREN)) {
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
        }
    }

    @Test
    public void testWithNull() {
        final NodeInfo startNode = findNode("CLASS_DEF");

        try (DescendantIterator iterator = new DescendantIterator(startNode, null)) {
            assertWithMessage("Node should be null")
                    .that(iterator.next())
                    .isNull();
        }
    }

    @Test
    public void testNoStackOverflowOnDeepTree() {
        final int depth = 10_000;
        final DetailAstImpl root = new DetailAstImpl();
        root.setType(TokenTypes.EXPR);
        root.setLineNo(1);
        root.setColumnNo(0);
        DetailAstImpl current = root;
        for (int index = 1; index < depth; index++) {
            final DetailAstImpl child = new DetailAstImpl();
            child.setType(TokenTypes.EXPR);
            child.setLineNo(index + 1);
            child.setColumnNo(0);
            current.addChild(child);
            current = child;
        }
        final RootNode rootNode = new RootNode(root);
        try (DescendantIterator iterator = new DescendantIterator(
                rootNode, DescendantIterator.StartWith.CHILDREN)) {
            int count = 0;
            while (iterator.next() != null) {
                count++;
            }
            assertWithMessage("Expected %s descendants", depth)
                    .that(count)
                    .isEqualTo(depth);
        }
    }
}
