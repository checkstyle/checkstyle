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

import static com.puppycrawl.tools.checkstyle.internal.utils.XpathUtil.getXpathItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.Item;
import net.sf.saxon.om.NamespaceBinding;
import net.sf.saxon.tree.iter.EmptyIterator;

public class RootNodeTest extends AbstractPathTestSupport {

    private static RootNode rootNode;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @Before
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathMapperAst.java"));
        final DetailAST rootAst = TestUtil.parseFile(file);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testXpath() throws Exception {
        final String xpath = "/";
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        assertTrue("Should return true, because selected node is RootNode",
                nodes.get(0) instanceof RootNode);
        assertEquals("Result node should have same reference as expected",
                nodes.get(0),
                rootNode);
    }

    @Test
    public void testGetTokenType() {
        assertEquals("Invalid token type", TokenTypes.EOF, rootNode.getTokenType());
    }

    @Test
    public void testGetLineNumber() {
        assertEquals("Invalid line number", 1, rootNode.getLineNumber());
    }

    @Test
    public void testGetColumnNumber() {
        assertEquals("Invalid column number", 0, rootNode.getColumnNumber());
    }

    @Test
    public void testGetLocalPart() {
        assertEquals("Invalid local part", "ROOT", rootNode.getLocalPart());
    }

    @Test
    public void testGetStringValue() {
        assertEquals("Invalid string value", "ROOT", rootNode.getStringValue());
    }

    @Test
    public void testIterate() {
        assertEquals("Result iterator does not match expected",
                EmptyIterator.OfNodes.THE_INSTANCE, rootNode.iterateAxis(AxisInfo.PARENT));
        assertEquals("Result iterator does not match expected",
                EmptyIterator.OfNodes.THE_INSTANCE, rootNode.iterateAxis(AxisInfo.PARENT,
                        null));
    }

    @Test
    public void testIterateWithoutArgument() {
        try {
            rootNode.iterate();
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertEquals("Invalid exception message",
                    "Operation is not supported",
                    ex.getMessage());
        }
    }

    @Test
    public void testGetAttributeValue() {
        try {
            rootNode.getAttributeValue("", "");
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
    public void testGetDeclaredNamespaces() {
        try {
            rootNode.getDeclaredNamespaces(
                    new NamespaceBinding[] {new NamespaceBinding("prefix", "uri")});
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
    public void testGetTreeInfo() {
        try {
            rootNode.getTreeInfo();
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
    public void testIsId() {
        try {
            rootNode.isId();
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
    public void testIsIdref() {
        try {
            rootNode.isIdref();
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
    public void testIsNilled() {
        try {
            rootNode.isNilled();
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
    public void testIsStreamed() {
        try {
            rootNode.isStreamed();
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
    public void testGetConfiguration() {
        try {
            rootNode.getConfiguration();
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
    public void testSetSystemId() {
        try {
            rootNode.setSystemId("1");
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
    public void testGetSystemId() {
        try {
            rootNode.getSystemId();
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
    public void testGetPublicId() {
        try {
            rootNode.getPublicId();
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
    public void testBaseUri() {
        try {
            rootNode.getBaseURI();
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
    public void testSaveLocation() {
        try {
            rootNode.saveLocation();
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
    public void testComparePosition() {
        try {
            rootNode.comparePosition(null);
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
    public void testHead() {
        try {
            rootNode.head();
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
    public void testGetStringValueCs() {
        try {
            rootNode.getStringValueCS();
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
    public void testFingerprint() {
        try {
            rootNode.getFingerprint();
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
    public void testGetDisplayName() {
        try {
            rootNode.getDisplayName();
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
    public void testGetPrefix() {
        try {
            rootNode.getPrefix();
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
    public void testGetSchemaType() {
        try {
            rootNode.getSchemaType();
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
    public void testAtomize() {
        try {
            rootNode.atomize();
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
    public void testGenerateId() {
        try {
            rootNode.generateId(null);
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
    public void testCopy() {
        try {
            rootNode.copy(null, -1, null);
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
    public void testSameNodeInfo() {
        assertTrue("Should return true, because object is being compared to itself",
                rootNode.isSameNodeInfo(rootNode));
        assertFalse("Should return false, because object does not equal null",
                rootNode.isSameNodeInfo(null));
    }
}
