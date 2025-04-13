///
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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.tree.iter.AxisIterator;

public class AttributeNodeTest {

    private static AttributeNode attributeNode;

    @BeforeEach
    public void init() {
        attributeNode = new AttributeNode("name", "value");
    }

    @Test
    public void testGetNamespaceUri() {
        assertWithMessage("Attribute node should have default namespace URI")
            .that(attributeNode.getNamespaceUri())
            .isEqualTo(NamespaceUri.NULL);
    }

    @Test
    public void testGetUri() {
        assertWithMessage("Attribute node should have blank URI")
            .that(attributeNode.getURI())
            .isEqualTo("");
    }

    @Test
    public void testCompareOrder() {
        try {
            attributeNode.compareOrder(null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetDepth() {
        final UnsupportedOperationException exception =
            getExpectedThrowable(UnsupportedOperationException.class, attributeNode::getDepth);
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
                .isEqualTo("Operation is not supported");
    }

    @Test
    public void testHasChildNodes() {
        assertWithMessage("Attribute node shouldn't have children")
            .that(attributeNode.hasChildNodes())
            .isFalse();
    }

    @Test
    public void testGetAttributeValue() {
        try {
            attributeNode.getAttributeValue("", "");
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetChildren() {
        final UnsupportedOperationException exception =
            getExpectedThrowable(UnsupportedOperationException.class, attributeNode::getChildren);
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
            .isEqualTo("Operation is not supported");
    }

    @Test
    public void testGetParent() {
        try {
            attributeNode.getParent();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetRoot() {
        try {
            attributeNode.getRoot();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetStringValue() {
        assertWithMessage("Invalid string value")
            .that(attributeNode.getStringValue())
            .isEqualTo("value");
    }

    @Test
    public void testIterate() {
        try (AxisIterator ignored = attributeNode.iterateAxis(AxisInfo.SELF)) {
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetLineNumber() {
        try {
            attributeNode.getLineNumber();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetColumnNumber() {
        try {
            attributeNode.getColumnNumber();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetTokenType() {
        try {
            attributeNode.getTokenType();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetUnderlyingNode() {
        try {
            attributeNode.getUnderlyingNode();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetAllNamespaces() {
        try {
            attributeNode.getAllNamespaces();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }
}
