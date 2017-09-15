////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import net.sf.saxon.om.AxisInfo;

public class AttributeNodeTest {

    private static AttributeNode attributeNode;

    @Before
    public void init() {
        attributeNode = new AttributeNode("name", "value");
    }

    @Test
    public void testGetAttributeValue() {
        try {
            attributeNode.getAttributeValue("", "");
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetParent() {
        try {
            attributeNode.getParent();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetRoot() {
        try {
            attributeNode.getRoot();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetStringValue() {
        assertEquals("Invalid string value", "value", attributeNode.getStringValue());
    }

    @Test
    public void testIterate() {
        try {
            attributeNode.iterateAxis(AxisInfo.SELF);
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetLineNumber() {
        try {
            attributeNode.getLineNumber();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetColumnNumber() {
        try {
            attributeNode.getColumnNumber();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetTokenType() {
        try {
            attributeNode.getTokenType();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }

    @Test
    public void testGetUnderlyingNode() {
        try {
            attributeNode.getUnderlyingNode();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals(
                "Invalid exception message",
                "Operation is not supported",
                ex.getMessage());
        }
    }
}
