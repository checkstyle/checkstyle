///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NamespaceUri;
import net.sf.saxon.tree.iter.AxisIterator;

class AttributeNodeTest {

    private static AttributeNode attributeNode;

    @BeforeEach
    void init() {
        attributeNode = new AttributeNode("name", "value");
    }

    @Test
    void getNamespaceUri() {
        assertWithMessage("Attribute node should have default namespace URI")
            .that(attributeNode.getNamespaceUri())
            .isEqualTo(NamespaceUri.NULL);
    }

    @Test
    void getUri() {
        assertWithMessage("Attribute node should have blank URI")
            .that(attributeNode.getURI())
            .isEqualTo("");
    }

    @Test
    void compareOrder() {
        try {
            attributeNode.compareOrder(null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getDepth() {
        final UnsupportedOperationException exception =
            getExpectedThrowable(UnsupportedOperationException.class, attributeNode::getDepth);
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
                .isEqualTo("Operation is not supported");
    }

    @Test
    void hasChildNodes() {
        assertWithMessage("Attribute node shouldn't have children")
            .that(attributeNode.hasChildNodes())
            .isFalse();
    }

    @Test
    void getAttributeValue() {
        try {
            attributeNode.getAttributeValue("", "");
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getChildren() {
        final UnsupportedOperationException exception =
            getExpectedThrowable(UnsupportedOperationException.class, attributeNode::getChildren);
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
            .isEqualTo("Operation is not supported");
    }

    @Test
    void getParent() {
        try {
            attributeNode.getParent();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getRoot() {
        try {
            attributeNode.getRoot();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getStringValue() {
        assertWithMessage("Invalid string value")
            .that(attributeNode.getStringValue())
            .isEqualTo("value");
    }

    @Test
    void iterate() {
        try (AxisIterator ignored = attributeNode.iterateAxis(AxisInfo.SELF)) {
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getLineNumber() {
        try {
            attributeNode.getLineNumber();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getColumnNumber() {
        try {
            attributeNode.getColumnNumber();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getTokenType() {
        try {
            attributeNode.getTokenType();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getUnderlyingNode() {
        try {
            attributeNode.getUnderlyingNode();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void getAllNamespaces() {
        try {
            attributeNode.getAllNamespaces();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }
}
