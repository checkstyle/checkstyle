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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getXpathItems;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NamespaceBinding;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;

public class RootNodeTest extends AbstractPathTestSupport {

    private static RootNode rootNode;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @BeforeEach
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathMapperAst.java"));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testCompareOrder() {
        try {
            rootNode.compareOrder(null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testXpath() throws Exception {
        final String xpath = "/";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
            .that(nodes)
            .hasSize(1);
        final NodeInfo firstNode = nodes.get(0);
        assertWithMessage("Should return true, because selected node is RootNode")
                .that(firstNode instanceof RootNode)
                .isTrue();
        assertWithMessage("Result node should have same reference as expected")
            .that(rootNode)
            .isEqualTo(firstNode);
    }

    @Test
    public void testGetDepth() {
        assertWithMessage("Root node depth should be 0")
                .that(rootNode.getDepth())
                .isEqualTo(0);
    }

    @Test
    public void testGetTokenType() {
        assertWithMessage("Invalid token type")
            .that(rootNode.getTokenType())
            .isEqualTo(TokenTypes.COMPILATION_UNIT);
    }

    @Test
    public void testGetLineNumber() {
        assertWithMessage("Invalid line number")
            .that(rootNode.getLineNumber())
            .isEqualTo(1);
    }

    @Test
    public void testGetColumnNumber() {
        assertWithMessage("Invalid column number")
            .that(rootNode.getColumnNumber())
            .isEqualTo(0);
    }

    @Test
    public void testGetLocalPart() {
        assertWithMessage("Invalid local part")
            .that(rootNode.getLocalPart())
            .isEqualTo("ROOT");
    }

    @Test
    public void testIterate() {
        try (AxisIterator following = rootNode.iterateAxis(AxisInfo.FOLLOWING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(following)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator followingSibling = rootNode.iterateAxis(AxisInfo.FOLLOWING_SIBLING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(followingSibling)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator preceding = rootNode.iterateAxis(AxisInfo.PRECEDING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(preceding)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator precedingSibling = rootNode.iterateAxis(AxisInfo.PRECEDING_SIBLING)) {
            assertWithMessage("Result iterator does not match expected")
                .that(precedingSibling)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator parent = rootNode.iterateAxis(AxisInfo.PARENT)) {
            assertWithMessage("Result iterator does not match expected")
                .that(parent)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator parentNull = rootNode.iterateAxis(AxisInfo.PARENT, null)) {
            assertWithMessage("Result iterator does not match expected")
                .that(parentNull)
                .isEqualTo(EmptyIterator.ofNodes());
        }
    }

    @Test
    public void testRootWithNullDetailAst() {
        final RootNode emptyRootNode = new RootNode(null);
        assertWithMessage("Empty node should not have children")
                .that(emptyRootNode.hasChildNodes())
                .isFalse();

        try (AxisIterator iterator = emptyRootNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertWithMessage("Result iterator does not match expected")
                .that(iterator)
                .isEqualTo(EmptyIterator.ofNodes());
        }
        try (AxisIterator iterator = emptyRootNode.iterateAxis(AxisInfo.CHILD)) {
            assertWithMessage("Result iterator does not match expected")
                .that(iterator)
                .isEqualTo(EmptyIterator.ofNodes());
        }
    }

    @Test
    public void testGetStringValue() {
        try {
            rootNode.getStringValue();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetAttributeValue() {
        try {
            rootNode.getAttributeValue("", "");
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetDeclaredNamespaces() {
        final NamespaceBinding[] namespaceBindings = {new NamespaceBinding("prefix", "uri")};
        try {
            rootNode.getDeclaredNamespaces(namespaceBindings);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testIsId() {
        try {
            rootNode.isId();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testIsIdref() {
        try {
            rootNode.isIdref();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testIsNilled() {
        try {
            rootNode.isNilled();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testIsStreamed() {
        try {
            rootNode.isStreamed();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetConfiguration() {
        try {
            rootNode.getConfiguration();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testSetSystemId() {
        try {
            rootNode.setSystemId("1");
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetSystemId() {
        try {
            rootNode.getSystemId();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetPublicId() {
        try {
            rootNode.getPublicId();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testBaseUri() {
        try {
            rootNode.getBaseURI();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testSaveLocation() {
        try {
            rootNode.saveLocation();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetStringValueCs() {
        try {
            rootNode.getUnicodeStringValue();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testFingerprint() {
        try {
            rootNode.getFingerprint();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetDisplayName() {
        try {
            rootNode.getDisplayName();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetPrefix() {
        try {
            rootNode.getPrefix();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGetSchemaType() {
        try {
            rootNode.getSchemaType();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testAtomize() {
        try {
            rootNode.atomize();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testGenerateId() {
        try {
            rootNode.generateId(null);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testCopy() {
        try {
            rootNode.copy(null, -1, null);
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
            rootNode.getAllNamespaces();
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Operation is not supported");
        }
    }

    @Test
    public void testSameNodeInfo() {
        assertWithMessage("Should return true, because object is being compared to itself")
                .that(rootNode.isSameNodeInfo(rootNode))
                .isTrue();
        assertWithMessage("Should return false, because object does not equal null")
                .that(rootNode.isSameNodeInfo(null))
                .isFalse();
    }
}
